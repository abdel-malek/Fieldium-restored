<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class countries extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/country_service");
        $this->load->model('Permissions/user_permissions');
    }

    public function get_all_get() {
        $countries = $this->country_service->get_all($this->response->lang);
        $this->response(array('status' => true, 'data' => $countries, 'message' => ""));
    }

    public function get_get() {
        if (!$this->get('country_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('country_id') . " " . $this->lang->line('required')));
        else {
            $country = $this->country_service->get($this->get('country_id'), $this->response->lang);
            $this->response(array('status' => true, 'data' => $country, 'message' => ""));
        }
    }
}
