<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */

class Invalid_Amenities_Exception extends Parent_Exception {

    public function __construct($message="Invalid Amenities") {
        parent::__construct($message);
    }
 
}