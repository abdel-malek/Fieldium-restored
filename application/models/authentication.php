<?php
/**
 * @author Farah Etmeh
 */

class authentication extends CI_Model {


    public function __construct() {
        $this->load->model('DataSources/user');
    }

     function check_user($username,$password) {
        
        $user = $this->user->check_authentication($username, $password);
       
        return $user ; 
    }
    function logout () {
        $this->load->library('session');
        $this->session->unset_userdata('PHP_AUTH_USER');
        $this->session->unset_userdata('PHP_AUTH_PW');
    }
}
?>
