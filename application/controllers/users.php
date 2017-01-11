<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class users extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/user_service");
        $this->load->model('Permissions/user_permissions');
    }

    public function create_post() {
        $this->user_permissions->support_permission($this->current_user);
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
    
    public function save_token_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('token', 'Token', 'required');
        $this->form_validation->set_rules('os', 'os', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $token = $this->input->post('token');
            $os = $this->input->post('os');
            $user_id = $this->current_user->user_id;
            $user = $this->user_service->save_token(
                    $user_id, $token, $os, $this->response->lang
            );
            $this->response(array('status' => true, 'data' => $user, "message" => $this->lang->line('updated')));
        }
    }

    public function change_password_post() {
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
        if ($this->response->format == "html")
            redirect("dashboard");
        else
            $this->response(array('status' => true, 'data' => null, "message" => "Done"));
    }

    public function delete_get() {
        $this->user_permissions->support_permission($this->current_user);
        if (!$this->get('user_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('user_id') . " " . $this->lang->line('required')));
        else {
            $this->user_service->deactive($this->get('user_id'));
            $this->response(array('status' => true, 'data' => null, 'message' => $this->lang->line('deleted')));
        }
    }

    public function show_get() {
        $this->user_permissions->support_permission($this->current_user);
        if (!$this->get('user_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('user_id') . " " . $this->lang->line('required')));
        else {
            $user = $this->user_service->get($this->get('user_id'), $this->response->lang);
            $this->response(array('status' => true, 'data' => $user, 'message' => ""));
        }
    }

    public function users_management_post($operation = null) {
        $this->user_permissions->support_permission($this->current_user);
        $this->load->library('grocery_CRUD');
        try {
            $crud = new grocery_CRUD();

            $crud->set_theme('datatables')
                    ->set_table('user')
                    ->where("user_id != " . $this->current_user->user_id)
                    ->where('active', 1)
                    ->set_subject('user')
                    ->columns('user_id', 'name', 'username', 'phone', 'mobile', 'email', 'role_id', 'company_id', 'active')
                    ->order_by('user_id')
                    ->set_relation('role_id', 'role', 'en_name')
                    ->set_relation('company_id', 'company', 'en_name', null, null, array('company.deleted' => 0))
                    ->display_as('role_id', 'Role')
                    ->display_as('company_id', 'Company')
                    ->display_as('user_id', 'User')
                    ->edit_fields('name', 'phone', 'email', 'password', 'role_id', 'company_id')
                    ->add_fields('name', 'username', 'phone', 'email', 'password', 'role_id', 'company_id')
                    ->required_fields('name', 'username', 'role_id')
                    ->unique_fields('email')
                    ->field_type('phone', 'integer')
                    ->set_rules('name', 'Name', 'required|min_length[3]|max_length[16]')
                    ->callback_before_update(array($this, 'encrypt_password_callback'))
                    ->callback_edit_field('password', array($this, '_callback_password'))
                    ->callback_column('active', array($this, '_callback_active_render'))
                    ->callback_before_insert(array($this, 'encrypt_password_callback'))
                    ->callback_delete(array($this, 'delete_user'))
                    ->add_action('Deactivate', base_url() . 'assets/images/close.png', '', 'delete-icon', array($this, 'delete_user_callback'))
                    ->unset_export()
                    ->unset_read()
                    ->unset_print()
                    ->unset_delete();

            if ($operation == 'insert_validation' || $operation == 'insert' || $operation == 'add') {
                if ($this->current_user->role_id != ROLE::SUPPORT)
                    throw new Permission_Denied_Exception ();
                $crud
                        ->set_rules('password', 'Password', 'required|min_length[6]|max_length[30]')
                        ->set_rules('username', 'User Name', 'required|min_length[3]|max_length[16]|is_unique[user.username]');
            } else {
                $crud
                        ->set_rules('password', 'Password', 'min_length[6]|max_length[30]');
            }
            $output = $crud->render();

            $this->load->view('template.php', array(
                'view' => 'users_management',
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files
                    )
            );
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    public function users_management_get($operation = null) {
        $this->users_management_post($operation);
    }

    public function _callback_password($value = '', $primary_key = null) {

        return '<input type="text"  maxlength="50" value="" name="password" style="width:100%" autocomplete="false">';
    }

    function encrypt_password_callback($post_array, $primary_key) {

        if (!empty($post_array['password'])) {
            $post_array['password'] = md5($post_array['password']);
        } else {
            unset($post_array['password']);
        }

        return $post_array;
    }

    public function _callback_active_render($value, $row) {
        if ($value == 1) {
            return ""
                    . "<p class='active'>Active</p>";
        } else if ($value == 0) {
            return ""
                    . "<p class='hide_tr'>Not Active</p>";
        }
    }

    function confirm_password_add_callback($post_array) {
        $post_array['password'] = md5($post_array['password']);
        unset($post_array['confirm_password']);

        return $post_array;
    }

    public function delete_user_callback($primary_key) {
        return site_url('/users/delete_user/' . $primary_key);
    }

    public function delete_user_get($primary_key) {

        $this->user_service->deactive($primary_key);
        redirect('users/users_management');
    }

    public function edit_profile_post($operation = null) {
        if (!$this->session->userdata('PHP_AUTH_USER'))
            redirect("dashboard");
        else {
            $this->load->library('grocery_CRUD');
            try {
                $crud = new grocery_CRUD();

                $crud->set_theme('datatables')
                        ->where('user_id', $this->current_user->user_id)
                        ->set_table('user')
                        ->set_subject('user')
                        ->columns('user_id', 'name', 'email', 'role_id', 'active', 'creation_date')
                        ->order_by('user_id')
                        ->set_relation('role_id', 'role', 'name')
                        ->display_as('role_id', 'User Type')
                        ->field_type('phone', 'integer')
                        ->edit_fields('name', 'email', 'phone', 'password')
                        ->add_fields('name', 'email', 'password')
                        ->required_fields('name', 'role_id')
                        ->set_rules('name', 'Name', 'required|min_length[3]|max_length[16]')
                        ->callback_before_update(array($this, 'confirm_password_edit_profile_callback'))
                        ->callback_edit_field('password', array($this, '_callback_password'))
                        ->callback_after_update(array($this, 'after_edit_profile_callback'))
                        ->set_lang_string('update_success_message', 'Your data has been successfully stored into the database.<br/>Please wait while you are redirecting to the list page.
		 <script type="text/javascript">
                 setTimeout(func, 3000);
		  function func() {window.location = "' . site_url('users/edit_profile/edit/' . $this->current_user->user_id) . '"; }
		 </script>
		 <div style="display:none">
		 '
                        )
                        ->unset_export()
                        ->unset_print()
                        ->unset_texteditor('address')
                        ->unset_read()
                        ->unset_back_to_list()
                        ->unset_delete();

                if ($operation == 'update_validation' || $operation == 'edit' || $operation == 'update') {

                    $crud
                            ->set_rules('password', 'Password', 'min_length[6]|max_length[30]');
                } else {
                    redirect('users/edit_profile/edit/' . $this->current_user->user_id);
                }

                $output = $crud->render();
                if ($this->session->userdata('logout') == true)
                    redirect("dahsboard");
                $this->load->view('template.php', array(
                    'view' => 'users_management',
                    'output' => $output->output,
                    'js_files' => $output->js_files,
                    'css_files' => $output->css_files
                        )
                );
            } catch (Exception $e) {
                show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
            }
        }
    }

    public function edit_profile_get($operation = null) {
        $this->edit_profile_post($operation);
    }

    function confirm_password_edit_profile_callback($post_array) {
        if (!empty($post_array['password'])) {
            $this->session->set_userdata(array('logout' => true));
            $post_array['password'] = md5($post_array['password']);
        } else if ($post_array['email'] != $this->session->userdata('PHP_AUTH_USER')) {
            $this->session->set_userdata(array('logout' => true));
            unset($post_array['password']);
        } else {
            $this->session->set_userdata(array('logout' => false));
            unset($post_array['password']);
        }
        return $post_array;
    }

    function after_edit_profile_callback($post_array, $primary_key) {
        if ($this->session->userdata('logout') == true) {
            $this->user_service->logout();
        }
        return true;
    }

}
