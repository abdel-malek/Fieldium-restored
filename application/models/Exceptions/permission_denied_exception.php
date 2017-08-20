<?php

/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */
class Permission_Denied_Exception extends Parent_Exception {

    public function __construct($lang = "en") { // [Error code # , line #] 
        parent::__construct(($lang == "en") ? "you don't have permission" : 'ليس لديك الصلاحية للقيام بهذا الإجراء');
    }

}
