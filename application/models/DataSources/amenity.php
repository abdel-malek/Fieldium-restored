<?php

class amenity extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function add_field_amenity($data) {
        if ($this->db->insert('field_amenity', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

    public function get_field_amenities($field_id) {
        return $this->db->select("*")
                        ->from('field_amenity')
                        ->join('amenity', 'amenity.amenity_id = field_amenity.amenity_id')
                        ->where('field_id', $field_id)
                        ->get()->result();
    }

    public function get($amenity_id) {
        return $this->db->select("*")
                        ->from('amenity')
                        ->where('amenity_id', $amenity_id)
                        ->get()->row();
    }
    
    public function get_all() {
        return $this->db->select("*")
                        ->from('amenity')
                        ->get()->result();
    }

    public function delete_field_amenities($field_id) {
        $this->db->delete("field_amenity", array('field_id' => $field_id));
    }

}
