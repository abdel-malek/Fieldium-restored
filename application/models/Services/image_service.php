<?php

/**
 * @author Amal Abdulraouf
 */
class image_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/image');
    }

    public function save_image($image_name) 
    {
        return $this->image->save_image(array('name'=>$image_name));
    }

}

?>
