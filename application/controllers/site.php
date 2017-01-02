<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class site extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model('Services/message_service');
        $this->load->model('Services/field_service');
        $this->load->model('Services/game_service');
    }

    public function index_get() {
        if ($this->current_user)
            redirect('dashboard');
        else {
            $featured_places = $this->field_service->get_featured_places();
            $games = $this->game_service->get_all();
            $this->load->view('index', array(
                'featured_places' => $featured_places,
                'games' => $games
                    )
            );
        }
    }

    public function send_message_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('name', 'Name', 'required|min_length[6]|max_length[30]');
        $this->form_validation->set_rules('email', 'Email', 'required|valid_email');
        $this->form_validation->set_rules('subject', 'Subject', 'required|min_length[6]|max_length[30]');
        $this->form_validation->set_rules('text', 'Text', 'required|min_length[10]');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $this->message_service->save($this->post('name'), $this->post('email'), $this->post('subject'), $this->post('text'));
            $this->response(array('status' => true, 'data' => null, 'message' => $this->lang->line('message_sent')));
        }
    }

}
