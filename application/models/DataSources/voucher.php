<?php

class voucher extends CI_Model {

    private $LANG;

    public function __construct() {
        $CI = & get_instance();
        $this->LANG = $CI->language;
        $this->load->database();
    }

    public function get_by_voucher($voucher) {
        return $this->db->select(
                                "voucher.*"
                        )
                        ->from('voucher')
                        ->where('voucher', $voucher)
                        ->get()->row();
    }

    public function get($voucher_id) {
        return $this->db->select(
                                "voucher.*"
                        )
                        ->from('voucher')
                        ->where('voucher_id', $voucher_id)
                        ->get()->row();
    }

    public function get_voucher_users($voucher_id) {
        return $this->db->select('*')
                        ->from('voucher_player')
                        ->where('voucher_id', $voucher_id)
                        ->get()->result();
    }

    public function get_voucher_fields($voucher_id) {
        return $this->db->select('voucher_field.field_id,field.' . $this->LANG . '_name as field_name')
                        ->from('voucher_field')
                        ->join('field', 'field.field_id = voucher_field.field_id')
                        ->where('voucher_id', $voucher_id)
                        ->get()->result();
    }

    public function add($data) {
        if ($this->db->insert('voucher', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

    public function add_field($data) {
        if ($this->db->insert('voucher_field', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

    public function add_player($data) {
        if ($this->db->insert('voucher_player', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

    public function delete($voucher_id) {
        $this->db->where('voucher_id', $voucher_id)
                ->delete('voucher_player');
        $this->db->where('voucher_id', $voucher_id)
                ->delete('voucher_field');
        $this->db->where('voucher_id', $voucher_id)
                ->delete('voucher');
    }

    public function update($voucher_id, $data) {
        $res = $this->db
                ->where(array('voucher_id' => $voucher_id))
                ->update('voucher', $data);
        if ($res) {
            return $res;
        } else {
            throw new DatabaseException();
        }
    }

    public function check_player($voucher_id, $player_id) {

        $valid = $this->db->select('*')
                        ->from('voucher_player')
                        ->join('player', 'player.phone = voucher_player.phone', 'left')
                        ->where('voucher_player.voucher_id', $voucher_id)
                        ->where('(voucher_player.player_id = ' . $player_id . ' OR player.player_id = ' . $player_id . ")")
                        ->get()->row();
        if ($valid)
            return true;
        else
            return false;
    }

    public function check_field($voucher_id, $field_id) {
        $valid = $this->db->select('*')
                        ->from('voucher_field')
                        ->where('voucher_field.voucher_id', $voucher_id)
                        ->where('voucher_field.field_id', $field_id)
                        ->get()->row();
        if ($valid)
            return true;
        else
            return false;
    }

    public function get_my_vouchers($player_id) {

        return $this->db->select('voucher.*')
                        ->from('voucher_player')
                        ->join('voucher', 'voucher.voucher_id = voucher_player.voucher_id')
                        ->join('player', 'player.phone = voucher_player.phone', 'left')
                        ->where('(voucher_player.player_id = ' . $player_id . ' OR player.player_id = ' . $player_id . ")")
                        ->group_by('voucher.voucher_id')
                        ->get()->result();
    }

}
