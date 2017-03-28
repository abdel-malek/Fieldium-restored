<?php

/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */
class Uploading_Image_Exception extends Parent_Exception {

    public function __construct($lang ="Uploading Image exception") { // [Error code # , line #] 
        parent::__construct($lang);
		
    }

}
