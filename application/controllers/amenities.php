<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class amenities extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/amenity_service");
        $this->load->library('grocery_CRUD');
    }

    public function get_all_get() {
        $amenities = $this->amenity_service->get_all();
        $this->response(array('status' => true, 'data' => $amenities, 'message' => ""));
    }

}
