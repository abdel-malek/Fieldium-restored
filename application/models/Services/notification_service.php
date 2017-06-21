<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class notification_service extends CI_Model {

    public function __construct() {
        $this->load->model('DataSources/notification');
        $this->load->helper('notification_helper');
        $this->load->library('send_sms');
    }

    public function send_notification_4_all_users($message, $data = null) {
        $this->load->model('Services/user_service');
        $users = $this->user_service->get_customers();
        $tokens = array();
        $notification_helper = new NotificationHelper();
        foreach ($users as $user) {
            if ($user->device_id) {
                $notification_helper->send_notification_to_device(array($player->token), $message, $data, $player->os);
            }
        }

        $notification_helper->send_notification_to_android_device($tokens, $message, $data);
    }

    public function send_notification_4customer($customer_id, $message, $data, $message_key, $type = 1) {
        $this->load->model('Services/player_service');
        $player = $this->player_service->get($customer_id);
        //$this->send_sms->send_sms($message["ar"], $user->phone, 2);
//        $this->load->library('email');
//        $this->email->from("info@estore.com");
//        $this->email->to($user->email);
//        $this->email->subject($this->lang->line('estore'));
//        $this->email->message($message["ar"]."<br>".$message["en"]);
//        $this->email->send();
        $message = $message[$player->lang];
        
        $notification_helper = new NotificationHelper();
        $notification_helper->send_notification_to_device(array($player->token), $message, $data, $player->os, $type);
//        $this->notification->save_notification(array(
//            'player_id' => $player->player_id,
//            'content' => $message,
//            'booking_id' => $data["booking"]->booking_id
//                )
//        );
    }

    public function send_notification_admin($company_id, $message, $data, $message_key) {
        $this->load->model('Services/user_service');
        $users = $this->user_service->get_company_users($company_id);
        $notification_helper = new NotificationHelper();
        foreach ($users as $value) {
            $tokens = $this->user_service->get_tokens($value->user_id);
            foreach ($tokens as $value) {
                $notification_helper->send_notification_to_device(array($value->token), $message, $data, $value->os);
            }
        }
    }
    
    public function send_notification_support($message, $data, $message_key) {
        $this->load->model('Services/user_service');
        $users = $this->user_service->get_support_users();
        $notification_helper = new NotificationHelper();
        foreach ($users as $value) {
            $tokens = $this->user_service->get_tokens($value->user_id);
            foreach ($tokens as $value) {
                $notification_helper->send_notification_to_device(array($value->token), $message, $data, $value->os);
            }
        }
    }

    public function register_notification_token($user_id, $token) {
        $this->load->model('DataSource/user');
        $this->user->register_notification_token($user_id, $token);
        return $this->user->get($user_id);
    }

    public function get_notifications($player_id) {
        $notifications = $this->notification->get_notifications($player_id);
        foreach ($notifications as $noti)
            if ($noti->logo != null)
                $noti->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $noti->logo;
        return $notifications;
    }

}
