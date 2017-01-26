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
        $this->load->library('send_sms');
    }

    public function create_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('field_id', 'Field id', 'required');
        $this->form_validation->set_rules('date', 'Date', 'required');
        $this->form_validation->set_rules('start', 'Start time', 'required|trim|min_length[7]|max_length[8]|callback_validate_time');
        $this->form_validation->set_rules('duration', 'Duration', 'required|is_natural_no_zero');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $field_id = $this->input->post('field_id');
            $this->user_permissions->is_player($this->current_user);
            $date = $this->input->post('date');
            if (strtotime($date) < strtotime(date('Y-m-d')))
                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid date"));
            $start = $this->input->post('start');
            $duration = $this->input->post('duration');
            $notes = $this->input->post('notes');
            $manually = false;
            $player_id = $this->current_user->player_id;
            $user_id = null;
            if (strlen($start) == 7)
                $start = "0" . $start;
            $booking = $this->booking_service
                    ->create(
                    $field_id, $player_id, $date, $start, $duration, $notes, $user_id, $manually, $this->response->lang
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
        $this->form_validation->set_rules('player_name', 'Player name', 'required');
        $this->form_validation->set_rules('player_phone', 'Player phone', '');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $field_id = $this->input->post('field_id');
            $this->user_permissions->is_company($this->current_user);
            $this->user_permissions->management_permission($this->current_user, $this->current_user->company_id);
            $date = $this->input->post('date');
            if (strtotime($date) < strtotime(date('Y-m-d')))
                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid date"));
            $start = $this->input->post('start');
            $duration = $this->input->post('duration');
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
                    $field_id, $player->player_id, $date, $start, $duration, $notes, $user_id, $manually, $this->response->lang
            );
            $sms = $this->post('sms_option') ? true : false;
//            if ($sms == true)
//                $this->send_sms->send_sms($phone, "");
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
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $this->user_permissions->update_booking_permission($this->current_user);
            $booking_id = $this->input->post('booking_id');
            $field_id = $this->input->post('field_id');
            $date = $this->input->post('date');
            if (strtotime($date) < strtotime(date('Y-m-d')))
                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid date"));
            $start = $this->input->post('start');
            $duration = $this->input->post('duration');
            $notes = $this->input->post('notes');
            $user_id = $this->current_user->user_id;
            if (strlen($start) == 7)
                $start = "0" . $start;
            $booking = $this->booking_service
                    ->update(
                    $booking_id, $field_id, $date, $start, $duration, $notes, $user_id, $this->response->lang
            );
            $this->response(array(
                'status' => true,
                'data' => $booking,
                'message' => $this->lang->line('created')
                    )
            );
        }
    }

    public function delete_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('booking_id') . " " . $this->lang->line('required')));
        else {
            $this->booking_service->delete($this->get('booking_id'));
            $this->response(array('status' => true, 'data' => null, 'message' => $this->lang->line('deleted')));
        }
    }

    public function decline_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('booking_id') . " " . $this->lang->line('required')));
        else {
            if ($this->booking_service->decline($this->get('booking_id')))
                $this->response(array('status' => true, 'data' => null, 'message' => $this->lang->line('declined')));
            else {
                
            } $this->response(array('status' => false, 'data' => null, 'message' => "already declined"));
        }
    }

    public function approve_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('booking_id') . " " . $this->lang->line('required')));
        else {
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

    public function company_bookings_get() {
        $this->user_permissions->is_company($this->current_user);
        $bookings = $this->booking_service->company_bookings($this->current_user->company_id, $this->response->lang);
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
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    function bookings_management_get($operation = null) {
        $this->bookings_management_post($operation);
    }

    public function _callback_duration_render($value, $row) {

        return $value . " h";
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

}
