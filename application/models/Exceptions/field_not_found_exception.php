<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */

class Field_Not_Found_Exception extends Parent_Exception {

    public function __construct($lang="en") { // [Error code # , line #] 
        parent::__construct(($lang == "en")?'Field is not found ':'لم يتم العثور على الملعب');
    }
 
}
