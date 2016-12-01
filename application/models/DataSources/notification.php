<?php

class notification extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function get_notifications($player_id) {
        return $this->db->select("*")
                        ->from('notification')
                        ->where('player_id', $player_id)
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
