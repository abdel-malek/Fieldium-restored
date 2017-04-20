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

    public function create(
    $name, $ar_name, $phone, $address, $ar_address, $longitude, $latitude, $area_id, $description, $ar_description, $image_name, $logo, $lang
    ) {
        $company_id = $this->company->add(array(
            'en_name' => $name,
            'ar_name' => $ar_name,
            'phone' => $phone,
            'en_description' => $description,
            'ar_description' => $ar_description,
            'en_address' => $address,
            'ar_address' => $ar_address,
            'longitude' => $longitude,
            'latitude' => $latitude,
            'area_id' => $area_id,
            'image' => $image_name,
            'logo' => $logo
        ));
        $company = $this->get($company_id, $lang);
        return $company;
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

    public function update(
    $company_id, $name, $ar_name, $phone, $address, $ar_address, $longitude, $latitude, $area_id, $description, $ar_description, $image_name, $logo, $lang
    ) {
        $this->get($company_id, $lang);
        $r = $this->company->update($company_id, array(
            'en_name' => $name,
            'ar_name' => $ar_name,
            'phone' => $phone,
            'en_description' => $description,
            'ar_description' => $ar_description,
            'en_address' => $address,
            'ar_address' => $ar_address,
            'longitude' => $longitude,
            'latitude' => $latitude,
            'area_id' => $area_id,
            'image' => $image_name,
            'logo' => $logo
        ));
        $company = $this->get($company_id, $lang);
        return $company;
    }

    
    
    public function delete($company_id) {
        $this->get($company_id, "en");

        $fields = $this->field->get_by_company($company_id, 0, 0, "en");
        foreach ($fields as $field) {
            $this->field_service->delete($field->field_id);
        }

        $this->company->update($company_id, array(
            'deleted' => 1
        ));
    }

    public function update_location($company_id, $lng, $lat) {
        $this->company->update($company_id, array(
            'longitude' => $lng,
            'latitude' => $lat
                )
        );
    }
    
    public function start_and_end_time($company_id){
        return $this->company->start_and_end_time($company_id);
    }

}

?>
