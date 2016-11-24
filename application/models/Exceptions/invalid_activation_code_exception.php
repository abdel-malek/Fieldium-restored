<?php
/**
 * Description of OrderNotFoundException
 *
 * @author Farah Etmeh
 */

class Invalid_Activation_Code_Exception extends Parent_Exception {

    public function __construct($message="Invalid activation code") {
        parent::__construct($message);
    }
 
}
