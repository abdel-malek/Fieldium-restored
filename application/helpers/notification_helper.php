<?php

/**
 * Description of GCM
 *
 * @author Amal Abdulraouf
 */
class NotificationHelper {

    function __construct() {
        
    }

    public function send_notification_to_android_device($registatoin_ids,$message , $data) {

        $message_object = array(
            "message_text" => $message,
            "message_body" => $data);

        $url = 'https://android.googleapis.com/gcm/send';

        print_r(json_encode( $message_object));
        $fields = array(
            'registration_ids' => $registatoin_ids,
            'data' =>  array("message" =>json_encode( $message_object)),
        );

        $headers = array(
            'Authorization: key=' . "AIzaSyAgM3r32-CrXxVhFQcHfi_IIlJVF_ukAa8",
            'Content-Type: application/json'
        );

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        // Disabling SSL Certificate support temporarly
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

        //print_r(json_encode($fields));
        //echo"</br>";
        //print_r($headers);
        //echo"</br>";
        // Execute post
        $result = curl_exec($ch);
        if ($result === FALSE) {
            die('Curl failed: ' . curl_error($ch));
        }

        // Close connection
        curl_close($ch);
        //echo $result;
    }


}

?>
