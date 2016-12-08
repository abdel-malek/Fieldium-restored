<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class players extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/player_service");
        $this->load->model('Permissions/user_permissions');
    }

    public function register_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('phone', 'Phone', 'required');
        $this->form_validation->set_rules('os', 'os', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $phone = $this->input->post('phone');
            $os = $this->input->post('os');
            $user = $this->player_service->register($phone, $os);
            $this->response(array('status' => true, 'data' => $user, "message" => $this->lang->line('created')));
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
        $this->form_validation->set_rules('server_id', 'Server id', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $phone = $this->input->post('phone');
            $server_id = $this->input->post('server_id');
            $player = $this->player_service->request_verification_code($phone, $server_id);
            $this->response(array('status' => true, 'data' => $player, "message" => $this->lang->line('new_code')));
        }
    }

    public function refresh_token_post() {
        $player_id = $this->current_user->player_id;
        $token = $this->post('token');
        $player = $this->player_service->refresh_token($player_id, $token);
        $this->response(array('status' => true, 'data' => $player, "message" => ''));
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
            $games = $this->input->post('prefered_games');
            $profile_picture = "";
            try {
                $this->load->helper('image_uploader_helper');
                $image = upload_image($this);
                $profile_picture = $image['profile_picture']['upload_data']['file_name'];
            } catch (Uploading_Image_Exception $ex) {
                $profile_picture = "";
            }
            $player = $this->player_service->update(1, $name, $email, $address, $games, $profile_picture, $this->response->lang);
            $this->response(array('status' => true, 'data' => $player, "message" => $this->lang->line('updated')));
        }
    }

    public function deactive_get() {
        $this->player_service->deactive($this->current_user->player_id);
        $this->response(array('status' => true, 'data' => null, 'message' => $this->lang->line('account_deleted')));
    }

}
