<?php

class area extends CI_Model {

    private $country;

    public function __construct() {
        $this->load->database();
        $CI = & get_instance();
        $this->country = $CI->user_country;
    }

    public function get_all($lang = "en") {
        return $this->db->select("area_id, " . $lang . "_name as name, "
                                . "IFNULL((SELECT company.company_id from company join field on field.company_id = company.company_id where "
                                . "company.area_id = area.area_id group by company_id limit 1),0 ) as active", false)
                        ->from('area')
                        ->where('area.country_id', $this->country)
                        ->get()->result();
    }
    public function get_by_country($lang = "en", $country) {
        return $this->db->select("area_id, " . $lang . "_name as name, "
                                . "IFNULL((SELECT company.company_id from company join field on field.company_id = company.company_id where "
                                . "company.area_id = area.area_id group by company_id limit 1),0 ) as active", false)
                        ->from('area')
                        ->where('area.country_id', $country)
                        ->get()->result();
    }

}
