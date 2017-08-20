<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class companies extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/company_service");
    }

    public function show_get() {
        if (!$this->get('company_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('company_id') . " " . $this->lang->line('required')));
        else {
            $company = $this->company_service->get($this->get('company_id'), $this->response->lang);
            $this->response(array('status' => true, 'data' => $company, 'message' => ""));
        }
    }

    public function get_all_get() {
        $lon = (!$this->get('longitude')) ? 0.0 : $this->get('longitude');
        $lat = (!$this->get('latitude')) ? 0.0 : $this->get('latitude');
        $companies = $this->company_service->get_all($lon, $lat, $this->response->lang);
        $this->response(array('status' => true, 'data' => $companies, 'message' => ""));
    }

    public function get_nearby_companies_get() {
        if (!$this->get('longitude') || !$this->get('latitude'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('long_lat_req')));
        else {
            $lon = $this->get('longitude');
            $lat = $this->get('latitude');
            $companies = $this->company_service->get_all($lon, $lat, $this->response->lang);
            $this->response(array('status' => true, 'data' => $companies, 'message' => ""));
        }
    }
}
