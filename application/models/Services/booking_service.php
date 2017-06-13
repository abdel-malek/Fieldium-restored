<?php

/**
 * @author Amal Abdulraouf
 */
class booking_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/booking');
        $this->load->model('DataSources/field');
        $this->load->model('DataSources/image');
    }

    public function create(
    $field_id, $player_id, $date, $start, $duration, $game_type, $notes, $user_id, $manually, $lang = "en", $voucher = null
    ) {
        if ($voucher) {
            $this->load->model("Services/voucher_service");
//            $this->voucher_service->check_validity($voucher, $field_id, $player_id, $date, $start, $duration);
        }
        $field = $this->field->get($field_id);
        if (!$field)
            throw new Field_Not_Found_Exception();
        $fendtime = strtotime($start) + doubleval($duration) * 60;
        $fend = strftime('%H:%M:%S', $fendtime);
        if ($fend == "00:00:00" && $field->close_time == "23:59:00")
            $duration--;
        $bookings = $this->booking->field_bookings_by_timing($field_id, $date, $start, $duration);
        $endtime = strtotime($start) + doubleval($duration) * 60;
        $end = strftime('%H:%M:%S', $endtime);
        $accepted = true;
        if ($field->close_time < $field->open_time) {

            $accepted = !(
                    ($start >= $field->close_time && $start < $field->open_time) ||
                    ($end > $field->close_time && $end <= $field->open_time)
                    )
            ;
        } else if ($field->close_time > $field->open_time) {
            $accepted = ($start >= $field->open_time && $start < $field->close_time &&
                    $end > $field->open_time && $end <= $field->close_time)
            ;
        }

        if ($bookings || !$accepted
        ) {
            throw new Field_Not_Available_Exception();
        }
        if ($fend == "00:00:00" && $field->close_time == "23:59:00")
            $duration++;
        $subtotal = ($duration * ($field->hour_rate / 60));
        $total = $subtotal;

        $state = BOOKING_STATE::PENDING;
        if ($manually == true || $field->auto_confirm == 1)
            $state = BOOKING_STATE::APPROVED;
        $reference = $this->booking->get_max_id($field_id);

        if ($voucher) {
            $vou = $this->voucher_service->get($voucher);
            if ($vou->type == 1) {
                $total = $subtotal - ($subtotal * $vou->value / 100);
            } else if ($vou->type == 2) {
                if ($vou->value > $duration)
                    $total = 0;
                else
                    $total = ($duration - $vou->value) * ($field->hour_rate / 60);
            }
        }

        $booking_id = $this->booking->add(array(
            'field_id' => $field_id,
            'player_id' => $player_id,
            'start' => $start,
            'date' => $date,
            'duration' => $duration,
            'notes' => $notes,
            'user_id' => $user_id,
            'manually' => $manually,
            'game_type_id' => $game_type,
            'state_id' => $state,
            'reference' => ($reference->ref + 1),
            'hour_rate' => $field->hour_rate,
            'subtotal' => round($subtotal),
            'total' => round($total),
            'voucher' => $voucher
        ));
        if ($voucher) {
            $vou = $this->voucher_service->get($voucher);
            if ($vou->one_time == 1)
                $this->voucher_service->update($vou->voucher_id, array('valid' => 0));
        }
        $booking = $this->get($booking_id, $lang);
//        if ($manually == false) {
//            $this->load->model('Services/notification_service');
//            $message = "You have received a new booking No." . $booking_id;
//            $this->notification_service->send_notification_admin($field->company_id, $message, array("booking" => $booking), "booking_created_message");
//            $message = "You have received a new booking No." . $booking_id . " for company \"" . $booking->company_name . "\" field \"" . $booking->field_name . "\"";
//            $this->notification_service->send_notification_support($message, array("booking" => $booking), "booking_created_message");
//        }
        return $booking;
    }

    public function update(
    $booking_id, $field_id, $player_id, $date, $start, $duration, $game_type, $notes, $user_id, $manually, $lang
    ) {
        $this->get($booking_id);
        $field = $this->field->get($field_id);
        if (!$field)
            throw new Field_Not_Found_Exception();
        $fendtime = strtotime($start) + doubleval($duration) * 60;
        $fend = strftime('%H:%M:%S', $fendtime);
        if ($fend == "00:00:00" && $field->close_time == "00:00:00")
            $duration--;
        $bookings = $this->booking->field_bookings_by_timing($field_id, $date, $start, $duration, $booking_id);
        $endtime = strtotime($start) + doubleval($duration) * 60;
        $end = strftime('%H:%M:%S', $endtime);
        $accepted = true;
        if ($field->close_time < $field->open_time) {

            $accepted = !(
                    ($start >= $field->close_time && $start < $field->open_time) ||
                    ($end > $field->close_time && $end <= $field->open_time)
                    )
            ;
        } else if ($field->close_time > $field->open_time) {
            $accepted = ($start >= $field->open_time && $start < $field->close_time &&
                    $end > $field->open_time && $end <= $field->close_time)
            ;
        }
        if ($bookings || !$accepted
        ) {
            throw new Field_Not_Available_Exception();
        }
        if ($fend == "00:00:00" && $field->close_time == "00:00:00")
            $duration++;
        $total = ($duration * $field->hour_rate);

        $this->booking->update($booking_id, array(
            'field_id' => $field_id,
            'player_id' => $player_id,
            'start' => $start,
            'date' => $date,
            'duration' => $duration,
            'notes' => $notes,
            'user_id' => $user_id,
            'manually' => $manually,
            'game_type_id' => $game_type,
            'hour_rate' => $field->hour_rate
        ));

        $booking = $this->get($booking_id, $lang);

        $this->load->model('Services/notification_service');
        $message = array();
        $message['en'] = "Your booking No." . $booking_id . " has been updated. ";
        $message["ar"] = "تم رفض الطلب رقم " . $booking_id;
        $this->notification_service->send_notification_4customer($booking->player_id, $message, array("booking" => $this->notification_object($booking)), "booking_updated_message");

        return $booking;
    }

    public function get($booking_id, $lang = "en") {
        $booking = $this->booking->get($booking_id, $lang);
        if (!$booking)
            throw new Booking_Not_Found_Exception($lang);
        if ($booking->logo != null)
            $booking->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $booking->logo;
        return $booking;
    }

    public function delete($booking_id) {
        $booking = $this->get($booking_id, "en");
        $this->booking->update($booking_id, array('deleted' => 1));
    }

    public function cancel($booking_id, $msg = "") {
        $booking = $this->get($booking_id, "en");
        if ($booking->state_id == BOOKING_STATE::CANCELLED)
            throw new Parent_Exception("The booking is already canceled");
        $this->booking->update($booking_id, array(
            'state_id' => BOOKING_STATE::CANCELLED,
            'cancellation_reason' => $msg
                )
        );
        $this->load->model('Services/notification_service');
        $message = $msg;
        $this->notification_service->send_notification_4customer($booking->player_id, $message, array("booking" => $booking), "booking_cancelled_message");
        $this->load->library('send_sms');
        $this->send_sms->send_sms($booking->player_phone, $message);
        return $this->get($booking_id, "en");
    }

    private function notification_object($booking) {
        $to_send = new stdClass();
        $to_send->notification_time = date('Y-m-d H:i:s');
        $to_send->booking_id = $booking->booking_id;
        $to_send->company_name = $booking->company_name;
        $to_send->logo = $booking->logo;
        $to_send->address = $booking->address;
        $to_send->state_id = $booking->state_id;
        $to_send->start = $booking->start;
        $to_send->duration = $booking->duration;
        $to_send->date = $booking->date;
        $to_send->field_name = $booking->field_name;
        $to_send->logo_url = $booking->logo_url;
        $to_send->notes = $booking->notes;
        $to_send->total = ($booking->total);
        return $to_send;
    }

    public function decline($booking_id) {
        $booking = $this->get($booking_id);
        if ($booking->state_id == BOOKING_STATE::DECLINED)
            return false;
        $this->booking->update($booking_id, array('state_id' => BOOKING_STATE::DECLINED));
        $booking = $this->get($booking_id);
        $this->load->model('Services/notification_service');
        $message = array();
        $message['en'] = "Your booking No." . $booking_id . " has been declined. ";
        $message["ar"] = "تم رفض الطلب رقم " . $booking_id;
        $this->notification_service->send_notification_4customer($booking->player_id, $message, array("booking" => $this->notification_object($booking)), "booking_declined_message");

        return true;
    }

    public function approve($booking_id, $lang) {
        $booking = $this->get($booking_id, $lang);
        if ($booking->state_id == BOOKING_STATE::APPROVED)
            throw new Parent_Exception("The booking is already approved");
        $bookings = $this->booking->field_bookings_by_timing($booking->field_id, $booking->date, $booking->start, $booking->duration);
        if ($bookings)
            throw new Field_Not_Available_Exception();
        $this->booking->update($booking_id, array('state_id' => BOOKING_STATE::APPROVED));
        $booking = $this->get($booking_id, $lang);
        try {
            $this->load->model('Services/notification_service');
            $player = $this->player_service->get($booking->player_id);
            if ($player->active == 1) {
                $this->load->model('Services/notification_service');
                $message = array();
                $message['en'] = "لقد تم قبول طلبك " . $booking_id;
                $message['ar'] = "Your booking No." . $booking_id . " has been approved. ";
                $this->notification_service->send_notification_4customer($booking->player_id, $message, array("booking" => $this->notification_object($booking)), "booking_confirmed_message");
            }
        } catch (Player_Not_Found_Exception $e) {
            
        }
        $this->load->library('send_sms');
        $msg = "Dear Player,%0AYour Field is booked.%0A"
                . $booking->company_name . "%0A"
                . "Field: " . $booking->field_name . "%0A"
                . "On: " . date("D, d/m/Y", strtotime($booking->date)) . "%0A"
                . "At: " . date('h:i A', strtotime($booking->start)) . "%0A"
                . "For: " . $booking->duration / 60 . " Hour%0A"
                . "Enjoy the Game,%0A"
                . "Fieldium";
        $this->send_sms->send_sms($booking->player_phone, $msg);
        return $booking;
    }

    public function get_my_bookings($palyer_id, $lang) {
        $bookings = $this->booking->get_my_bookings($palyer_id, $lang);
        foreach ($bookings as $booking) {
            $images = $this->image->get_images($booking->field_id);
            $result = array();
            foreach ($images as $image) {
                if ($image->name != "" && $image->name != null) {
                    $image->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $image->name;
                }
                $result[] = $image;
            }
            $booking->images = $result;
            if ($booking->logo != null)
                $booking->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $booking->logo;
        }
        return $bookings;
    }

    public function company_bookings($company_id, $lang) {
        return $this->booking->company_bookings($company_id, $lang);
    }

    public function company_pending_bookings($company_id, $lang) {
        return $this->booking->company_pending_bookings($company_id, $lang);
    }

    public function company_bookings_calendar($company_id) {
        return $this->booking->company_bookings_calendar($company_id);
    }

    public function pending_bookings() {
        return $this->booking->pending_bookings();
    }

    public function field_bookings($field_id, $lang) {
        return $this->booking->field_bookings($field_id, $lang);
    }

    public function field_bookings_by_date($field_id, $date, $lang = "en") {
        return $this->booking->field_bookings_by_date($field_id, $date, $lang);
    }

    public function upcoming_booking($player, $lang) {
        $bookings = $this->booking->upcoming_booking($player, $lang);
        foreach ($bookings as $booking) {
            $images = $this->image->get_images($booking->field_id);
            $result = array();
            foreach ($images as $image) {
                if ($image->name != "" && $image->name != null) {
                    $image->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $image->name;
                }
                $result[] = $image;
            }
            $booking->images = $result;
            if (isset($booking->logo) && $booking->logo != "")
                $booking->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $booking->logo;
        }
        return $bookings;
    }

    public function last_bookings($palyer_id, $lang) {
        $bookings = $this->booking->last_bookings($palyer_id, $lang);
        $company = 0;
        $results = array();
        foreach ($bookings as $booking) {
            if ($company != $booking->company_id) {
                $company = $booking->company_id;
                $images = $this->image->get_images($booking->field_id);
                $result = array();
                foreach ($images as $image) {
                    if ($image->name != "" && $image->name != null) {
                        $image->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $image->name;
                    }
                    $result[] = $image;
                }
                $booking->images = $result;
                if ($booking->logo != null)
                    $booking->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $booking->logo;
                $results[] = $booking;
            }
        }
        return $results;
    }

}

?>
