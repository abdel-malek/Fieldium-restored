<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Farah Etmeh
 */

class SQL_Exception extends Parent_Exception {

    public function __construct($message="") { // [Error code # , line #] 
        parent::__construct('SQLException');
    }
 
}
