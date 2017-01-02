<?php

class search extends CI_Model {

    public function __construct() {
        $this->load->database();
        $this->load->model("Services/player_service");
    }

    public function get_searches_by_user($player_id) {
        $player = $this->player_service->get($player_id);
        return $this->db->select("*")
                        ->from('search')
                        ->where('player_id', $player_id)
                        ->or_where('token', $player->token)
                        ->get()->result();
    }

    public function get_searches_by_token($token) {
        return $this->db->select("*")
                        ->from('search')
                        ->where('token', $token)
                        ->get()->result();
    }

    public function save_notification($data) {
        if ($this->db->insert('notification', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

    public function search($name, $game, $area, $timing, $start, $duration, $date, $lang = "en") {
        if ($timing == 'true') {
            return $this->db->select(ENTITY::FIELD . ", "
                                    . "field." . $lang . "_name as name, 
                field." . $lang . "_description as description, 
                DATE_SUB(SUBTIME(field.close_time, field.open_time), INTERVAL IFNULL((SELECT sum(duration) from booking where 
                booking.field_id = field.field_id AND 
                booking.state_id = " . BOOKING_STATE::PENDING . " AND 
                booking.date = '" . $date . "' AND 
                booking.deleted = 0),0 ) HOUR
                )  as available_time", false)
                            ->from('field')
                            ->join('company', 'company.company_id = field.company_id')
                            ->join('field_game_type', 'field_game_type.field_id = field.field_id', 'left')
                            ->where("(company.area_id = $area OR field_game_type.game_type_id = $game OR field.en_name like '%$name%' OR field.ar_name like '%$name%')
                                and NOT EXISTS (SELECT booking.* FROM booking
                    WHERE booking.field_id =field.field_id and booking.date = '$date' and booking.deleted = 0 and ("
                                    . "( "
                                    . "booking.start >= time('$start')"
                                    . "and booking.start < (time('$start') + INTERVAL $duration HOUR)"
                                    . ")"
                                    . " OR ( "
                                    . "(booking.start + INTERVAL booking.duration HOUR) > time('$start')"
                                    . "and (booking.start + INTERVAL booking.duration HOUR) < (time('$start') + INTERVAL $duration HOUR)"
                                    . ")))"
                                    . " AND field.deleted = 0"
                                    . " AND (time('$start') between field.open_time and field.close_time)"
                                    . " AND ((time('$start') + INTERVAL $duration HOUR) between field.open_time and field.close_time)", '', false)
                            ->get()->result();
        } else {
            return $this->db->select(ENTITY::COMPANY . ", "
                                    . "company." . $lang . "_name as name, "
                                    . "company." . $lang . "_description as description, "
                                    . "company." . $lang . "_address as address, "
                                    . "(SELECT count(field_id) FROM field "
                                    . "where company.company_id = field.company_id AND field.deleted = 0"
                                    . ") as fields_number", false)
                            ->from('company ')
                            ->join('field', 'company.company_id = field.company_id', 'left')
                            ->join('field_game_type', 'field_game_type.field_id = field.field_id', 'left')
                            ->where('company.deleted', 0)
                            ->where('company.area_id', $area)
                            ->or_where('field_game_type.game_type_id', $game)
                            ->or_where("field.en_name like '%$name%'")
                            ->or_where("field.ar_name like '%$name%'")
                            ->order_by("fields_number desc")
                            ->group_by('company_id')
                            ->get()->result();
        }
    }

    public function save_search($name, $game, $area, $timing, $start, $duration, $date, $player_id, $token) {
        if ($timing == 'true')
            $this->db->insert('search', array(
                'player_id' => $player_id,
                'token' => $token,
                'area_id' => $area,
                'game_type_id' => $game,
                'start' => $start,
                'duration' => $duration,
                'date' => $date,
                'text' => $name
            ));
        else
            $this->db->insert('search', array(
                'player_id' => $player_id,
                'token' => $token,
                'area_id' => $area,
                'game_type_id' => $game,
                'text' => $name
            ));
    }

}
