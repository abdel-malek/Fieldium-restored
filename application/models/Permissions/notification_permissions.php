<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
*/

class notification_permissions  extends CI_Model {
    public function __construct() {
        parent::__construct();
    }

    function send_permission($user ) {
        if ($user && $user->role_id == ROLE::ADMIN ) {
            return TRUE;
        }else
            throw new Permission_Denied_Exception();
    }
   
}
?>
