<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */

class Invalid_Activation_Code_Exception extends Parent_Exception {

    public function __construct($lang = "en") { // [Error code # , line #] 
        parent::__construct(($lang == "en") ? 'Invalid activation code' : 'رمز التحقيق غير صالح');
    }
}
