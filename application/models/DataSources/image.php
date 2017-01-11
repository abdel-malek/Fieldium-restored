<?php

class image extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    
    public function add_image($data) {
        if ($this->db->insert('image', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

    public function update($image_id, $data) {
        $res = $this->db
                ->where(array('image_id' => $image_id))
                ->update('image', $data);
        if ($res) {
            return $res;
        } else {
            throw new DatabaseException();
        }
    }
    
    public function get_images($field_id) {
        return $this->db->select("*")
                        ->from('image')
                        ->where('field_id', $field_id)
                        ->get()->result();
    }
    
    public function delete_field_images($field_id) {
        $res = $this->db
                ->where(array('field_id' => $field_id))
                ->update('image', array('field_id'=>null));
        if ($res) {
            return $res;
        } else {
            throw new DatabaseException();
        }
    }
    
    public function save_image($image_name) 
    {
//                var_dump($image_name);die();
         if ($this->db->insert('image', array('name'=>$image_name['name']))) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }
}
