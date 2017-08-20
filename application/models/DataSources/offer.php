<?php

class offer extends CI_Model {

    private $LANG;

    public function __construct() {
        $CI = & get_instance();
        $this->LANG = $CI->language;
        $this->load->database();
    }

    public function get($offer_id) {
        return $this->db->select(
                                "offer.*, offer.description_" . $this->LANG . ' as description'
                        )
                        ->from('offer')
                        ->where('offer_id', $offer_id)
                        ->get()->row();
    }

    public function get_all($country = null) {
        $this->db->select(
                        "offer.*, '0' as booked_hours, offer.description_" . $this->LANG . ' as description', false
                )
                ->from('offer')
                ->where('offer.valid', 1)
                ->where('date(offer.expiry_date) >=', date('Y-m-d'));
        if ($country != null)
            $this->db->where('offer.country_id', $country);
        return $this->db->get()->result();
    }

    public function get_all_with_hours($player_id, $country = null) {
        $this->db->select('offer.*, offer.description_' . $this->LANG . ' as description, IFNULL(((select sum(duration) from booking '
                        . 'where booking.player_id = ' . $player_id . ' and '
                        . 'booking.state_id = ' . BOOKING_STATE::APPROVED . " and "
                        . "date(booking.date) >= date(offer.start_date) and "
                        . 'date(booking.date) <= date(offer.expiry_date) and '
                        . '((offer.public_field = 1 and '
                        . 'booking.field_id IN ('
                        . 'select field_id from field '
                        . 'join company on company.company_id = field.company_id '
                        . 'join area on company.area_id = area.area_id where area.country_id = offer.country_id'
                        . ')'
                        . ') OR booking.field_id IN ('
                        . 'select field.field_id from offer_company '
                        . 'join field on field.company_id = offer_company.company_id '
                        . 'where offer_company.offer_id = offer.offer_id )) '
                        . ') - ('
                        . 'select count(*) from offer_usage '
                        . 'where offer_usage.offer_id = offer.offer_id and '
                        . 'offer_usage.player_id = ' . $player_id
                        . ')*offer.set_of_minutes),0) as booked_hours', false)
                ->from('offer')
                ->join('offer_usage', 'offer_usage.offer_id = offer.offer_id and offer_usage.player_id = ' . $player_id, 'left')
//                ->join('player', '1 and player.player_id = '.$player_id, 'left')
                ->where('offer.valid', 1)
//                ->where('(player.country_id is null or offer.country_id = player.country_id)')
                ->where('date(offer.expiry_date) >=', date('Y-m-d'));
//                        ->where('((select sum(duration) from booking '
//                                . 'where booking.player_id = ' . $player_id . ' and '
//                                . 'booking.state_id = ' . BOOKING_STATE::APPROVED . " and "
//                                . "date(booking.creation_date) > date(offer.start_date) and "
//                                . 'date(booking.creation_date) <= date(offer.expiry_date) and '
//                                . '(offer.public_field = 1 OR booking.field_id IN ('
//                                . 'select field.field_id from offer_company '
//                                . 'join field on field.company_id = offer_company.company_id '
//                                . 'where offer_company.offer_id = offer.offer_id )) and '
//                                . '(offer.all_games = 1 OR booking.game_type_id IN ('
//                                . 'select offer_game.game_type_id from offer_game '
//                                . 'where offer_game.offer_id = offer.offer_id ))'
//                                . ') - ('
//                                . 'select count(*) from offer_usage '
//                                . 'where offer_usage.offer_id = offer.offer_id and '
//                                . 'offer_usage.player_id = ' . $player_id
//                                . ')*offer.set_of_minutes)/offer.set_of_minutes >= 1')
//        if ($country != null)
//            $this->db->where('offer.country_id = player.country_id');
        return $this->db->group_by('offer.offer_id')
                        ->get()->result();
    }

    public function get_offer_companies($offer_id) {
        return $this->db->select('offer_company.company_id,company.' . $this->LANG . '_name as company_name,'
                                . "CONCAT('" . base_url(UPLOADED_IMAGES_PATH) . "','/',company.logo) as logo_url", false)
                        ->from('offer_company')
                        ->join('company', 'company.company_id = offer_company.company_id')
                        ->where('offer_id', $offer_id)
                        ->get()->result();
    }

    public function get_offer_games($offer_id) {
        return $this->db->select('offer_game.game_type_id,game_type.' . $this->LANG . '_name as game_name, '
                                . "CONCAT('" . base_url(UPLOADED_IMAGES_PATH) . "','/',game_type.image) as image_url", false)
                        ->from('offer_game')
                        ->join('game_type', 'game_type.game_type_id = offer_game.game_type_id')
                        ->where('offer_id', $offer_id)
                        ->get()->result();
    }

    public function add($data) {
        if ($this->db->insert('offer', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

    public function add_company($data) {
        if ($this->db->insert('offer_company', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

    public function add_game($data) {
        if ($this->db->insert('offer_game', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

    public function delete($offer_id) {
        $this->db->where('offer_id', $offer_id)
                ->delete('offer_company');
        $this->db->where('offer_id', $offer_id)
                ->delete('offer_game');
        $this->db->where('offer_id', $offer_id)
                ->delete('offer');
    }

    public function update($offer_id, $data) {
        $res = $this->db
                ->where(array('offer_id' => $offer_id))
                ->update('offer', $data);
        if ($res) {
            return $res;
        } else {
            throw new DatabaseException();
        }
    }

    public function check_company($voucher_id, $company_id) {
        $valid = $this->db->select('*')
                        ->from('voucher_company')
                        ->where('voucher_company.voucher_id', $voucher_id)
                        ->where('voucher_company.company_id', $company_id)
                        ->get()->row();
        if ($valid)
            return true;
        else
            return false;
    }

    public function check_for_offers($player_id) {
        return $this->db->select('offer.*, offer.description_' . $this->LANG . ' as description, max(offer_usage.creation_date) as last_offer,((select sum(duration) from booking '
                                . 'where booking.player_id = ' . $player_id . ' and '
                                . 'booking.state_id = ' . BOOKING_STATE::APPROVED . " and "
                                . "date(booking.creation_date) > date(offer.start_date) and "
                                . 'date(booking.creation_date) <= date(offer.expiry_date) and '
                                . '(offer.public_field = 1 OR booking.field_id IN ('
                                . 'select field.field_id from offer_company '
                                . 'join field on field.company_id = offer_company.company_id '
                                . 'where offer_company.offer_id = offer.offer_id )) '
                                . ''
                                . ') - ('
                                . 'select count(*) from offer_usage '
                                . 'where offer_usage.offer_id = offer.offer_id and '
                                . 'offer_usage.player_id = ' . $player_id
                                . ')*offer.set_of_minutes)/offer.set_of_minutes as vouchers', false)
                        ->from('offer')
                        ->join('offer_usage', 'offer_usage.offer_id = offer.offer_id and offer_usage.player_id = ' . $player_id, 'left')
                        ->join('player', 'player.player_id = offer_usage.player_id ', 'left')
                        ->where('offer.valid', 1)
                        ->where('offer.country_id = player.country_id')
                        ->where('date(offer.expiry_date) >=', date('Y-m-d'))
                        ->where('((select sum(duration) from booking '
                                . 'where booking.player_id = ' . $player_id . ' and '
                                . 'booking.state_id = ' . BOOKING_STATE::APPROVED . " and "
                                . "date(booking.creation_date) >= date(offer.start_date) and "
                                . 'date(booking.creation_date) <= date(offer.expiry_date) and '
                                . '((offer.public_field = 1 and '
                                . 'booking.field_id IN ('
                                . 'select field_id from field '
                                . 'join company on company.company_id = field.company_id '
                                . 'join area on company.area_id = area.area_id where area.country_id = offer.country_id'
                                . ')'
                                . ') OR booking.field_id IN ('
                                . 'select field.field_id from offer_company '
                                . 'join field on field.company_id = offer_company.company_id '
                                . 'where offer_company.offer_id = offer.offer_id )) and '
                                . '(offer.all_games = 1 OR booking.game_type_id IN ('
                                . 'select offer_game.game_type_id from offer_game '
                                . 'where offer_game.offer_id = offer.offer_id ))'
                                . ') - ('
                                . 'select count(*) from offer_usage '
                                . 'where offer_usage.offer_id = offer.offer_id and '
                                . 'offer_usage.player_id = ' . $player_id
                                . ')*offer.set_of_minutes)/offer.set_of_minutes >= 1')
                        ->group_by('offer.offer_id')
                        ->get()->result();
    }

    public function generate_voucher($offer_id, $player_id, $booking_id) {
        $this->db->insert('offer_usage', array('player_id' => $player_id, 'offer_id' => $offer_id, 'booking_id' => $booking_id));
    }

}
