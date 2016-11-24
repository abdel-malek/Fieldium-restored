<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');

function generate_restaurant_code () {
    $characters = 'abcdefghijklmnopqrstuvwxyz'; // strtoupper ($name);
    $digites = '0123456789';
    $randomString = $characters[rand(0, strlen($characters) - 1)]
                    .$characters[rand(0, strlen($characters) - 1)]
                    .$digites[rand(0, strlen($digites) - 1)]
                    .$digites[rand(0, strlen($digites) - 1)]; 
    return $randomString;
}

function generate_table_code ($id=1) {

    $characters = 'abcdefghijklmnopqrstuvwxyz';
    $digites = '0123456789';
    $randomString = $characters[rand(0, strlen($characters) - 1)]
                    .$characters[rand(0, strlen($characters) - 1)]
                    .$digites[rand(0, strlen($digites) - 1)]
                    .$digites[rand(0, strlen($digites) - 1)];
                    
    return $randomString;
}

function generate_activation_code () {
    $instanceName =& get_instance();
    $instanceName->load->helper('string');
    return random_string('alnum', 16);
}

function generate_password () {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $randomString = $characters[rand(0, strlen($characters) - 1)]
                    .$characters[rand(0, strlen($characters) - 1)]
                    .$characters[rand(0, strlen($characters) - 1)]
                    .$characters[rand(0, strlen($characters) - 1)]
                    .$characters[rand(0, strlen($characters) - 1)]
                    .$characters[rand(0, strlen($characters) - 1)];

    return $randomString;
}

function generate_voucher_code () {
    $chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    $code = "T_";
    for ($i = 0; $i < 6; $i++) {
        $code .= $chars[mt_rand(0, strlen($chars)-1)];
    }
    return $code;
}