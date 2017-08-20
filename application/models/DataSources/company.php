<?php

class company extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function get_all($lon, $lat, $lang="en") {
        
        $this->db->select(ENTITY::COMPANY.", "
                .$lang."_name as name, "
                .$lang."_description as description, "
                .$lang."_address as address, "
                . " sqrt(pow(longitude- $lon,2) + pow(latitude - $lat,2)) as distance, "
                        . "(SELECT count(field_id) FROM field "
                        . "where company.company_id = field.company_id AND field.deleted = 0"
                        . ") as fields_number", false)
                ->from('company ')
                ->where('company.deleted', 0);
        if ($lat == 0 || $lon == 0)
            $this->db->order_by("fields_number desc");
        else
            $this->db->order_by("distance ASC, fields_number desc");
        return $this->db->get()->result();
    }

    public function get($company_id, $lang="en") {
        return $this->db->select(ENTITY::COMPANY.", "
                .$lang."_name as name, "
                .$lang."_description as description, "
                .$lang."_address as address, "
                                . "(SELECT count(field_id) FROM field "
                                . "where company.company_id = field.company_id AND field.deleted = 0"
                                . ") as fields_number")
                        ->from('company')
                        ->where('company.company_id', $company_id)
                        ->where('company.deleted', false)
                        ->get()->row();
    }

}
