<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

class user_permissions extends CI_Model {

    public function __construct() {
        parent::__construct();
    }

    function active_permission($user) {
        if ($user && ($user->role_id == ROLE::ADMIN )) {
            return TRUE;
        } else
            throw new Permission_Denied_Exception();
    }
    
    function deactive_permission($user) {
        if ($user && ($user->role_id == ROLE::ADMIN )) {
            return TRUE;
        } else
            throw new Permission_Denied_Exception();
    }
    
    function update_info_permission($user) {
        if ($user ) {
            return TRUE;
        } else
            throw new Permission_Denied_Exception();
    }
    
    function show_customers_permission($user) {
       if ($user && ($user->role_id == ROLE::ADMIN )) {
            return TRUE;
        } else
            throw new Permission_Denied_Exception();
    }
    

}

?>
