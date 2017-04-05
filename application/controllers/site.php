<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class site extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model('Services/field_service');
        $this->load->model('Services/game_service');
    }

    public function index_get() {
        if ($this->current_user) {
            if ($this->current_user->role_id == ROLE::SUPPORT)
                redirect('dashboard');
            else if ($this->current_user->role_id == ROLE::ADMIN) {
                $this->session->set_userdata(array('company_id' => $this->current_user->company_id));
                redirect('bookings/calendar');
            }
        } else {
            $featured_places = $this->field_service->get_featured_places();
            $games = $this->game_service->get_all();
            $this->load->view('index', array(
                'featured_places' => $featured_places,
                'games' => $games
                    )
            );
        }
    }

    public function about_get() {
        $this->load->model('Services/site_service');
        $this->response(array('status' => true, 'data' => $this->site_service->about(), "message" => ""));
    }

}
