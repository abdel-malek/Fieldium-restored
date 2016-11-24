<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Farah Etmeh
 */

class Uploading_Image_Exception extends Parent_Exception {

    public function __construct($message="Uploading Image Exception") { // [Error code # , line #] 
        parent::__construct($message);
    }
 
}
