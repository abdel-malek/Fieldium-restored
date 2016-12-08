<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class dashboard extends REST_Controller {

    function __construct() {
        parent::__construct();
    }

    public function index_get() {
        if ($this->current_user)
            redirect('companies/companies_management');
        else
            $this->load->view('index');
    }

}
