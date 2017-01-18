<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class notifications extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/notification_service");
        $this->load->helper('notification_helper');
    }

    public function get_notifications_get() {
        $notifications = $this->notification_service->get_notifications($this->current_user->player_id);
        $this->response(array('status' => true, 'data' => $notifications, 'message' => ""));
    }

    public function send_post() {
        $notification_helper = new NotificationHelper();
        $notification_helper->send_notification_to_android_device(array('eRaI_SKbdXk:APA91bGD5ReDssiQWOqU-WivbTig3KuAa_q8yzeKXIAYTAs-XzGdeH2nrCW2Fxv5Dcz_TVJsTZsT8-UKMgCDjQ8UpykmwEZb36sHeaAdibsUdR4SrOdtpGaD3pt_caiUQ9QmyM_Z3ydX'),"hello", null);
    }
}
