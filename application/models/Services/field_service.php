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
    $company_id, $name, $ar_name, $phone, $hour_rate, $open_time, $cloes_time, $area_x, $area_y, $max_capacity, $description, $ar_description, $images, $amenities, $games_types, $auto_confirm, $lang, $fields = null
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
            'auto_confirm' => $auto_confirm,
            'max_capacity' => $max_capacity
        ));

        if ($amenities) {
            $amenities = $this->decodeAmenities($amenities);
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
            $games_types = $this->decode($games_types);
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
        if ($fields && $fields != "" && $fields != null) {
            $fields = json_decode($fields);
            foreach ($fields as $field)
                $this->field_service->add_child($field_id, $field);
        }
        $field = $this->get($field_id, $lang);
        return $field;
    }

    public function update(
    $field_id, $name, $ar_name, $phone, $hour_rate, $open_time, $cloes_time, $area_x, $area_y, $max_capacity, $description, $ar_description, $images, $amenities, $games_types, $auto_confirm, $lang
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
            'auto_confirm' => $auto_confirm,
            'max_capacity' => $max_capacity
        ));
        // $games_types = $this->decode($games_types);
        // $amenities = $this->decode($amenities);
        if ($amenities) {
            $amenities = $this->decodeAmenities($amenities);
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
            $games_types = $this->decode($games_types);
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

    function decode($json) {
        $data = json_decode($json, true);
//        var_dump($datsa);die();
        if (!is_array($data))
            throw new Invalid_Format_Exception("en");

//        for ($i = 0; $i < count($data); $i++) {
//            if (!array_key_exists("amenity", $data[$i]))
//                throw new Invalid_Amenities_Exception($lang);
//        }
        return $data;
    }

    function decodeAmenities($json) {
        $data = json_decode($json, true);
//        var_dump($datsa);die();
        if (!is_array($data))
            throw new Invalid_Amenities_Exception("en");

//        for ($i = 0; $i < count($data); $i++) {
//            if (!array_key_exists("amenity", $data[$i]))
//                throw new Invalid_Amenities_Exception($lang);
//        }
        return $data;
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
        if ($field->logo != null)
            $field->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $field->logo;
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
            if ($field->logo != null)
                $field->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $field->logo;
        }
        return $result;
    }

    public function get_by_company($company_id, $lon = 0.0, $lat = 0.0, $lang = "en") {
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
            if ($field->logo != null)
                $field->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $field->logo;
            $field->children = $this->field->get_children($field->field_id);
            $result[] = $field;
        }
        return $result;
    }

    public function get_by_company_with_timing($game, $company_id, $timing, $start, $date, $duration, $lon, $lat, $lang = "en") {
        if ($timing == 1) {
            $fields = $this->field->get_by_company_with_timing($game, $company_id, $timing, $start, $date, $duration, $lon, $lat, $lang);
        } else {
            $fields = $this->field->get_by_company_with_filters($game, $company_id, $lon, $lat, $lang);
        }
        $result = array();
        foreach ($fields as $field) {
            $available = true;
            if ($timing == 2 && count($this->check_availability($field->field_id, $date, $game)) == 0) {
                $available = false;
            }
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
            foreach ($games as $g) {
                if ($g->image != "" && $g->image != null) {
                    $g->image_url = base_url() . UPLOADED_IMAGES_PATH_URL . $g->image;
                }
                $results[] = $g;
            }

            $field->games = $results;
            if ($field->logo != null)
                $field->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $field->logo;
            if ($available)
                $result[] = $field;
        }
        return $result;
    }

    public function delete($field_id) {
        $this->get($field_id, "en");
        $bookings = $this->booking_service->field_bookings($field_id, "en");
        foreach ($bookings as $booking) {
            $this->booking_service->cancel($booking->booking_id, 'Your booking has been canceled. Sorry for the inconvienve but the field is undergoing maintenance.');
        }
        $this->field->delete($field_id);
        $this->field->update($field_id, array('deleted' => 1));
    }

    public function active($field_id) {
        $this->get($field_id, "en");
        $bookings = $this->booking_service->field_bookings($field_id, "en");
        foreach ($bookings as $booking) {
            $this->booking_service->cancel($booking->booking_id, 'Your booking has been canceled. Sorry for the inconvienve but the field is undergoing maintenance.');
        }
        $this->field->update($field_id, array('deleted' => 1));
    }

    public function check_availability($field_id, $date, $game_type) {
        $field = $this->get($field_id);
        $bookings = array();
        $bookings = $this->booking_service->field_bookings_by_date($field_id, $date,"en",$bookings);
        $result = array();
        if ($field->open_time < $field->close_time) {
            $time = $field->open_time;
            $end = $field->close_time;

            foreach ($bookings as $key => $booking) {
                $endtime = strftime('%H:%M:%S', strtotime($booking->start) + doubleval($booking->duration) * 60);
                if ($booking->start >= $field->open_time && $booking->start < $field->close_time &&
                        $endtime > $field->open_time && $endtime <= $field->close_time) {
                    if ($booking->start != $time) {
                        $result[] = $time;
                        $result[] = $booking->start;
                    }
                    $time = $endtime;
                }
            }

            if ($time < $end) {
                $result[] = $time;
                $result[] = $end;
            }
        } else if ($field->open_time > $field->close_time) {
            $r1 = array("00:00:00", $field->close_time);
            $r2 = array($field->open_time, "23:59:59");
            $time = "00:00:00";
            $end = $field->close_time;
            $first = true;
            $changed = false;
            foreach ($bookings as $key => $booking) {
                $endtime = strftime('%H:%M:%S', strtotime($booking->start) + doubleval($booking->duration) * 60);
                if (!(
                        ($booking->start >= $field->close_time && $booking->start < $field->open_time) ||
                        ($endtime > $field->close_time && $endtime <= $field->open_time)
                        )) {
                    if (
                            strtotime($booking->start) >= strtotime($r1[0]) &&
                            strtotime($booking->start) <= strtotime($r1[1])
                    ) {

                        if ($booking->start != $time) {
                            $changed = true;
                            $result[] = $time;
                            $result[] = $booking->start;
                        }
                        $time = $endtime;
                    } else if (
                            strtotime($booking->start) >= strtotime($r2[0]) &&
                            strtotime($booking->start) <= strtotime($r2[1])
                    ) {

                        if ($first) {
                            if ($time != $end) {
                                $changed = true;
                                $result[] = $time;
                                $result[] = $end;
                            }
                            $time = $field->open_time;
                            $end = "23:59:59";
                            $first = false;
                        }
                        if ($booking->start != $time) {
                            $changed = true;
                            $result[] = $time;
                            $result[] = $booking->start;
                        }
                        $time = $endtime;
                    }
                }
            }
            if (count($bookings) == 0) {
                $result[] = $r1[0];
                $result[] = $r1[1];
                $result[] = $r2[0];
                $result[] = $r2[1];
            } else {
                if ($time < $end && $time != "00:00:00") {
                    $result[] = $time;
                    $result[] = $end;
                }
                if ($end != "23:59:59") {
                    $result[] = $field->open_time;
                    $result[] = "23:59:59";
                }
            }
        }
        $available_times = $result;
        $times = array();
        $count = 0;
        $range = array();
        foreach ($available_times as $key => $value) {
            if ($count % 2 == 0) {
                $range["start"] = $value;
            } else {
                $range["end"] = $value;
                array_push($times, $range);
            }
            $count++;
        }
        $results = array();
//        var_dump($game_type,"\n");
        $game = $this->game_service->get($game_type);
//        var_dump($game,"\n");
        $current = date('H:i:00');
        $mins = date('i');
        if ($mins % $game->increament_factor != 0)
            $current = date('H:i:00', strtotime($current) + doubleval(
                            (
                            $game->increament_factor - ($mins % $game->increament_factor)
                            )
                    ) * 60);
//        $current = $hour . ":00:00";
        $this->load->model("Services/game_service");

        foreach ($times as $key => $range) {
            if (strtotime($date) > strtotime(date('Y-m-d'))) {

                if ($this->check_ranege_duration($range, $game))
                    array_push($results, $range);
                else {
                    
                }
            } else {

                if (strtotime($range["start"]) < strtotime($current) && strtotime($range["end"]) > strtotime($current)) {
                    $ran = array(
                        "start" => $current,
                        "end" => $range["end"]
                    );
                    if ($this->check_ranege_duration($ran, $game))
                        array_push($results, $ran);
                } else if (strtotime($range["start"]) >= strtotime($current) && strtotime($range["end"]) > strtotime($current)) {

                    if ($this->check_ranege_duration($range, $game))
                        array_push($results, $range);
                }
            }
        }
        return $results;
    }

    private function check_ranege_duration($range, $game) {
        $diff = abs(strtotime($range["end"]) - strtotime($range["start"]));
        $years = floor($diff / (365 * 60 * 60 * 24));
        $months = floor(($diff - $years * 365 * 60 * 60 * 24) / (30 * 60 * 60 * 24));
        $days = floor(($diff - $years * 365 * 60 * 60 * 24 - $months * 30 * 60 * 60 * 24) / (60 * 60 * 24));
        $hours = floor(($diff - $years * 365 * 60 * 60 * 24 - $months * 30 * 60 * 60 * 24 - $days * 60 * 60 * 24) / (60 * 60));
        if ($hours < 10)
            $hours = "0" . $hours;
        $minuts = floor(($diff - $years * 365 * 60 * 60 * 24 - $months * 30 * 60 * 60 * 24 - $days * 60 * 60 * 24 - $hours * 60 * 60) / 60);
        if ($minuts < 10)
            $minuts = "0" . $minuts;
        $seconds = floor(($diff - $years * 365 * 60 * 60 * 24 - $months * 30 * 60 * 60 * 24 - $days * 60 * 60 * 24 - $hours * 60 * 60 - $minuts * 60));
        $minimum = "";
        if (floor($game->minimum_duration / 60) < 10)
            $minimum .= "0";
        $minimum .= floor($game->minimum_duration / 60) . ":";
        if (($game->minimum_duration % 60) < 10)
            $minimum .= "0";
        $minimum .= ($game->minimum_duration % 60) . ":00";
//        var_dump($minimum, $range["start"], $range["end"], $hours . ":" . $minuts . ":00");
        if (
                strtotime($hours . ":" . $minuts . ":00") >= strtotime($minimum)
        )
            return true;
        return false;
    }

    public function busy_range($field_id, $date) {
        $field = $this->get($field_id);
        $bookings = array();
        $bookings = $this->booking_service->field_bookings_by_date($field_id, $date,"en",$bookings);
        $time = $field->open_time;
        $end = $field->close_time;
        $result = array();
        foreach ($bookings as $key => $booking) {
            $endtime = strftime('%H:%M:%S', strtotime($booking->start) + doubleval($booking->duration) * 60);
            if (strtotime($time) == strtotime($booking->start) && count($result) != 0) {
                $key = array_search($booking->start, $result);
                array_splice($result, $key, 1);
                $result[] = $endtime;
            } else {
                $result[] = $booking->start;
                $result[] = $endtime;
            }
            $time = $endtime;
        }
        $times = array();
        $count = 0;
        $range = array();
        foreach ($result as $key => $value) {
            if ($count % 2 == 0) {
                $range["start"] = $value;
            } else {
                $range["end"] = $value;
                array_push($times, $range);
            }
            $count++;
        }
        return $times;
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
            if ($field->logo != null)
                $field->logo_url = base_url() . UPLOADED_IMAGES_PATH_URL . $field->logo;
            $result[] = $field;
        }
        return $result;
    }

    public function get_children($field_id, $root = array()) {
        return $this->field->get_children($field_id, $root);
    }

    public function get_parents($field_id, $root = array()) {
        return $this->field->get_parents($field_id, $root);
    }

    public function add_child($parent, $child) {

        try {
            $this->get($parent);
            $this->get($child);
            $this->field->add_child($parent, $child);
        } catch (Field_Not_Found_Exception $ex) {
            
        }
    }

}

?>
