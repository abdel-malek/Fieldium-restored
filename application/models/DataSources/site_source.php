<?php

class site_source extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function about(){
        return $this->db->select('mobile, phone, email')->get('settings')->row();
    }

}
