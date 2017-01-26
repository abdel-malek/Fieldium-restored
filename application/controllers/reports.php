<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class reports extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/report_service");
        $this->load->model("Services/field_service");
        $this->load->model('Permissions/user_permissions');
    }

    public function reservations_report_get() {
        if (!$this->get('company_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('company_id') . " " . $this->lang->line('required')));
        $company_id = $this->input->get('company_id');
        $this->user_permissions->management_permission($this->current_user, $company_id);
        if (!$this->get('from_date'))
            $from_date = date('Y-m-d');
        else
            $from_date = $this->input->get("from_date");
        $from_date = date('Y-m-d', strtotime($from_date));
        if (!$this->get('to_date'))
            $to_date = date('Y-m-d');
        else
            $to_date = $this->input->get("to_date");
        $to_date = date('Y-m-d', strtotime($to_date));
        $report = $this->report_service->reservations_report($company_id, $from_date, $to_date);
        $this->response(array('status' => true, 'data' => $report, 'message' => ""));
    }
    
    public function field_reservations_report_get() {
        if (!$this->get('field_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('field_id') . " " . $this->lang->line('required')));
        $field_id = $this->input->get('field_id');
        $field = $this->field_service->get($field_id);
        $this->user_permissions->management_permission($this->current_user, $field->company_id);
        if (!$this->get('from_date'))
            $from_date = date('Y-m-d');
        else
            $from_date = $this->input->get("from_date");
        $from_date = date('Y-m-d', strtotime($from_date));
        if (!$this->get('to_date'))
            $to_date = date('Y-m-d');
        else
            $to_date = $this->input->get("to_date");
        $to_date = date('Y-m-d', strtotime($to_date));
        $report = $this->report_service->field_reservations_report($field_id, $from_date, $to_date);
        $this->response(array('status' => true, 'data' => $report, 'message' => ""));
    }

}
