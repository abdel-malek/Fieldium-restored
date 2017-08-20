<?php

/**
 * @author Amal Abdulraouf
 */
class company_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/company');
        $this->load->model('DataSources/field');
    }

    public function get($company_id, $lang = "en") {
        $company = $this->company->get($company_id, $lang);
        if (!$company)
            throw new Company_Not_Found_Exception($lang);
        if ($company->image != null)
            $company->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $company->image;
        if ($company->logo != null)
            $company->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $company->logo;
        return $company;
    }

    public function get_all($lon = 0.0, $lat = 0.0, $lang = "en") {
        $companies = $this->company->get_all($lon, $lat, $lang);
        foreach ($companies as $company) {
            if ($company->image != null)
                $company->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $company->image;
            if ($company->logo != null)
                $company->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $company->logo;
        }
        return $companies;
    }
}

?>
