<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class bookings extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/booking_service");
        $this->load->model('Permissions/user_permissions');
        $this->load->library('grocery_CRUD');
        $this->load->library('send_sms');
    }

    public function create_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('field_id', 'Field id', 'required');
        $this->form_validation->set_rules('date', 'Date', 'required');
        $this->form_validation->set_rules('start', 'Start time', 'required');
        $this->form_validation->set_rules('duration', 'Duration', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $field_id = $this->input->post('field_id');
            //   $this->user_permissions->management_permission($this->current_user, $company_id);
            $date = $this->input->post('date');
            $start = $this->input->post('start');
            $duration = $this->input->post('duration');
            $notes = $this->input->post('notes');
            $manually = false;
            $player_id = 1; //$this->current_user->user_id;
            $user_id = null;
            $booking = $this->booking_service
                    ->create(
                    $field_id, $player_id, $date, $start, $duration, $notes, $user_id, $manually
            );
            $this->response(array('status' => true, 'data' => $booking, 'message' => "The booking has been succesfully created."));
        }
    }
    
    public function create_manually_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('field_id', 'Field id', 'required');
        $this->form_validation->set_rules('date', 'Date', 'required');
        $this->form_validation->set_rules('start', 'Start time', 'required');
        $this->form_validation->set_rules('duration', 'Duration', 'required');
        $this->form_validation->set_rules('player_name', 'Player name', 'required');
        $this->form_validation->set_rules('player_phone', 'Player phone', '');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $field_id = $this->input->post('field_id');
            //   $this->user_permissions->management_permission($this->current_user, $company_id);
            $date = $this->input->post('date');
            $start = $this->input->post('start');
            $duration = $this->input->post('duration');
            $notes = $this->input->post('notes');
            $name = $this->input->post('player_name');
            $phone = $this->input->post('player_phone');
            $manually = true;
            $player_id = 2;//$this->player_service->create($name, $phone);
            $user_id = $this->current_user->user_id;
            $booking = $this->booking_service
                    ->create(
                    $field_id, $player_id, $date, $start, $duration, $notes, $user_id, $manually
            );
            $sms = $this->post('sms_option') ? true : false;
            if($sms == true)
                $this->send_sms->send_sms($phone, "");
            $this->response(array('status' => true, 'data' => $booking, 'message' => "The booking has been succesfully created."));
        }
    }

    public function update_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('booking_id', 'Booking id', 'required');
        $this->form_validation->set_rules('field_id', 'Field id', 'required');
        $this->form_validation->set_rules('date', 'Date', 'required');
        $this->form_validation->set_rules('start', 'Start time', 'required');
        $this->form_validation->set_rules('duration', 'Duration', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $booking_id = $this->input->post('booking_id');
            $field_id = $this->input->post('field_id');
            $company_id = $this->input->post('company_id');
            // $this->user_permissions->management_permission($this->current_user, $field_id);
            $name = $this->input->post('name');
            $phone = $this->input->post('phone');
            $hour_rate = $this->input->post('hour_rate');
            $open_time = $this->input->post('open_time');
            $cloes_time = $this->input->post('close_time');
            $area_x = $this->input->post('area_x');
            $area_y = $this->input->post('area_y');
            $max_capacity = $this->input->post('max_capacity');
            $description = $this->input->post('description');
            $amenities = $this->input->post('amenities');
            $images = $this->input->post('images');
            $image_name = "";
            $logo = "";
            $field = $this->booking_service
                    ->update(
                    $field_id, $player_id, $date, $start, $duration, $notes, $user_id, $manually
            );
            $this->response(array('status' => true, 'data' => $field, 'message' => "The informations has been updated."));
        }
    }

    public function delete_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => "The booking id is required"));
        else {
            $this->booking_service->delete($this->get('booking_id'));
            $this->response(array('status' => true, 'data' => null, 'message' => "The booking has been deleted."));
        }
    }

    public function decline_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => "The booking id is required"));
        else {
            $this->booking_service->decline($this->get('booking_id'));
            $this->response(array('status' => true, 'data' => null, 'message' => "The booking has been declined."));
        }
    }

    public function approve_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => "The booking id is required"));
        else {
            $booking = $this->booking_service->approve($this->get('booking_id'));
            $this->response(array('status' => true, 'data' => $booking, 'message' => "The booking has been approved."));
        }
    }

    public function show_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => "The booking id is required"));
        else {
            $booking = $this->booking_service->get($this->get('booking_id'));
            $this->response(array('status' => true, 'data' => $booking, 'message' => ""));
        }
    }

    public function my_bookings_get() {
        $bookings = $this->booking_service->get_my_bookings(1);//$this->current_user->player_id);
        $this->response(array('status' => true, 'data' => $bookings, 'message' => ""));
    }
    
    public function company_bookings_get() {
        $bookings = $this->booking_service->company_bookings(2);//$this->current_user->company_id);
        $this->response(array('status' => true, 'data' => $bookings, 'message' => ""));
    }

}
