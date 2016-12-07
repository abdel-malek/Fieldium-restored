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
            $date = $this->input->post('date');
            $start = $this->input->post('start');
            $duration = $this->input->post('duration');
            $notes = $this->input->post('notes');
            $manually = false;
            $player_id = $this->current_user->player_id;
            $user_id = null;
            $booking = $this->booking_service
                    ->create(
                    $field_id, $player_id, $date, $start, $duration, $notes, $user_id, $manually, $this->response->lang
            );
            $this->response(array(
                'status' => true,
                'data' => $booking,
                'message' => $this->lang->line('created')
                    )
            );
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
            $this->user_permissions->management_permission($this->current_user, $company_id);
            $date = $this->input->post('date');
            $start = $this->input->post('start');
            $duration = $this->input->post('duration');
            $notes = $this->input->post('notes');
            $name = $this->input->post('player_name');
            $phone = $this->input->post('player_phone');
            $manually = true;
            $player_id = $this->player_service->create($name, $phone);
            $user_id = $this->current_user->user_id;
            $booking = $this->booking_service
                    ->create(
                    $field_id, $player_id, $date, $start, $duration, $notes, $user_id, $manually, $this->response->lang
            );
            $sms = $this->post('sms_option') ? true : false;
            if ($sms == true)
                $this->send_sms->send_sms($phone, "");
            $this->response(array('status' => true, 'data' => $booking, 'message' => $this->lang->line('created')));
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
            $this->user_permissions->update_booking_permission($this->current_user);
            $booking_id = $this->input->post('booking_id');
            $field_id = $this->input->post('field_id');
            $date = $this->input->post('date');
            $start = $this->input->post('start');
            $duration = $this->input->post('duration');
            $notes = $this->input->post('notes');
            $user_id = $this->current_user->user_id;
            $booking = $this->booking_service
                    ->update(
                    $booking_id, $field_id, $date, $start, $duration, $notes, $user_id, $this->response->lang
            );
            $this->response(array(
                'status' => true,
                'data' => $booking,
                'message' => $this->lang->line('created')
                    )
            );
        }
    }

    public function delete_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('booking_id') . " " . $this->lang->line('required')));
        else {
            $this->booking_service->delete($this->get('booking_id'));
            $this->response(array('status' => true, 'data' => null, 'message' => $this->lang->line('deleted')));
        }
    }

    public function decline_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('booking_id') . " " . $this->lang->line('required')));
        else {
            $this->booking_service->decline($this->get('booking_id'));
            $this->response(array('status' => true, 'data' => null, 'message' => $this->lang->line('declined')));
        }
    }

    public function approve_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('booking_id') . " " . $this->lang->line('required')));
        else {
            $booking = $this->booking_service->approve($this->get('booking_id'), $this->response->lang);
            $this->response(array('status' => true, 'data' => $booking, 'message' => $this->lang->line('approved')));
        }
    }

    public function show_get() {
        if (!$this->get('booking_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('booking_id') . " " . $this->lang->line('required')));
        else {
            $booking = $this->booking_service->get($this->get('booking_id'), $this->response->lang);
            $this->response(array('status' => true, 'data' => $booking, 'message' => ""));
        }
    }

    public function my_bookings_get() {
        $bookings = $this->booking_service->get_my_bookings(1, $this->response->lang); //$this->current_user->player_id, $this->response->lang);
        $this->response(array('status' => true, 'data' => $bookings, 'message' => ""));
    }

    public function company_bookings_get() {
        $bookings = $this->booking_service->company_bookings(2, $this->response->lang); //$this->current_user->company_id, $this->response->lang);
        $this->response(array('status' => true, 'data' => $bookings, 'message' => ""));
    }

}
