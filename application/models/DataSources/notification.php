<?php

class notification extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function get_notifications($player_id) {
        return $this->db->select("notification.date as notification_time, company.en_name as company_name, company.logo, company.en_address as address, booking.state_id , booking.start, booking.duration, "
                                . "(booking.duration * field.hour_rate) as total, booking.date, field.en_name as field_name")
                        ->from('notification')
                        ->join('booking', 'booking.booking_id = notification.booking_id')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('company', 'company.company_id = field.company_id')
                        ->where('notification.player_id', $player_id)
                        ->get()->result();
    }

    public function save_notification($data) {
        if ($this->db->insert('notification', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

}
