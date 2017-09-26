<?php

class booking extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function get($booking_id, $lang = "en") {
        return $this->db->select(
                                "booking.*, voucher.value as voucher_value, voucher.type as voucher_type,"
                                . "game_type.".$lang."_name as game_type_name, game_type.image as game_image,"
                                . "booking.total, " . ENTITY::FIELD . ","
                                . " field.$lang" . "_name as field_name, player.name as player_name,"
                                . " player.phone as player_phone, company." . $lang . "_address as address,"
                                . " company." . $lang . "_name as company_name, company.logo,country.*"
                        )
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('company', 'company.company_id = field.company_id')
                        ->join('area', 'company.area_id = area.area_id')
                        ->join('country', 'country.country_id = area.country_id')
//                        ->join('curreny', 'country.curreny_id = curreny.curreny_id')
                        ->join('voucher', 'voucher.voucher = booking.voucher', 'left')
                        ->join('game_type', 'game_type.game_type_id = booking.game_type_id')
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

    public function get_max_id($field_id) {
        return $this->db->select('max(reference) as ref')
                        ->from('booking')
                        ->join('field base_field', 'base_field.field_id = booking.field_id')
                        ->where('base_field.field_id', $field_id)
                        ->where('booking.field_id IN(select field_id from `field` where field.company_id = base_field.company_id)')
                        ->get()->row();
    }

    public function get_my_bookings($player_id, $lang = "en") {
        return $this->db->select(
                                "booking.*,"
                                . "game_type.".$lang."_name as game_type_name, game_type.image as game_image,"
                                . "booking.total,  " . ENTITY::FIELD . ", "
                                . "field.$lang" . "_name as field_name, company." . $lang . "_name as company_name, "
                                . "company." . $lang . "_address as address, company.logo"
                        )
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('company', 'company.company_id = field.company_id')
                        ->join('game_type', 'game_type.game_type_id = booking.game_type_id')
                        ->where('player_id', $player_id)
                        ->where('booking.deleted', 0)
                        ->where('booking.state_id != ', BOOKING_STATE::DECLINED)
                        ->order_by('booking.date DESC, booking.start DESC')
                        ->get()->result();
    }

    public function pending_bookings($lang = "en") {
        return $this->db->select(
                                "booking.*,"
                                . "game_type.".$lang."_name as game_type_name, game_type.image as game_image,"
                                . "booking.total, " . ENTITY::FIELD . ","
                                . " field.$lang" . "_name as field_name, company.$lang" . "_name as company_name, "
                                . "player.name as player_name, player.phone as player_phone"
                        )
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('company', 'field.company_id = company.company_id')
                        ->join('player', 'player.player_id = booking.player_id')
                        ->join('game_type', 'game_type.game_type_id = booking.game_type_id')
                        ->where('booking.state_id = ', BOOKING_STATE::PENDING)
                        ->where('booking.deleted', 0)
                        ->where('field.deleted', 0)
                        ->order_by('booking.booking_id')
                        ->get()->result();
    }

    public function company_bookings($company_id, $lang = "en") {
        return $this->db->select("booking.*,"
                                . "game_type.".$lang."_name as game_type_name, game_type.image as game_image,"
                                . "booking.total, " . ENTITY::FIELD . ","
                                . " field.$lang" . "_name as field_name, company.$lang" . "_name as company_name,"
                                . " player.name as player_name, player.phone as player_phone"
                        )
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('company', 'field.company_id = company.company_id')
                        ->join('player', 'player.player_id = booking.player_id')
                        ->join('game_type', 'game_type.game_type_id = booking.game_type_id')
                        ->where('company.company_id', $company_id)
                        ->where('booking.state_id != ', BOOKING_STATE::DECLINED)
                        ->where('booking.state_id != ', BOOKING_STATE::CANCELLED)
                        ->where('booking.deleted', 0)
                        ->where('field.deleted', 0)
                        ->order_by('booking.booking_id')
                        ->get()->result();
    }

    public function company_pending_bookings($company_id, $lang = "en") {
        return $this->db->select("booking.*,"
                                . "game_type.".$lang."_name as game_type_name, game_type.image as game_image,"
                                . "booking.total, " . ENTITY::FIELD . ","
                                . " field.$lang" . "_name as field_name, company.$lang" . "_name as company_name,"
                                . " player.name as player_name, player.phone as player_phone"
                        )
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('company', 'field.company_id = company.company_id')
                        ->join('player', 'player.player_id = booking.player_id')
                        ->join('game_type', 'game_type.game_type_id = booking.game_type_id')
                        ->where('company.company_id', $company_id)
                        ->where('booking.state_id = ', BOOKING_STATE::PENDING)
                        ->where('booking.deleted', 0)
                        ->where('field.deleted', 0)
                        ->order_by('booking.booking_id')
                        ->get()->result();
    }

    public function company_bookings_calendar($company_id) {
        return $this->db->select("booking.*"
                        )
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('company', 'field.company_id = company.company_id')
                        ->join('player', 'player.player_id = booking.player_id')
                        ->join('game_type', 'game_type.game_type_id = booking.game_type_id')
                        ->where('company.company_id', $company_id)
                        ->where('booking.state_id != ', BOOKING_STATE::DECLINED)
                        ->where('booking.state_id != ', BOOKING_STATE::CANCELLED)
                        ->where('booking.deleted', 0)
                        ->where('field.deleted', 0)
                        ->order_by('booking.booking_id')
                        ->get()->result();
    }

    public function field_bookings($field_id, $lang = "en") {
        return $this->db->select("booking.*,"
                                . "game_type.".$lang."_name as game_type_name, game_type.image as game_image,"
                                . "booking.total, " . ENTITY::FIELD . ","
                                . " field.$lang" . "_name as field_name, player.name as player_name,"
                                . " player.phone as player_phone")
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('player', 'player.player_id = booking.player_id')
                        ->join('game_type', 'game_type.game_type_id = booking.game_type_id')
                        ->where('booking.state_id != ', BOOKING_STATE::DECLINED)
                        ->where('booking.field_id', $field_id)
                        ->where('booking.deleted', 0)
                        ->get()->result();
    }

    public function bookings_by_date_qry($field_id, $date, $lang = "en") {
        return $this->db->select("booking.*,"
                                . "game_type.".$lang."_name as game_type_name, game_type.image as game_image,"
                                . "booking.total, " . ENTITY::FIELD . ","
                                . " field.$lang" . "_name as field_name")
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('player', 'player.player_id = booking.player_id')
                        ->join('game_type', 'game_type.game_type_id = booking.game_type_id')
                        ->where('booking.field_id', $field_id)
                        ->where('booking.state_id', BOOKING_STATE::APPROVED)
                        ->where('booking.date', $date)
                        ->where('booking.deleted', 0)
                        ->order_by('booking.start ASC')
                        ->get()->result();
    }

    public function field_bookings_by_date($field_id, $date, $lang = "en", &$bookings, &$root = array()) {

        $b = $this->bookings_by_date_qry($field_id, $date, $lang = "en");
        $bookings = array_merge($bookings, $b);
        //var_dump($b);
        $root[] = $field_id;
        $parents = $this->field->get_parents($field_id, $root);
        foreach ($parents as $parent) {
            $b = $this->bookings_by_date_qry($parent->field_id, $date, $lang = "en");
            $bookings = array_merge($bookings, $b);
            $root[] = $parent->field_id;
        }
        $children = $this->field->get_children($field_id, $root);
        foreach ($children as $child) {
            $this->field_bookings_by_date($child->field_id, $date, $lang, $bookings, $root);
            //$bookings = array_merge($bookings, $b);
        }
        return $bookings;
    }

//    public function field_bookings_by_date($field_id, $date, $lang = "en") {
//        return $this->db->select("booking.*,"
//                                . "game_type.en_name as game_type_name, game_type.image as game_image,"
//                                . "booking.total, " . ENTITY::FIELD . ","
//                                . " field.$lang" . "_name as field_name")
//                        ->from('booking')
//                        ->join('field', 'field.field_id = booking.field_id')
//                        ->join('player', 'player.player_id = booking.player_id')
//                        ->join('game_type', 'game_type.game_type_id = booking.game_type_id')
//                        ->where('booking.field_id', $field_id)
//                        ->where('booking.state_id', BOOKING_STATE::APPROVED)
//                        ->where('booking.date', $date)
//                        ->where('booking.deleted', 0)
//                        ->order_by('booking.start ASC')
//                        ->get()->result();
//    }

    public function field_bookings_by_timing($field_id, $date, $start, $duration, $booking_id = null) {
        $where = "";
        if ($booking_id != null)
            $where .= "booking.booking_id != " . $booking_id . " and ";
        return $this->db->query("SELECT booking.*,
            game_type.en_name as game_type_name, game_type.image as game_image, booking.total FROM booking
JOIN field on field.field_id = booking.field_id 
JOIN game_type on game_type.game_type_id = booking.game_type_id 
WHERE $where booking.field_id =$field_id and booking.date = '$date' and booking.deleted = 0 and booking.state_id =" . BOOKING_STATE::APPROVED . " and ("
                        . "( "
                        . "booking.start >= time('$start')"
                        . "and booking.start < (time('$start') + INTERVAL $duration MINUTE)"
                        . ")"
                        . " OR ( "
                        . "(booking.start + INTERVAL booking.duration MINUTE) > time('$start')"
                        . "and (booking.start + INTERVAL booking.duration MINUTE) < (time('$start') + INTERVAL $duration MINUTE)"
                        . "))")->result();
    }

    public function upcoming_booking($player_id, $lang = "en") {
        $date = date('Y-m-d');
        $time = date("H:i:s");
        return $this->db->query("
            SELECT booking.*,game_type.".$lang."_name as game_type_name, game_type.image as game_image,booking.total,  field.$lang" . "_name as field_name, company." . $lang . "_name as comapny_name, company." . $lang . "_address as address, company.logo,
                company.company_id FROM booking
                JOIN field on field.field_id = booking.field_id
                JOIN game_type on game_type.game_type_id = booking.game_type_id 
                JOIN company on company.company_id = field.company_id
                    WHERE booking.state_id = " . BOOKING_STATE::APPROVED . " and booking.player_id =$player_id and booking.deleted = 0 "
                        . "and ("
                        . "booking.date > '$date'"
                        . " OR ( "
                        . "booking.date = '$date' and  time(booking.start) > time('$time')"
                        . ")"
                        . ") ORDER BY booking.date, booking.start"
                        . " LIMIT 1")->result();
    }

    public function last_bookings($player_id, $lang = "en") {
        $date = date('Y-m-d');
        $time = date("H:i:s");
        return $this->db->select("DISTINCT(`booking`.`field_id`),booking.booking_id, company.company_id ,"
                                . "game_type.".$lang."_name as game_type_name, game_type.image as game_image,"
                                . "booking.player_id,(booking.date) as date, (booking.start),"
                                . " booking.duration, booking.state_id,booking.total, booking.notes,"
                                . "  field.$lang" . "_name as field_name, company." . $lang . "_address as address, "
                                . "company." . $lang . "_name as company_name, company.logo")
                        ->DISTINCT()
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('company', 'company.company_id = field.company_id')
                        ->join('game_type', 'game_type.game_type_id = booking.game_type_id')
                        ->where('player_id', $player_id)
                        ->where('booking.state_id', BOOKING_STATE::APPROVED)
                        ->where("(booking.date < '$date' OR (booking.date = '$date' and booking.start < time('$time')))")
                        ->where('booking.deleted', 0)
                        ->order_by('company.company_id ASC, booking.date DESC, booking.start DESC')
                        //->group_by('booking.field_id')
                        // ->having('max(date) = date')
                        // ->limit(3)
                        ->get()->result();
    }

    public function reservations_report($company_id, $from_date, $to_date) {
        $this->db->query("SET sql_mode = ''");
        $total = $this->db->select('count(booking.booking_id) as bookings_number, '
                                . 'sum(booking.total) as total')
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->where('field.company_id', $company_id)
                        ->where('booking.state_id', BOOKING_STATE::APPROVED)
                        ->where('booking.deleted', 0)
                        ->where('booking.date >=', $from_date)
                        ->where('booking.date <= ', $to_date)
                        ->get()->row();
        if (!$total->bookings_number)
            return array();
        $details = $this->db->select('field.en_name as field_name, field.field_id,'
                                . 'count(booking.booking_id) as bookings_number, '
                                . "game_type.en_name as game_type_name, game_type.image as game_image,"
                                . 'sum(booking.total) as total')
                        ->from('booking')
                        ->join('field', 'field.field_id = booking.field_id')
                        ->join('game_type', 'game_type.game_type_id = booking.game_type_id')
                        ->where('field.company_id', $company_id)
                        ->where('booking.state_id', BOOKING_STATE::APPROVED)
                        ->where('booking.deleted', 0)
                        ->where('booking.date >=', $from_date)
                        ->where('booking.date <= ', $to_date)
                        ->group_by('booking.field_id')
                        ->get()->result();
        $this->db->query("SET sql_mode = 'only_full_group_by'");
        return array(
            "bookings_number" => $total->bookings_number,
            "total" => $total->total,
            "details" => $details
        );
    }

    public function field_reservations_report($company_id, $field_id, $from_date, $to_date) {
        $this->db->select('count(booking.booking_id) as bookings_number, '
                        . 'sum(booking.total) as total')
                ->from('booking')
                ->join('field', 'field.field_id = booking.field_id')
                ->where('field.company_id', $company_id);
        if ($field_id != 0)
            $this->db->where('field.field_id', $field_id);
        $total = $this->db->where('booking.state_id', BOOKING_STATE::APPROVED)
                        ->where('booking.deleted', 0)
                        ->where('field.deleted', 0)
                        ->where('booking.date >=', $from_date)
                        ->where('booking.date <= ', $to_date)
                        ->get()->row();

        if (!$total->bookings_number)
            return array();
        $this->db->select('field.en_name as field_name, field.field_id, booking.hour_rate, '
                        . "game_type.en_name as game_type_name, game_type.image as game_image,"
                        . 'booking.booking_id,booking.reference, booking.date, booking.start, booking.duration,'
                        . 'booking.player_id, player.name as player_name, booking.manually,'
                        . 'booking.total')
                ->from('booking')
                ->join('field', 'field.field_id = booking.field_id')
                ->join('player', 'player.player_id = booking.player_id')
                ->join('game_type', 'game_type.game_type_id = booking.game_type_id')
                ->where('field.company_id', $company_id);
        if ($field_id != 0)
            $this->db->where('field.field_id', $field_id);
        $details = $this->db->where('booking.state_id', BOOKING_STATE::APPROVED)
                        ->where('booking.deleted', 0)
                        ->where('field.deleted', 0)
                        ->where('booking.date >=', $from_date)
                        ->where('booking.date <= ', $to_date)
                        ->get()->result();

        return array(
            "bookings_number" => $total->bookings_number,
            "total" => $total->total,
            "details" => $details
        );
    }

    public function declined_reservations_report($company_id, $field_id, $from_date, $to_date) {
        $this->db->select('count(booking.booking_id) as bookings_number, '
                        . 'sum(booking.total) as total')
                ->from('booking')
                ->join('field', 'field.field_id = booking.field_id')
                ->where('field.company_id', $company_id);
        if ($field_id != 0)
            $this->db->where('field.field_id', $field_id);
        $total = $this->db->where('booking.state_id', BOOKING_STATE::DECLINED)
                        ->where('booking.deleted', 0)
                        ->where('field.deleted', 0)
                        ->where('booking.date >=', $from_date)
                        ->where('booking.date <= ', $to_date)
                        ->get()->row();

        if (!$total->bookings_number)
            return array();
        $this->db->select('field.en_name as field_name, field.field_id, booking.hour_rate, '
                        . "game_type.en_name as game_type_name, game_type.image as game_image,"
                        . 'booking.booking_id,booking.reference, booking.date, booking.start, booking.duration,'
                        . 'booking.player_id, player.name as player_name, booking.manually,'
                        . 'booking.total')
                ->from('booking')
                ->join('field', 'field.field_id = booking.field_id')
                ->join('player', 'player.player_id = booking.player_id')
                ->join('game_type', 'game_type.game_type_id = booking.game_type_id')
                ->where('field.company_id', $company_id);
        if ($field_id != 0)
            $this->db->where('field.field_id', $field_id);
        $details = $this->db->where('booking.state_id', BOOKING_STATE::DECLINED)
                        ->where('booking.deleted', 0)
                        ->where('field.deleted', 0)
                        ->where('booking.date >=', $from_date)
                        ->where('booking.date <= ', $to_date)
                        ->get()->result();

        return array(
            "bookings_number" => $total->bookings_number,
            "total" => $total->total,
            "details" => $details
        );
    }

}
