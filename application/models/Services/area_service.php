<?php

/**
 * @author Amal Abdulraouf
 */
class area_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/area');
    }
  
    public function get_all() {
        $areas = $this->area->get_all();
        return $areas;
    }

}

?>
