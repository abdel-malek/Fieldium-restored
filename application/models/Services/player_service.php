<?php

/**
 * @author Amal Abdulraouf
 */
class player_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/player');
        $this->load->library('send_sms');
    }

    public function verify($phone, $code) {
        $player = $this->player->verify($phone, $code);
        if (!$user) {
            throw new Invalid_Activation_Code_Exception ();
        }

        $this->player->update($player->player_id, array(
            'active' => 1
        ));
        $player = $this->get($player->player_id);
        return $player;
    }

    public function get($id) {
        $res = $this->player->get($id);
        if (!$res)
            throw new Player_Not_Found_Exception ();
        return $res;
    }

    public function update($player_id, $name, $email, $address) {
        $this->player->update($player_id, array(
            'name' => $name,
            'email' => $email,
            'address' => $address
        ));
        $player = $this->player->get($player_id);
        return $player;
    }

    public function deactive($player_id) {
        $this->get($player_id);
        $this->player->update($player_id, array(
            'active' => 0
        ));
    }

    public function request_verification_code($phone) {

        $player = $this->player->get_by_phone($phone);
        if ($player) {
            $player_id = $player->player_id;

            $code = $this->generate_activation_code();
            $this->send_sms->send_sms($phone, "Your activation code is: " . $code);
            $this->player->update($player->player_id, array(
                'verification_code' => $code
            ));
            $player = $this->player->get_by_phone($phone);
            return $player;
        } else {
            throw Player_Not_Found_Exception();
        }
    }

    public function register($phone, $device_id, $token, $os) {

        $player = $this->player->get_by_phone($phone);
        if ($player) {
            $player_id = $player->player_id;
            $code = $this->generate_activation_code();
            $this->send_sms->send_sms($phone, "Your activation code is: " . $code);
            $this->player->update($player->player_id, array(
                'phone' => $phone,
                'token' => $token,
                'device_id' => $device_id,
                'os' => $os,
                'verification_code' => $code
            ));
        } else {
            $code = $this->generate_activation_code();
            $this->send_sms->send_sms($phone, "Your activation code is: " . $code);
            $player_id = $this->player->register(array(
                'phone' => $phone,
                'token' => $token,
                'device_id' => $device_id,
                'os' => $os,
                'verification_code' => $code
            ));
        }
        $player = $this->player->get($player_id);
        return $player;
    }

    public function refresh_token($player_id, $token) {
        $this->player->update($player_id, array(
            'token' => $token
        ));
        $player = $this->get($player_id);
        return $player;
    }

    public function active($player_id) {
        $this->get($player_id);
        $this->player->update($player_id, array(
            'active' => true
        ));
        $player = $this->get($player_id);
        return $player;
    }

    function generate_activation_code() {

        $digites = '0123456789';
        $randomString = $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
        ;

        return $randomString;
    }

}

?>
