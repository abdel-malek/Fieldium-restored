<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class searches extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model("Services/search_service");
    }

    public function get_searches_get() {
        if ($this->current_user) {
            $searches = $this->search_service->get_searches_by_user($this->current_user->player_id);
            $this->response(array('status' => true, 'data' => $searches, 'message' => ""));
        } else {
            if (!$this->get('token'))
                $this->response(array('status' => false, 'data' => NULL, 'message' => "The token is required."));
            else {
                $searches = $this->search_service->get_searches_by_token($this->get('token'));
                $this->response(array('status' => true, 'data' => $searches, 'message' => ""));
            }
        }
    }

    public function get_searches_by_user_get() {
        $searches = $this->search_service->get_searches_by_user($this->current_user->player_id);
        $this->response(array('status' => true, 'data' => $searches, 'message' => ""));
    }

    public function search_get() {
        if (!$this->get('name') && (!$this->get('game_type') || !$this->get('area_id')))
            $this->response(array('status' => false, 'data' => null, 'message' => 'The area_id and game_type details are required.'));
        $name = $this->get('name');
        $game = $this->get('game_type');
        $area = $this->get('area_id');
        if (!$this->get('game_type')) $game = 0;
        if (!$this->get('area_id')) $area = 0;
        $timing = $this->get('timing');
        if ($timing == 2) {
            if (!$this->get('date'))
                $this->response(array('status' => false, 'data' => null, 'message' => 'The date is required.'));
            $date = $this->get('date');
            if (strtotime($date) < strtotime(date('Y-m-d')))
                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid date"));
        } else if ($timing == 1) {
            if (!$this->get('start') || !$this->get('duration') || !$this->get('date') || !$this->validate_time($this->get('start')))
                $this->response(array('status' => false, 'data' => null, 'message' => 'The date, start time and duration details are required.'));
            $start = $this->get('start');
            $duration = $this->get('duration');
            $date = $this->get('date');
            if (strtotime($date) < strtotime(date('Y-m-d')))
                $this->response(array('status' => false, 'data' => null, 'message' => "Invalid date"));
            if (strlen($start) <= 7)
                $start = "0" . $start;
        }
        $start = $this->get('start');
        $duration = $this->get('duration');
        $date = $this->get('date');
        $results = $this->search_service->search($name, $game, $area, $timing, $start, $duration, $date, $this->response->lang);
        //$this->search_service->save_search($name, $game, $area, $timing, $start, $duration, $date, ($this->current_user) ? $this->current_user->player_id : null, $this->get('token'));
        $this->response(array('status' => true, 'data' => $results, 'message' => ""));
    }

    public function validate_time($str) {
        if (strrchr($str, ":")) {
            list($hh, $mm, $ss) = explode(':', $str);
            if (!is_numeric($hh) || !is_numeric($mm) || !is_numeric($ss)) {
                return FALSE;
            } elseif ((int) $hh > 24 || (int) $mm > 59 || (int) $ss > 59) {
                return FALSE;
            } elseif (mktime((int) $hh, (int) $mm, (int) $ss) === FALSE) {
                return FALSE;
            }
            return TRUE;
        } else {
            return FALSE;
        }
    }

}
