<?php

/**
 * Description of FCM
 *
 * @author Amal Abdulraouf
 */
class NotificationHelper {
z
    function __construct() {
        
    }

    public function send_notification_to_device($registatoin_ids, $message, $data, $os) {

        $message_object = array(
            "ntf_type" => 1,
            "ntf_text" => array("en" => $message),
            "ntf_body" => $data);

        $url = 'https://fcm.googleapis.com/fcm/send';
        if ($os == "android")
            $fields = array(
                'registration_ids' => $registatoin_ids,
                'data' => $message_object 
            );
        else if ($os == "ios")
            $fields = array(
                'registration_ids' => $registatoin_ids,
                'data' => $message_object,
                'content_available' => true,
                'priority' => 'high',
                'notification' => array('sound' => 'default', 'badge' => 0, 'body' => $message)
            );
        //AIzaSyCWLceEX-QN5Az96nvsvMGlYHbsh49-XfM
        $headers = array(
            'Authorization: key=' . "AAAAwgvsrCg:APA91bEwPMm8XYxGAEoOefo57UAUhgDGzcj_RS5FN2QJH1U9X4enyM3HCaJZkC99xSa0YxbsulZ0nV2WnZpNS8AN5ZZy28_8BRvwLcN5CQEbIt6heEwybagD01sG6nkUQCsFc-wx-QlH6l4KqMoAaMyu_X4yVKUlJg",
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

        // Execute post
        $result = curl_exec($ch);
       // var_dump($result);
       //die();
        if ($result === FALSE) {
            die('Curl failed: ' . curl_error($ch));
        }

        // Close connection
        curl_close($ch);
    }

    public function send_notification_to_ios_device($registatoin_ids, $message, $data) {

        $url = "https://fcm.googleapis.com/fcm/send";
        $message = array(
            "ntf_type" => $notification_type,
            "ntf_text" => $msg,
            "sound" => $notification_sound,
            "ntf_body" => $notification_data
        );

        $fields = array(
            'registration_ids' => $registatoin_ids,
            'data' => $message,
            'content_available' => true,
            'priority' => 'high',
            'notification' => array('sound' => 'default', 'badge' => 0, 'body' => $msg)
        );


        //AIzaSyCWLceEX-QN5Az96nvsvMGlYHbsh49-XfM
        $headers = array(
            'Authorization: key=' . "AIzaSyDbI7DYn57zUVEh9LfhksHkcyCt7t2RgU8",
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

        // Execute post
        $result = curl_exec($ch);
        var_dump($fields);
        die();
        if ($result === FALSE) {
            die('Curl failed: ' . curl_error($ch));
        }

        // Close connection
        curl_close($ch);
    }

    public function send_notification_to_ios_device_apple_push($deviceToken, $message, $data = null, $type) {


        $passphrase = 'tradinos';

        // $tile = $argv[1];
        // $arabicMessage = $argv[2];
        // $englishMessage = $argv[3];

        $ctx = stream_context_create();
        stream_context_set_option($ctx, 'ssl', 'local_cert', 'pushcert.pem');
        stream_context_set_option($ctx, 'ssl', 'passphrase', $passphrase);

        // for sandbox use ssl://gateway.sandbox.push.apple.com:2195
        // Open a connection to the APNS server
        //ssl://gateway.push.apple.com:2195
        //ssl://gateway.sandbox.push.apple.com:2195
        $fp = stream_socket_client(
                'ssl://gateway.push.apple.com:2195', $err, $errstr, 60, STREAM_CLIENT_CONNECT | STREAM_CLIENT_PERSISTENT, $ctx);
        $data = $data["order"];
        if (!$fp)
            exit("Failed to connect: $err $errstr" . PHP_EOL);

        echo 'Connected to APNS' . PHP_EOL;
        if ($type == 1) {
            $body['aps'] = array(
                'alert' => array(
                    "loc-key" => $message,
                    "loc-args" => array($data->order_id)
                ),
                'sound' => 'default'
            );

            $body['Content'] = array(
                'type' => $type,
                'message_body' => $data
            );
        } else if ($type == 2) {
            $body['aps'] = array(
                'alert' => $message,
                'sound' => 'default'
            );
            $body['Content'] = array(
                'type' => $type,
                'message_body' => $data
            );
        }

        $payload = json_encode($body);
//        print_r($payload);
//        die();
        $msg = chr(0) . pack('n', 32) . pack('H*', $deviceToken) . pack('n', strlen($payload)) . $payload;

        $result = fwrite($fp, $msg, strlen($msg));

        if (!$result)
            echo 'Message not delivered' . PHP_EOL;
        else
            echo 'Message successfully delivered' . PHP_EOL;

        fclose($fp);
    }

}

?>
