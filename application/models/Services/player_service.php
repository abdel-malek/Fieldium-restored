<?php

/**
 * @author Amal Abdulraouf
 */
class player_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/player');
        $this->load->model('DataSources/game');
        $this->load->model('Services/game_service');
        $this->load->library('send_sms');
    }

    public function register($phone, $os, $name) {

        $player = $this->player->get_by_phone($phone);
        if ($player) {
            $player_id = $player->player_id;
            $code = $this->generate_activation_code();
            $server_id = uniqid();
            while ($this->player->check_server_id(md5($server_id))) {
                $server_id = uniqid();
            }
            $this->player->update($player_id, array(
                'phone' => $phone,
                'os' => $os,
                'token' => '',
                'name' => $name,
                'server_id' => md5($server_id),
                'verification_code' => $code,
                'active' => 0
            ));
            $this->send_sms->send_sms($phone, $this->lang->line('verification_sms') . $code);
        } else {
            $code = $this->generate_activation_code();
            $server_id = uniqid();
            while ($this->player->check_server_id($server_id)) {
                $server_id = uniqid();
            }
            $player_id = $this->player->register(array(
                'phone' => $phone,
                'os' => $os,
                'token' => '',
                'name' => $name,
                'server_id' => md5($server_id),
                'verification_code' => $code
            ));
            $this->send_sms->send_sms($phone, $this->lang->line('verification_sms') . $code);
        }
        $player = $this->get($player_id);
        $player->server_id = md5($server_id);
        return $player;
    }

    public function create($name, $phone) {

        $player = $this->player->get_by_phone($phone);
        if ($player) {
            $player_id = $player->player_id;
//            $code = $this->generate_activation_code();
//            $server_id = uniqid();
//            while ($this->player->check_server_id(md5($server_id))) {
//                $server_id = uniqid();
//            }
//            $this->player->update($player_id, array(
//                'phone' => $phone,
//                'os' => $os,
//                'server_id' => md5($server_id),
//                'verification_code' => $code
//            ));
////            $this->send_sms->send_sms($phone, $this->lang->line('verification_sms') . $code);
        } else {
//            $code = $this->generate_activation_code();
//            $server_id = uniqid();
//            while ($this->player->check_server_id($server_id)) {
//                $server_id = uniqid();
//            }
            $player_id = $this->player->register(array(
                'phone' => $phone,
                'name' => $name,
                'server_id' => "",
                'token' => "",
                'address' => ""
            ));
            $this->send_sms->send_sms($phone, $this->lang->line('verification_sms') . $code);
        }
        $player = $this->get($player_id);
//        $player->server_id = $server_id;
        return $player;
    }

    public function verify($phone, $code) {
        $player = $this->player->get_by_phone($phone);
        $player = $this->player->verify($phone, $code);
        if (!$player) {
            throw new Invalid_Activation_Code_Exception($this->session->userdata('language'));
        }

        $this->player->update($player->player_id, array(
            'active' => 1
        ));
        $player = $this->get($player->player_id);
        return $player;
    }

    public function get($id, $lang = "en") {
        $res = $this->player->get($id, $lang);
        if (!$res)
            throw new Player_Not_Found_Exception($lang);
        $res->prefered_games = $this->game->get_player_games($id, $lang);
        if ($res->profile_picture != "" && $res->profile_picture != null)
            $res->profile_picture_url = base_url() . UPLOADED_IMAGES_PATH_URL . $res->profile_picture;
        return $res;
    }

    public function update($player_id, $name, $email, $address, $games_types, $lang) {
        $this->player->update($player_id, array(
            'name' => $name,
            'email' => $email,
            'address' => $address
        ));
        $this->game->delete_player_games($player_id);
        if ($games_types) {
            $games_types = $this->decode($games_types);
            foreach ($games_types as $type) {
                if (!is_array($type)) {
                    try {
                        $games_type = $this->game_service->get($type);
                        $this->game->add_player_game(array(
                            'player_id' => $player_id,
                            'game_type_id' => $games_type->game_type_id
                        ));
                    } catch (Game_Not_Found_Exception $e) {
                        
                    }
                }
            }
        }
        $player = $this->get($player_id, $lang);
        return $player;
    }

    function decode($json) {
        $data = json_decode($json, true);
//        var_dump($datsa);die();
        if (!is_array($data))
            throw new Invalid_Format_Exception($lang);

//        for ($i = 0; $i < count($data); $i++) {
//            if (!array_key_exists("amenity", $data[$i]))
//                throw new Invalid_Amenities_Exception($lang);
//        }
        return $data;
    }

    public function deactive($player_id) {
        $this->get($player_id);
        $this->player->update($player_id, array(
            'active' => 0,
            'server_id' => ''
        ));
    }

    public function deactive_active($player_id) {
        $player = $this->get($player_id);
        $this->player->update($player_id, array(
            'active' => !$player->active,
            'server_id' => ''
        ));
    }

    public function request_verification_code($phone, $server_id) {

        $player = $this->player->get_by_phone($phone);
        if ($player && $player->server_id == $server_id) {
            $player_id = $player->player_id;

            $code = $this->generate_activation_code();

            $this->player->update($player->player_id, array(
                'verification_code' => $code
            ));

//            $this->send_sms->send_sms($phone, $this->lang->line('verification_sms') . $code);
            return $this->get($player->player_id);
        } else {
            throw new Player_Not_Found_Exception($this->session->userdata('language'));
        }
    }

    public function refresh_token($player_id, $token) {

        $this->player->update($player_id, array(
            'token' => $token
        ));
        $player = $this->get($player_id, $this->session->userdata('language'));
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

    function update_player_image($player_id, $image_name) {
        $image = $this->get($player_id);
        $image = $image->profile_picture;
        if ($image != "")
            if (unlink(dirname($_SERVER["SCRIPT_FILENAME"]) . "/assets/uploaded_images/" . $image))
                $true = true;
        $this->player->update($player_id, array('profile_picture' => $image_name));
    }

}

?>
