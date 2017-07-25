<?php

/**
 * @author Amal Abdulraouf
 */
class country_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/country');
    }

    public function get_all($lang = "en") {
        $results = $this->country->get_all($lang);

        return $results;
    }

    public function get($country_id, $lang = "en") {
        $country = $this->country->get($country_id, $lang);
        if (!$country)
            throw new Parent_Exception("Country type is not found");
        return $country;
    }

}

?>
