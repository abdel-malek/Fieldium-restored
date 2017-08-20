<?php

class message extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function save($data) {
        if ($this->db->insert('website_messages', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

}
