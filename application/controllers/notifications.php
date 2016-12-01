<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class notifications extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/notification_service");
    }

    public function get_notifications_get() {
        $notifications = $this->notification_service->get_notifications($this->current_user->player_id);
        $this->response(array('status' => true, 'data' => $notifications, 'message' => ""));
    }

}
