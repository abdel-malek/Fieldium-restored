<?php

class booking extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function get($booking_id, $lang = "en") {
        return $this->db->select("booking.*, " . ENTITY::FIELD . ", field.$lang" . "_name as field_name, player.name as player_name, player.phone as player_phone, company." . $lang . "_address as address, company.logo")
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('company', 'company.company_id = field.company_id')
                        ->join('player', 'player.player_id = booking.player_id', 'left')
                        ->where('booking_id', $booking_id)
                        ->where('booking.deleted', 0)
                        ->get()->row();
    }

    public function add($data) {
        if ($this->db->insert('booking', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

    public function update($booking_id, $data) {
        $res = $this->db
                ->where(array('booking_id' => $booking_id))
                ->update('booking', $data);
        if ($res) {
            return $res;
        } else {
            throw new DatabaseException();
        }
    }

    public function get_my_bookings($player_id, $lang = "en") {
        return $this->db->select("booking.*, " . ENTITY::FIELD . ", field.$lang" . "_name as field_name, company." . $lang . "_address as address, company.logo")
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('company', 'company.company_id = field.company_id')
                        ->where('player_id', $player_id)
                        ->where('booking.deleted', 0)
                        ->order_by('booking.field_id, booking.date ASC')
                        ->get()->result();
    }

    public function company_bookings($company_id, $lang = "en") {
        return $this->db->select("booking.*, " . ENTITY::FIELD . ", field.$lang" . "_name as field_name, player.name as player_name, player.phone as player_phone")
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('company', 'field.company_id = company.company_id')
                        ->join('player', 'player.player_id = booking.player_id')
                        ->where('company.company_id', $company_id)
                        ->where('booking.deleted', 0)
                        ->order_by('booking.field_id, booking.date ASC')
                        ->get()->result();
    }

    public function field_bookings($field_id, $lang = "en") {
        return $this->db->select("booking.*, " . ENTITY::FIELD . ", field.$lang" . "_name as field_name, player.name as player_name, player.phone as player_phone")
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('player', 'player.player_id = booking.player_id')
                        ->where('booking.field_id', $field_id)
                        ->where('booking.deleted', 0)
                        ->get()->result();
    }

    public function field_bookings_by_date($field_id, $date, $lang = "en") {
        return $this->db->select("booking.*, " . ENTITY::FIELD . ", field.$lang" . "_name as field_name")
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('player', 'player.player_id = booking.player_id')
                        ->where('booking.field_id', $field_id)
                        ->where('booking.date', $date)
                        ->where('booking.deleted', 0)
                        ->get()->result();
    }

    public function field_bookings_by_timing($field_id, $date, $start, $duration) {
        return $this->db->query("SELECT booking.* FROM booking
                    WHERE booking.field_id =$field_id and booking.date = '$date' and booking.deleted = 0 and ("
                        . "( "
                        . "booking.start >= time('$start')"
                        . "and booking.start < (time('$start') + INTERVAL $duration HOUR)"
                        . ")"
                        . " OR ( "
                        . "(booking.start + INTERVAL booking.duration HOUR) > time('$start')"
                        . "and (booking.start + INTERVAL booking.duration HOUR) < (time('$start') + INTERVAL $duration HOUR)"
                        . "))")->result();
    }

    public function upcoming_booking($player_id, $lang = "en") {
        $date = date('Y-m-d');
        $time = date("h:i:s");
        return $this->db->query("
            SELECT booking.* FROM booking
                    WHERE booking.player_id =$player_id and booking.deleted = 0 and ("
                        . "booking.date >= '$date'"
                        . " OR ( "
                        . "booking.date = '$date' and  time(booking.start) > time('$time')"
                        . ")"
                . ") ORDER BY booking.date, booking.start"
                . " LIMIT 1")->result();
    }

}
