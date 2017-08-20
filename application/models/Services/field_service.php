<?php

/**
 * @author Amal Abdulraouf
 */
class field_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/field');
        $this->load->model('DataSources/amenity');
        $this->load->model('DataSources/image');
        $this->load->model('DataSources/game');
        $this->load->model('Services/amenity_service');
        $this->load->model('Services/game_service');
    }
    
    public function get($field_id, $lang = "en") {
        $field = $this->field->get($field_id, $lang);
        if (!$field)
            throw new Field_Not_Found_Exception($lang);
        $amenities = $this->amenity->get_field_amenities($field_id, $lang);
        $result = array();
        foreach ($amenities as $amenity) {
            if ($amenity->image != "" && $amenity->image != null)
                $amenity->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $amenity->image;
            $result[] = $amenity;
        }
        $field->amenities = $result;

        $images = $this->image->get_images($field_id);
        $result = array();
        foreach ($images as $image) {
            if ($image->name != "" && $image->name != null) {
                $image->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $image->name;
            }
            $result[] = $image;
        }
        $field->images = $result;
        $games = $this->game->get_field_games($field_id, $lang);
        $results = array();
        foreach ($games as $game) {
            if ($game->image != "" && $game->image != null) {
                $game->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $game->image;
            }
            $results[] = $game;
        }
        $field->games = $results;
        return $field;
    }

    public function get_all($lang = "en") {
        $fields = $this->field->get_all($lang);
        $result = array();
        foreach ($fields as $field) {
            $result[] = $field;
            $result->amenities = $this->amenity->get_field_amenities($field->field_id, $lang);
            $images = $this->image->get_images($field->field_id);
            $results = array();
            foreach ($images as $image) {
                if ($image->name != "" && $image->name != null) {
                    $image->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $image->name;
                }
                $results[] = $image;
            }
            $result->images = $results;
            $result->games = $this->game->get_field_games($field->field_id, $lang);
        }
        return $result;
    }

    public function get_by_company($company_id, $lon, $lat, $lang = "en") {
        $fields = $this->field->get_by_company($company_id, $lon, $lat, $lang);
        $result = array();
        foreach ($fields as $field) {
            $amenities = $this->amenity->get_field_amenities($field->field_id, $lang);
            $results = array();
            foreach ($amenities as $amenity) {
                if ($amenity->image != "" && $amenity->image != null) {
                    $amenity->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $amenity->image;
                }
                $results[] = $amenity;
            }
            $field->amenities = $results;
            $images = $this->image->get_images($field->field_id);
            $results = array();
            foreach ($images as $image) {
                if ($image->name != "" && $image->name != null) {
                    $image->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $image->name;
                }
                $results[] = $image;
            }
            $field->images = $results;
            $games = $this->game->get_field_games($field->field_id, $lang);
            $results = array();
            foreach ($games as $game) {
                if ($game->image != "" && $game->image != null) {
                    $game->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $game->image;
                }
                $results[] = $game;
            }
            $field->games = $results;
            $result[] = $field;
        }
        return $result;
    }

    public function get_featured_places($lang = "en") {
        $fields = $this->field->get_featured_places($lang);
        $result = array();
        foreach ($fields as $field) {
           $amenities = $this->amenity->get_field_amenities($field->field_id, $lang);
            $results = array();
            foreach ($amenities as $amenity) {
                if ($amenity->image != "" && $amenity->image != null) {
                    $amenity->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $amenity->image;
                }
                $results[] = $amenity;
            }
            $field->amenities = $results;
            $images = $this->image->get_images($field->field_id);
            $results = array();
            foreach ($images as $image) {
                if ($image->name != "" && $image->name != null) {
                    $image->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $image->name;
                }
                $results[] = $image;
            }
            $field->images = $results;
            $games = $this->game->get_field_games($field->field_id, $lang);
            $results = array();
            foreach ($games as $game) {
                if ($game->image != "" && $game->image != null) {
                    $game->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $game->image;
                }
                $results[] = $game;
            }
            $field->games = $results;
            $result[] = $field;
        }
        return $result;
    }

}

?>
