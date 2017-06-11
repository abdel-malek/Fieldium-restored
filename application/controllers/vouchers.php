<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class vouchers extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/voucher_service");
        $this->load->model('Permissions/user_permissions');
    }

    public function check_validity_get() {
        $this->user_permissions->is_player($this->current_user);
        if (!$this->get('voucher'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('voucher') . " " . $this->lang->line('required')));
        else {
            $voucher = $this->voucher_service->check_validity(
                    $this->get('voucher'), $this->get('field_id'), $this->current_user->player_id, $this->get('date'), $this->get('start'), $this->get('duration')
                    );
            $this->response(array(
                'status' => true,
                'data' => $voucher,
                'message' => $this->lang->line('valid_voucher')
                    )
            );
        }
    }

    public function generate_voucher_get() {
        $voucher = $this->voucher_service->generate_voucher();
        $this->response(array(
            'status' => true,
            'data' => $voucher,
            'message' => ''
                )
        );
    }

    public function create_post() {
//        $this->user_permissions->support_permission($this->current_user);
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('type', 'Type', 'required|callback_valid_voucher_type');
        $this->form_validation->set_rules('voucher', 'voucher code', 'required|is_unique[voucher.voucher]');
        $this->form_validation->set_rules('value', 'value', 'required|is_natural_no_zero');
        $this->form_validation->set_rules('start_date', 'start_date', 'callback_check_date_format');
        $this->form_validation->set_rules('expiry_date', 'expiry_date', 'callback_check_date_format');
        $this->form_validation->set_rules('from_hour', 'from_hour', 'callback_validate_time');
        $this->form_validation->set_rules('to_hour', 'to_hour', 'callback_validate_time');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $data = array(
                'type' => $this->input->post('type'),
                'voucher' => $this->input->post('voucher'),
                'value' => $this->input->post('value'),
                'creator' => $this->current_user->player_id,
                'expiry_date' => date('Y-m-d', strtotime($this->input->post('expiry_date'))),
                'from_hour' => $this->input->post('from_hour') ? date('H:i:s', strtotime($this->input->post('from_hour'))) : null,
                'to_hour' => $this->input->post('to_hour') ? date('H:i:s', strtotime($this->input->post('to_hour'))) : null,
                'description_en' => $this->input->post('description_en'),
                'description_ar' => $this->input->post('description_ar')
            );
            if ($this->input->post('start_date') != "" && $this->input->post('start_date') != null)
                if (strtotime($data['expiry_date']) < $this->input->post('start_date'))
                    $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('voucher') . " " . $this->lang->line('required')));
                else
                    $data['start_date'] = $this->input->post('start_date');
            $users = array();
            if ($this->input->post('all_users') == 1)
                $data['public_user'] = 1;
            else {
                $data['public_user'] = 0;
                $users = $this->input->post('users');
                if (!is_array($users) || count($users) == 0)
                    $this->response(array('status' => false, 'data' => null, 'message' => "Select at least one user"));
            }
            $phones = array();
            if ($this->input->post('phones'))
                $phones = $this->input->post('phones');
            $fields = array();
            if ($this->input->post('all_fields') == 1)
                $data['public_field'] = 1;
            else {
                $data['public_field'] = 0;
                $fields = $this->input->post('fields');
                if (!is_array($fields) || count($fields) == 0)
                    $this->response(array('status' => false, 'data' => null, 'message' => "Select at least one field"));
            }
            $voucher = $this->voucher_service
                    ->create(
                    $data, $users, $phones, $fields
            );
            $this->response(array(
                'status' => true,
                'data' => $voucher,
                'message' => ''
                    )
            );
        }
    }

    public function valid_voucher_type($str) {
        $this->form_validation->set_message('valid_voucher_type', $str . ' is not a valid type for vouchers.');
        if ($str != 1 && $str != 2) {
            return FALSE;
        } else {
            return TRUE;
        }
    }

    function check_date_format($date) {
        $this->form_validation->set_message('check_date_format', $date . ' is not a valid date (YYYY-MM-DD).');
        if ($date == "" || $date == null)
            return true;
        if (!DateTime::createFromFormat('Y-m-d', $date)) { //yes it's YYYY-MM-DD
            return FALSE;
        } else {
            if (strtotime($date) < strtotime(date('Y-m-d')))
                return false;
            return TRUE;
        }
    }

    public function show_get() {
        if (!$this->get('voucher'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('voucher') . " " . $this->lang->line('required')));
        else {
            $voucher = $this->voucher_service->get($this->get('voucher'));
            $this->response(array('status' => true, 'data' => $voucher, 'message' => ""));
        }
    }

    public function my_vouchers_get() {
        $this->user_permissions->is_player($this->current_user);
        $vouchers = $this->voucher_service->get_my_vouchers($this->current_user->player_id);
        $this->response(array('status' => true, 'data' => $vouchers, 'message' => ""));
    }

    public function validate_time($str) {
        if ($str == "" || $str == null)
            return true;
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

    function vouchers_management_post($operation = null) {

        $this->user_permissions->support_permission($this->current_user);
        $this->load->library('grocery_CRUD');
        try {
            $crud = new grocery_CRUD();

            $crud->set_theme('datatables')
                    ->set_table('voucher')
                    ->set_subject('voucher')
//                    ->where('booking.deleted', 0)
//                    ->columns('booking_id', 'field_id', 'player_id', 'state_id', 'creation_date', 'date', 'start', 'duration', 'total')
//                    ->order_by('booking_id')
                    ->set_relation('field_id', 'field', 'en_name', array('deleted' => 0))
//                    ->set_relation('player_id', 'player', '{name} <br> {phone}')
//                    ->set_relation('state_id', 'state', 'en_name')
//                    ->edit_fields('state_id', 'date', 'start', 'duration', 'notes')
//                    ->display_as('field_id', 'Field')
//                    ->display_as('player_id', 'Player')
//                    ->display_as('state_id', 'State')
//                    ->display_as('booking_id', 'ID')
//                    ->field_type('start', 'time')
//                    ->callback_column('duration', array($this, '_callback_duration_render'))
//                    ->callback_column('total', array($this, '_callback_total_render'))
//                    ->required_fields('date', 'start', 'duration', 'state_id')
//                    ->callback_delete(array($this, 'delete_booking'))
                    ->unset_export()
                    ->unset_add()
                    ->unset_read()
                    ->unset_print();
            $output = $crud->render();
//            $this->load->model("Services/booking_service");
            $this->load->view('template.php', array(
                'view' => 'vouchers_management',
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files
                    )
            );
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    function vouchers_management_get($operation = null) {
        $this->vouchers_management_post($operation);
    }

    public function _callback_duration_render($value, $row) {

        return $value . " Mins";
    }

}
