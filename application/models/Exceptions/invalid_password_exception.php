<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */

class Invalid_Password_Exception extends Parent_Exception {

    public function __construct($message="Invalid password") {
        parent::__construct($message);
    }
 
}
