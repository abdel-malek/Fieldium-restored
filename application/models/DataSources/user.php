<?php

class user extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function create($data) {
        if ($this->db->insert('user', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else
            throw new Database_Exception();
    }

    public function get($id) {
        return $this->db->select("user.*")
                        ->from('user')
                        ->where('user_id', $id)
//                        ->where('active', 1)
                        ->get()->row();
    }

    public function update($user_id, $data) {
        $res = $this->db->where(array('user_id' => $user_id))
                ->update('user', $data);
        if ($res)
            return $res;
        else
            throw new DatabaseException();
    }

    public function login($username, $password) {
        return $this->db->select("*")
                        ->from('user')
                        ->where('username', $username)
                        ->where('password', $password)
                        ->where('active', 1)
                        ->get()->row();
    }

    public function check_authentication($username, $password) {
        $user = $this->db->select('*')
                        ->from('user')
                        ->where('username', $username)
                        ->where('password', $password)
                        ->where('active', 1)
                        ->get()->row();
        if ($user)
            return $user;
        else
            return false;
    }

}
