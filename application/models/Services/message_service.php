<?php

/**
 * @author Amal Abdulraouf
 */
class message_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/message');
    }

    public function save($name, $email, $subject, $text, $phone = "") {
        $message = $this->message->save(array(
            'name' => $name,
            'email' => $email,
            'subject' => $subject,
            'text' => $text,
            'phone' => $phone
                )
        );
        $this->send_email($message);
    }

    function send_email($message) {
//        $fieldium = FIELLDIUM_CONTACTUS_ACCOUNT;
        $fieldiu_emails = array(
            'hussain.badri@fieldium.com',
            'zakaria.khiami@fieldium.com',
            'mozaffar.addam@fieldium.com',
            'amalabdulraouf@gmail.com',
            'customer-care@fieldium.com'
        );
        $config = array();
        $config['protocol'] = 'mail';
        $config['smtp_host'] = 'fieldium.com';
        $config['smtp_port'] = 465;
        $config['mailtype'] = 'html';
        $config['charset'] = 'utf-8';
        $config['smtp_user'] = 'customer-care@fieldium.com';
        $config['smtp_pass'] = 'vEMZOPdc9VVD';
        $config['smtp_timeout'] = '7';
        $config['newline'] = "\r\n";
        $config['send_multipart'] = FALSE;
        $config['wordwrap'] = TRUE;
        $this->load->library('email');
        foreach ($fieldiu_emails as $email) {
            $this->email->initialize($config);
            $this->email->from('customer-care@fieldium.com', "Fieldium Customer Care");
            $this->email->to($email);
            $this->email->subject($message->subject);
            $this->email->message($message->text . '<br>Name: ' . $message->name . '<br>Phone: ' . $message->phone);
            $this->email->send();
            //show_error($this->email->print_debugger());
        }
    }

}

?>
