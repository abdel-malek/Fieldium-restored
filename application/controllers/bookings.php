
<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class bookings extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/booking_service");
        $this->load->model("Services/player_service");
        $this->load->model("Services/game_service");
        $this->load->model("Services/area_service");
        $this->load->model('Permissions/user_permissions');
    }

    public function set_up_get() {
        $this->load->database();
        $companies = $this->db->select('*')
                        ->from('company')
                        ->get()->result();

        foreach ($companies as $company) {
            $reference = 0;
            $bookings = $this->db->select('*')
                            ->from('booking')
                            ->where('booking.deleted', 0)
                            ->where('booking.state_id !=', 4)
                            ->where('booking.field_id IN (select field_id from `field` where field.company_id = ' . $company->company_id . ")")
                            ->get()->result();
            foreach ($bookings as $booking) {
                $reference++;
                $this->db->where('booking_id', $booking->booking_id)
                        ->update('booking', array('reference' => $reference));
            }
        }
    }

    public function calendar_get() {
        $this->user_permissions->is_company($this->current_user);
        $this->load->model("Services/field_service");
        $this->load->model("Services/company_service");
        $fields = $this->field_service->get_by_company($this->current_user->company_id, 0, 0, $this->response->lang);
        $times = $this->company_service->start_and_end_time($this->current_user->company_id);
        $this->load->view('template.php', array(
            'view' => 'admin_dashboard/calendar',
            'fields' => $fields,
            'company_id' => $this->current_user->company_id,
            'times' => $times
//                'js_files' => $output->js_files,
//                'css_files' => $output->css_files
                )
        );
    }

    public function create_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('field_id', 'Field id', 'required');
        $this->form_validation->set_rules('date', 'Date', 'required');
        $this->form_validation->set_rules('start', 'Start time', 'required|trim|min_length[7]|max_length[8]|callback_validate_time');
        $this->form_validation->set_rules('duration', 'Duration', 'required|is_natural_no_zero');
        $this->form_validation->set_rules('game_type', 'game type', 'required|is_natural_no_zero');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $field_id = $this->input->post('field_id');
            $this->user_permissions->is_player($this->current_user);
            $date = $this->input->post('date');
            $start = $this->input->post('start');
            $start = date("H:i:s", strtotime($start));
            $duration = $this->input->post('duration');
            $endtime = strtotime($start) + doubleval($duration) * 60;
            $end = strftime('%H:%M:%S', $endtime);
            if (strtotime($date) < strtotime(date('Y-m-d')))
                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid date"));
            else if (strtotime($date) == strtotime(date('Y-m-d')) && strtotime($start) < strtotime(date('H:i:s')))
                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid time"));
            else if (strtotime($start) > strtotime($end) && $end != "00:00:00")
                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid start and end time."));

            $game_type = $this->input->post('game_type');
            $game = $this->game_service->get($game_type);
            if ($duration < $game->minimum_duration)
                $this->response(array('status' => false, 'data' => null, 'message' => "The duration must be minimum " . $game->minimum_duration . " mins."));
            $notes = $this->input->post('notes');
            $manually = false;
            $player_id = $this->current_user->player_id;
            $user_id = null;
            if (strlen($start) == 7)
                $start = "0" . $start;
            $booking = $this->booking_service
                    ->create(
                    $field_id, $player_id, $date, $start, $duration, $game_type, $notes, $user_id, $manually, $this->response->lang, $this->input->post('voucher')
            );
            $this->response(array(
                'status' => true,
                'data' => $booking,
                'message' => $this->lang->line('created')
                    )
            );
        }
    }

    public function create_manually_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('field_id', 'Field id', 'required');
        $this->form_validation->set_rules('date', 'Date', 'required');
        $this->form_validation->set_rules('start', 'Start time', 'required|trim|min_length[7]|max_length[8]|callback_validate_time');
        $this->form_validation->set_rules('duration', 'Duration', 'required|is_natural_no_zero');
        $this->form_validation->set_rules('game_type', 'game type', 'required|is_natural_no_zero');
        $this->form_validation->set_rules('player_name', 'Player name', 'required');
        $this->form_validation->set_rules('player_phone', 'Player phone', 'required|numeric');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $field_id = $this->input->post('field_id');
            $this->user_permissions->is_company($this->current_user);
            $this->user_permissions->management_permission($this->current_user, $this->current_user->company_id);
            $date = $this->input->post('date');
            $start = $this->input->post('start');
            $start = date("H:i:s", strtotime($start));
            $duration = $this->input->post('duration');
            $endtime = strtotime($start) + doubleval($duration) * 60;
            $end = strftime('%H:%M:%S', $endtime);

            if (strtotime($date) < strtotime(date('Y-m-d')))
                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid date"));
            else if (strtotime($date) == strtotime(date('Y-m-d')) && strtotime($start) < strtotime(date('H:i:s')))
                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid time"));
            else if (strtotime($start) > strtotime($end) && $end != "00:00:00")
                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid start and end time."));
            $game_type = $this->input->post('game_type');
            $game = $this->game_service->get($game_type);
            if ($duration < $game->minimum_duration)
                $this->response(array('status' => false, 'data' => null, 'message' => "The duration must be minimum " . $game->minimum_duration . " mins."));
            $notes = $this->input->post('notes');
            $name = $this->input->post('player_name');
            $phone = $this->input->post('player_phone');
            $manually = true;
            $player = $this->player_service->create($name, $phone);
            $user_id = $this->current_user->user_id;
            if (strlen($start) == 7)
                $start = "0" . $start;
            $booking = $this->booking_service
                    ->create(
                    $field_id, $player->player_id, $date, $start, $duration, $game_type, $notes, $user_id, $manually, $this->response->lang
            );
            $sms = $this->post('sms_option') == 'true' || $this->post('sms_option') == 1 ? true : false;
            if ($sms == true) {
                $this->load->library('send_sms');
                $msg = "Dear Player,%0AYour Field is booked.%0A"
                        . $booking->company_name . "%0A"
                        . "Field: " . $booking->field_name . "%0A"
                        . "On: " . date("D, d/m/Y", strtotime($booking->date)) . "%0A"
                        . "At: " . date('h:i A', strtotime($booking->start)) . "%0A"
                        . "For: " . $booking->duration / 60 . " Hour%0A"
                        . "Enjoy the Game,%0A"
                        . "Fieldium";
                $this->send_sms->send_sms($phone, $msg);
            }
            $this->response(array('status' => true, 'data' => $booking, 'message' => $this->lang->line('created')));
        }
    }

    public function update_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('booking_id', 'Booking id', 'required');
        $this->form_validation->set_rules('field_id', 'Field id', 'required');
        $this->form_validation->set_rules('date', 'Date', 'required');
        $this->form_validation->set_rules('start', 'Start time', 'required|trim|min_length[7]|max_length[8]|callback_validate_time');
        $this->form_validation->set_rules('duration', 'Duration', 'required|is_natural_no_zero');
        $this->form_validation->set_rules('game_type', 'game type', 'required|is_natural_no_zero');
        $this->form_validation->set_rules('player_name', 'Player name', 'required');
        $this->form_validation->set_rules('player_phone', 'Player phone', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $booking_id = $this->input->post('booking_id');
            $field_id = $this->input->post('field_id');
            $this->user_permissions->is_company($this->current_user);
            $this->user_permissions->management_permission($this->current_user, $this->current_user->company_id);
            $date = $this->input->post('date');
            $start = $this->input->post('start');
            $start = date("H:i:s", strtotime($start));
            $duration = $this->input->post('duration');
            $endtime = strtotime($start) + doubleval($duration) * 60;
            $end = strftime('%H:%M:%S', $endtime);

            if (strtotime($date) < strtotime(date('Y-m-d')))
                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid date"));
            else if (strtotime($date) == strtotime(date('Y-m-d')) && strtotime($start) < strtotime(date('H:i:s')))
                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid time"));
            else if (strtotime($start) > strtotime($end) && $end != "00:00:00")
                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid start and end time."));
            $game_type = $this->input->post('game_type');
            $game = $this->game_service->get($game_type);
            if ($duration < $game->minimum_duration)
                $this->response(array('status' => false, 'data' => null, 'message' => "The duration must be minimum " . $game->minimum_duration . " mins."));
            $notes = $this->input->post('notes');
            $name = $this->input->post('player_name');
            $phone = $this->input->post('player_phone');
            $manually = true;
            $player = $this->player_service->create($name, $phone);
            $user_id = $this->current_user->user_id;
            if (strlen($start) == 7)
                $start = "0" . $start;
            $booking = $this->booking_service
                    ->update(
                    $booking_id, $field_id, $player->player_id, $date, $start, $duration, $game_type, $notes, $user_id, $manually, $this->response->lang
            );
            $sms = $this->post('sms_option') == 'true' || $this->post('sms_option') == 1 ? true : false;
            if ($sms == true) {
                $this->load->library('send_sms');
                $msg = "Dear Player,%0AYour booking number." . $booking_id . " is updated.%0A"
                        . $booking->company_name . "%0A"
                        . "Field: " . $booking->field_name . "%0A"
                        . "On: " . date("D, d/m/Y", strtotime($booking->date)) . "%0A"
                        . "At: " . date('h:i A', strtotime($booking->start)) . "%0A"
                        . "For: " . $booking->duration / 60 . " Hour%0A"
                        . "Enjoy the Game,%0A"
                        . "Fieldium";
                $this->send_sms->send_sms($phone, $msg);
            }
            $this->response(array('status' => true, 'data' => $booking, 'message' => $this->lang->line('updated')));
        }
    }

    public function cancel_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('booking_id') . " " . $this->lang->line('required')));
        else {
            try {
                $this->user_permissions->company_booking($this->current_user, $this->get('booking_id'));
            } catch (Permission_Denied_Exception $e) {
                $this->user_permissions->support_permission($this->current_user);
            }
            $msg = $this->get('reason_id');
            if ($msg != 0) {
                $messages = get_cancellation_reasons();
                if (isset($messages[$msg]))
                    $msg = $messages[$msg];
                else
                    $this->response(array('status' => false, 'data' => null, 'message' => "Error in reason id."));
            } else {
                $msg = $this->get('note');
            }
            if ($msg == "" && $msg == 0)
                $this->response(array('status' => false, 'data' => null, 'message' => "The reason is required."));

            $booking = $this->booking_service->cancel($this->get('booking_id'), $msg);
            $this->response(array('status' => true, 'data' => $booking, 'message' => $this->lang->line('cancelled')));
        }
    }

    public function get_cancellation_reasons_get() {
        $messages = get_cancellation_reasons($this->response->lang);
        $result = array();
        foreach ($messages as $key => $value) {
            $result[] = array('id' => $key, 'message' => $value);
        }
        $this->response(array('status' => true, 'data' => $result, "message" => ""));
    }

    public function decline_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('booking_id') . " " . $this->lang->line('required')));
        else {
            try {
                $this->user_permissions->company_booking($this->current_user, $this->get('booking_id'));
            } catch (Permission_Denied_Exception $e) {
                $this->user_permissions->support_permission($this->current_user);
            }
            if ($this->booking_service->decline($this->get('booking_id')))
                $this->response(array('status' => true, 'data' => null, 'message' => $this->lang->line('declined')));
            else {
                
            } $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line("already declined")));
        }
    }

    public function approve_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('booking_id') . " " . $this->lang->line('required')));
        else {
            try {
                $this->user_permissions->company_booking($this->current_user, $this->get('booking_id'));
            } catch (Permission_Denied_Exception $e) {
                $this->user_permissions->support_permission($this->current_user);
            }
            $booking = $this->booking_service->approve($this->get('booking_id'), $this->response->lang);
            $this->response(array('status' => true, 'data' => $booking, 'message' => $this->lang->line('approved')));
        }
    }

    public function show_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('booking_id') . " " . $this->lang->line('required')));
        else {
            $booking = $this->booking_service->get($this->get('booking_id'), $this->response->lang);
            $this->response(array('status' => true, 'data' => $booking, 'message' => ""));
        }
    }

    public function my_bookings_get() {
        $this->user_permissions->is_player($this->current_user);
        $bookings = $this->booking_service->get_my_bookings($this->current_user->player_id, $this->response->lang);
        $this->response(array('status' => true, 'data' => $bookings, 'message' => ""));
    }

    public function by_date_get() {
//        $this->user_permissions->is_player($this->current_user);
        $bookings = array();
        $bookings = $this->booking_service->field_bookings_by_date($this->get('field_id'), $this->get('date'), $this->response->lang, $bookings, null);
        $this->response(array('status' => true, 'data' => $bookings, 'message' => ""));
    }

    public function company_bookings_get() {
        try {
            $this->user_permissions->is_company($this->current_user);
        } catch (Permission_Denied_Exception $e) {
            $this->user_permissions->support_permission($this->current_user);
        }
        if ($this->current_user->role_id == ROLE::SUPPORT)
            $company_id = $this->input->get('company_id');
        else
            $company_id = $this->current_user->company_id;
        $bookings = $this->booking_service->company_bookings($company_id, $this->response->lang);
        $this->response(array('status' => true, 'data' => $bookings, 'message' => ""));
    }

    public function pending_bookings_get() {
        $this->user_permissions->support_permission($this->current_user);
        $bookings = $this->booking_service->pending_bookings();
        $this->response(array('status' => true, 'data' => $bookings, 'message' => ""));
    }

    public function validate_time($str) {
        if (strlen($str) == 7)
            $str = "0" . $str;
        $this->form_validation->set_message('validate_time', $str . ' is not a valid time. Ex:( 10:00:00 )');
        if (strrchr($str, ":")) {
            list($hh, $mm, $ss) = explode(':', $str);
            if (!is_numeric($hh) || !is_numeric($mm) || !is_numeric($ss)) {
                return FALSE;
            } elseif ((int) $hh > 24 || (int) $mm > 59 || (int) $ss > 59) {
                return FALSE;
            } elseif (mktime((int) $hh, (int) $mm, (int) $ss) === FALSE) {
                return FALSE;
            }
            return TRUE;
        } else {
            return FALSE;
        }
    }

    function bookings_management_post($operation = null) {

        $this->user_permissions->support_permission($this->current_user);
        $this->load->library('grocery_CRUD');
        try {
            $crud = new grocery_CRUD();

            if ($this->session->userdata('lang') == 'arabic') {
                $crud->set_language('arabic');
                 $crud->set_theme('datatables')
                    ->set_table('booking')
                    ->set_subject('الحجوز')
                    ->where('booking.deleted', 0)
                    ->columns('booking_id', 'field_id', 'player_id', 'state_id', 'creation_date', 'date', 'start', 'duration', 'total')
                    ->order_by('booking_id')
                    ->set_relation('field_id', 'field', 'en_name', array('deleted' => 0))
                    ->set_relation('player_id', 'player', '{name} <br> {phone}')
                    ->set_relation('state_id', 'state', 'en_name')
                    ->edit_fields('state_id', 'date', 'start', 'duration', 'notes')
                         ->display_as('field_id', 'الحقل')
                    ->display_as('player_id', 'الاعب')
                         ->display_as('start', 'الوقت')
                         ->display_as('creation_date', 'وقت الحجز')
                         ->display_as('date', 'وقت المبارة')
                         ->display_as('duration', 'المدة الزمني')
                          ->display_as('notes', 'الملاحظات')
                         ->display_as('total', 'المجموع')
                    ->display_as('state_id', 'الحالة')
                    ->display_as('booking_id', 'الرقم تسلسلي')
                    ->field_type('start', 'time')
                    ->callback_column('duration', array($this, '_callback_duration_render'))
                    ->callback_column('total', array($this, '_callback_total_render'))
                    ->required_fields('date', 'start', 'duration', 'state_id')
                    ->callback_delete(array($this, 'delete_booking'))
                    ->unset_export()
                    ->unset_add()
                    ->unset_read()
                    ->unset_print();
            $output = $crud->render();
            $this->load->model("Services/booking_service");
            $this->load->view('template.php', array(
                'view' => 'bookings_management',
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files
                    )
            );
            } else {
                $crud->set_language('english');
                 $crud->set_theme('datatables')
                    ->set_table('booking')
                    ->set_subject('booking')
                    ->where('booking.deleted', 0)
                    ->columns('booking_id', 'field_id', 'player_id', 'state_id', 'creation_date', 'date', 'start', 'duration', 'total')
                    ->order_by('booking_id')
                    ->set_relation('field_id', 'field', 'en_name', array('deleted' => 0))
                    ->set_relation('player_id', 'player', '{name} <br> {phone}')
                    ->set_relation('state_id', 'state', 'en_name')
                    ->edit_fields('state_id', 'date', 'start', 'duration', 'notes')
                    ->display_as('field_id', 'Field')
                    ->display_as('player_id', 'Player')
                    ->display_as('state_id', 'State')
                    ->display_as('booking_id', 'ID')
                    ->field_type('start', 'time')
                    ->callback_column('duration', array($this, '_callback_duration_render'))
                    ->callback_column('total', array($this, '_callback_total_render'))
                    ->required_fields('date', 'start', 'duration', 'state_id')
                    ->callback_delete(array($this, 'delete_booking'))
                    ->unset_export()
                    ->unset_add()
                    ->unset_read()
                    ->unset_print();
            $output = $crud->render();
            $this->load->model("Services/booking_service");
            $this->load->view('template.php', array(
                'view' => 'bookings_management',
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files
                    )
            );
            }

           
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    function bookings_management_get($operation = null) {
        $this->bookings_management_post($operation);
    }

    public function _callback_duration_render($value, $row) {

        return $value . " Mins";
    }

    public function _callback_total_render($value, $row) {

        return $value . " AED";
    }

    public function delete_booking($primary_key) {
        $this->user_permissions->support_permission($this->current_user);
        $this->booking_service->delete($primary_key);
        return true;
    }

    public function upcoming_booking_get() {
        $booking = $this->booking_service->upcoming_booking($this->current_user->player_id, $this->response->lang);
        $this->response(array('status' => true, 'data' => $booking, 'message' => ""));
    }

    public function upcoming_and_last_bookings_get() {
        $booking = $this->booking_service->upcoming_booking($this->current_user->player_id, $this->response->lang);
        $bookings = $this->booking_service->last_bookings($this->current_user->player_id, $this->response->lang);
        $this->response(array('status' => true, 'data' =>
            array(
                'last_bookings' => $bookings,
                'upcoming_booking' => $booking,
                'areas' => $this->area_service->get_all($this->response->lang),
                'games' => $this->game_service->get_all($this->response->lang)
            )
            , 'message' => ""));
    }

    function company_pending_bookings_post($operation = null) {
        $this->user_permissions->is_company($this->current_user);
        $this->load->library('grocery_CRUD');
        try {
            $crud = new grocery_CRUD();
            $this->load->model("Services/field_service");
            $fields = $this->field_service->get_by_company($this->current_user->company_id, 0, 0, $this->response->lang);
            $fields_ids = array();
            foreach ($fields as $field) {
                $fields_ids[] = $field->field_id;
            }
            $fields_ids = implode("','", $fields_ids);
            $crud->set_theme('datatables')
                    ->set_table('booking')
                    ->set_subject('booking')
                    ->where('booking.deleted', 0)
                    ->where('booking.state_id', BOOKING_STATE::PENDING)
                    ->where("booking.field_id IN ('" . $fields_ids . "')")
                    ->columns('booking_id', 'field_id', 'player_id', 'state_id', 'creation_date', 'date', 'start', 'duration', 'total')
                    ->order_by('booking_id')
                    ->set_relation('field_id', 'field', 'en_name', array('deleted' => 0))
                    ->set_relation('player_id', 'player', '{name} <br> {phone}')
                    ->set_relation('state_id', 'state', 'en_name')
                    ->set_relation('field_id', 'field', 'en_name', array('deleted' => 0))
                    ->edit_fields('state_id', 'date', 'start', 'duration', 'notes')
                    ->display_as('field_id', 'Field')
                    ->display_as('player_id', 'Player')
                    ->display_as('state_id', 'State')
                    ->display_as('booking_id', 'ID')
                    ->field_type('start', 'time')
                    ->callback_column('duration', array($this, '_callback_duration_render'))
                    ->callback_column('total', array($this, '_callback_total_render'))
                    ->required_fields('date', 'start', 'duration', 'state_id')
                    ->callback_delete(array($this, 'delete_booking'))
                    ->unset_export()
                    ->unset_add()
                    ->unset_edit()
                    ->unset_delete()
                    ->unset_read()
                    ->unset_print();
            $output = $crud->render();
            $this->load->model("Services/booking_service");
            $this->load->view('template.php', array(
                'view' => 'bookings_management',
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files
                    )
            );
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    function company_pending_bookings_get($operation = null) {
        try {
            $this->user_permissions->is_company($this->current_user);
        } catch (Permission_Denied_Exception $e) {
            $this->user_permissions->support_permission($this->current_user);
        }
        if ($this->current_user->role_id == ROLE::SUPPORT)
            $company_id = $this->input->get('company_id');
        else
            $company_id = $this->current_user->company_id;
        $bookings = $this->booking_service->company_pending_bookings($company_id, $this->response->lang);
        $this->response(array('status' => true, 'data' => $bookings, 'message' => ""));
    }

}
