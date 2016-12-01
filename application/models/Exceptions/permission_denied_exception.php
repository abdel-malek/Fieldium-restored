<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */

class Permission_Denied_Exception extends Parent_Exception {

    public function __construct($message="you don't have permission") {
        parent::__construct($message);
    }
 
}
