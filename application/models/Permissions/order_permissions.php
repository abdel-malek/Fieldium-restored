<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
*/

class order_permissions  extends CI_Model {
    public function __construct() {
        parent::__construct();
    }

    function show_all_permission($user ) {
        if ($user && $user->role_id == ROLE::ADMIN ) {
            return TRUE;
        }else
            throw new Permission_Denied_Exception();
    }
    
    function show_details_permission($user ) {
        if ($user && $user->role_id == ROLE::ADMIN ) {
            return TRUE;
        }else
            throw new Permission_Denied_Exception();
    }
    
     function show_my_orders_permission($user ) {
        if ($user ) {
            return TRUE;
        }else
            throw new Permission_Denied_Exception();
    }
    
    function create_permission($user ) {
        if ($user && $user->role_id == ROLE::CUSTOMER ) {
            return TRUE;
        }else
            throw new Permission_Denied_Exception();
    }
   
     function receive_permission($user ) {
        if ($user && $user->role_id == ROLE::ADMIN ) {
            return TRUE;
        }else
            throw new Permission_Denied_Exception();
    }
   
    
     function delete_permission($user ) {
        if ($user && $user->role_id == ROLE::ADMIN ) {
            return TRUE;
        }else
            throw new Permission_Denied_Exception();
    }
   
    
     function show_permission($user ) {
        if ($user && $user->role_id == ROLE::ADMIN ) {
            return TRUE;
        }else
            throw new Permission_Denied_Exception();
    }
   
   
}
?>
