<?php

/**
 * @author Amal Abdulraouf
 */
class message_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/message');
    }

    public function save($name, $email, $subject, $text) {
        $this->message->save(array(
            'name' => $name,
            'email' => $email,
            'subject' => $subject,
            'text' => $text
                )
        );
    }

}

?>
