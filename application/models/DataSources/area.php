<?php

class area extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function get_all() {
        return $this->db->select("*")
                        ->from('area')
                        ->get()->result();
    }

}
