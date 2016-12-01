<?php

if (!defined('BASEPATH'))
    exit('No direct script access allowed');

function upload_image($controller) {

    $data = array();
    foreach ($_FILES as $index => $value) {
        if ($value['name'] != '') {
            $controller->load->library('upload');
            $controller->upload->initialize(set_upload_options());

            //upload the image
            if (!$controller->upload->do_upload($index)) {
                $error =  $controller->upload->display_errors();
                //throw new Uploading_Image_Exception($error);
            } else {

                $data[$index] = array('upload_data' => $controller->upload->data());
            }
        }
    }
    return $data;
}

function set_upload_options() {
    //upload an image options
    $config = array();
    $config['upload_path'] = dirname($_SERVER["SCRIPT_FILENAME"]) . UPLOADED_IMAGES_PATH;
    $config['allowed_types'] = 'jpg|png|jpeg';

    return $config;
}
