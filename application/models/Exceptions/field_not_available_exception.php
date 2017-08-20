<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */

class Field_Not_Available_Exception extends Parent_Exception {

    public function __construct($lang="en") { // [Error code # , line #] 
        parent::__construct(($lang == "en")?'Field is not available at the specific time ':'الملعب غير متاح في الوقت الذ تم تحديده');
    }
 
}
