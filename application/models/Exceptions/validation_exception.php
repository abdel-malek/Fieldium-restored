<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */

class Validation_Exception extends Parent_Exception {

    public function __construct($message="") {
        parent::__construct($message);
    }
 
}
