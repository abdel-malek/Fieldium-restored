<?php

/**
 * @author Amal Abdulraouf
 */
class company_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/company');
    }

    public function create(
    $name, $phone, $address, $longitude, $latitude, $area_id, $description, $image_name, $logo
    ) {
        $company_id = $this->company->add(array(
            'name' => $name,
            'phone' => $phone,
            'description' => $description,
            'address' => $address,
            'longitude' => $longitude,
            'latitude' => $latitude,
            'area_id' => $area_id,
            'image' => $image_name,
            'logo' => $logo
        ));
        $company = $this->company->get($company_id);
        return $company;
    }

    public function get($company_id) {
        $company = $this->company->get($company_id);
        if (!$company)
            throw new Company_Not_Found_Exception ();
        return $company;
    }

    public function get_all($lon=0.0, $lat=0.0) {
        $companies = $this->company->get_all($lon, $lat);
        return $companies;
    }

    public function update(
    $company_id, $name, $phone, $address, $longitude, $latitude, $area_id, $description, $image_name, $logo
    ) {
        $this->get($company_id);
        $r = $this->company->update($company_id, array(
            'name' => $name,
            'phone' => $phone,
            'description' => $description,
            'address' => $address,
            'longitude' => $longitude,
            'latitude' => $latitude,
            'area_id' => $area_id,
            'image' => $image_name,
            'logo' => $logo
        ));
        $company = $this->company->get($company_id);
        return $company;
    }

    public function delete($company_id) {
        $this->get($company_id);

        $fields = $this->field->get_by_company($company_id);
        foreach ($fields as $field) {
            $this->field->update($field->field_id, array('deleted' => 1));
        }

        $this->company->update($company_id, array(
            'deleted' => 1
        ));
    }

}

?>
