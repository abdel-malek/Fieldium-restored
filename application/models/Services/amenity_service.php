<?php

/**
 * @author Amal Abdulraouf
 */
class amenity_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/amenity');
    }

    public function get($amenity_id) {
        $amenity = $this->amenity->get($amenity_id);
        if (!$amenity)
            throw new Amenity_Not_Found_Exception ();
        return $amenity;
    }

    public function get_all() {
        $amenities = $this->amenity->get_all();
        return $amenities;
    }
}

?>
