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

}

?>
