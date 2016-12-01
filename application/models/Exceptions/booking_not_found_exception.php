<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */

class Booking_Not_Found_Exception extends Parent_Exception {

    public function __construct($message="") { // [Error code # , line #] 
        parent::__construct('Booking is not found ');
    }
 
}
