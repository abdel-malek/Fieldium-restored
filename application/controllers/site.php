<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class site extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model('Services/user_service');
    }

    public function index_get() {
            $this->load->view('index');
    }

    public function notifications_page_get() {
        $this->load->view('template', array('view' => 'send_notification'));
    }

}
