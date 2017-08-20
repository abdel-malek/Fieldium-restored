<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';
require APPPATH . '/libraries/lib/Braintree.php';

class payment extends REST_Controller {

    function __construct() {
        parent::__construct();
    }

    public function index_get() {

        $gateway = new Braintree_Gateway(array(
            'accessToken' => 'access_token$sandbox$6cwfmm26d7xck95g$1541c42c07e3ae5358b5181a0053933f',
        ));
        $token = ($clientToken = $gateway->clientToken()->generate());
        $this->load->view('payment', array('token' => $token));
    }

    public function generate_token_get() {
        $gateway = new Braintree_Gateway(array(
            'accessToken' => 'access_token$production$p25mgb4588cnpht7$17ca84085cb0b23c961e1424a9e12b04',
        ));
        $token = ($clientToken = $gateway->clientToken()->generate());
        $this->response(array('status' => true, 'data' => array(
                "token" => $token
            ), 'message' => ''
                )
        );
    }

    public function checkout_post() {
        $gateway = new Braintree_Gateway(array(
            'accessToken' => 'access_token$production$p25mgb4588cnpht7$17ca84085cb0b23c961e1424a9e12b04',
        ));
        $result = $gateway->transaction()->sale([
            "amount" => $_POST['amount'],
            'merchantAccountId' => 'USD',
            "paymentMethodNonce" => $_POST['payment_method_nonce'],
            "orderId" => 1,
            "descriptor" => [
                "name" => "Descriptor displayed in customer CC statements. 22 char max"
            ],
            "shipping" => [
                "firstName" => "Jen",
                "lastName" => "Smith",
                "company" => "Braintree",
                "streetAddress" => "1 E 1st St",
                "extendedAddress" => "Suite 403",
                "locality" => "Bartlett",
                "region" => "IL",
                "postalCode" => "60103",
                "countryCodeAlpha2" => "US"
            ],
//            "options" => [
//                "paypal" => [
//                    "customField" => $_POST["PayPal custom field"],
//                    "description" => $_POST["Description for PayPal email receipt"]
//                ],
//            ]
        ]);
        if ($result->success) {
            $this->response(array('status' => true, 'data' => array("transaction_id" => $result->transaction->id), 'message' => "Success ID: " . $result->transaction->id));
        } else {
            $this->response(array('status' => false, 'data' => array(), 'message' => "Error Message: " . $result->message));
        }
    }

}
