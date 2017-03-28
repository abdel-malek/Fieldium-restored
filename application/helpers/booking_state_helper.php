<?php

class BOOKING_STATE {

    const PENDING = 1;
    const APPROVED = 2;
    const DECLINED = 3;
    const CANCELLED = 4;

}

function get_cancellation_reasons() {
    return array(
        1 => 'Your booking has been canceled because the field is undergoing maintenance.',
        2 => 'Booking canceled upon your request. Hope to see you again.'
    );
}
