<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class fields extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/field_service");
        $this->load->model('Permissions/user_permissions');
        $this->load->library('grocery_CRUD');
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
            //   $this->user_permissions->management_permission($this->current_user, $company_id);
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
            // $this->user_permissions->management_permission($this->current_user, $field_id);
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
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('field_id')." ".$this->lang->line('required')));
        else {
            $this->field_service->delete($this->get('field_id'));
            $this->response(array('status' => true, 'data' => null, 'message' => $this->lang->line('deleted')));
        }
    }

    public function show_get() {
        if (!$this->get('field_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('field_id')." ".$this->lang->line('required')));
        else {
            $field = $this->field_service->get($this->get('field_id'), $this->response->lang);
            $this->response(array('status' => true, 'data' => $field, 'message' => ""));
        }
    }
    
    public function check_availability_get() {
//        if (!$this->get('field_id'))
//            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('field_id')." ".$this->lang->line('required')));
//        else {
//            $date = $this->get('date');
//            if(empty($date)) $date = date('y-m-d');
//            $available_times = $this->field_service->check_availability($this->get('field_id'), $date);
//            $this->response(array('status' => true, 'data' => $available_times, 'message' => ""));
//        }
    }

    public function get_by_company_get() {
        if (!$this->get('company_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('company_id')." ".$this->lang->line('required')));
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

}
