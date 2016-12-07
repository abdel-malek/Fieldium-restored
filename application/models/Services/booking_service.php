<?php

/**
 * @author Amal Abdulraouf
 */
class booking_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/booking');
        $this->load->model('DataSources/field');
    }

    public function create(
    $field_id, $player_id, $date, $start, $duration, $notes, $user_id, $manually, $lang
    ) {
        $field = $this->field->get($field_id);
        $bookings = $this->booking->field_bookings_by_timing($field_id, $date, $start, $duration);
        $endtime = strtotime($start) + doubleval($duration) * 3600;
        $end = strftime('%H:%M:%S', $endtime);
        if ($bookings || !(
                $start >= $field->open_time && $start <= $field->close_time &&
                $end >= $field->open_time && $end <= $field->close_time
                )) {
            throw new Field_Not_Available_Exception();
        }
        $total = ($duration * $field->hour_rate);

        $booking_id = $this->booking->add(array(
            'field_id' => $field_id,
            'player_id' => $player_id,
            'start' => $start,
            'date' => $date,
            'duration' => $duration,
            'notes' => $notes,
            'user_id' => $user_id,
            'total' => $total,
            'manually' => $manually
        ));

        $booking = $this->get($booking_id, $lang);
        return $booking;
    }

    public function update(
    $booking_id, $field_id, $date, $start, $duration, $notes, $user_id, $lang
    ) {
        $field = $this->field->get($field_id);
        $bookings = $this->booking->field_bookings_by_timing($field_id, $date, $start, $duration);
        $endtime = strtotime($start) + doubleval($duration) * 3600;
        $end = strftime('%H:%M:%S', $endtime);
        if ($bookings || !(
                $start >= $field->open_time && $start <= $field->close_time &&
                $end >= $field->open_time && $end <= $field->close_time
                )) {
            throw new Field_Not_Available_Exception();
        }
        $total = ($duration * $field->hour_rate);

        $booking = $this->booking->update($booking_id, array(
            'field_id' => $field_id,
            'start' => $start,
            'date' => $date,
            'duration' => $duration,
            'notes' => $notes,
            'user_id' => $user_id,
            'total' => $total
        ));

        $booking = $this->get($booking_id, $lang);
        return $booking;
    }

    public function get($booking_id, $lang = "en") {
        $booking = $this->booking->get($booking_id, $lang);
        if (!$booking)
            throw new Booking_Not_Found_Exception($lang);
        return $booking;
    }

    public function delete($booking_id) {
        $booking = $this->get($booking_id, "en");
        $this->booking->update($booking_id, array('deleted' => 1));
        $this->load->model('Services/notification_service');
        $message = "Your booking No." . $booking_id . " has been cancelled. ";
        $this->notification_service->send_notification_4customer($booking->player_id, $message, array("booking" => $booking), "booking_cancelled_message");
    }

    public function decline($booking_id) {
        $this->get($booking_id);
        $this->booking->update($booking_id, array('state_id' => BOOKING_STATE::DECLINED));
        $booking = $this->get($booking_id);
        $this->load->model('Services/notification_service');
        $message = array();
        $message["en"] = "Your booking No." . $booking_id . " has been declined. ";
        $message["ar"] = "تم رفض الطلب رقم " . $booking_id;
        // $this->notification_service->send_notification_4customer($booking->player_id, $message["en"], array("booking" => $booking), "booking_declined_message");
    }

    public function approve($booking_id, $lang) {
        $this->get($booking_id, $lang);
        $this->booking->update($booking_id, array('state_id' => BOOKING_STATE::APPROVED));
        $booking = $this->get($booking_id, $lang);
        $this->load->model('Services/notification_service');
        $message = array();
        $message["en"] = "Your booking No." . $booking_id . " has been approved. ";
        $message["ar"] = "تم قبول الطلب رقم " . $booking_id;
        // $this->notification_service->send_notification_4customer($booking->player_id, $message["en"], array("booking" => $booking), "booking_confirmed_message");
        return $booking;
    }

    public function get_my_bookings($palyer_id, $lang) {
        return $this->booking->get_my_bookings($palyer_id, $lang);
    }

    public function company_bookings($company_id, $lang) {
        return $this->booking->company_bookings($company_id, $lang);
    }

    public function field_bookings($field_id, $lang) {
        return $this->booking->field_bookings($field_id, $lang);
    }

    public function field_bookings_by_date($field_id, $date, $lang = "en") {
        return $this->booking->field_bookings_by_date($field_id, $date, $lang);
    }

}

?>
