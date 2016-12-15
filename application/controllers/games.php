<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class games extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/game_service");
        $this->load->model('Permissions/user_permissions');
    }

    public function get_all_get() {
        $games = $this->game_service->get_all($this->response->lang);
        $this->response(array('status' => true, 'data' => $games, 'message' => ""));
    }

    function games_management_post($primary_key = null) {
        $this->user_permissions->support_permission($this->current_user);
        $this->load->library('grocery_CRUD');
        try {
            $crud = new grocery_CRUD();

            $crud->set_theme('datatables')
                    ->set_table('game_type')
                    ->set_subject('game')
                    ->columns('game_id', 'en_name', 'image', 'en_description')
                    ->display_as('game_id', 'id')
                    ->display_as('en_name', 'name')
                    ->display_as('en_description', 'description')
                    ->unset_edit_fields('ar_name', 'ar_description')
                    ->unset_add_fields('ar_name', 'ar_description')
                    ->required_fields('en_name')
                    ->set_field_upload('image', 'assets/uploaded_images/')
                    ->unset_export()
                    ->unset_read()
                    ->unset_print();
            $output = $crud->render();
            $this->load->view('template.php', array(
                'view' => 'games_management',
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files
                    )
            );
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    function games_management_get($primary_key = null) {
        $this->games_management_post($primary_key);
    }

}
