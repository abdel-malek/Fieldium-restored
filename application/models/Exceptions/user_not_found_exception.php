<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Farah Etmeh
 */

class User_Not_Found_Exception extends Parent_Exception {

    public function __construct($message="") { // [Error code # , line #] 
        parent::__construct('User not found ');
    }
 
}
