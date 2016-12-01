<?php

class field extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function get_all($lang="en") {
        return $this->db->select(ENTITY::FIELD.", "
                .$lang."_name as name, "
                .$lang."_description as description"
                )
                        ->from('field')
                        ->where('deleted', 0)
                        ->get()->result();
    }

    public function get($field_id, $lang="en") {
        return $this->db->select(ENTITY::FIELD.", "
                .$lang."_name as name, "
                .$lang."_description as description"
                )
                        ->from('field')
                        ->where('field_id', $field_id)
                        ->where('deleted', false)
                        ->get()->row();
    }

    public function get_by_company($company_id, $lon, $lat, $lang="en") {
        $this->db->select(ENTITY::FIELD.", "
                ."field.".$lang."_name as name, "
                ."field.".$lang."_description as description, "
                ."sqrt(pow(longitude- $lon,2) + pow(latitude - $lat,2)) as distance", false)
                ->from('field')
                ->join('company', 'company.company_id = field.company_id')
                ->where('field.deleted', 0)
                ->where('field.company_id', $company_id);
        if ($lat == 0 || $lon == 0)
            $this->db->order_by("field_id");
        else
            $this->db->order_by("distance ASC");
        return $this->db->get()->result();
    }

    public function add($data) {
        if ($this->db->insert('field', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

    public function update($field_id, $data) {
        $res = $this->db
                ->where(array('field_id' => $field_id))
                ->update('field', $data);
        if ($res) {
            return $res;
        } else {
            throw new DatabaseException();
        }
    }

}
