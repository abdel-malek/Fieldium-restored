<?php

class field extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function get_all($lang = "en") {
        return $this->db->select(ENTITY::FIELD . ", "
                                . "field.".$lang . "_name as name, "
                                . "field.".$lang . "_description as description,"
                                . "company.en_name as company_name,"
                                . "company.latitude,"
                                . "company.longitude,"
                                . "company.logo"
                        )
                        ->from('field')
                 ->join('company', 'company.company_id = field.company_id')
                        ->where('deleted', 0)
                        ->get()->result();
    }

    public function get_featured_places($lang = "en") {
        return $this->db->select(ENTITY::FIELD . ", "
                                . "field.".$lang . "_name as name, "
                                . "field.".$lang . "_description as description,"
                                . "company.en_name as company_name,"
                                . "company.latitude,"
                                . "company.longitude,"
                                . "company.logo"
                        )
                        ->from('field')
                        ->join('company', 'company.company_id = field.company_id')
                        ->where('field.deleted', 0)
                        ->where('featured_place', 1)
                        ->get()->result();
    }

    public function get($field_id, $lang = "en") {
        return $this->db->select(ENTITY::FIELD . ", "
                                . "field.".$lang . "_name as name, "
                                . "field.".$lang . "_description as description,"
                                . "company.en_name as company_name,"
                                . "company.latitude,"
                                . "company.longitude,"
                                . "company.logo"
                        )
                        ->from('field')
                ->join('company', 'company.company_id = field.company_id')
                        ->where('field_id', $field_id)
                        ->where('field.deleted', false)
                        ->get()->row();
    }

    public function get_by_company($company_id, $lon, $lat, $lang = "en") {
        $this->db->select(ENTITY::FIELD . ", "
                        . "field." . $lang . "_name as name, 
                field." . $lang . "_description as description, 
                company.en_name as company_name, company.longitude, company.latitude, company.logo,
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
                ->where('field.deleted', 0)
                ->where('field.company_id', $company_id);
        if ($lat == 0 || $lon == 0)
            $this->db->order_by("field_id,available_time desc");
        else
            $this->db->order_by("field_id,distance ASC, available_time desc");
        return $this->db->get()->result();
    }

    public function add($data) {
        if ($this->db->insert('field', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

    public function update($field_id, $data) {
        $res = $this->db
                ->where(array('field_id' => $field_id))
                ->update('field', $data);
        if ($res) {
            return $res;
        } else {
            throw new DatabaseException();
        }
    }

}
