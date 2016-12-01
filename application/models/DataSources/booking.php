<?php

class booking extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function get($booking_id) {
        return $this->db->select("*")
                        ->from('booking')
                        ->where('booking_id', $booking_id)
                        ->where('deleted', 0)
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

    public function get_my_bookings($player_id) {
        return $this->db->select("*")
                        ->from('booking')
                        ->where('player_id', $player_id)
                        ->where('deleted', 0)
                        ->get()->result();
    }

    public function company_bookings($company_id) {
        return $this->db->select("booking.*")
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('company', 'field.company_id = company.company_id')
                        ->where('company.company_id', $company_id)
                        ->where('booking.deleted', 0)
                        ->get()->result();
    }

}
