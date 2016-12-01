<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class users extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/user_service");
        $this->load->model('Permissions/user_permissions');
        $this->load->library('grocery_CRUD');
    }

    public function create_post() {
        //   $this->user_permissions->support_permission($this->current_user);
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('username', 'Username', 'required|is_unique[user.username]');
        $this->form_validation->set_rules('name', 'Name', 'required');
        $this->form_validation->set_rules('password', 'Password', 'required|min_length[6]|max_length[30]');
        $this->form_validation->set_rules('password_confirmation', 'Password confirmation', 'required|min_length[6]|max_length[30]|matches[password]');
        $this->form_validation->set_rules('company_id', 'Company id', 'required');
        $this->form_validation->set_rules('role_id', 'Role id', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $username = $this->input->post('username');
            $name = $this->input->post('name');
            $phone = $this->input->post('phone');
            $email = $this->input->post('email');
            $role_id = $this->input->post('role_id');
            $company_id = $this->input->post('company_id');
            $password = $this->input->post('password');
            $user = $this->user_service->create(
                    $username, $name, md5($password), $phone, $email, $company_id, $role_id
                    );
            $this->response(array('status' => true, 'data' => $user, "message" => $this->lang->line('created')));
        }
    }
    
    public function update_post() {
        //   $this->user_permissions->support_permission($this->current_user);
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('name', 'Name', 'required');
        $this->form_validation->set_rules('user_id', 'User id', 'required');
        $this->form_validation->set_rules('role_id', 'Role id', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $name = $this->input->post('name');
            $phone = $this->input->post('phone');
            $email = $this->input->post('email');
            $role_id = $this->input->post('role_id');
            $user_id = $this->input->post('user_id');
            $user = $this->user_service->update(
                   $user_id, $name, $phone, $email, $role_id, $this->response->lang
                    );
            $this->response(array('status' => true, 'data' => $user, "message" => $this->lang->line('updated')));
        }
    }
    
    public function change_password_post() {
        //   $this->user_permissions->support_permission($this->current_user);
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('user_id', 'User id', 'required');
        $this->form_validation->set_rules('password', 'Password', 'required|min_length[6]|max_length[30]');
        $this->form_validation->set_rules('password_confirmation', 'Password confirmation', 'required|min_length[6]|max_length[30]|matches[password]');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $password = $this->input->post('password');
            $user_id = $this->input->post('user_id');
            $user = $this->user_service->change_password(
                   $user_id, md5($password), $this->response->lang
                    );
            $this->response(array('status' => true, 'data' => $user, "message" => $this->lang->line('updated')));
        }
    }
    
    public function login_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('username', 'Username', 'required');
        $this->form_validation->set_rules('password', 'Password', 'required|min_length[6]|max_length[30]');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $username = $this->input->post('username');
            $password = $this->input->post('password');
            $user = $this->user_service->login($username, md5($password));
            $this->response(array('status' => true, 'data' => $user, "message" => "You are logged in."));
        }
    }

    function logout_get() {
        $this->user_service->logout();
        redirect();
    }

    public function delete_get() {
        //   $this->user_permissions->support_permission($this->current_user);
        if (!$this->get('user_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('user_id')." ".$this->lang->line('required')));
        else {
            $this->user_service->deactive($this->get('user_id'));
            $this->response(array('status' => true, 'data' => null, 'message' =>$this->lang->line('deleted')));
        }
    }
    
    public function show_get() {
        //   $this->user_permissions->support_permission($this->current_user);
        if (!$this->get('user_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('user_id')." ".$this->lang->line('required')));
        else {
            $user = $this->user_service->get($this->get('user_id'), $this->response->lang);
            $this->response(array('status' => true, 'data' => $user, 'message' => ""));
        }
    }
    
}
