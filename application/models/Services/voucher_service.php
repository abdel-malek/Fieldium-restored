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

    public function check_validity($voucher, $field_id = null, $player_id = null, $date = null, $start = null, $duration = 0) {
        $res = $this->voucher->get_by_voucher($voucher);

        //Voucher is not valid
        if (!$res || $res->valid == 0) {
            throw new Parent_Exception("It is not a valid voucher");
        }

        //The voucher is expired
        if ($res->expiry_date != null && strtotime($res->expiry_date) < strtotime(date('Y-m-d'))) {
            //$this->voucher->update($res->voucher_id, array('valid' => 0));
            throw new Parent_Exception("It is not a valid voucher");
        }

        //The voucher is not valid for the selected date
        if ($date != null) {
            if ($res->expiry_date != null && strtotime($res->expiry_date) < strtotime($date)) {
                throw new Parent_Exception("It is not a valid voucher");
            }
        }

        //The voucher is not valid for the selected time
        if ($start != null) {
            if ($res->from_hour != null && strtotime($res->from_hour) > strtotime($start))
                throw new Parent_Exception("It is not a valid voucher");
            $endtime = strtotime($start) + doubleval($duration) * 60;
            $end = strftime('%H:%M:%S', $endtime);
            if ($res->to_hour != null && strtotime($res->to_hour) < strtotime($end))
                throw new Parent_Exception("It is not a valid voucher");
        }

        //The voucher is not available for selectd user
        if ($player_id != null && $res->public_user != 1) {
            $this->load->model("Services/player_service");
            $this->player_service->get($player_id);
            if (!$this->voucher->check_player($res->voucher_id, $player_id))
                throw new Parent_Exception("It is not a valid voucher");
        }
        //The voucher is not available for selectd field
        if ($field_id != null && $res->public_field != 1) {
            $this->load->model("Services/field_service");
            $this->field_service->get($field_id);
            if (!$this->voucher->check_field($res->voucher_id, $field_id))
                throw new Parent_Exception("It is not a valid voucher");
        }
        return $this->get($res->voucher_id);
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
    $data, $users, $phones, $fields
    ) {
        $voucher_id = $this->voucher->add($data);
        if ($data['public_user'] == 0) {
            $this->load->model("Services/player_service");
            $this->load->model('Services/notification_service');
            foreach ($users as $user) {

                $res = $this->player_service->get($user);
                if (!$res) {
                    $this->delete($voucher_id);
                    throw new Player_Not_Found_Exception($lang);
                }
                $this->voucher->add_player(
                        array(
                            'player_id' => $user,
                            'voucher_id' => $voucher_id
                        )
                );

                $message = array();
                if ($data['type'] == 1) {
                    $message["en"] = "You have received a new voucher with a discount of " . $data['value'] . "%.\nVoucher code: " . $data['voucher'];
                    $message["ar"] = "لقد ربحت قسيمة بحسم " . $data['value'] . "%.\n رمز القسيمة: " . $data['voucher'];
                } else if ($data['type'] == 2) {
                    $message["en"] = "You have received a new voucher with " . $data['value'] . " of free hours.\nVoucher code: " . $data['voucher'];
                    $message["ar"] = "لقد ربحت قسيمة بقيمة " . $data['value'] . "ساعة مجانية.\n رمز القسيمة: " . $data['voucher'];
                }
                $this->notification_service->send_notification_4customer($user, $message, array(), "new_voucher");
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
            }
        }
        if ($data['public_field'] == 0) {
            $this->load->model("Services/field_service");
            foreach ($fields as $field) {
                $res = $this->field_service->get($field);
                if (!$res) {
                    $this->delete($voucher_id);
                    throw new Field_Not_Found_Exception();
                }
                $this->voucher->add_field(
                        array(
                            'field_id' => $field,
                            'voucher_id' => $voucher_id
                        )
                );
            }
        }
        $voucher = $this->get($voucher_id);
        if ($manually == false) {

            $message = "You have received a new booking No." . $booking_id;
            $this->notification_service->send_notification_admin($field->company_id, $message, array("booking" => $booking), "booking_created_message");
            $message = "You have received a new booking No." . $booking_id . " for company \"" . $booking->company_name . "\" field \"" . $booking->field_name . "\"";
            $this->notification_service->send_notification_support($message, array("booking" => $booking), "booking_created_message");
        }
        return $voucher;
    }

    public function get($voucher_id) {
        $voucher = $this->voucher->get($voucher_id);
        if (!$voucher)
            $voucher = $this->voucher->get_by_voucher($voucher_id);
        if (!$voucher)
            throw new Parent_Exception("It is not a valid voucher");
        $voucher->users = $this->voucher->get_voucher_users($voucher_id);
        $voucher->fields = $this->voucher->get_voucher_fields($voucher_id);
        return $voucher;
    }

    public function update($voucher_id, $data) {
        $this->voucher->update($voucher_id, $data);
    }

    public function delete($voucher_id) {
        $voucher = $this->get($voucher_id);
        $this->voucher->delete($voucher_id);
    }

    public function get_my_vouchers($palyer_id) {
        $vouchers = $this->voucher->get_my_vouchers($palyer_id);
        foreach ($vouchers as $voucher) {
            $voucher->users = $this->voucher->get_voucher_users($voucher_id);
            $voucher->fields = $this->voucher->get_voucher_fields($voucher_id);
        }
        return $vouchers;
    }

}

?>
