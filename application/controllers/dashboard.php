<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class dashboard extends REST_Controller {

    function __construct() {
        parent::__construct();
    }

    public function index_get() {
        if ($this->current_user)
            redirect('companies/companies_management/'.UAE);
        else
            $this->load->view('index');
    }
    
    public function language_post(){
        $this->session->set_userdata('lang', $this->input->post('lang'));
//        $this->session->set_userdata('language', 'arabic');
        $this->response(array(
            'status' => true,
            'data' => $this->session->userdata('lang'),
            'message' => ''
                )
        );
    }
    
}
