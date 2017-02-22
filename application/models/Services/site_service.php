<?php

/**
 * @author Amal Abdulraouf
 */
class site_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/site_source');
    }

    public function about(){
        return $this->site_source->about();
    }
}

?>
