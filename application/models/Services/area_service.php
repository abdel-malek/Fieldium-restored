<?php

/**
 * @author Amal Abdulraouf
 */
class area_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/area');
    }
  
    public function get_all($lang="en") {
        $areas = $this->area->get_all($lang);
        return $areas;
    }
    public function get_by_country($lang="en", $country) {
        $areas = $this->area->get_by_country($lang, $country);
        return $areas;
    }

}

?>
