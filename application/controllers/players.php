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
        $this->form_validation->set_rules('phone', 'lang:phone', 'required');
        $this->form_validation->set_rules('name', 'lang:name', 'required');
        $this->form_validation->set_rules('os', 'os', 'required');
        $this->form_validation->set_rules('lang', 'lang:lang', 'callback_valid_lang');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $phone = $this->input->post('phone');
            $os = $this->input->post('os');
            $name = $this->input->post('name');
            $lang = $this->input->post('lang') != "" ? strtolower($this->input->post('lang')) : $this->response->lang;
            $user = $this->player_service->register($phone, $os, $name, $lang);
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
            throw new Validation_Exception((validation_errors()) ? validation_errors() : "Validation Exception");
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
        $this->form_validation->set_rules('name', 'lang:name', 'required');
        $this->form_validation->set_rules('email', 'lang:email', 'valid_email');
        $this->form_validation->set_rules('lang', 'lang:lang', 'callback_valid_lang');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $name = $this->input->post('name');
            $email = $this->input->post('email');
            $address = $this->input->post('address');
            $games = $this->input->post('prefered_games');
            $lang = $this->input->post('lang') != "" ? strtolower($this->input->post('lang')) : $this->response->lang;
            $player = $this->player_service->update($this->current_user->player_id, $name, $email, $address, $games, $lang);
            $this->response(array('status' => true, 'data' => $player, "message" => $this->lang->line('updated')));
        }
    }

    public function valid_lang($str) {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_message($this->lang->line('valid_lang'));
        if ($str == "" || strtolower($str) == "ar" || strtolower($str) == "en") {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public function deactive_post() {
        $this->player_service->deactive($this->current_user->player_id);
        $this->response(array('status' => true, 'data' => array('message' => $this->lang->line('account_deleted')), 'message' => $this->lang->line('account_deleted')));
    }

    public function players_management_post($operation = null) {
        $this->user_permissions->support_permission($this->current_user);
        $this->load->library('grocery_CRUD');
        try {
            $crud = new grocery_CRUD();

            $crud->set_theme('datatables')
                    ->set_table('player')
                    ->set_subject('player')
                    ->columns('player_id', 'name', 'phone', 'email', 'profile_picture', 'address', 'os', 'prefered_games', 'active')
                    ->display_as('player_id', 'Player')
                    ->set_relation_n_n('prefered_games', 'prefered_game', 'game_type', 'player_id', 'game_type_id', 'en_name')
                    ->callback_column('active', array($this, '_callback_active_render'))
                    ->callback_before_insert(array($this, 'encrypt_password_callback'))
                    ->add_action('Deactivate', base_url() . 'assets/images/close.png', '', 'delete-icon', array($this, 'delete_user_callback'))
                    ->set_field_upload('profile_picture', 'assets/uploaded_images/')
                    ->unset_export()
                    ->unset_read()
                    ->unset_print()
                    ->unset_add()
                    ->unset_edit()
                    ->unset_delete();
            $output = $crud->render();

            $this->load->view('template.php', array(
                'view' => 'players_management',
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files
                    )
            );
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    public function players_management_get($operation = null) {
        $this->players_management_post($operation);
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

    public function delete_user_callback($primary_key) {
        return site_url('/players/delete_user/' . $primary_key);
    }

    public function delete_user_get($primary_key) {

        $this->player_service->deactive_active($primary_key);
        redirect('players/players_management');
    }

    function upload_image_post() {
        $image_name = "";
        if ($this->input->post('delete') && $this->input->post('delete') == 1) {
            $image_name = "";
        } else {
            $this->load->helper('image_uploader_helper');
            $image_file = upload_image($this);
            if (!isset($image_file['image']))
                $this->response(array('status' => false, 'data' => null, "message" => "Uploading error"));
            else
                $image_name = $image_file['image']['upload_data']['file_name'];
        }

        $this->player_service->update_player_image($this->current_user->player_id, $image_name);
        $this->response(array('status' => true, 'data' => $this->player_service->get($this->current_user->player_id), "message" => $image_name == "" ? "The image has been deleted" : "the image has been saved on the server"));
    }

    function contact_us_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('message', 'Message', 'required');
        $this->form_validation->set_rules('phone', 'phone', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $this->load->model("Services/message_service");
            $this->message_service->save(($this->current_user) ? $this->current_user->name : '', $this->post('email'), "Fieldium Contact us", $this->post('message'), $this->post('phone'));
            $this->response(array('status' => true, 'data' => array('message' => $this->lang->line('message_sent')), 'message' => $this->lang->line('message_sent')));
        }
    }

}
