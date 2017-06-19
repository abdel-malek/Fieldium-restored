<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class offers extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/offer_service");
        $this->load->model('Permissions/user_permissions');
    }

    public function create_post() {
//        $this->user_permissions->support_permission($this->current_user);
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('title_en', 'English title', 'required');
        $this->form_validation->set_rules('title_ar', 'Arabic title', 'required');
        $this->form_validation->set_rules('start_date', 'start_date', 'callback_check_date_format');
        $this->form_validation->set_rules('expiry_date', 'expiry_date', 'callback_check_date_format');
        $this->form_validation->set_rules('set_of_minutes', 'Minutes set', 'required|is_natural_no_zero');
        $this->form_validation->set_rules('voucher_type', 'Voucher Type', 'required|callback_valid_voucher_type');
        $this->form_validation->set_rules('voucher_value', 'voucher value', 'required|is_natural_no_zero');
        $this->form_validation->set_rules('voucher_start_after', 'start after', 'required');
        $this->form_validation->set_rules('valid_days', 'Valid days', 'required');
        $this->form_validation->set_rules('voucher_from_hour', 'voucher from hour', 'callback_validate_time');
        $this->form_validation->set_rules('voucher_to_hour', 'voucher to hour', 'callback_validate_time');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $data = array(
                'voucher_type' => $this->input->post('voucher_type'),
                'voucher_value' => $this->input->post('voucher_value'),
                'title_en' => $this->input->post('title_en'),
                'title_ar' => $this->input->post('title_ar'),
                'set_of_minutes' => $this->input->post('minutes'),
                'expiry_date' => ($this->input->post('expiry_date') != "") ? date('Y-m-d', strtotime($this->input->post('expiry_date'))) : null,
                'voucher_from_hour' => $this->input->post('voucher_from_hour') ? date('H:i:s', strtotime($this->input->post('voucher_from_hour'))) : null,
                'voucher_to_hour' => $this->input->post('voucher_to_hour') ? date('H:i:s', strtotime($this->input->post('voucher_to_hour'))) : null,
                'voucher_start_after' => $this->input->post('voucher_start_after'),
                'valid_days' => $this->input->post('valid_days'),
                'description_en' => $this->input->post('description_en'),
                'description_ar' => $this->input->post('description_ar')
            );
            if ($this->input->post('start_date') != "" && $this->input->post('start_date') != null)
                if (strtotime($data['expiry_date']) < $this->input->post('start_date'))
                    $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('voucher') . " " . $this->lang->line('required')));
                else
                    $data['start_date'] = $this->input->post('start_date');
            $companies = array();
            if ($this->input->post('all_fields') == true)
                $data['public_field'] = 1;
            else {
                $data['public_field'] = 0;
                $companies = $this->input->post('companies');
                if (!is_array($companies) || count($companies) == 0)
                    $this->response(array('status' => false, 'data' => null, 'message' => "Select at least one field"));
            }

            $games = array();
            if ($this->input->post('all_games') == true)
                $data['all_games'] = 1;
            else {
                $data['all_games'] = 0;
                $games = $this->input->post('games');
                if (!is_array($games) || count($games) == 0)
                    $this->response(array('status' => false, 'data' => null, 'message' => "Select at least one game"));
            }

            $offer = $this->offer_service
                    ->create(
                    $data, $companies, $games
            );
            $this->response(array(
                'status' => true,
                'data' => $offer,
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
        if (!$this->get('offer_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('offer_id') . " " . $this->lang->line('required')));
        else {
            $offer = $this->offer_service->get($this->get('offer_id'));
            $this->response(array('status' => true, 'data' => $offer, 'message' => ""));
        }
    }

    public function delete_get() {
        if (!$this->get('offer_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('offer_id') . " " . $this->lang->line('required')));
        else {
            $offer = $this->offer_service->delete($this->get('offer_id'));
            $this->response(array('status' => true, 'data' => $offer, 'message' => ""));
        }
    }

    public function validate_time($str) {
        if ($str == "" || $str == null)
            return true;
        if (strlen($str) == 7)
            $str = "0" . $str;
        $str = date('H:i:s', strtotime($str));
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

    function offers_management_post($operation = null) {

        $this->user_permissions->support_permission($this->current_user);
        $this->load->library('grocery_CRUD');
        try {
            $crud = new grocery_CRUD();
            $crud->set_theme('datatables')
                    ->set_table('offer')
                    ->set_subject('offer')
                    ->order_by('offer_id', 'desc')
                    ->columns('offer_id', 'title_en', 'set_of_minutes', 'voucher_type', 'voucher_value', 'games', 'companies', 'start_date', 'expiry_date', 'actions')
                    ->set_relation_n_n('games', 'offer_game', 'game_type', 'offer_id', 'game_type_id', 'en_name')
                    ->display_as('title_en', 'Title')
                    ->callback_column('games', array($this, '_callback_games_render'))
                    ->callback_column('start_date', array($this, '_callback_date_render'))
                    ->field_type('voucher_type', 'dropdown', array(1 => 'discount', 2 => 'free hours'))
                    ->unset_export()
                    ->unset_add()
                    ->unset_edit()
                    ->unset_delete()
                    ->unset_read()
                    ->unset_print();
            $output = $crud->render();
            $this->load->view('template.php', array(
                'view' => 'offers_management',
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files
                    )
            );
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    function offers_management_get($operation = null) {
        $this->offers_management_post($operation);
    }

    public function _callback_games_render($value, $row) {
        if ($row->voucher_type == "discount")
            $row->voucher_value = $row->voucher_value . " %";
        else
            $row->voucher_value = round($row->voucher_value/60) . " h";
        if ($row->public_field == 1)
            $row->fields = "Public";
        if ($row->all_games == 1)
            $_str = "All";
        $offer = $this->offer_service->get($row->offer_id);
        $games = array();
        foreach ($offer->games as $value) {
            array_push($games, $value->game_name);
        }
        $_str = implode(', ', $games);
        $fields = array();
        foreach ($offer->companies as $value) {
            array_push($fields, $value->company_name);
        }
        $row->companies = implode(', ', $fields);
        $row->set_of_minutes = round($row->set_of_minutes / 60) . " h";
        return $_str;
    }

    public function _callback_date_render($value, $row) {
        $row->actions = "<button class='btn btn-success' onclick='edit_offer(\"" . $row->offer_id . "\")'><span class='glyphicon glyphicon-pencil'></span></button>";
        $row->actions .= "<button class='btn btn-danger' onclick='delete_offer(\"" . $row->offer_id . "\")'>X</button>";
        return date('Y-m-d', strtotime($value));
    }

}
