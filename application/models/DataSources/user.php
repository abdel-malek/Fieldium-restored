<?php

class user extends CI_Model {

       private $LANG;

    public function __construct() {
        $CI = & get_instance();
        $this->LANG = $CI->language;
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
        return $this->db->select("*, ".$this->LANG."_name as country_name,concat('".base_url() . UPLOADED_IMAGES_PATH_URL."',image) as image_url",false)
                        ->from('user')
                        ->join('country', 'country.country_id = user.country_id', 'left')
                        ->where('username', $username)
                        ->where('password', $password)
                        ->where('active', 1)
                        ->get()->row();
    }

    public function check_password($user_id, $str) {
        return $this->db->select("*")
                        ->from("user")
                        ->where('user_id', $user_id)
                        ->where('password', $str)
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

    public function save_token($data) {
        if ($this->db->insert('tokens', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else
            throw new Database_Exception();
    }

    public function check_token($user, $token) {
        return $this->db->get_where('tokens', array('token' => $token, 'user_id' => $user))->row();
    }

}
