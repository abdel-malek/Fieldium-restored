<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */

class Invalid_Login_Exception extends Parent_Exception {

    public function __construct($message="Invalid username or password") {
        parent::__construct($message);
    }
 
}
