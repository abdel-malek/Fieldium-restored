<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class games extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/game_service");
    }

    public function get_all_get() {
        $games = $this->game_service->get_all($this->response->lang);
        $this->response(array('status' => true, 'data' => $games, 'message' => ""));
    }
}
