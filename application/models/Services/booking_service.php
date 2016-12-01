<?php

/**
 * @author Amal Abdulraouf
 */
class booking_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/booking');
        $this->load->model('Services/field_service');
    }

    public function create(
    $field_id, $player_id, $date, $start, $duration, $notes, $user_id, $manually
    ) {
        $field = $this->field_service->get($field_id);
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

        $booking = $this->get($booking_id);
        return $booking;
    }

    public function update(
    $booking_id, $field_id, $player_id, $date, $start, $duration, $notes, $user_id, $manually
    ) {
        $field = $this->field_service->get($field_id);
        $total = ($duration * $field->hour_rate);

        $booking = $this->booking->update($booking_id, array(
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

        $booking = $this->get($booking_id);
        return $booking;
    }

    public function get($booking_id) {
        $booking = $this->booking->get($booking_id);
        if (!$booking)
            throw new Booking_Not_Found_Exception ();
        return $booking;
    }

    public function delete($booking_id) {
        $booking = $this->get($booking_id);
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
        $message = "Your booking No." . $booking_id . " has been declined. ";
        $this->notification_service->send_notification_4customer($booking->player_id, $message, array("booking" => $booking), "booking_declined_message");
    }

    public function approve($booking_id) {
        $this->get($booking_id);
        $this->booking->update($booking_id, array('state_id' => BOOKING_STATE::APPROVED));
        $booking = $this->get($booking_id);
        $this->load->model('Services/notification_service');
        $message = "Your booking No." . $booking_id . " has been approved. ";
        $this->notification_service->send_notification_4customer($booking->player_id, $message, array("booking" => $booking), "booking_confirmed_message");
        return $this->get($booking_id);
    }

    public function get_my_bookings($palyer_id) {
        return $this->booking->get_my_bookings($palyer_id);
    }

    public function company_bookings($company_id) {
        return $this->booking->company_bookings($company_id);
    }

}

?>
