<?php

/**
 * @author Amal Abdulraouf
 */
class amenity_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/amenity');
    }

    public function get($amenity_id, $lang="en") {
        $amenity = $this->amenity->get($amenity_id, $lang);
        if (!$amenity)
            throw new Amenity_Not_Found_Exception ($lang);
        return $amenity;
    }

    public function get_all($lang="en") {
        $amenities = $this->amenity->get_all($lang);
        return $amenities;
    }
}

?>
