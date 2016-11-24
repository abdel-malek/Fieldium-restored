<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
*/

class ticket_permissions  extends CI_Model {
    public function __construct() {
        parent::__construct();
        $this->load->database();
        
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
   
    
     function show_permission($user) {
        if ($user && $user->role_id == ROLE::USER ) {
            return TRUE;
        }else
            throw new Permission_Denied_Exception();
    }
     function show_items($user) {
        if ($user && ($user->role_id == ROLE::ADMIN )) {
            return TRUE;
        } else if ($user) {
            $permission = $this->db->select('*')
                    ->from('user_permission')
                    ->where('permission_id', PERMISSION::SHOW_TICKETS)
                    ->where('user_id', $user->user_id)
                    ->get();
            
            if($permission->num_rows()!=0)
                return true;
            else 
                return false;
        } else
            return false;
    }
   
}
?>
