<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class amenities extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/amenity_service");
        $this->load->model('Permissions/user_permissions');
    }

    public function get_all_get() {
        $amenities = $this->amenity_service->get_all($this->response->lang);
        $this->response(array('status' => true, 'data' => $amenities, 'message' => ""));
    }

    function amenities_management_post($primary_key = null) {
        $this->user_permissions->support_permission($this->current_user);
        $this->load->library('grocery_CRUD');
        try {
            $crud = new grocery_CRUD();

            $crud->set_theme('datatables')
                    ->set_table('amenity')
                    ->set_subject('amenity')
                    ->columns('amenity_id', 'en_name', 'image')
                    ->display_as('amenity_id', 'id')
                    ->display_as('en_name', 'name')
                    ->set_field_upload('image', 'assets/uploaded_images/')
                    ->unset_edit_fields('ar_name')
                    ->unset_add_fields('ar_name')
                    ->unset_export()
                    ->unset_read()
                    ->unset_print();
            $output = $crud->render();
            $this->load->view('template.php', array(
                'view' => 'amenities_management',
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files
                    )
            );
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    function amenities_management_get($primary_key = null) {
        $this->amenities_management_post($primary_key);
    }
}
