<?php

class player extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function login($username, $password) {
        return $this->db->select("*")
                        ->from('user')
                        ->where('user_name', $username)
                        ->where('password', $password)
                        ->where('active', 1)
                        ->get()->row();
    }

    public function check_authentication($username, $password) {
        $user = $this->db->select('*')
                        ->from('user')
                        ->where('user_name', $username)
                        ->where('password', $password)
                        ->where('active', 1)
                        ->get()->row();
        if($user) return $user;
        else return false;
    }

    /////////////////////////////////////////////////////////////

    public function register($data) {
        if ($this->db->insert('user', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else
            throw new Database_Exception();
    }

    public function get_by_phone($phone) {
        return $this->db->select("user_id,name,user_type_id,phone,email,address, active,token")
                        ->from('user')
                        ->where('phone', $phone)
                        ->get()->row();
    }

    public function verify($phone, $code) {
        return $this->db->select("user_id,name,user_type_id,phone,email,address,token")
                        ->from('user')
                        ->where('phone', $phone)
                        ->where('activation_code', $code)
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

    public function remove_token($token) {
        $res = $this->db->where(array('token' => $token))
                ->update('user', array('token' => ''));
        return $res;
    }

    public function get($id) {
        return $this->db->select("user_id,name,email,role_id,active,phone,address")
                        ->from('user')
                        ->where('user_id', $id)
                        ->get()->row();
    }

    public function get_all_info($id) {
        return $this->db->select('*')
                        ->from('user')
                        ->where('user_id', $id)
                        ->get()->row();
    }

    public function get_by_mail($email) {
        return $this->db->select('*')
                        ->from('user')
                        ->where('email', $email)
                        ->get()->row();
    }

    public function check_email_authentication($email, $password) {
        return $this->db->select('*')
                        ->from('user')
                        ->where('email', $email)
                        ->where('password', $password)
                        ->where('active', 1)
                        ->get()->row();
    }

    public function check_phone_authentication($phone, $device_id) {
        return $this->db->select('*')
                        ->from('user')
                        ->where('phone', $phone)
                        ->where('token', $device_id)
                        ->where('active', 1)
                        ->get()->row();
    }

    public function get_customers() {
        return $this->db->select("user_id,name,email")
                        ->from('user')
                        ->where('user_type_id', ROLE::CUSTOMER)
                        ->get()->result();
    }

    public function register_notification_token($user_id, $token) {
        $res = $this->db
                ->where(array('user_id' => $user_id))
                ->update('`user`', array('token' => $token));
        if (!$res) {
            throw new InvalidJSONException();
        }
    }

}
