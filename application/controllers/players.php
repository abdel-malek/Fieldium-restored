<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class players extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/user_service");
        $this->load->model('Permissions/user_permissions');
        $this->load->library('grocery_CRUD');
    }

    public function login_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('username', 'Username', 'required');
        $this->form_validation->set_rules('password', 'Password', 'required|min_length[6]|max_length[30]');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $email = $this->input->post('username');
            $password = $this->input->post('password');
            $user = $this->user_service->login($email, md5($password));
            $this->response(array('status' => true, 'data' => $user));
        }
    }

    function logout_get() {
        $this->user_service->logout();
        redirect();
    }

}
