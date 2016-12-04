<?php

defined('BASEPATH') OR exit('No direct script access allowed');

class search_service extends CI_Model {

    public function __construct() {
        $this->load->model('DataSources/search');
    }

    public function get_searches_by_user($player_id) {
        return $this->search->get_searches_by_user($player_id);
    }

    public function get_searches_by_token($token) {
        return $this->search->get_searches_by_token($token);
    }

}
