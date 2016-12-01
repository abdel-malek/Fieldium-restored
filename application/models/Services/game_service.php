<?php

/**
 * @author Amal Abdulraouf
 */
class game_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/game');
    }
  
    public function get_all($lang="en") {
        $games = $this->game->get_all($lang);
        return $games;
    }

    public function get($game_id, $lang="en") {
        $game = $this->game->get($game_id, $lang);
        if (!$game)
            throw new Game_Not_Found_Exception ($lang);
        return $game;
    }
}

?>
