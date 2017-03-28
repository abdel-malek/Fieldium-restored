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
        if ($timing == 1) {
            $search_result = $this->search->search_with_timing($name, $game, $area, $timing, $start, $duration, $date, $lang);
            $res = array();
            $company = 0;
            foreach ($search_result as $field) {
                if ($company != $field->company_id) {
                    $company = $field->company_id;
                    $this->load->model('Services/field_service');
                    $field->fields_number = (string) count($this->field_service->get_by_company_with_timing($game, $field->company_id, $timing, $start, $date, $duration, 0.0, 0.0, $lang));
                    if ($field->logo != null)
                        $field->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $field->logo;
                    if ($field->image != null)
                        $field->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $field->image;
//                    if (!($timing == 2 && $field->available_time <= "00:00:00")) {
//                        $available = false;
//                        $this->load->model('Services/field_service');
//                        $this->load->model('DataSources/field');
//                        $company_fields = $this->field->get_by_company($field->company_id, 0.0, 0.0, $lang);
//                        foreach ($company_fields as $value) {
//                            if (
//                                    count(
//                                            $this->field_service->check_availability($value->field_id, $date)
//                                    ) != 0
//                            ) {
//
//                                $available = true;
//                                break;
//                            }
//                        }
//                    }
//                    if ($available)
                    $res[] = $field;
                }
            }
        } else {
            $search_result = $this->search->search($name, $game, $area, $lang);
            $res = array();
            foreach ($search_result as $company) {
                $this->load->model('Services/field_service');
                $company->fields_number = (string) count($this->field_service->get_by_company_with_timing($game, $company->company_id, $timing, $start, $date, $duration, 0.0, 0.0, $lang));
                if ($company->image != null)
                    $company->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $company->image;
                if ($company->logo != null)
                    $company->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $company->logo;
                if ($timing == 2) {
                    $available = false;
                    $this->load->model('DataSources/field');
                    $company_fields = $this->field->get_by_company($company->company_id, 0.0, 0.0, $lang);
                    foreach ($company_fields as $value) {
                        if (
                                count(
                                        $this->field_service->check_availability($value->field_id, $date, $game)
                                ) != 0
                        ) {
                            $available = true;
                            break;
                        }
                    }
                    if ($available)
                        $res[] = $company;
                } else
                    $res[] = $company;
            }
        }
        return $res;
    }

    public function __search($name, $game, $area, $timing, $start, $duration, $date, $lang = "en") {
        $search_result = $this->search->search($name, $game, $area, $timing, $start, $duration, $date, $lang);

        if ($timing != 0) {
            $res = array();
            $company = 0;
            foreach ($search_result as $field) {
                if ($company != $field->company_id) {
                    $company = $field->company_id;
                    $amenities = $this->amenity->get_field_amenities($field->field_id, $lang);
                    $result = array();
                    foreach ($amenities as $amenity) {
                        if ($amenity->image != "" && $amenity->image != null)
                            $amenity->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $amenity->image;
                        $result[] = $amenity;
                    }
                    $field->amenities = $result;

                    $images = $this->image->get_images($field->field_id);
                    $result = array();
                    foreach ($images as $image) {
                        if ($image->name != "" && $image->name != null) {
                            $image->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $image->name;
                        }
                        $result[] = $image;
                    }
                    $field->images = $result;
                    $games = $this->game->get_field_games($field->field_id, $lang);
                    $results = array();
                    foreach ($games as $game) {
                        if ($game->image != "" && $game->image != null) {
                            $game->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $game->image;
                        }
                        $results[] = $game;
                    }
                    $field->games = $results;
                    if ($field->logo != null)
                        $field->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $field->logo;
                    if (!($timing == 2 && $field->available_time <= "00:00:00")) {
                        $available = false;
                        $this->load->model('Services/field_service');
                        $this->load->model('DataSources/field');
                        $company_fields = $this->field->get_by_company($company_id, 0.0, 0.0, $lang);
                        foreach ($company_fields as $value) {
                            if (!($timing == 2 &&
                                    count(
                                            $this->field_service->check_availability($value->field_id, $date, $game)
                                    ) != 0
                                    )
                            ) {

                                $available = true;
                                break;
                            }
                        }
                    }
                    if ($available)
                        $res[] = $field;
                }
            }
        } else {
            $res = array();
            foreach ($search_result as $company) {
                if ($company->image != null)
                    $company->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $company->image;
                if ($company->logo != null)
                    $company->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $company->logo;
                $res[] = $company;
            }
        }
        return $res;
    }

    public function save_search($name, $game, $area, $timing, $start, $duration, $date, $player_id, $token) {
        $this->search->save_search($name, $game, $area, $timing, $start, $duration, $date, $player_id, $token);
    }

}
