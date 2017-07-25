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
                    $this->get('voucher'), $this->get('field_id'), $this->current_user->player_id, $this->get('date'), $this->get('start'), $this->get('duration'), $this->get('game_type')
            );
            $this->response(array(
                'status' => true,
                'data' => $voucher
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
        $this->user_permissions->support_permission($this->current_user);
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('type', 'Type', 'required|callback_valid_voucher_type');
        $this->form_validation->set_rules('voucher', 'voucher code', 'required|is_unique[voucher.voucher]');
        $this->form_validation->set_rules('country_id', 'country', 'required');
        $this->form_validation->set_rules('value', 'value', 'required|is_natural_no_zero');
        $this->form_validation->set_rules('start_date', 'start_date', 'callback_check_date_format');
        $this->form_validation->set_rules('expiry_date', 'expiry_date', 'required|callback_check_date_format');
        $this->form_validation->set_rules('from_hour', 'from_hour', 'callback_validate_time');
        $this->form_validation->set_rules('to_hour', 'to_hour', 'callback_validate_time');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $data = array(
                'type' => $this->input->post('type'),
                'voucher' => $this->input->post('voucher'),
                'country_id' => $this->input->post('country_id'),
                'value' => $this->input->post('value'),
                'user_id' => $this->current_user->user_id,
                'one_time' => $this->input->post('one_time') == true ? 1 : 0,
                'expiry_date' => ($this->input->post('expiry_date') != "") ? date('Y-m-d', strtotime($this->input->post('expiry_date'))) : null,
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
            $phones = array();
            if ($this->input->post('phones'))
                $phones = $this->input->post('phones');
            $users = array();
            if ($this->input->post('all_users') == true)
                $data['public_user'] = 1;
            else {
                $data['public_user'] = 0;
                $users = $this->input->post('users');
                if ((!is_array($users) || count($users) == 0) && count($phones) == 0)
                    $this->response(array('status' => false, 'data' => null, 'message' => "Select at least one user"));
            }

            $companies = array();
            if ($this->input->post('all_fields') == true)
                $data['public_field'] = 1;
            else {
                $data['public_field'] = 0;
                $companies = $this->input->post('companies');
                if (!is_array($companies) || count($companies) == 0)
                    $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line("Select at least one field")));
            }
            if ($data['type'] == 1 && $data['value']>100)
                    $this->response(array('status' => false, 'data' => null, 'message' => "The voucher value can't be greator than 100%"));
            $games = array();
            if ($this->input->post('all_games') == true)
                $data['all_games'] = 1;
            else {
                $data['all_games'] = 0;
                $games = $this->input->post('games');
                if (!is_array($games) || count($games) == 0)
                    $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line("Select at least one game")));
            }
            $voucher = $this->voucher_service
                    ->create(
                    $data, $users, $phones, $companies, $games
            );
            $this->response(array(
                'status' => true,
                'data' => $voucher,
                'message' => ''
                    )
            );
        }
    }

    public function update_post() {
        $this->user_permissions->support_permission($this->current_user);
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('voucher_id', 'Voucher', 'required');
        $this->form_validation->set_rules('description_en', 'English Description', 'required');
        $this->form_validation->set_rules('description_ar', 'Arabic Description', 'required');
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
                'user_id' => $this->current_user->user_id,
                'one_time' => $this->input->post('valid_once') == true ? 1 : 0,
                'expiry_date' => date('Y-m-d', strtotime($this->input->post('expiry_date'))),
                'from_hour' => $this->input->post('from_hour') ? date('H:i:s', strtotime($this->input->post('from_hour'))) : null,
                'to_hour' => $this->input->post('to_hour') ? date('H:i:s', strtotime($this->input->post('to_hour'))) : null,
                'description_en' => $this->input->post('description_en'),
                'description_ar' => $this->input->post('description_ar'),
                'game_type_id' => $this->input->post('game_type_id') == 0 ? null : $this->input->post('game_type_id')
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
            $companies = array();
            if ($this->input->post('all_fields') == 1)
                $data['public_field'] = 1;
            else {
                $data['public_field'] = 0;
                $companies = $this->input->post('companies');
                if (!is_array($companies) || count($companies) == 0)
                    $this->response(array('status' => false, 'data' => null, 'message' => "Select at least one field"));
            }
            $voucher = $this->voucher_service
                    ->create(
                    $data, $users, $phones, $companies
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

    public function delete_get() {
        if (!$this->get('voucher'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('voucher') . " " . $this->lang->line('required')));
        else {
            $voucher = $this->voucher_service->delete($this->get('voucher'));
            $this->response(array('status' => true, 'data' => $voucher, 'message' => ""));
        }
    }

    public function my_vouchers_get() {
        $this->user_permissions->is_player($this->current_user);
        $vouchers = $this->voucher_service->get_my_vouchers($this->current_user->player_id, $this->get('field_id'), $this->get('date'), $this->get('start'), $this->get('duration'), $this->get('game_type'));
        $this->response(array('status' => true, 'data' => $vouchers, 'message' => ""));
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

    function vouchers_management_post($operation = null) {

        $this->user_permissions->support_permission($this->current_user);
        $this->load->library('grocery_CRUD');
        try {
            $crud = new grocery_CRUD();
            $crud->set_theme('datatables')
                    ->set_table('voucher')
                    ->set_subject('voucher')
                    ->order_by('voucher_id', 'desc')
                    ->columns('voucher', 'type', 'value', 'user_id', 'players', 'companies', 'start_date', 'expiry_date', 'from_hour', 'to_hour', 'actions')
                    ->set_relation_n_n('players', 'voucher_player', 'player', 'voucher_id', 'player_id', 'name')
                    ->set_relation('user_id', 'user', 'name', array('deleted' => 0))
                    ->display_as('user_id', 'Created by')
                    ->callback_column('players', array($this, '_callback_players_render'))
                    ->callback_column('start_date', array($this, '_callback_date_render'))
                    ->field_type('type', 'dropdown', array(1 => 'discount', 2 => 'free hours'))
                    ->unset_export()
                    ->unset_add()
                    ->unset_edit()
                    ->unset_delete()
                    ->unset_read()
                    ->unset_print();
            $output = $crud->render();
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

    public function _callback_players_render($value, $row) {
        if ($row->type == "discount")
            $row->value = $row->value . " %";
        else
            $row->value = round($row->value / 60) . " h";
        $player_str = "";
        if ($row->public_field == 1)
            $row->fields = "Public";
        if ($row->public_user == 1)
            $player_str = "Public";
        $voucher = $this->voucher_service->get($row->voucher);
        $players = array();
        foreach ($voucher->users as $value) {
            if ($value->player_id == null)
                array_push($players, $value->phone);
            else
                array_push($players, $value->player_name);
        }
        $player_str = implode(', ', $players);
        $fields = array();
        foreach ($voucher->companies as $value) {
            array_push($fields, $value->company_name);
        }
        $row->companies = implode(', ', $fields);

        return $player_str;
    }

    public function _callback_date_render($value, $row) {
        $row->actions = "<button class='btn btn-success' onclick='edit_voucher(\"" . $row->voucher . "\")'><span class='glyphicon glyphicon-pencil'></span></button>";
        $row->actions .= "<button class='btn btn-danger' onclick='delete_voucher(\"" . $row->voucher . "\")'>X</button>";
        $row->from_hour = date('H:i A', strtotime($row->from_hour));
        $row->to_hour = date('H:i A', strtotime($row->to_hour));
        return date('Y-m-d', strtotime($value));
    }

}
