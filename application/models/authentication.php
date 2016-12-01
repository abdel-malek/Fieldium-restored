<?php

/**
 * @author Amal Abdulraouf
 */
class authentication extends CI_Model {

    public function __construct() {
        $this->load->model('DataSources/user');
        $this->load->model('DataSources/player');
    }

    function check_user($param1, $param2) {
        $user = $this->user->check_authentication($param1, $param2);
        if (!$user) {
            $user = $this->player->check_authentication($param1, $param2);
        }
        return $user;
    }

    function logout() {
        $this->load->library('session');
        $this->session->unset_userdata('PHP_AUTH_USER');
        $this->session->unset_userdata('PHP_AUTH_PW');
    }

}

?>
