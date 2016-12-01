<?php

/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */
class Invalid_Login_Exception extends Parent_Exception {

    public function __construct($lang = "en") { // [Error code # , line #] 
        parent::__construct(($lang == "en") ? 'Invalid username or password' : 'خطأ في اسم المستخدم أو كلمة المرور');
    }

}
