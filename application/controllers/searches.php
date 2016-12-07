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
        $name = $this->get('name');
        $game = $this->get('game_type');
        $area = $this->get('area_id');
        if (!$this->get('game_type') || !$this->get('area_id'))
                $this->response(array('status' => false, 'data' => null, 'message' => 'The area_id and game_type details are required.'));
        $timing = $this->get('timing');
        if($timing == 'true' || $timing == true) {
            if (!$this->get('start') || !$this->get('duration') || !$this->get('date'))
                $this->response(array('status' => false, 'data' => null, 'message' => 'The date and time details are required.'));
        }
        $start = $this->get('start');
        $duration = $this->get('duration');
        $date = $this->get('date');
        $results = $this->search_service->search($name, $game, $area, $timing, $start, $duration, $date, $this->response->lang);
        $this->search_service->save_search($name, $game, $area, $timing, $start, $duration, $date,($this->current_user)?$this->current_user->player_id:null, $this->get('token'));
        $this->response(array('status' => true, 'data' => $results, 'message' => ""));
    }

}
