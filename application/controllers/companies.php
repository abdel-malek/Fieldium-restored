<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class companies extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/company_service");
        $this->load->model('Permissions/user_permissions');
        $this->load->library('grocery_CRUD');
    }

    public function create_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('name', 'Name', 'required|min_length[6]|max_length[30]');
        $this->form_validation->set_rules('phone', 'Phone', 'required');
        $this->form_validation->set_rules('address', 'Address', 'required');
        $this->form_validation->set_rules('longitude', 'Longitude', 'required');
        $this->form_validation->set_rules('latitude', 'Latitude', 'required');
        $this->form_validation->set_rules('area_id', 'Area', 'required');
        $this->form_validation->set_rules('description', 'Description', '');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            //   $this->user_permissions->support_permission($this->current_user);
            $name = $this->input->post('name');
            $phone = $this->input->post('phone');
            $address = $this->input->post('address');
            $longitude = $this->input->post('longitude');
            $latitude = $this->input->post('latitude');
            $area_id = $this->input->post('area_id');
            $description = $this->input->post('description');
            $image_name = "";
            $logo = "";
            try {
                $this->load->helper('image_uploader_helper');
                $image = upload_image($this);
                $image_name = $image['image']['upload_data']['file_name'];
                $logo = $image['logo']['upload_data']['file_name'];
            } catch (Uploading_Image_Exception $ex) {
                $image_name = "";
                $logo = "";
            }
            $company = $this->company_service
                    ->create(
                    $name, $phone, $address, $longitude, $latitude, $area_id, $description, $image_name, $logo
            );
            $this->response(array('status' => true, 'data' => $company, 'message' => "The company has been created."));
        }
    }

    public function update_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('name', 'Name', 'required|min_length[6]|max_length[30]');
        $this->form_validation->set_rules('phone', 'Phone', 'required');
        $this->form_validation->set_rules('company_id', 'Company id', 'required');
        $this->form_validation->set_rules('address', 'Address', 'required');
        $this->form_validation->set_rules('longitude', 'Longitude', 'required');
        $this->form_validation->set_rules('latitude', 'Latitude', 'required');
        $this->form_validation->set_rules('area_id', 'Area', 'required');
        $this->form_validation->set_rules('description', 'Description', '');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $company_id = $this->input->post('company_id');
            // $this->user_permissions->management_permission($this->current_user, $company_id);
            $name = $this->input->post('name');
            $phone = $this->input->post('phone');
            $address = $this->input->post('address');
            $longitude = $this->input->post('longitude');
            $latitude = $this->input->post('latitude');
            $area_id = $this->input->post('area_id');
            $description = $this->input->post('description');
            $image_name = "";
            $logo = "";
            try {
                $this->load->helper('image_uploader_helper');
                $image = upload_image($this);
                $image_name = $image['image']['upload_data']['file_name'];
                $logo = $image['logo']['upload_data']['file_name'];
            } catch (Uploading_Image_Exception $ex) {
                $image_name = "";
                $logo = "";
            }
            $company = $this->company_service
                    ->update(
                    $company_id, $name, $phone, $address, $longitude, $latitude, $area_id, $description, $image_name, $logo
            );
            $this->response(array('status' => true, 'data' => $company, 'message' => "The informations has been updated."));
        }
    }

    public function show_get() {
        if (!$this->get('company_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => "The company id is required"));
        else {
            $company = $this->company_service->get($this->get('company_id'));
            $this->response(array('status' => true, 'data' => $company, 'message' => ""));
        }
    }

    public function get_all_get() {
        $lon = (!$this->get('longitude')) ? 0.0 : $this->get('longitude');
        $lat = (!$this->get('latitude')) ? 0.0 : $this->get('latitude');
        $companies = $this->company_service->get_all($lon, $lat);
        $this->response(array('status' => true, 'data' => $companies, 'message' => ""));
    }

    public function get_nearby_companies_get() {
        if (!$this->get('longitude') || !$this->get('latitude'))
            $this->response(array('status' => false, 'data' => null, 'message' => "The longitude and latitude are required"));
        else {
            $lon = $this->get('longitude');
            $lat = $this->get('latitude');
            $companies = $this->company_service->get_all($lon, $lat);
            $this->response(array('status' => true, 'data' => $companies, 'message' => ""));
        }
    }

}
