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
        $this->load->model('Services/booking_service');
    }

    public function create(
    $company_id, $name, $ar_name, $phone, $hour_rate, $open_time, $cloes_time, $area_x, $area_y, $max_capacity, $description, $ar_description, $images, $amenities, $games_types, $lang
    ) {

        $field_id = $this->field->add(array(
            'company_id' => $company_id,
            'en_name' => $name,
            'ar_name' => $ar_name,
            'phone' => $phone,
            'en_description' => $description,
            'ar_description' => $ar_description,
            'hour_rate' => $hour_rate,
            'open_time' => $open_time,
            'close_time' => $cloes_time,
            'area_x' => $area_x,
            'area_y' => $area_y,
            'max_capacity' => $max_capacity
        ));

        if ($amenities) {
            foreach ($amenities as $am) {
                if (!is_array($am)) {
                    try {
                        $amenity = $this->amenity_service->get($am, $lang);
                        $this->amenity->add_field_amenity(array(
                            'field_id' => $field_id,
                            'amenity_id' => $amenity->amenity_id
                        ));
                    } catch (Amenity_Not_Found_Exception $e) {
                        
                    }
                }
            }
        }

        if ($games_types) {
            foreach ($games_types as $type) {
                if (!is_array($type)) {
                    try {
                        $games_type = $this->game_service->get($type, $lang);
                        $this->game->add_field_game(array(
                            'field_id' => $field_id,
                            'game_type_id' => $games_type->game_type_id
                        ));
                    } catch (Game_Not_Found_Exception $e) {
                        
                    }
                }
            }
        }

        if ($images) {
            foreach ($images as $image) {
                $images = $this->image->update($image, array(
                    'field_id' => $field_id
                ));
            }
        }

        $field = $this->get($field_id, $lang);
        return $field;
    }

    public function update(
    $field_id, $name, $ar_name, $phone, $hour_rate, $open_time, $cloes_time, $area_x, $area_y, $max_capacity, $description, $ar_description, $images, $amenities, $games_types, $lang
    ) {
        $this->get($field_id, $lang);

        $field = $this->field->update($field_id, array(
            'en_name' => $name,
            'ar_name' => $ar_name,
            'phone' => $phone,
            'en_description' => $description,
            'ar_description' => $ar_description,
            'hour_rate' => $hour_rate,
            'open_time' => $open_time,
            'close_time' => $cloes_time,
            'area_x' => $area_x,
            'area_y' => $area_y,
            'max_capacity' => $max_capacity
        ));

        if ($amenities) {
            $this->amenity->delete_field_amenities($field_id);
            foreach ($amenities as $am) {
                if (!is_array($am)) {
                    try {
                        $amenity = $this->amenity_service->get($am, $lang);
                        $this->amenity->add_field_amenity(array(
                            'field_id' => $field_id,
                            'amenity_id' => $amenity->amenity_id
                        ));
                    } catch (Amenity_Not_Found_Exception $e) {
                        
                    }
                }
            }
        }

        if ($games_types) {
            $this->game->delete_field_games($field_id);
            foreach ($games_types as $type) {
                if (!is_array($type)) {
                    try {
                        $games_type = $this->game_service->get($type, $lang);
                        $this->game->add_field_game(array(
                            'field_id' => $field_id,
                            'game_type_id' => $games_type->game_type_id
                        ));
                    } catch (Game_Not_Found_Exception $e) {
                        
                    }
                }
            }
        }

        if ($images) {
            $this->image->delete_field_images($field_id);
            foreach ($images as $image) {
                $images = $this->image->update($image, array(
                    'field_id' => $field_id
                ));
            }
        }
        $field = $this->get($field_id, $lang);
        return $field;
    }

    function decodeAmenities($json) {
        $data = json_decode($json, true);
        if (!is_array($data))
            throw new Invalid_Amenities_Exception($lang);

        for ($i = 0; $i < count($data); $i++) {
            if (!array_key_exists("amenity", $data[$i]))
                throw new Invalid_Amenities_Exception($lang);
        }
        return $data;
    }

    public function get($field_id, $lang = "en") {
        $field = $this->field->get($field_id, $lang);
        if (!$field)
            throw new Field_Not_Found_Exception($lang);
        $field->amenities = $this->amenity->get_field_amenities($field_id, $lang);
        $field->images = $this->image->get_images($field_id);
        $field->games = $this->game->get_field_games($field_id, $lang);
        return $field;
    }

    public function get_all($lang = "en") {
        $fields = $this->field->get_all($lang);
        $result = array();
        foreach ($fields as $field) {
            $result[] = $field;
            $result->amenities = $this->amenity->get_field_amenities($field->field_id, $lang);
            $result->images = $this->image->get_images($field->field_id);
            $result->games = $this->game->get_field_games($field->field_id, $lang);
        }
        return $result;
    }

    public function get_by_company($company_id, $lon, $lat, $lang = "en") {
        $fields = $this->field->get_by_company($company_id, $lon, $lat, $lang);
        $result = array();
        foreach ($fields as $field) {
            $field->amenities = $this->amenity->get_field_amenities($field->field_id, $lang);
            $field->images = $this->image->get_images($field->field_id);
            $field->games = $this->game->get_field_games($field->field_id, $lang);
            $result[] = $field;
        }
        return $result;
    }

    public function delete($field_id) {
        $this->get($field_id, "en");
        $this->field->update($field_id, array('deleted' => 1));
    }

    public function check_availability($field_id, $date) {
        $bookings = $this->booking_service->field_bookings_by_date($field_id, $date);
        $field = $this->get($field_id);
        $time = $field->open_time;
        $end = $field->close_time;
        $result = array();
        foreach ($bookings as $booking) {
            if ($booking->start != $time)
                $result[] = $time;
            $result[] = $booking->start;
            $time = time($booking->start) + doubleval($booking->duration) * 60;
        }
        if($time < $end){
            $result[] = $time;
            $result[] = $end;
        }
        return $result;
    }

}

?>
