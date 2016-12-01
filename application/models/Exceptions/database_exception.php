<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */

class Database_Exception extends Parent_Exception {

 
    public function __construct($lang = "en") { // [Error code # , line #] 
        parent::__construct(($lang == "en") ? 'Database Exception ' : 'خطأ أثناء المعالجة');
    }
}
