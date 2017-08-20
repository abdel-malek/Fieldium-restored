<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class areas extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/area_service");
    }

    public function get_all_get() {
        $areas = $this->area_service->get_all($this->response->lang);
        $this->response(array('status' => true, 'data' => $areas, 'message' => ""));
    }
}
