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

    public function show_report_get($name) {
        $this->load->library('grocery_CRUD');
        $crud = new grocery_CRUD();
        $crud->set_theme('datatables')
                ->set_table('booking');
        $output = $crud->render();
        $this->load->view('template.php', array(
            'view' => 'reports/' . $name,
            'fields' => $this->field_service->get_by_company($this->current_user->company_id, 0, 0, $this->response->lang)
                )
        );
    }

    public function reservations_report_get() {

        if ($this->current_user->role_id == ROLE::ADMIN)
            $company_id = $this->current_user->company_id;
        else {
            if (!$this->get('company_id'))
                $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('company_id') . " " . $this->lang->line('required')));
            $company_id = $this->input->get('company_id');
        }

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
            $field_id = 0;
        else {
            $field_id = $this->input->get('field_id');
            $field = $this->field_service->get($field_id);
        }
//        $this->user_permissions->management_permission($this->current_user, $this->get('company_id'));
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
        $report = $this->report_service->field_reservations_report($this->current_user->company_id, $field_id, $from_date, $to_date);
        $this->response(array('status' => true, 'data' => $report, 'message' => ""));
    }

    public function declined_reservations_report_get() {
        if (!$this->get('field_id'))
            $field_id = 0;
        else {
            $field_id = $this->input->get('field_id');
            $field = $this->field_service->get($field_id);
        }
//        $this->user_permissions->management_permission($this->current_user, $this->get('company_id'));
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
        $report = $this->report_service->declined_reservations_report($this->current_user->company_id, $field_id, $from_date, $to_date);
        $this->response(array('status' => true, 'data' => $report, 'message' => ""));
    }

}
