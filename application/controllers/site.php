<?php

defined('BASEPATH') OR exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';

class site extends REST_Controller {

    function __construct() {
        parent::__construct();
        $this->load->model('Services/field_service');
        $this->load->model('Services/game_service');
        $this->load->model('Permissions/user_permissions');
    }

    public function index_get() {
        if ($this->current_user) {
            if ($this->current_user->role_id == ROLE::SUPPORT)
                redirect('dashboard');
            else if ($this->current_user->role_id == ROLE::ADMIN) {
                $this->session->set_userdata(array('company_id' => $this->current_user->company_id));
                redirect('bookings/calendar');
            }
        } else {
            $featured_places = $this->field_service->get_featured_places();
            $games = $this->game_service->get_all();
            $this->load->view('index', array(
                'featured_places' => $featured_places,
                'games' => $games
                    )
            );
        }
    }

    public function about_get() {
        $this->load->model('Services/site_service');
        $this->response(array('status' => true, 'data' => $this->site_service->about(), "message" => ""));
    }

    public function home_get() {

        $this->load->model('Services/area_service');
        $this->load->model('Services/offer_service');
        $data = array(
            'areas' => $this->area_service->get_all($this->response->lang),
            'games' => $this->game_service->get_all($this->response->lang)
//            'offers' => $this->offer_service->get_all()
        );
        if ($this->current_user) {
            $this->user_permissions->is_player($this->current_user);
            $this->load->model('Services/booking_service');
            $this->load->model('Services/voucher_service');
            $booking = $this->booking_service->upcoming_booking($this->current_user->player_id, $this->response->lang);
            $bookings = $this->booking_service->last_bookings($this->current_user->player_id, $this->response->lang);
            $data['last_bookings'] = $bookings;
            $data['upcoming_booking'] = $booking;
            $data['vouchers'] = $this->voucher_service->get_my_vouchers($this->current_user->player_id);
            $data['offers'] = $this->offer_service->get_all_with_hours($this->current_user->player_id);
        } else {
            $data['offers'] = $this->offer_service->get_all();
        }

        $this->response(array('status' => true, 'data' =>
            $data
            , 'message' => ""));
    }

}
