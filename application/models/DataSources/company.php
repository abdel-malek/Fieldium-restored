<?php

class company extends CI_Model {

    private $country;

    public function __construct() {
        $this->load->database();
        $CI = & get_instance();
        $this->country = $CI->user_country;
    }

    public function get_all($lon, $lat, $lang = "en", $country = null) {

        $this->db->select(ENTITY::COMPANY . ", "
                        . "company." . $lang . "_name as name, "
                        . "company." . $lang . "_name as text, "
                        . "company.company_id as id, "
                        . $lang . "_description as description, "
                        . $lang . "_address as address, "
                        . " sqrt(pow(longitude- $lon,2) + pow(latitude - $lat,2)) as distance, "
                        . "(SELECT count(field_id) FROM field "
                        . "where company.company_id = field.company_id AND field.deleted = 0"
                        . ") as fields_number", false)
                ->from('company ')
                ->join('area', 'company.area_id = area.area_id');

        if ($country == null)
            $country = $this->country;
        $this->db->where('area.country_id', $country);
        $this->db->where('company.deleted', 0);
        if ($lat == 0 || $lon == 0)
            $this->db->order_by("fields_number desc");
        else
            $this->db->order_by("distance ASC, fields_number desc");
        return $this->db->get()->result();
    }

    public function get($company_id, $lang = "en", $country = null) {
        if ($country == null)
            $country = $this->country;
//        var_dump($country);
        return $this->db->select(ENTITY::COMPANY . ", "
                                . "company." . $lang . "_name as name, "
                                . $lang . "_description as description, "
                                . $lang . "_address as address, "
                                . "(SELECT count(field_id) FROM field "
                                . "where company.company_id = field.company_id AND field.deleted = 0"
                                . ") as fields_number")
                        ->from('company')
                        ->join('area', 'company.area_id = area.area_id')
//                        ->where('area.country_id', $country)
                        ->where('company.company_id', $company_id)
                        ->where('company.deleted', false)
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

    public function start_and_end_time($company_id) {
        return $this->db->select('min(LEAST(field.open_time,field.close_time)) as min_time, max(GREATEST(field.open_time,field.close_time)) as max_time', false)
                        ->from('field')
                        ->join('company', 'company.company_id = field.company_id')
                        ->where('field.company_id', $company_id)
                        ->where('field.deleted', 0)
                        ->where('company.deleted', 0)
                        ->get()->row();
    }

}
