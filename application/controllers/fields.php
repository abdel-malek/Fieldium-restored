<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class fields extends REST_Controller {

    function __construct() {
        parent::__construct();

        $this->load->model("Services/field_service");
        $this->load->model('Permissions/user_permissions');
        $this->load->library('send_sms');
    }

    public function send_sms_get() {
        $this->send_sms->send_sms("", "", "");
    }

    public function create_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('company_id', 'Company id', 'required');
        $this->form_validation->set_rules('name', 'Name', 'required|min_length[6]|max_length[30]');
        $this->form_validation->set_rules('phone', 'Phone', 'required');
        $this->form_validation->set_rules('hour_rate', 'Hour Rate', 'required');
        $this->form_validation->set_rules('open_time', 'Open Time', 'required|min_length[7]|max_length[8]|callback_validate_time');
        $this->form_validation->set_rules('close_time', 'Close Time', 'required|min_length[7]|max_length[8]|callback_validate_time');
        $this->form_validation->set_rules('area_x', 'Area X', 'required');
        $this->form_validation->set_rules('area_y', 'Area Y', 'required');
        $this->form_validation->set_rules('games_types', 'games types', 'required');
        $this->form_validation->set_rules('max_capacity', 'Max Capacity', 'required|integer');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {

            $company_id = $this->input->post('company_id');
            $this->user_permissions->management_permission($this->current_user, $company_id);
            $name = $this->input->post('name');
            $ar_name = $this->input->post('name');
            $phone = $this->input->post('phone');
            $hour_rate = $this->input->post('hour_rate');
            $open_time = $this->input->post('open_time');
            $cloes_time = $this->input->post('close_time');
            $area_x = $this->input->post('area_x');
            $area_y = $this->input->post('area_y');
            $max_capacity = $this->input->post('max_capacity');
            $description = $this->input->post('description');
            $ar_description = $this->input->post('description');
            $amenities = $this->input->post('amenities');
            $images = $this->input->post('images');
            $games_types = $this->input->post('games_types');
            $auto_confirm = $this->input->post('auto_confirm');
            if (strlen($open_time) == 7)
                $open_time = "0" . $open_time;
            if (strlen($cloes_time) == 7)
                $cloes_time = "0" . $cloes_time;
            if ($cloes_time == $open_time) {
                $open_time = "00:00:00";
                $cloes_time = "23:59:00";
            }
            $field = $this->field_service
                    ->create(
                    $company_id, $name, $ar_name, $phone, $hour_rate, $open_time, $cloes_time, $area_x, $area_y, $max_capacity, $description, $ar_description, $images, $amenities, $games_types, $auto_confirm, $this->response->lang, $this->input->post('children')
            );
            $this->response(array('status' => true, 'data' => $field, 'message' => $this->lang->line('created')));
        }
    }

    public function update_post() {
        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_rules('company_id', 'Company id', 'required');
        $this->form_validation->set_rules('field_id', 'Field id', 'required');
        $this->form_validation->set_rules('name', 'Name', 'required|min_length[6]|max_length[30]');
        $this->form_validation->set_rules('phone', 'Phone', 'required');
        $this->form_validation->set_rules('hour_rate', 'Hour Rate', 'required');
        $this->form_validation->set_rules('open_time', 'Open Time', 'required|min_length[7]|max_length[8]|callback_validate_time');
        $this->form_validation->set_rules('close_time', 'Close Time', 'required|min_length[7]|max_length[8]|callback_validate_time');
        $this->form_validation->set_rules('area_x', 'Area X', 'required');
        $this->form_validation->set_rules('area_y', 'Area Y', 'required');
        $this->form_validation->set_rules('games_types', 'games types', 'required');
        $this->form_validation->set_rules('max_capacity', 'Max Capacity', 'required|integer');
        if (!$this->form_validation->run()) {
            throw new Validation_Exception(validation_errors());
        } else {
            $field_id = $this->input->post('field_id');
            $company_id = $this->input->post('company_id');
            $this->user_permissions->management_permission($this->current_user, $company_id);
            $name = $this->input->post('name');
            $ar_name = $this->input->post('name');
            $phone = $this->input->post('phone');
            $hour_rate = $this->input->post('hour_rate');
            $open_time = $this->input->post('open_time');
            $cloes_time = $this->input->post('close_time');
            $area_x = $this->input->post('area_x');
            $area_y = $this->input->post('area_y');
            $max_capacity = $this->input->post('max_capacity');
            $description = $this->input->post('description');
            $ar_description = $this->input->post('description');
            $amenities = $this->input->post('amenities');
            $images = $this->input->post('images');
            $games_types = $this->input->post('games_types');
            $auto_confirm = $this->input->post('auto_confirm');
            if (strlen($open_time) == 7)
                $open_time = "0" . $open_time;
            if (strlen($cloes_time) == 7)
                $cloes_time = "0" . $cloes_time;
            if ($cloes_time == $open_time) {
                $open_time = "00:00:00";
                $cloes_time = "23:59:00";
            }
            $field = $this->field_service
                    ->update(
                    $field_id, $name, $ar_name, $phone, $hour_rate, $open_time, $cloes_time, $area_x, $area_y, $max_capacity, $description, $ar_description, $images, $amenities, $games_types, $auto_confirm, $this->response->lang
            );
            $this->response(array('status' => true, 'data' => $field, 'message' => $this->lang->line('updated')));
        }
    }

    public function delete_get() {
        if (!$this->get('field_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('field_id') . " " . $this->lang->line('required')));
        else {
            $field = $this->field_service->get($this->get('field_id'));
            $this->user_permissions->management_permission($this->current_user, $field->company_id);
            $this->field_service->delete($this->get('field_id'));
            $this->response(array('status' => true, 'data' => null, 'message' => $this->lang->line('deleted')));
        }
    }

    public function show_get() {
        if (!$this->get('field_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('field_id') . " " . $this->lang->line('required')));
        else {
            $field = $this->field_service->get($this->get('field_id'), $this->response->lang);
            $this->response(array('status' => true, 'data' => $field, 'message' => ""));
        }
    }

    public function check_availability_get() {
        if (!$this->get('field_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('field_id') . " " . $this->lang->line('required')));
        else if (!$this->get('game_type'))
            $this->response(array('status' => false, 'data' => null, 'message' => "the game type " . $this->lang->line('required')));
        else {
            $date = $this->get('date');
            $date = date_format(date_create($date), 'Y-m-d');
//            if (strtotime($date) < strtotime(date('Y-m-d')))
//                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid date"));
            $game_type = $this->input->get('game_type');
            $available_times = $this->field_service->check_availability($this->get('field_id'), $date, $game_type);
            $busy_times = $this->field_service->busy_range($this->get('field_id'), $date);

            $this->response(array('status' => true, 'data' =>
                array(
                    'available' => $available_times,
                    'busy' => $busy_times
                ), 'message' => ""
                    )
            );
        }
    }

    public function busy_range_get() {
        if (!$this->get('field_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('field_id') . " " . $this->lang->line('required')));
        else {
            $date = $this->get('date');
            $date = date_format(date_create($date), 'Y-m-d');
//            if (strtotime($date) < strtotime(date('Y-m-d')))
//                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid date"));
            $busy_times = $this->field_service->busy_range($this->get('field_id'), $date);
            $this->response(array('status' => true, 'data' => $busy_times, 'message' => ""));
        }
    }

    public function get_by_company_get() {
        if (!$this->get('company_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('company_id') . " " . $this->lang->line('required')));
        else {
            $lon = (!$this->get('longitude')) ? 0.0 : $this->get('longitude');
            $lat = (!$this->get('latitude')) ? 0.0 : $this->get('latitude');
            $fields = $this->field_service->get_by_company($this->get('company_id'), $lon, $lat, $this->response->lang);
            $this->response(array('status' => true, 'data' => $fields, 'message' => ""));
        }
    }

    public function get_by_company_with_timing_get() {
        $game = $this->get('game_type');
        if (!$this->get('game_type'))
            $game = 0;
        if (!$this->get('company_id'))
            $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('company_id') . " " . $this->lang->line('required')));
        $timing = $this->get('timing');
//        if ($timing == 0)
//            $this->response(array('status' => false, 'data' => null, 'message' => 'The timing is required.'));
        $duration = $this->get('duration');
        if ($timing == 2) {
            if (!$this->get('date'))
                $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('date') . " " . $this->lang->line('required')));
            $date = $this->get('date');
            if (strtotime($date) < strtotime(date('Y-m-d')))
                $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line("Invalid date")));
        } else if ($timing == 1) {
            if (!$this->get('start') || !$this->get('duration') || !$this->get('date') || !$this->validate_time($this->get('start')))
                $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('timing_issue')));
            $start = $this->get('start');
            $this->load->model("Services/game_service");
            $g = $this->game_service->get($game);
            if ($duration < $g->minimum_duration)
                $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line('min_duration') . $g->minimum_duration . $this->lang->line('minutes')));
            $date = $this->get('date');
            if (strtotime($date) < strtotime(date('Y-m-d')))
                $this->response(array('status' => false, 'data' => null, 'message' => $this->lang->line("Invalid date")));
            if (strlen($start) <= 7)
                $start = "0" . $start;
        }
        $start = $this->get('start');
        $date = $this->get('date');
        $lon = (!$this->get('longitude')) ? 0.0 : $this->get('longitude');
        $lat = (!$this->get('latitude')) ? 0.0 : $this->get('latitude');
        $fields = $this->field_service->get_by_company_with_timing($game, $this->get('company_id'), $timing, $start, $date, $duration, $lon, $lat, $this->response->lang);
        $this->response(array('status' => true, 'data' => $fields, 'message' => ""));
    }

    function upload_image_post() {
        $this->load->helper('image_uploader_helper');
        $image_file = upload_image($this);
        if (!isset($image_file['image']))
            $this->response(array('status' => false, 'data' => null, "message" => "Uploading error"));
        else
            $image_name = $image_file['image']['upload_data']['file_name'];

        $this->load->model('Services/image_service');
        $image = $this->image_service->save_image($image_name);

        $this->response(array('status' => true, 'data' => array("image_id" => $image), "message" => $this->lang->line('image_saved')));
    }

    public function get_featured_places_get() {

        $fields = $this->field_service->get_featured_places($this->response->lang);
        $this->response(array('status' => true, 'data' => $fields, 'message' => ""));
    }

    public function get_children_get() {

        $fields = $this->field_service->get_children($this->get('field_id'));
        $this->response(array('status' => true, 'data' => $fields, 'message' => ""));
    }

    private $COMPANY_ID;

    function fields_management_post($primary_key = null, $operation = null) {

        try {
            $this->user_permissions->management_permission($this->current_user, $primary_key);
        } catch (Permission_Denied_Exception $e) {
            $this->user_permissions->support_permission($this->current_user);
        }
        $this->COMPANY_ID = $primary_key;
        $this->load->library('grocery_CRUD');
        try {
            $crud = new grocery_CRUD();
              if ($this->session->userdata('lang') == 'arabic') {
                $crud->set_language('arabic');
                 $crud->set_theme('datatables')
                    ->set_table('field')
                    ->set_subject('ملعب')
                    ->where('field.company_id', $primary_key)
                    ->where('field.deleted', 0)
                    ->columns('field_id', 'en_name', 'children', 'phone', 'open_time', 'close_time', 'games', 'amenities', 'featured_place')
                    ->order_by('field_id')
                    ->set_relation('company_id', 'company', 'en_name', array('deleted' => 0))
                    ->set_relation_n_n('games', 'field_game_type', 'game_type', 'field_id', 'game_type_id', 'en_name')
                    ->set_relation_n_n('amenities', 'field_amenity', 'amenity', 'field_id', 'amenity_id', 'en_name')
//                    ->set_relation_n_n('fields', 'field', 'field', 'parent_field', 'field_id', 'en_name', null, array('field.company_id' => $primary_key, 'field.deleted' => 0))
                    ->display_as('field_id', 'الرقم تسلسلي')
                    ->display_as('company_id', 'الشركة')
                    ->display_as('en_description', 'الوصف')
                         ->display_as('ar_description', 'الوصف بالعربي')
                         ->display_as('deleted', 'محذوف')
                         ->display_as('auto_confirm', 'الموافقة تلقائيا')
                    ->display_as('en_name', 'الاسم')
                         ->display_as('ar_name', 'الاسم بالعربي')
                         ->display_as('phone', 'رقم الهاتف')
                         ->display_as('open_time', 'وقت الفتح')
                         ->display_as('close_time', 'وقت الاغلاق')
                          ->display_as('games', 'الالعاب')
                         ->display_as('amenities', 'وسائل الراحة')
                         ->display_as('featured_place', 'مكان مميز')
                         ->display_as('hour_rate', 'معدل الساعات')
                          ->display_as('children', 'ملعب فرعي')
                         ->display_as('area_x', 'المكان (أ)')
                          ->display_as('area_y', 'المكان (ب)')
                      
                    ->display_as('max_capacity', 'السعة')
                    ->unset_edit_fields('parent_field', 'ar_name', 'auto_confirm', 'ar_description', 'deleted', 'company_id', 'featured_place')
                    ->unset_add_fields('parent_field', 'ar_name', 'ar_description', 'auto_confirm', 'deleted', 'featured_place')
                    ->field_type('open_time', 'time')
                    ->field_type('close_time', 'time')
                    ->field_type('hour_rate', 'integer')
                    ->field_type('phone', 'integer')
                    ->field_type('area_x', 'integer')
                    ->field_type('area_y', 'integer')
                    ->field_type('max_capacity', 'integer')
                    ->callback_before_insert(array($this, 'add_update_callback'))
                    ->callback_before_update(array($this, 'add_update_callback'))
                    ->required_fields('company_id', 'en_name', 'phone', 'open_time', 'close_time', 'games', 'max_capacity', 'hour_rate')
                    ->callback_delete(array($this, 'delete_field'))
                    ->callback_column('children', array($this, '_callback_children_render'))
//                    ->add_action('active', '', '', 'recieve-icon', array($this, 'active_callback'))
                    ->add_action('المعرض', base_url() . 'assets/images/gallery.png', '', '', array($this, 'view_images'))
                    ->unset_export()
//                    ->unset_add()
                    ->unset_print();
            } else {
                $crud->set_language('english');
                 $crud->set_theme('datatables')
                    ->set_table('field')
                    ->set_subject('field')
                    ->where('field.company_id', $primary_key)
                    ->where('field.deleted', 0)
                    ->columns('field_id', 'en_name', 'children', 'phone', 'open_time', 'close_time', 'games', 'amenities', 'featured_place')
                    ->order_by('field_id')
                    ->set_relation('company_id', 'company', 'en_name', array('deleted' => 0))
                    ->set_relation_n_n('games', 'field_game_type', 'game_type', 'field_id', 'game_type_id', 'en_name')
                    ->set_relation_n_n('amenities', 'field_amenity', 'amenity', 'field_id', 'amenity_id', 'en_name')
//                    ->set_relation_n_n('fields', 'field', 'field', 'parent_field', 'field_id', 'en_name', null, array('field.company_id' => $primary_key, 'field.deleted' => 0))
                    ->display_as('field_id', 'id')
                    ->display_as('company_id', 'company')
                    ->display_as('en_description', 'Description')
                    ->display_as('en_name', 'Name')
                    ->display_as('max_capacity', 'Capacity')
                    ->unset_edit_fields('parent_field', 'ar_name', 'auto_confirm', 'ar_description', 'deleted', 'company_id', 'featured_place')
                    ->unset_add_fields('parent_field', 'ar_name', 'ar_description', 'auto_confirm', 'deleted', 'featured_place')
                    ->field_type('open_time', 'time')
                    ->field_type('close_time', 'time')
                    ->field_type('hour_rate', 'integer')
                    ->field_type('phone', 'integer')
                    ->field_type('area_x', 'integer')
                    ->field_type('area_y', 'integer')
                    ->field_type('max_capacity', 'integer')
                    ->callback_before_insert(array($this, 'add_update_callback'))
                    ->callback_before_update(array($this, 'add_update_callback'))
                    ->required_fields('company_id', 'en_name', 'phone', 'open_time', 'close_time', 'games', 'max_capacity', 'hour_rate')
                    ->callback_delete(array($this, 'delete_field'))
                    ->callback_column('children', array($this, '_callback_children_render'))
//                    ->add_action('active', '', '', 'recieve-icon', array($this, 'active_callback'))
                    ->add_action('Gallery', base_url() . 'assets/images/gallery.png', '', '', array($this, 'view_images'))
                    ->unset_export()
//                    ->unset_add()
                    ->unset_print();
            }
           
            if (!($operation == 'insert_validation' || $operation == 'insert' || $operation == 'add')) {
                $this->session->unset_userdata('fields');
            } else {
                if ($this->session->userdata('fields'))
                    $crud->callback_after_insert(array($this, 'add_children_callback'));
            }
            $output = $crud->render();

            $this->load->model("Services/company_service");
            $this->load->view('template.php', array(
                'view' => 'fields_management',
                'company' => $this->company_service->get($primary_key),
                'fields' => $this->field_service->get_by_company($primary_key),
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files
                    )
            );
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    function fields_management_get($primary_key = null, $operation = null) {
        $this->fields_management_post($primary_key, $operation);
    }

    public function _callback_children_render($value, $row) {
        $children = $this->field_service->get_children($row->field_id);
        if (count($children) == 0)
            return "";
        else
            return "<button class='btn btn-default' onclick='show_children(" . $row->field_id . ",\"" . $row->en_name . "\")'>Yes</button>";
    }

    function add_children_callback($post_array, $primary_key) {
        $fields = json_decode($this->session->userdata('fields'));

        foreach ($fields as $field)
            $this->field_service->add_child($primary_key, $field);
    }

    function add_update_callback($post_array) {
        if ($post_array['open_time'] == $post_array['close_time']) {
            $post_array['open_time'] = "00:00:00";
            $post_array['close_time'] = "23:59:00";
        }
        if ($post_array['area_x'] == "") {
            $post_array['area_x'] = 0;
        }
        if ($post_array['area_y'] == "") {
            $post_array['area_y'] = 0;
        }
        return $post_array;
    }

    function active_callback($primary_key, $row) {

        return site_url('/fields/active/' . $primary_key);
    }

    function view_images($primary_key, $row) {
        return site_url('/fields/field_images_management/' . $primary_key);
    }

    public function delete_field($primary_key) {
        $this->user_permissions->support_permission($this->current_user);
        $this->field_service->delete($primary_key);
        return true;
    }

    public function active($primary_key) {
        $this->user_permissions->support_permission($this->current_user);
        $this->field_service->active($primary_key);
        return site_url('/fields/field_images_management');
    }

    private $field_id;

    function field_images_management_post($primary_key = null, $operation = null) {
        $this->user_permissions->support_permission($this->current_user);
        $this->load->library('grocery_CRUD');
        $this->field_id = $primary_key;
        try {
            $crud = new grocery_CRUD();

            $crud->set_theme('datatables')
                    ->set_table('image')
                    ->where('field_id', $primary_key)
                    ->set_subject('image')
                    ->columns('name')
                    ->display_as('image_id', 'Id')
                    ->set_field_upload('name', 'assets/uploaded_images')
                    ->unset_edit_fields('field_id')
                    ->callback_before_insert(array($this, 'add_field_id'))
                    ->field_type('field_id', 'hidden')
                    ->unset_read()
                    ->unset_edit()
                    ->unset_export()
                    ->unset_print();

            $output = $crud->render();
            $this->load->view('template.php', array(
                'view' => 'field_images_management',
                'field_id' => $this->field_id,
                'field' => $this->field_service->get($this->field_id),
                'output' => $output->output,
                'js_files' => $output->js_files,
                'css_files' => $output->css_files
                    )
            );
        } catch (Exception $e) {
            show_error($e->getMessage() . ' --- ' . $e->getTraceAsString());
        }
    }

    function field_images_management_get($primary_key = null, $operation = null) {
        $this->field_images_management_post($primary_key, $operation);
    }

    function add_field_id($post_array) {
        $post_array['field_id'] = $this->field_id;
        return $post_array;
    }

    public function validate_time($str) {
        $this->load->helper('form');
        $this->load->library('form_validation');
        if (strlen($str) == 7)
            $str = "0" . $str;
        $this->form_validation->set_message('validate_time', $str . ' is not a valid time. Ex:( 10:00:00 )');
        if (strrchr($str, ":")) {
            list($hh, $mm, $ss) = explode(':', $str);
            if (!is_numeric($hh) || !is_numeric($mm) || !is_numeric($ss)) {
                return FALSE;
            } elseif ((int) $hh > 24 || (int) $mm > 59 || (int) $ss > 59) {
                return FALSE;
            } elseif (mktime((int) $hh, (int) $mm, (int) $ss) === FALSE) {
                return FALSE;
            }
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public function save_in_session_get() {
        $this->session->set_userdata(array('fields' => $this->get('fields')));
        $this->response(array('status' => true, 'data' => $this->session->userdata('fields'), 'message' => ""));
    }

}
