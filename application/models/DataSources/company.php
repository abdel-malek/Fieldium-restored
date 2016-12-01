<?php

class company extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function get_all($lon, $lat) {
        $this->db->select("c.*, sqrt(pow(longitude- $lon,2) + pow(latitude - $lat,2)) as distance, "
                        . "(SELECT count(field_id) FROM field "
                        . "where c.company_id = field.company_id AND field.deleted = 0"
                        . ") as fields_number", false)
                ->from('company c')
                ->where('c.deleted', 0);
        if ($lat == 0 || $lon == 0)
            $this->db->order_by("fields_number desc");
        else
            $this->db->order_by("distance ASC, fields_number desc");
        return $this->db->get()->result();
    }

    public function get($company_id) {
        return $this->db->select("c.*, "
                                . "(SELECT count(field_id) FROM field "
                                . "where c.company_id = field.company_id AND field.deleted = 0"
                                . ") as fields_number")
                        ->from('company c')
                        ->where('c.company_id', $company_id)
                        ->where('c.deleted', false)
                        ->get()->row();
    }

    public function add($data) {
        if ($this->db->insert('company', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

    public function update($company_id, $data) {
        $res = $this->db
                ->where(array('company_id' => $company_id))
                ->update('company', $data);
        if ($res) {
            return $res;
        } else {
            throw new DatabaseException();
        }
    }

}
