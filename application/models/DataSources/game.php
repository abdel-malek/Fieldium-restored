<?php

class game extends CI_Model {

    public function __construct() {
        $this->load->database();
    }

    public function get_all($lang="en") {
        return $this->db->select(ENTITY::GAME_TYPE.", ".$lang."_name as name")
                        ->from('game_type')
                        ->get()->result();
    }

    public function get($game_id, $lang="en") {
        return $this->db->select(ENTITY::GAME_TYPE.", ".$lang."_name as name")
                        ->from('game_type')
                        ->where('game_type_id', $game_id)
                        ->get()->row();
    }

    public function delete_field_games($field_id) {
        $this->db->delete("field_game_type", array('field_id' => $field_id));
    }

    public function get_field_games($field_id, $lang="en") {
        return $this->db->select(ENTITY::GAME_TYPE.", game_type.".$lang."_name as name")
                        ->from('field_game_type')
                        ->join('game_type', 'field_game_type.game_type_id = game_type.game_type_id')
                        ->where('field_id', $field_id)
                        ->get()->result();
    }

    public function add_field_game($data) {
        if ($this->db->insert('field_game_type', $data)) {
            $id = $this->db->insert_id();
            return $id;
        } else {
            throw new Database_Exception();
        }
    }

}
