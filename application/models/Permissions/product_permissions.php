<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
*/

class product_permissions  extends CI_Model {
    public function __construct() {
        parent::__construct();
    }

    function create_permission($user ) {
        if ($user && $user->role_id == ROLE::ADMIN ) {
            return TRUE;
        }else
            throw new Permission_Denied_Exception();
    }
   
     function update_permission($user ) {
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
        if ($user && $user->role_id == ROLE::USER ) {
            return TRUE;
        }else
            throw new Permission_Denied_Exception();
    }
   
    
}
?>
