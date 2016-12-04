<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class searches extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/search_service");
    }

    public function get_searches_get() {
        if ($this->current_user) {
            $searches = $this->search_service->get_searches_by_user($this->current_user->player_id);
            $this->response(array('status' => true, 'data' => $searches, 'message' => ""));
        } else {
            $searches = $this->search_service->get_searches_by_token($this->get('token'));
            $this->response(array('status' => true, 'data' => $searches, 'message' => ""));
        }
    }

    public function get_searches_by_user_get() {
        $searches = $this->search_service->get_searches_by_user($this->current_user->player_id);
        $this->response(array('status' => true, 'data' => $searches, 'message' => ""));
    }

}
