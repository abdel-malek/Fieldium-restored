<?php

/**
 * @author Amal Abdulraouf
 */
class report_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/booking');
    }

    public function reservations_report($company_id, $from_date, $to_date) {
        return $this->booking->reservations_report($company_id, $from_date, $to_date);
    }

    public function field_reservations_report($company_id, $field_id, $from_date, $to_date) {
        return $this->booking->field_reservations_report($company_id, $field_id, $from_date, $to_date);
    }

}

?>
