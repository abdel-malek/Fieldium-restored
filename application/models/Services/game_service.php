<?php

/**
 * @author Amal Abdulraouf
 */
class game_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/game');
    }
  
    public function get_all() {
        $games = $this->game->get_all();
        return $games;
    }

    public function get($game_id) {
        $game = $this->game->get($game_id);
        if (!$game)
            throw new Game_Not_Found_Exception ();
        return $game;
    }
}

?>
