<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

class user_permissions extends CI_Model {

    public function __construct() {
        parent::__construct();
    }

    function management_permission($user, $company_id) {
        if ($user && (
                ($user->role_id == ROLE::ADMIN && $user->company_id == $company_id) || $user->role_id == ROLE::SUPPORT
                )
        )
            return true;
        else
            throw new Permission_Denied_Exception();
    }
    
    function support_permission($user) {
        if ($user &&  $user->role_id == ROLE::SUPPORT)
            return true;
        else
            throw new Permission_Denied_Exception();
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
        if ($user) {
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
