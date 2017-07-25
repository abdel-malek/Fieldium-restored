<?php

class country extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function get_all($lang="en") {
        return $this->db->select("*, ".$lang."_name as name")
                        ->from('country')
                        ->get()->result();
    }

    public function get($country_id, $lang="en") {
        return $this->db->select("*, ".$lang."_name as name")
                        ->from('country')
                        ->where('country_id', $country_id)
                        ->get()->row();
    }

}
