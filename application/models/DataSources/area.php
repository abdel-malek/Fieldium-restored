<?php

class area extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function get_all($lang="en") {
        return $this->db->select("area_id, ".$lang."_name as name")
                        ->from('area')
                        ->get()->result();
    }

}
