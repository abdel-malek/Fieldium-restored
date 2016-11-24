<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


class Parent_Exception extends Exception {

     public function ParentException($message="") {
        parent::__construct($message);
    }
    
    public function fillMessage($msg) {
        return $msg ;
    }
    
}
