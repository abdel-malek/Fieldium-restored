<?php

class area extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function get_all($lang = "en") {
        return $this->db->select("area_id, " . $lang . "_name as name, "
                                . "IFNULL((SELECT company.company_id from company where "
                                . "company.area_id = area.area_id group by company_id limit 1),0 ) as active", false)
                        ->from('area')
                        ->get()->result();
    }

}
