<?php

class search extends CI_Model {

    public function __construct() {
        $this->load->database();
        $this->load->model("Services/player_service");
    }

    public function get_searches_by_user($player_id) {
        $player = $this->player_service->get($player_id);
        return $this->db->select("*")
                        ->from('search')
                       // ->where('player_id', $player_id)
                        ->where('token', $player->token)
                        ->get()->result();
    }

    public function get_searches_by_token($token) {
        return $this->db->select("*")
                        ->from('search')
                        ->where('token', $token)
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
