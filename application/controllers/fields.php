<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class fields extends REST_Controller {

    function __construct() {
        parent::__construct();

        $this->load->model("Services/field_service");
        $this->load->model('Permissions/user_permissions');
    }

    public function create_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('company_id', 'Company id', 'required');
        $this->form_validation->set_rules('name', 'Name', 'required|min_length[6]|max_length[30]');
        $this->form_validation->set_rules('phone', 'Phone', 'required');
        $this->form_validation->set_rules('hour_rate', 'Hour Rate', 'required');
        $this->form_validation->set_rules('open_time', 'Open Time', 'required');
        $this->form_validation->set_rules('close_time', 'Close Time', 'required');
        $this->form_validation->set_rules('area_x', 'Area X', 'required');
        $this->form_validation->set_rules('area_y', 'Area Y', 'required');
        $this->form_validation->set_rules('games_types', 'games types', 'required');
        $this->form_validation->set_rules('max_capacity', 'Max Capacity', 'required|integer');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {

            $company_id = $this->input->post('company_id');
            $this->user_permissions->management_permission($this->current_user, $company_id);
            $name = $this->input->post('name');
            $ar_name = $this->input->post('name');
            $phone = $this->input->post('phone');
            $hour_rate = $this->input->post('hour_rate');
            $open_time = $this->input->post('open_time');
            $cloes_time = $this->input->post('close_time');
            $area_x = $this->input->post('area_x');
            $area_y = $this->input->post('area_y');
            $max_capacity = $this->input->post('max_capacity');
            $description = $this->input->post('description');
            $ar_description = $this->input->post('description');
            $amenities = $this->input->post('amenities');
            $images = $this->input->post('images');
            $games_types = $this->input->post('games_types');
            $field = $this->field_service
                    ->create(
                    $company_id, $name, $ar_name, $phone, $hour_rate, $open_time, $cloes_time, $area_x, $area_y, $max_capacity, $description, $ar_description, $images, $amenities, $games_types, $this->response->lang
            );
            $this->response(array('status' => true, 'data' => $field, 'message' => $this->lang->line('created')));
        }
    }

    public function update_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('company_id', 'Company id', 'required');
        $this->form_validation->set_rules('field_id', 'Field id', 'required');
        $this->form_validation->set_rules('name', 'Name', 'required|min_length[6]|max_length[30]');
        $this->form_validation->set_rules('phone', 'Phone', 'required');
        $this->form_validation->set_rules('hour_rate', 'Hour Rate', 'required');
        $this->form_validation->set_rules('open_time', 'Open Time', 'required');
        $this->form_validation->set_rules('close_time', 'Close Time', 'required');
        $this->form_validation->set_rules('area_x', 'Area X', 'required');
        $this->form_validation->set_rules('area_y', 'Area Y', 'required');
        $this->form_validation->set_rules('games_types', 'games types', 'required');
        $this->form_validation->set_rules('max_capacity', 'Max Capacity', 'required|integer');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $field_id = $this->input->post('field_id');
            $company_id = $this->input->post('company_id');
            $this->user_permissions->management_permission($this->current_user, $company_id);
            $name = $this->input->post('name');
            $ar_name = $this->input->post('name');
            $phone = $this->input->post('phone');
            $hour_rate = $this->input->post('hour_rate');
            $open_time = $this->input->post('open_time');
            $cloes_time = $this->input->post('close_time');
            $area_x = $this->input->post('area_x');
            $area_y = $this->input->post('area_y');
            $max_capacity = $this->input->post('max_capacity');
            $description = $this->input->post('description');
            $ar_description = $this->input->post('description');
            $amenities = $this->input->post('amenities');
            $images = $this->input->post('images');
            $games_types = $this->input->post('games_types');
            $field = $this->field_service
                    ->update(
                    $field_id, $name, $ar_name, $phone, $hour_rate, $open_time, $cloes_time, $area_x, $area_y, $max_capacity, $description, $ar_description, $images, $amenities, $games_types, $this->response->lang
            );
            $this->response(array('status' => true, 'data' => $field, 'message' => $this->lang->line('updated')));
        }
    }

    public function delete_get() {
        if (!$this->get('field_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('field_id') . " " . $this->lang->line('required')));
        else {
            $field = $this->field_service->get($this->get('field_id'));
            $this->user_permissions->management_permission($this->current_user, $field->company_id);
            $this->field_service->delete($this->get('field_id'));
            $this->response(array('status' => true, 'data' => null, 'message' => $this->lang->line('deleted')));
        }
    }

    public function show_get() {
        if (!$this->get('field_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('field_id') . " " . $this->lang->line('required')));
        else {
            $field = $this->field_service->get($this->get('field_id'), $this->response->lang);
            $this->response(array('status' => true, 'data' => $field, 'message' => ""));
        }
    }

    public function check_availability_get() {
        if (!$this->get('field_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('field_id') . " " . $this->lang->line('required')));
        else {
            $date = $this->get('date');
            if (empty($date))
                $date = date('y-m-d');
            $available_times = $this->field_service->check_availability($this->get('field_id'), $date);
            $this->response(array('status' => true, 'data' => $available_times, 'message' => ""));
        }
    }

    public function get_by_company_get() {
        if (!$this->get('company_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('company_id') . " " . $this->lang->line('required')));
        else {
            $lon = (!$this->get('longitude')) ? 0.0 : $this->get('longitude');
            $lat = (!$this->get('latitude')) ? 0.0 : $this->get('latitude');
            $fields = $this->field_service->get_by_company($this->get('company_id'), $lon, $lat, $this->response->lang);
            $this->response(array('status' => true, 'data' => $fields, 'message' => ""));
        }
    }

    function upload_image_post() {
        $this->load->helper('image_uploader_helper');
        $image_file = upload_image($this);
        $image_name = $image_file['image']['upload_data']['file_name'];

        $this->load->model('Services/image_service');
        $image = $this->image_service->save_image($image_name);

        $this->response(array('status' => true, 'data' => array("image_id" => $image), "message" => $this->lang->line('image_saved')));
    }

    public function get_featured_places_get() {

        $fields = $this->field_service->get_featured_places($this->response->lang);
        $this->response(array('status' => true, 'data' => $fields, 'message' => ""));
    }

    function fields_management_post($primary_key = null) {

        $this->user_permissions->support_permission($this->current_user);
        $this->load->library('grocery_CRUD');
        try {
            $crud = new grocery_CRUD();

            $crud->set_theme('datatables')
                    ->set_table('field')
                    ->where('field.company_id', $primary_key)
                    ->where('field.deleted', 0)
                    ->set_subject($this->lang->line('item'))
                    ->columns('field_id', 'en_name', 'phone', 'open_time', 'close_time', 'games', 'amenities', 'featured_place')
                    ->order_by('field_id')
                    ->set_relation('company_id', 'company', 'en_name', array('deleted' => 0))
                    ->set_relation_n_n('games', 'field_game_type', 'game_type', 'field_id', 'game_type_id', 'en_name')
                    ->set_relation_n_n('amenities', 'field_amenity', 'amenity', 'field_id', 'amenity_id', 'en_name')
                    ->display_as('field_id', 'id')
                    ->display_as('en_description', 'description')
                    ->display_as('en_name', 'name')
                    ->display_as('max_capacity', 'capacity')
                    ->unset_edit_fields('ar_name', 'ar_description', 'deleted', 'company_id', 'featured_place')
                    ->unset_add_fields('ar_name', 'ar_description', 'deleted', 'featured_place')
                    ->field_type('open_time', 'time')
                    ->field_type('close_time', 'time')
                    ->required_fields('en_name')
                    ->callback_delete(array($this, 'delete_field'))
                    ->add_action('Gallery', base_url() . 'assets/images/gallery.png', '', '', array($this, 'view_images'))
                    ->unset_export()
                    ->unset_print();
            $output = $crud->render();
            $this->load->model("Services/company_service");
            $this->load->view('template.php', array(
                'view' => 'fields_management',
                'company' => $this->company_service->get($primary_key),
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files
                    )
            );
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    function fields_management_get($primary_key = null) {
        $this->fields_management_post($primary_key);
    }

    function view_images($primary_key, $row) {
        return site_url('/fields/field_images_management/' . $primary_key);
    }

    public function delete_field($primary_key) {
        $this->user_permissions->support_permission($this->current_user);
        $this->field_service->delete($primary_key);
        return true;
    }

    private $field_id;

    function field_images_management_post($primary_key = null, $operation = null) {
        $this->user_permissions->support_permission($this->current_user);
        $this->load->library('grocery_CRUD');
        $this->field_id = $primary_key;
        try {
            $crud = new grocery_CRUD();

            $crud->set_theme('datatables')
                    ->set_table('image')
                    ->where('field_id', $primary_key)
                    ->set_subject('image')
                    ->columns('name')
                    ->display_as('image_id', 'Id')
                    ->set_field_upload('name', 'assets/uploaded_images')
                    ->unset_edit_fields('field_id')
                    ->callback_before_insert(array($this, 'add_field_id'))
                    ->field_type('field_id', 'hidden')
                    ->unset_read()
                    ->unset_export()
                    ->unset_print();

            $output = $crud->render();
            $this->load->view('template.php', array(
                'view' => 'field_images_management',
                'field_id' => $this->field_id,
                'field' => $this->field_service->get($this->field_id),
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files
                    )
            );
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    function field_images_management_get($primary_key = null, $operation = null) {
        $this->field_images_management_post($primary_key, $operation = null);
    }

    function add_field_id($post_array) {
        $post_array['field_id'] = $this->field_id;
        return $post_array;
    }

}
