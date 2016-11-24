<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Farah Etmeh
 */

class No_Items_Exception extends Parent_Exception {

    public function __construct($message="") { // [Error code # , line #] 
        parent::__construct('No tickets or purchases !');
    }
 
}
