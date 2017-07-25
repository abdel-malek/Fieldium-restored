<?php

/**
 * @author Amal Abdulraouf
 */
class voucher_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/voucher');
        $this->load->model('DataSources/field');
        $this->load->model('DataSources/image');
        $this->load->library('send_sms');
    }

    public function generate_voucher() {
        $voucher = $this->generate_activation_code();
        while ($this->voucher->get_by_voucher($voucher)) {
            $voucher = $this->generate_activation_code();
        }
        return $voucher;
    }

    public function check_validity($voucher, $field_id = null, $player_id = null, $date = null, $start = null, $duration = 0, $game = null) {
        $res = $this->voucher->get_by_voucher($voucher);

//Voucher is not valid
        if (!$res || $res->valid == 0) {
            return array(
                'valid' => 0,
                'message' => $this->lang->line('It is not a valid voucher')
            );
        }

//The voucher is expired
        if ($res->expiry_date != null && strtotime($res->expiry_date) < strtotime(date('Y-m-d'))) {
            return array(
                'valid' => 0,
                'message' => $this->lang->line('It is not a valid voucher')
            );
        }

//The voucher is not valid for the selected date
        if ($date != null) {
            if ($res->expiry_date != null && strtotime($res->expiry_date) < strtotime($date)) {
                return array(
                    'valid' => 0,
                    'message' => $this->lang->line('It is not a valid voucher')
                );
            }
        }

//The voucher is not valid for the selected time
        if ($start != null) {
            if ($res->from_hour != null && strtotime($res->from_hour) > strtotime($start))
                return array(
                    'valid' => 0,
                    'message' => $this->lang->line('It is not a valid voucher')
                );
            $endtime = strtotime($start) + doubleval($duration) * 60;
            $end = strftime('%H:%M:%S', $endtime);
            if ($res->to_hour != null && strtotime($res->to_hour) < strtotime($end))
                return array(
                    'valid' => 0,
                    'message' => $this->lang->line('It is not a valid voucher')
                );
        }

//The voucher is not available for selectd user
        if ($player_id != null && $res->public_user != 1) {
            $this->load->model("Services/player_service");
            $this->player_service->get($player_id);
            if (!$this->voucher->check_player($res->voucher_id, $player_id))
                return array(
                    'valid' => 0,
                    'message' => $this->lang->line('It is not a valid voucher')
                );
        }
//The voucher is not available for selectd field
        if ($field_id != null && $res->public_field != 1) {
            $this->load->model("Services/field_service");
            $f = $this->field_service->get($field_id);
            if (!$this->voucher->check_company($res->voucher_id, $f->company_id))
                return array(
                    'valid' => 0,
                    'message' => $this->lang->line('It is not a valid voucher')
                );
        }

//The voucher is not available for selectd game
        if ($game != null && $res->all_games != 1) {
            $this->load->model("Services/game_service");
            $this->game_service->get($game);
            if (!$this->voucher->check_game($res->voucher_id, $game))
                return array(
                    'valid' => 0,
                    'message' => $this->lang->line('It is not a valid voucher')
                );
        }
        $res->message = $this->lang->line('valid_voucher');
        return array(
            'valid' => 1,
            'message' => $this->lang->line('It is not a valid voucher'),
            'data' => $res
        );
    }

    function generate_activation_code() {

        $digites = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
        $randomString = $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
                . $digites[rand(0, strlen($digites) - 1)]
        ;

        return $randomString;
    }

    public function create(
    $data, $users, $phones, $companies, $games
    ) {
        $voucher_id = $this->voucher->add($data);

        $this->load->model("Services/player_service");
        $this->load->model("Services/country_service");
        $this->load->model('Services/notification_service');
        $this->country_service->get($data['country_id']);
        if ($data['public_field'] == 0) {
            $this->load->model("Services/company_service");
            foreach ($companies as $company) {
                try {
                    $res = $this->company_service->get($company);
                } catch (Company_Not_Found_Exception $e) {
                    $this->delete($voucher_id);
                }
                $this->voucher->add_company(
                        array(
                            'company_id' => $company,
                            'voucher_id' => $voucher_id
                        )
                );
            }
        }
        if ($data['all_games'] == 0) {
            $this->load->model("Services/game_service");
            foreach ($games as $game) {
                $res = $this->game_service->get($game);
                $this->voucher->add_game(
                        array(
                            'game_type_id' => $game,
                            'voucher_id' => $voucher_id
                        )
                );
            }
        }
        $voucher = $this->get($voucher_id);
        $message = array();
        if ($data['type'] == 1) {
            $message["en"] = "You have received a new voucher with a discount of " . $data['value'] . "%.\nVoucher code: " . $data['voucher'];
            $message["ar"] = "لقد ربحت قسيمة بحسم " . $data['value'] . "%.\n رمز القسيمة: " . $data['voucher'];
        } else if ($data['type'] == 2) {
            $message["en"] = "You have received a new voucher with " . ($data['value'] / 60) . " of free hours.\nVoucher code: " . $data['voucher'];
            $message["ar"] = "لقد ربحت قسيمة بقيمة " . $data['value'] . "ساعة مجانية.\n رمز القسيمة: " . $data['voucher'];
        }
        foreach ($phones as $phone) {
            $this->voucher->add_player(
                    array(
                        'phone' => $phone,
                        'voucher_id' => $voucher_id
                    )
            );
            if ($data['type'] == 1)
                $msg = "You have received a new voucher with a discount of " . $data['value'] . "%.\nVoucher code: " . $data['voucher'];
            else
                $msg = "You have received a new voucher with " . $data['value'] . " of free hours.\nVoucher code: " . $data['voucher'];
            $this->send_sms->send_sms($phone, $msg);
            try {
                $player = $this->player_service->get_by_phone($phone);
                if ($player)
                    $this->notification_service->send_notification_4customer($player->player_id, $message, $voucher, "new_voucher", 2);
            } catch (Player_Not_Found_Exception $e) {
                
            }
        }
        $voucher = $this->get($voucher_id);

        if ($data['public_user'] == 0 && is_array($users)) {
            foreach ($users as $user) {
                try {
                    $res = $this->player_service->get($user);
                } catch (Player_Not_Found_Exception $e) {
                    $this->delete($voucher_id);
                }
                $this->voucher->add_player(
                        array(
                            'player_id' => $user,
                            'voucher_id' => $voucher_id
                        )
                );
                $this->notification_service->send_notification_4customer($user, $message, $voucher, "new_voucher", 2);
            }
        } else if ($data['public_user'] == 1) {
            $this->notification_service->send_notification_4_all_users($message, $voucher, "new_voucher", 2, $data['country_id']);
        }

        $voucher = $this->get($voucher_id);
        return $voucher;
    }

    public function get($voucher_id) {
        $voucher = $this->voucher->get($voucher_id);
        if (!$voucher)
            $voucher = $this->voucher->get_by_voucher($voucher_id);
        if (!$voucher)
            throw new Parent_Exception("It is not a valid voucher");
        $voucher->users = $this->voucher->get_voucher_users($voucher->voucher_id);
        $voucher->companies = $this->voucher->get_voucher_companies($voucher->voucher_id);
        $voucher->games = $this->voucher->get_voucher_games($voucher->voucher_id);
        return $voucher;
    }

    public function update($voucher_id, $data) {
        $this->voucher->update($voucher_id, $data);
    }

    public function delete($voucher_id) {
        $voucher = $this->get($voucher_id);
        $this->voucher->delete($voucher->voucher_id);
    }

    public function get_my_vouchers($palyer_id, $field_id = null, $date = null, $start = null, $duration = 0, $game = null) {
        $vouchers = $this->voucher->get_my_vouchers($palyer_id, $field_id, $date, $start, $duration, $game);
        foreach ($vouchers as $voucher) {
            $voucher->users = $this->voucher->get_voucher_users($voucher->voucher_id);
            $voucher->companies = $this->voucher->get_voucher_companies($voucher->voucher_id);
            $voucher->games = $this->voucher->get_voucher_games($voucher->voucher_id);
        }
        return $vouchers;
    }

}

?>
