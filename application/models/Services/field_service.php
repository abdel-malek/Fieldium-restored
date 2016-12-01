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

    public function create(
    $company_id, $name, $phone, $hour_rate, $open_time, $cloes_time, $area_x, $area_y, $max_capacity, $description, $images, $amenities, $games_types
    ) {

        $field_id = $this->field->add(array(
            'company_id' => $company_id,
            'name' => $name,
            'phone' => $phone,
            'description' => $description,
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
                        $amenity = $this->amenity_service->get($am);
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
                        $games_type = $this->game_service->get($type);
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

        $field = $this->get($field_id);
        return $field;
    }

    public function update(
    $field_id, $name, $phone, $hour_rate, $open_time, $cloes_time, $area_x, $area_y, $max_capacity, $description, $images, $amenities, $games_types
    ) {
        $this->get($field_id);

        $field = $this->field->update($field_id, array(
            'name' => $name,
            'phone' => $phone,
            'description' => $description,
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
                        $amenity = $this->amenity_service->get($am);
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
                        $games_type = $this->game_service->get($type);
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
        $field = $this->get($field_id);
        return $field;
    }

    function decodeAmenities($json) {
        $data = json_decode($json, true);
        if (!is_array($data))
            throw new Invalid_Amenities_Exception();

        for ($i = 0; $i < count($data); $i++) {
            if (!array_key_exists("amenity", $data[$i]))
                throw new Invalid_Amenities_Exception();
        }
        return $data;
    }

    public function get($field_id) {
        $field = $this->field->get($field_id);
        if (!$field)
            throw new Field_Not_Found_Exception ();
        $field->amenities = $this->amenity->get_field_amenities($field_id);
        $field->images = $this->image->get_images($field_id);
        $field->games = $this->game->get_field_games($field_id);
        return $field;
    }

    public function get_all() {
        $fields = $this->field->get_all();
        $result = array();
        foreach ($fields as $field) {
            $result[] = $field;
            $result->amenities = $this->amenity->get_field_amenities($field->field_id);
            $result->images = $this->image->get_images($field->field_id);
            $result->games = $this->game->get_field_games($field->field_id);
        }
        return $result;
    }

    public function get_by_company($company_id, $lon, $lat) {
        $fields = $this->field->get_by_company($company_id, $lon, $lat);
        $result = array();
        foreach ($fields as $field) {
            $field->amenities = $this->amenity->get_field_amenities($field->field_id);
            $field->images = $this->image->get_images($field->field_id);
            $field->games = $this->game->get_field_games($field->field_id);
            $result[] = $field;
        }
        return $result;
    }

    public function delete($field_id) {
        $this->get($field_id);
        $this->field->update($field_id, array('deleted' => 1));
    }

}

?>
