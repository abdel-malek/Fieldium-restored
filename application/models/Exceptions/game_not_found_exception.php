<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Amal Abdulraouf
 */

class Game_Not_Found_Exception extends Parent_Exception {
 
    public function __construct($lang = "en") { // [Error code # , line #] 
        parent::__construct(($lang == "en") ? 'Game type is not found ' : 'لم يتم العثور على نوع اللعبة');
    }
}
