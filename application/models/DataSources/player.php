<?php

class player extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function verify($phone, $code) {
        return $this->db->select("player.*")
                        ->from('player')
                        ->where('phone', $phone)
                        ->where('verification_code', $code)
                        ->get()->row();
    }

    public function update($player_id, $data) {
        $res = $this->db->where(array('player_id' => $player_id))
                ->update('player', $data);
        if ($res)
            return $res;
        else
            throw new DatabaseException();
    }

    public function get($id, $lang="en") {
        return $this->db->select("player.*")
                        ->from('player')
                        ->where('player_id', $id)
                        ->get()->row();
    }

    public function check_authentication($phone, $device_id) {
        $user = $this->db->select('*')
                        ->from('player')
                        ->where('phone', $phone)
                        ->where('device_id', $device_id)
                        ->where('active', 1)
                        ->get()->row();
        if ($user)
            return $user;
        else
            return false;
    }

    public function get_by_phone($phone) {
        return $this->db->select("*")
                        ->from('player')
                        ->where('phone', $phone)
                        ->get()->row();
    }

    public function register($data) {
        if ($this->db->insert('player', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else
            throw new Database_Exception();
    }

}
