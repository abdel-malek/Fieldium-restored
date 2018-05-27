<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class companies extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/company_service");
        $this->load->model('Permissions/user_permissions');
       
    }

    public function create_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('name', 'Name', 'required|min_length[6]|max_length[30]');
        $this->form_validation->set_rules('phone', 'Phone', 'required');
        $this->form_validation->set_rules('address', 'Address', 'required');
        $this->form_validation->set_rules('longitude', 'Longitude', 'required');
        $this->form_validation->set_rules('latitude', 'Latitude', 'required');
        $this->form_validation->set_rules('area_id', 'Area', 'required');
        $this->form_validation->set_rules('description', 'Description', '');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $this->user_permissions->support_permission($this->current_user);
            $name = $this->input->post('name');
            $ar_name = $this->input->post('name');
            $phone = $this->input->post('phone');
            $address = $this->input->post('address');
            $ar_address = $this->input->post('address');
            $longitude = $this->input->post('longitude');
            $latitude = $this->input->post('latitude');
            $area_id = $this->input->post('area_id');
            $description = $this->input->post('description');
            $ar_description = $this->input->post('description');
            $image_name = "";
            $logo = "";
            try {
                $this->load->helper('image_uploader_helper');
                $image = upload_image($this);
                $image_name = $image['image']['upload_data']['file_name'];
                $logo = $image['logo']['upload_data']['file_name'];
            } catch (Uploading_Image_Exception $ex) {
                $image_name = "";
                $logo = "";
            }
            $company = $this->company_service
                    ->create(
                    $name, $ar_name, $phone, $address, $ar_address, $longitude, $latitude, $area_id, $description, $ar_description, $image_name, $logo, $this->response->lang
            );
            $this->response(array('status' => true, 'data' => $company, 'message' => $this->lang->line('created')));
        }
    }

    public function update_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('name', 'Name', 'required|min_length[6]|max_length[30]');
        $this->form_validation->set_rules('phone', 'Phone', 'required');
        $this->form_validation->set_rules('company_id', 'Company id', 'required');
        $this->form_validation->set_rules('address', 'Address', 'required');
        $this->form_validation->set_rules('longitude', 'Longitude', 'required');
        $this->form_validation->set_rules('latitude', 'Latitude', 'required');
        $this->form_validation->set_rules('area_id', 'Area', 'required');
        $this->form_validation->set_rules('description', 'Description', '');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $company_id = $this->input->post('company_id');
            $this->user_permissions->management_permission($this->current_user, $company_id);
            $name = $this->input->post('name');
            $ar_name = $this->input->post('name');
            $phone = $this->input->post('phone');
            $address = $this->input->post('address');
            $ar_address = $this->input->post('address');
            $longitude = $this->input->post('longitude');
            $latitude = $this->input->post('latitude');
            $area_id = $this->input->post('area_id');
            $description = $this->input->post('description');
            $ar_description = $this->input->post('description');
            $image_name = $this->input->post('image');
            $logo = $this->input->post('logo');
            $company = $this->company_service
                    ->update(
                    $company_id, $name, $ar_name, $phone, $address, $ar_address, $longitude, $latitude, $area_id, $description, $ar_description, $image_name, $logo, $this->response->lang
            );
            $this->response(array('status' => true, 'data' => $company, 'message' => $this->lang->line('updated')));
        }
    }

    public function update_location_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('company_id', 'Company id', 'required');
        $this->form_validation->set_rules('longitude', 'Longitude', 'required');
        $this->form_validation->set_rules('latitude', 'Latitude', 'required');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $company_id = $this->input->post('company_id');
            $this->user_permissions->management_permission($this->current_user, $company_id);
            $longitude = $this->input->post('longitude');
            $latitude = $this->input->post('latitude');
            $this->company_service
                    ->update_location(
                            $company_id, $longitude, $latitude
            );
            $company = $this->company_service
                    ->get($company_id, $this->response->lang);
            $this->response(array('status' => true, 'data' => $company, 'message' => $this->lang->line('updated')));
        }
    }

    public function show_get() {
        if (!$this->get('company_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('company_id') . " " . $this->lang->line('required')));
        else {
            $company = $this->company_service->get($this->get('company_id'), $this->response->lang);
            $this->response(array('status' => true, 'data' => $company, 'message' => ""));
        }
    }

    public function get_all_get() {
        $lon = (!$this->get('longitude')) ? 0.0 : $this->get('longitude');
        $lat = (!$this->get('latitude')) ? 0.0 : $this->get('latitude');
        $country = null;
        if ($this->get('country'))
            $country = $this->get('country');

        $companies = $this->company_service->get_all($lon, $lat, $this->response->lang, $country);
        $this->response(array('status' => true, 'data' => $companies, 'message' => ""));
    }

    public function get_nearby_companies_get() {
        if (!$this->get('longitude') || !$this->get('latitude'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('long_lat_req')));
        else {
            $lon = $this->get('longitude');
            $lat = $this->get('latitude');
            $companies = $this->company_service->get_all($lon, $lat, $this->response->lang);
            $this->response(array('status' => true, 'data' => $companies, 'message' => ""));
        }
    }

    public function delete_get() {
        if (!$this->get('company_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('company_id') . " " . $this->lang->line('required')));
        else {
            $this->user_permissions->management_permission($this->current_user, $this->get('company_id'));
            $this->company_service->delete($this->get('company_id'));
            $this->response(array('status' => true, 'data' => null, 'message' => $this->lang->line('deleted')));
        }
    }

    protected $country_select = UAE;

    public function companies_management_post($country = UAE, $operation = null) {
        if ($operation == "edit" || $operation == "update" || $operation == "update_validation" || $operation == "upload_file") {
            try {
                $this->user_permissions->is_company($this->current_user);
            } catch (Permission_Denied_Exception $e) {
                $this->user_permissions->support_permission($this->current_user);
            }
        } else {
            $this->user_permissions->support_permission($this->current_user);
        }
        $this->country_select = $country;
        $this->load->model("Services/area_service");
        $areas = $this->area_service->get_by_country("en", $country);
        $this->load->library('grocery_CRUD');
        try {
            $crud = new grocery_CRUD();

            $crud->set_theme('datatables');
            $crud->set_table('company');
            
            if ($this->session->userdata('lang') == 'arabic') {
                $crud->set_language('Arabic');
            } else {
                $crud->set_language('English');
            }
            if (count($areas) > 0) {
                $crud->where('(company.deleted = 0 and company.area_id =' . $areas[0]->area_id . ")");
                foreach ($areas as $area) {
                    $crud->or_where('(company.deleted = 0 and company.area_id =' . $area->area_id . ")");
                }
            }
            if ($this->session->userdata('lang') == 'arabic') {
                $crud->where("company.deleted", 0)
                        ->set_subject('شركة')
                        ->columns('company_id', 'en_name', 'phone', 'en_address', 'area_id', 'logo', 'image', 'location')
                        ->order_by('company_id')
                        ->display_as('company_id', 'id')
//                    ->display_as('country_id', 'Country')
                        ->display_as('en_name', 'الاسم')
                        ->display_as('en_address', 'العنوان')
                        ->display_as('en_description', 'الوصف')
                        ->display_as('area_id', 'المكان')
                        ->display_as('phone', 'رقم الهاتف')
                        ->display_as('logo', 'الشعار')
                        ->display_as('image', 'صورة')
                        ->display_as('location', 'الموقع')
//                    ->field_type('area_id', 'dropdown', $areas_array)
                        ->set_relation('area_id', 'area', 'en_name', array('country_id' => $country))
                        ->field_type('phone', 'integer')
//                    ->set_lang_string('list_delete', '')
//                    ->set_lang_string('list_edit', '')
                        ->unset_edit_fields('ar_description', 'deleted', 'ar_address', 'ar_name', 'longitude', 'latitude')
                        ->unset_add_fields('ar_description', 'deleted', 'ar_address', 'ar_name', 'longitude', 'latitude')
//                    ->set_relation('area_id', 'area', 'en_name')
                        ->set_field_upload('image', 'assets/uploaded_images/')
                        ->set_field_upload('logo', 'assets/uploaded_images/')
                        ->required_fields('en_name', 'phone', 'en_address', 'area_id', 'location')
                        ->callback_delete(array($this, 'delete_company'))
                        ->callback_column('company_id', array($this, '_callback_location_render'))
                        ->add_action('حقول', base_url() . 'assets/images/magnifier.png', '', 'read-icon', array($this, 'view_company_fields'))
                        ->unset_read()
                        ->unset_export()
                        ->unset_print();
            } else {
                $crud->where("company.deleted", 0)
                        ->set_subject('Company')
                        ->columns('company_id', 'en_name', 'phone', 'en_address', 'area_id', 'logo', 'image', 'location')
                        ->order_by('company_id')
                        ->display_as('company_id', 'id')
//                    ->display_as('country_id', 'Country')
                        ->display_as('en_name', 'Name')
                        ->display_as('en_address', 'Address')
                        ->display_as('en_description', 'Description')
                        ->display_as('area_id', 'Area')
//                    ->field_type('area_id', 'dropdown', $areas_array)
                        ->set_relation('area_id', 'area', 'en_name', array('country_id' => $country))
                        ->field_type('phone', 'integer')
//                    ->set_lang_string('list_delete', '')
//                    ->set_lang_string('list_edit', '')
                        ->unset_edit_fields('ar_description', 'deleted', 'ar_address', 'ar_name', 'longitude', 'latitude')
                        ->unset_add_fields('ar_description', 'deleted', 'ar_address', 'ar_name', 'longitude', 'latitude')
//                    ->set_relation('area_id', 'area', 'en_name')
                        ->set_field_upload('image', 'assets/uploaded_images/')
                        ->set_field_upload('logo', 'assets/uploaded_images/')
                        ->required_fields('en_name', 'phone', 'en_address', 'area_id', 'location')
                        ->callback_delete(array($this, 'delete_company'))
                        ->callback_column('company_id', array($this, '_callback_location_render'))
                        ->add_action('fields', base_url() . 'assets/images/magnifier.png', '', 'read-icon', array($this, 'view_company_fields'))
                        ->unset_read()
                        ->unset_export()
                        ->unset_print();
            }


            $output = $crud->render();
//            $this->config->set_item('language', 'arabic');
//              $ci =& get_instance();
//            $ci->load->helper('language');
//           $this->lang->load(array('views', 'controllers','form_validation'), 'arabic');
            $lang["companies_management"] = $this->lang->line('companies_management');

//            echo $this->lang->line('companies_management');
//            return;

            $this->load->view('template.php', array(
                'view' => 'companies_management',
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files,
                'country' => $country,
                'lang' => $lang
                    )
            );
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    public function companies_management_get($country = UAE, $operation = null) {
        $lang = $this->session->userdata('lang');

        $this->companies_management_post($country, $operation);
    }

    public function delete_company($primary_key) {
        $this->user_permissions->support_permission($this->current_user);
        $this->company_service->delete($primary_key);
        return true;
    }

    function view_company_fields($primary_key, $row) {
        return site_url('/fields/fields_management/' . $primary_key);
    }

    public function _callback_location_render($value, $row) {
        $company = $this->company_service->get($row->company_id, "en", $this->country_select);
        $row->company_id = $company->company_id;
        $row->location = "<a class='fieldium-color' onclick='pan($company->longitude, $company->latitude, $company->company_id);$(\"#map_modal\").modal(\"show\");'>"
                . "<img width='25px' height='25px' src='" . base_url() . "assets/images/location.png' />  map"
                . "</a>";
        return $company->company_id;
    }

    public function update_location_get($company_id, $lng, $lat) {
        $this->company_service->update_location($company_id, $lng, $lat);
        redirect('companies/companies_management');
    }

    function upload_image_post() {
        $this->load->helper('image_uploader_helper');
        $image_file = upload_image($this);
        if (!isset($image_file['image']))
            $this->response(array('status' => false, 'data' => null, "message" => "Uploading error"));
        else
            $image_name = $image_file['image']['upload_data']['file_name'];

        $this->response(array('status' => true, 'data' => array("image_name" => $image_name), "message" => $this->lang->line('image_saved')));
    }

}
