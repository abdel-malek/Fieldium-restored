<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class areas extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/area_service");
        $this->load->model('Permissions/user_permissions');
    }

    public function get_all_get() {
        $areas = $this->area_service->get_all($this->response->lang);
        $this->response(array('status' => true, 'data' => $areas, 'message' => ""));
    }

    function areas_management_post($country = UAE, $primary_key = null) {
        $this->user_permissions->support_permission($this->current_user);
        $this->load->library('grocery_CRUD');
        try {
            $crud = new grocery_CRUD();
              if($this->session->userdata('lang') == 'arabic'){
              $crud->set_language('Arabic'); 
                    $crud->set_theme('datatables')
                    ->set_table('area')
                    ->where('area.country_id', $country)
                    ->set_subject('مكان')
                    ->columns('area_id', 'country_id', 'en_name')
                    ->display_as('area_id', 'الرقم تسلسلي')
                    ->display_as('country_id', 'البلد')
                    ->display_as('en_name', 'الاسم')
                    ->set_relation('country_id', 'country', 'en_name')
                    ->unset_edit_fields('ar_name')
                    ->unset_add_fields('ar_name')
                    ->required_fields('en_name')
                    ->unset_export()
                    ->unset_read()
                    ->unset_print();
            $output = $crud->render();
            $this->load->view('template.php', array(
                'view' => 'areas_management',
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files,
                'country' => $country
                    )
            );
            }else{
              $crud->set_language('English'); 
                    $crud->set_theme('datatables')
                    ->set_table('area')
                    ->where('area.country_id', $country)
                    ->set_subject('area')
                    ->columns('area_id', 'country_id', 'en_name')
                    ->display_as('area_id', 'id')
                    ->display_as('country_id', 'Country')
                    ->display_as('en_name', 'name')
                    ->set_relation('country_id', 'country', 'en_name')
                    ->unset_edit_fields('ar_name')
                    ->unset_add_fields('ar_name')
                    ->required_fields('en_name')
                    ->unset_export()
                    ->unset_read()
                    ->unset_print();
            $output = $crud->render();
            $this->load->view('template.php', array(
                'view' => 'areas_management',
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files,
                'country' => $country
                    )
            );
            }
      
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    function areas_management_get($country = UAE, $primary_key = null) {
        $this->areas_management_post($country , $primary_key);
    }

}
