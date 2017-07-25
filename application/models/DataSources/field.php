<?php

class field extends CI_Model {

    private $country;

    public function __construct() {
        $this->load->database();
        $CI = & get_instance();
        $this->country = $CI->user_country;
    }

    public function get_all($lang = "en") {
        return $this->db->select(ENTITY::FIELD . ", "
                                . "field." . $lang . "_name as name, "
                                . "field." . $lang . "_description as description,"
                                . "company.en_name as company_name,"
                                . "company.latitude,"
                                . "company.longitude,"
                                . "company.logo,"
                                . "company.en_address as address"
                        )
                        ->from('field')
                        ->join('company', 'company.company_id = field.company_id')
                        ->join('area', 'company.area_id = area.area_id')
                        ->where('area.country_id', $this->country)
                        ->where('deleted', 0)
                        ->get()->result();
    }

    public function get_featured_places($lang = "en") {
        return $this->db->select(ENTITY::FIELD . ", "
                                . "field." . $lang . "_name as name, "
                                . "field." . $lang . "_description as description,"
                                . "company.en_name as company_name,"
                                . "company.latitude,"
                                . "company.longitude,"
                                . "company.logo,"
                                . "company.en_address as address"
                        )
                        ->from('field')
                        ->join('company', 'company.company_id = field.company_id')
                        ->join('area', 'company.area_id = area.area_id')
                        ->where('area.country_id', $this->country)
                        ->where('field.deleted', 0)
                        ->where('featured_place', 1)
                        ->get()->result();
    }

    public function get($field_id, $lang = "en") {
        return $this->db->select(ENTITY::FIELD . ", "
                                . "field." . $lang . "_name as name, "
                                . "field." . $lang . "_description as description,"
                                . "company.en_name as company_name,"
                                . "company.latitude,"
                                . "company.longitude,"
                                . "company.logo,"
                                . "company.en_address as address"
                        )
                        ->from('field')
                        ->join('company', 'company.company_id = field.company_id')
                        ->join('area', 'company.area_id = area.area_id')
                        ->where('area.country_id', $this->country)
                        ->where('field_id', $field_id)
                        ->where('field.deleted', false)
                        ->get()->row();
    }

    public function get_by_company($company_id, $lon, $lat, $lang = "en") {
//        var_dump($this->country);
        $this->db->select(ENTITY::FIELD . ", "
                        . "field." . $lang . "_name as name, 
                field." . $lang . "_description as description, 
                company.en_name as company_name, company.longitude, company.latitude, company.logo,
                                company.en_address as address,
                sqrt(pow(longitude- $lon,2) + pow(latitude - $lat,2)) as distance, 
                IFNULL((SELECT sum(duration) from booking where 
                booking.field_id = field.field_id AND 
                booking.state_id = " . BOOKING_STATE::PENDING . " AND 
                booking.date = '" . date('Y-m-d') . "' AND 
                booking.deleted = 0), 0 ) as busy_time, 
                TIMEDIFF(field.close_time, field.open_time) as opening_hours_sum, 
                DATE_SUB(SUBTIME(field.close_time, field.open_time), INTERVAL IFNULL((SELECT sum(duration) from booking where 
                booking.field_id = field.field_id AND 
                booking.state_id = " . BOOKING_STATE::PENDING . " AND 
                booking.date = '" . date('Y-m-d') . "' AND 
                booking.deleted = 0),0 ) HOUR
                )  as available_time", false)
                ->from('field')
                ->join('company', 'company.company_id = field.company_id')
                ->join('area', 'company.area_id = area.area_id')
                ->where('area.country_id', $this->country)
                ->where('field.deleted', 0)
                ->where('field.company_id', $company_id);
        if ($lat == 0 || $lon == 0)
            $this->db->order_by("field_id,available_time desc");
        else
            $this->db->order_by("field_id,distance ASC, available_time desc");
        return $this->db->get()->result();
    }

    public function get_by_company_with_timing($game, $company_id, $timing, $start, $date, $duration, $lon, $lat, $lang = "en") {
        $hour = date('H');
        if (date('i') > "00")
            $hour++;
        $current = $hour . ":00:00";
        $available_time_query = "DATE_FORMAT(DATE_SUB(  
                            IF(
                                close_time > open_time, SUBTIME(field.close_time, field.open_time), 
                               (
                                   ADDTIME(
                                           SUBTIME( 
                                                  '12:00:00', SUBTIME(field.open_time, field.close_time)  
                                                  ),'12:00:00'
                                          )         
                                )
                            )
                            ,
                            INTERVAL IFNULL((SELECT sum(duration) from booking where "
                . "booking.field_id = field.field_id AND "
                . "booking.state_id = " . BOOKING_STATE::PENDING
                . " AND booking.date = '" . $date
                . "' AND booking.deleted = 0),0 ) MINUTE ), '%H:%i:%s')   as available_time";
        $where = "( field.deleted = 0 and field.company_id = $company_id ";
        if ($game != 0) {
            $where .= "and field_game_type.game_type_id = $game";
        }
        $where .= ") ";
        $where .= "and NOT EXISTS (SELECT booking.* FROM booking
                    WHERE booking.field_id =field.field_id and booking.date = '$date' and booking.deleted = 0 and ("
                . "( "
                . "booking.start >= time('$start')"
                . " and booking.start < (time('$start') + INTERVAL $duration MINUTE)"
                . ") OR booking.start = time('$start') "
                . " OR ( "
                . "(booking.start + INTERVAL booking.duration MINUTE) > time('$start')"
                . " and (booking.start + INTERVAL booking.duration MINUTE) <= (time('$start') + INTERVAL $duration MINUTE)"
                . ")))"
                . " AND field.deleted = 0  and field.company_id = " . $company_id
                . " AND (
                            (close_time < open_time and NOT (
                                (time('$start') >= `field`.close_time and time('$start') < `field`.open_time) OR
                                (
                                    (time('$start') + INTERVAL $duration MINUTE) > `field`.close_time 
                                    and (time('$start') + INTERVAL $duration MINUTE) <= `field`.open_time
                                )
                                )
                            )
                            OR
                            (close_time > open_time and (
                                    time('$start') >= `field`.open_time and time('$start') < `field`.close_time and
                                    (time('$start') + INTERVAL $duration MINUTE) > `field`.open_time 
                                    and (time('$start') + INTERVAL $duration MINUTE) <= `field`.close_time
                                )
                            )    
                            )";

        $this->db->select(ENTITY::FIELD . ", "
                        . "field." . $lang . "_name as name,"
                        . "field." . $lang . "_description as description, "
                        . "sqrt(pow(longitude- $lon,2) + pow(latitude - $lat,2)) as distance,"
                        . "company.en_name as company_name, company.longitude, company.latitude, company.logo,"
                        . $available_time_query
                        , false)
                ->from('field')
                ->join('company', 'company.company_id = field.company_id')
                ->join('area', 'company.area_id = area.area_id')
                ->where('area.country_id', $this->country)
                ->join('field_game_type', 'field_game_type.field_id = field.field_id', 'left');
        $this->db->where($where, '', false);
        if ($lat == 0 || $lon == 0)
            $this->db->order_by("field_id,available_time desc");
        else
            $this->db->order_by("field_id,distance ASC, available_time desc");
        $res = $this->db->group_by('field_id')->get()->result();
        return $res;
    }

//    public function get_by_company_with_timing($game, $company_id, $timing, $start, $date, $duration, $lon, $lat, $lang = "en") {
//        $available_time_query = "DATE_FORMAT(DATE_SUB(  
//                            IF(
//                                close_time > open_time, SUBTIME(field.close_time, field.open_time), 
//                               (
//                                   ADDTIME(
//                                           SUBTIME( 
//                                                  '12:00:00', SUBTIME(field.open_time, field.close_time)  
//                                                  ),'12:00:00'
//                                          )         
//                                )
//                            )
//                            ,
//                            INTERVAL IFNULL((SELECT sum(duration) from booking where "
//                . "booking.field_id = field.field_id AND "
//                . "booking.state_id = " . BOOKING_STATE::PENDING
//                . " AND booking.date = '" . $date
//                . "' AND booking.deleted = 0),0 ) hour ), '%H:%i:%s')   as available_time";
//        $where = "( field.deleted = 0 and field.company_id = $company_id ";
//        if ($game != 0) {
//            $where .= "and field_game_type.game_type_id = $game";
//        }
//        $where .= ")";
//        if ($timing == 1) {
//            $where .= " and NOT EXISTS (SELECT booking.* FROM booking
//                    WHERE booking.field_id =field.field_id and booking.date = '$date' and booking.deleted = 0 and ("
//                    . "( "
//                    . "booking.start >= time('$start')"
//                    . " and booking.start < (time('$start') + INTERVAL $duration HOUR)"
//                    . ") OR booking.start = time('$start') "
//                    . " OR ( "
//                    . "(booking.start + INTERVAL booking.duration HOUR) > time('$start')"
//                    . " and (booking.start + INTERVAL booking.duration HOUR) <= (time('$start') + INTERVAL $duration HOUR)"
//                    . ")))"
//                    . " AND field.deleted = 0"
//                    . " AND (
//                            (close_time < open_time and NOT (
//                                (time('$start') >= `field`.close_time and time('$start') < `field`.open_time) OR
//                                (
//                                    (time('$start') + INTERVAL $duration HOUR) > `field`.close_time 
//                                    and (time('$start') + INTERVAL $duration HOUR) <= `field`.open_time
//                                )
//                                )
//                            )
//                            OR
//                            (close_time > open_time and (
//                                    time('$start') >= `field`.open_time and time('$start') < `field`.close_time and
//                                    (time('$start') + INTERVAL $duration HOUR) > `field`.open_time 
//                                    and (time('$start') + INTERVAL $duration HOUR) <= `field`.close_time
//                                )
//                            )    
//                            )";
//        }
//        $this->db->select(ENTITY::FIELD . ", "
//                        . "field." . $lang . "_name as name,"
//                        . "field." . $lang . "_description as description, "
//                        . "sqrt(pow(longitude- $lon,2) + pow(latitude - $lat,2)) as distance,"
//                        . "company.en_name as company_name, company.longitude, company.latitude, company.logo,"
//                        . $available_time_query
//                        , false)
//                ->from('field')
//                ->join('company', 'company.company_id = field.company_id')
//                ->join('field_game_type', 'field_game_type.field_id = field.field_id', 'left');
//        $this->db->where($where, '', false);
//        if ($lat == 0 || $lon == 0)
//            $this->db->order_by("field_id,available_time desc");
//        else
//            $this->db->order_by("field_id,distance ASC, available_time desc");
//        $res = $this->db->group_by('field_id')->get()->result();
//        return $res;
//    }

    public function get_by_company_with_filters($game, $company_id, $lon, $lat, $lang = "en") {
        $this->db->select(ENTITY::FIELD . ", "
                        . "field." . $lang . "_name as name,"
                        . "field." . $lang . "_description as description, "
                        . "sqrt(pow(longitude- $lon,2) + pow(latitude - $lat,2)) as distance,"
                        . "company.en_name as company_name, company.longitude, company.latitude, company.logo"
                        , false)
                ->from('field')
                ->join('company', 'company.company_id = field.company_id')
                ->join('area', 'company.area_id = area.area_id')
                ->where('area.country_id', $this->country)
                ->join('field_game_type', 'field_game_type.field_id = field.field_id', 'left');
        $this->db->where('field.deleted', 0);
        $this->db->where('field.company_id', $company_id);
        if ($game != 0) {
            $this->db->where('field_game_type.game_type_id', $game);
        }
        if ($lat == 0 || $lon == 0)
            $this->db->order_by("field_id");
        else
            $this->db->order_by("field_id,distance ASC");
        $res = $this->db->group_by('field_id')->get()->result();
        return $res;
    }

    public function add($data) {
        if ($this->db->insert('field', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

    public
            function update($field_id, $data) {
        $res = $this->db
                ->where(array('field_id' => $field_id))
                ->update('field', $data);
        if ($res) {
            return $res;
        } else {
            throw new DatabaseException();
        }
    }

    public function get_children($field_id, $root = null) {
        $this->db->select(ENTITY::FIELD . ", "
                        . "field.en_name as name,"
                        . "company.en_name as company_name,"
                        . "company.latitude,"
                        . "company.longitude,"
                        . "company.logo,"
                        . "company.en_address as address"
                )
                ->from('field_child')
                ->join('field', 'field_child.child_id = field.field_id')
                ->join('company', 'company.company_id = field.company_id')
                ->join('area', 'company.area_id = area.area_id')
                ->where('area.country_id', $this->country)
                ->where('field_child.parent_id', $field_id);
        if ($root != null)
            $this->db->where('field.field_id !=', $root);
        return $this->db->where('field.deleted', 0)
                        ->get()->result();
    }

    public function get_parents($field_id, $root = null) {
        $this->db->select(ENTITY::FIELD . ", "
                        . "company.en_name as company_name,"
                        . "company.latitude,"
                        . "company.longitude,"
                        . "company.logo,"
                        . "company.en_address as address"
                )
                ->from('field_child')
                ->join('field', 'field_child.parent_id = field.field_id')
                ->join('company', 'company.company_id = field.company_id')
                ->join('area', 'company.area_id = area.area_id')
                ->where('area.country_id', $this->country)
                ->where('field_child.child_id', $field_id);
        if ($root != null)
            $this->db->where('field.field_id !=', $root);
        return $this->db->where('field.deleted', 0)
                        ->get()->result();
    }

    public function add_child($parent, $child) {
        $this->db->insert('field_child', array('parent_id' => $parent, 'child_id' => $child));
    }

}
