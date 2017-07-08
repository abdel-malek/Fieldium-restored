<?php

/**
 * @author Amal Abdulraouf
 */
class offer_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model("Services/voucher_service");
        $this->load->model('DataSources/offer');
        $this->load->model('DataSources/field');
        $this->load->library('send_sms');
    }

    public function create(
    $data, $companies, $games
    ) {
        $offer_id = $this->offer->add($data);
        if ($data['public_field'] == 0) {
            $this->load->model("Services/company_service");
            foreach ($companies as $company) {
                try {
                    $res = $this->company_service->get($company);
                } catch (Company_Not_Found_Exception $e) {
                    $this->delete($offer_id);
                }
                $this->offer->add_company(
                        array(
                            'company_id' => $company,
                            'offer_id' => $offer_id
                        )
                );
            }
        }
        if ($data['all_games'] == 0) {
            $this->load->model("Services/game_service");
            foreach ($games as $game) {
                $res = $this->game_service->get($game);
                if (!$res) {
                    throw new Game_Not_Found_Exception();
                }
                $this->offer->add_game(
                        array(
                            'game_type_id' => $game,
                            'offer_id' => $offer_id
                        )
                );
            }
        }
        $offer = $this->get($offer_id);
        return $offer;
    }

    public function get($offer_id) {
        $offer = $this->offer->get($offer_id);
        if (!$offer)
            throw new Parent_Exception("offer_not_found");
        $offer->companies = $this->offer->get_offer_companies($offer->offer_id);
        $offer->games = $this->offer->get_offer_games($offer->offer_id);
        return $offer;
    }

    public function update($voucher_id, $data) {
        $this->voucher->update($voucher_id, $data);
    }

    public function delete($offer_id) {
        $offer = $this->get($offer_id);
        $this->offer->delete($offer->offer_id);
    }

    public function get_all_with_hours($player_id) {
        $offers = $this->offer->get_all_with_hours($player_id);
        foreach ($offers as $offer) {
            $offer->companies = $this->offer->get_offer_companies($offer->offer_id);
            $offer->games = $this->offer->get_offer_games($offer->offer_id);
        }
        return $offers;
    }
    
     public function get_all() {
        $offers = $this->offer->get_all();
        foreach ($offers as $offer) {
            $offer->companies = $this->offer->get_offer_companies($offer->offer_id);
            $offer->games = $this->offer->get_offer_games($offer->offer_id);
        }
        return $offers;
    }

    public function check_for_offers($player_id) {
        return $this->offer->check_for_offers($player_id);
    }

    public function generate_voucher($offer, $player_id, $booking_id) {
        $this->offer->generate_voucher($offer->offer_id, $player_id, $booking_id);
//        $this->voucher_service->create();
    }

}

?>
