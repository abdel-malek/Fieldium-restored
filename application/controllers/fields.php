<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class fields extends REST_Controller {

    function __construct() {
        parent::__construct();

        $this->load->model("Services/field_service");
    }

    public function show_get() {
        if (!$this->get('field_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('field_id') . " " . $this->lang->line('required')));
        else {
            $field = $this->field_service->get($this->get('field_id'), $this->response->lang);
            $this->response(array('status' => true, 'data' => $field, 'message' => ""));
        }
    }
    public function get_by_company_get() {
        if (!$this->get('company_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('company_id') . " " . $this->lang->line('required')));
        else {
            $lon = (!$this->get('longitude')) ? 0.0 : $this->get('longitude');
            $lat = (!$this->get('latitude')) ? 0.0 : $this->get('latitude');
            $fields = $this->field_service->get_by_company($this->get('company_id'), $lon, $lat, $this->response->lang);
            $this->response(array('status' => true, 'data' => $fields, 'message' => ""));
        }
    }
    public function get_featured_places_get() {

        $fields = $this->field_service->get_featured_places($this->response->lang);
        $this->response(array('status' => true, 'data' => $fields, 'message' => ""));
    }

}
