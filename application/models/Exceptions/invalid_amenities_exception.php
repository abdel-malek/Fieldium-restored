<?php

/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */
class Invalid_Amenities_Exception extends Parent_Exception {

    public function __construct($lang = "en") { // [Error code # , line #] 
        parent::__construct(($lang == "en") ? 'Invalid Amenities' : 'نمط الميزات غير صالح');
    }

}
