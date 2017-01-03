<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class search_service extends CI_Model {

    public function __construct() {
        $this->load->model('DataSources/search');
        $this->load->model('DataSources/amenity');
        $this->load->model('DataSources/game');
        $this->load->model('DataSources/image');
    }

    public function get_searches_by_user($player_id) {
        return $this->search->get_searches_by_user($player_id);
    }

    public function get_searches_by_token($token) {
        return $this->search->get_searches_by_token($token);
    }

    public function search($name, $game, $area, $timing, $start, $duration, $date, $lang = "en") {
        $search_result = $this->search->search($name, $game, $area, $timing, $start, $duration, $date, $lang);

        if ($timing == 'true') {
            $result = array();
            foreach ($search_result as $field) {
                $field->amenities = $this->amenity->get_field_amenities($field->field_id, $lang);
                $field->images = $this->image->get_images($field->field_id);
                $field->games = $this->game->get_field_games($field->field_id, $lang);
                $result[] = $field;
            }
        } else {
            $result = array();
            foreach ($search_result as $company) {
                if ($company->image != null)
                    $company->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $company->image;
                if ($company->logo != null)
                    $company->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $company->logo;
                $result[] = $company;
            }
        }
        return $result;
    }

    public function save_search($name, $game, $area, $timing, $start, $duration, $date, $player_id, $token) {
        $this->search->save_search($name, $game, $area, $timing, $start, $duration, $date, $player_id, $token);
    }

}
