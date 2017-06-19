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
                                "voucher.*, voucher.description_". $this->LANG . ' as description', false
                        )
                        ->from('voucher')
                        ->where('voucher', $voucher)
                        ->get()->row();
    }

    public function get($voucher_id) {
        return $this->db->select(
                                "voucher.*, voucher.description_". $this->LANG . ' as description', false
                        )
                        ->from('voucher')
                        ->where('voucher_id', $voucher_id)
                        ->get()->row();
    }

    public function get_voucher_users($voucher_id) {
        return $this->db->select('voucher_player.*, player.name as player_name')
                        ->from('voucher_player')
                        ->join('player', 'player.player_id = voucher_player.player_id', 'left')
                        ->where('voucher_id', $voucher_id)
                        ->get()->result();
    }

    public function get_voucher_companies($voucher_id) {
        return $this->db->select('voucher_company.company_id,company.' . $this->LANG . '_name as company_name')
                        ->from('voucher_company')
                        ->join('company', 'company.company_id = voucher_company.company_id')
                        ->where('voucher_id', $voucher_id)
                        ->get()->result();
    }

    public function get_voucher_games($voucher_id) {
        return $this->db->select('voucher_game.game_type_id,game_type.' . $this->LANG . '_name as game_name')
                        ->from('voucher_game')
                        ->join('game_type', 'game_type.game_type_id = voucher_game.game_type_id')
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

    public function add_company($data) {
        if ($this->db->insert('voucher_company', $data)) {
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

    public function add_game($data) {
        if ($this->db->insert('voucher_game', $data)) {
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
                ->delete('voucher_company');
        $this->db->where('voucher_id', $voucher_id)
                ->delete('voucher_game');
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

    public function check_game($voucher_id, $game_id) {

        $valid = $this->db->select('*')
                        ->from('voucher_game')
                        ->where('voucher_game.voucher_id', $voucher_id)
                        ->where('voucher_game.game_type_id', $game_id)
                        ->get()->row();
        if ($valid)
            return true;
        else
            return false;
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

    public function get_my_vouchers($player_id, $field_id = null, $date = null, $start = null, $duration = 0, $game = null) {
        $this->db->select("voucher.*, voucher.description_". $this->LANG . ' as description', false)
                ->from('voucher_player')
                ->join('voucher', 'voucher.voucher_id = voucher_player.voucher_id')
                ->join('player', 'player.phone = voucher_player.phone', 'left')
                ->where('(voucher_player.player_id = ' . $player_id . ' OR player.player_id = ' . $player_id . ")")
                ->where('voucher.valid', 1)
                ->where('voucher.expiry_date >=', date('Y-m-d'));

        if ($date != null) {
            $this->db->where('voucher.expiry_date >=', $date);
        }

        if ($field_id != null)
            $this->db->where('(voucher.public_field = 1 OR \'' . $field_id . '\' IN ('
                    . 'select field.field_id from voucher_company '
                    . 'join field on field.company_id = voucher_company.company_id '
                    . 'where voucher_company.voucher_id = voucher.voucher_id )) ');
        if ($start != null) {
            $endtime = strtotime($start) + doubleval($duration) * 60;
            $end = strftime('%H:%M:%S', $endtime);
            $this->db->where('(voucher.from_hour is NULL OR TIME(voucher.from_hour) <= TIME(\'' . $start . '\'))')
                    ->where('(voucher.from_hour is NULL OR TIME(voucher.to_hour) >= TIME(\'' . $end . '\'))');
        }

        if ($game != null) {
            $this->db->where('(voucher.all_games = 1 OR \'' . $game . '\' IN ('
                    . 'select voucher_game.game_type_id from voucher_game '
                    . 'where voucher_game.voucher_id = voucher.voucher_id )) ');
        }

        return $this->db->group_by('voucher.voucher_id')
                        ->get()->result();
    }

}
