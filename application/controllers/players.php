<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class players extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/player_service");
        $this->load->model('Permissions/user_permissions');
        $this->load->library('grocery_CRUD');
    }

    public function send_sms_post() {
        echo($this->send_sms->send_sms($this->post('message'), $this->post('mobile'), $this->post('lang')));
    }

    public function refresh_token_post() {
        $user_id = $this->post('user_id');
        $token = $this->post('token');
        $user = $this->user_service->refresh_token($user_id, $token);
        $this->response(array('status' => true, 'data' => $user));
    }

    public function register_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('phone', 'Phone', 'required|is_unique[player.phone]');
        $this->form_validation->set_rules('device_id', 'Device id', 'required');
        $this->form_validation->set_rules('os', 'os', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $phone = $this->input->post('phone');
            $device_id = $this->input->post('device_id');
            $token = $this->input->post('token');
            $os = $this->input->post('os');
            $user = $this->player_service->register($phone, $device_id, $token, $os);
            $this->response(array('status' => true, 'data' => $user, "message" =>$this->lang->line('created')));
        }
    }

    public function verify_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('phone', 'Phone', 'required');
        $this->form_validation->set_rules('verification_code', 'Verification code', 'required');

        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $phone = $this->input->post('phone');
            $code = $this->input->post('verification_code');
            $player = $this->player_service->verify($phone, $code);
            $this->response(array('status' => true, 'data' => $player, "message" => $this->lang->line('account_confirmed')));
        }
    }

    public function request_verification_code_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('phone', 'Phone', 'required');

        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $phone = $this->input->post('phone');
            $player = $this->player_service->request_verification_code($phone);
            $this->response(array('status' => true, 'data' => $player, "message" => $this->lang->line('new_code')));
        }
    }

    public function update_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('name', 'name', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $name = $this->input->post('name');
            $email = $this->input->post('email');
            $address = $this->input->post('address');
            $player = $this->player_service->update($this->current_user->player_id, $name, $email, $address);
            $this->response(array('status' => true, 'data' => $player, "message" => $this->lang->line('updated')));
        }
    }

    public function delete_get() {
        $this->player_service->deactive($this->current_user->player_id);
        $this->response(array('status' => true, 'data' => null, 'message' => $this->lang->line('account_deleted')));
    }

}
