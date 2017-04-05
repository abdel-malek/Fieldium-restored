new_bookings = function () {
    jQuery.ajax({
        type: 'GET',
        url: site_url + "/bookings/company_bookings/format/json",
        dataType: 'json',
        success: function (response) {
            if (response.status == true) {
                var booking;
                data = response.data;
//                var j = pending_bookings.length - 1;
                for (var i = data.length - 1; i >= 0; i--) {
//                    id = data[i].booking_id;
//                    if (pending_bookings.length == 0) {
//                        notify();
//                        bookings.push(data[i]);
//                        append_new_booking(data[i]);
//                        continue;
//                    }
//
//                    if (j >= 0 && pending_bookings[j] == data[i].booking_id)
//                        break;

                    if (bookings_ids.indexOf(data[i].booking_id) <= -1) {
                        notify();
                        append_new_booking(data[i]);
                    }
//                    j--;
                }
            } else
                alert("Error in loading bookings");
        },
        error: function (response) {
        }
    });

};

setInterval(new_bookings, 30000);
//new_bookings();

function notify() {
    Push.create("New Reservation", {
        body: "You have received a new reservation !",
        icon: base_url + 'assets/images/logo.png',
        timeout: 4000,
        onClick: function () {
            window.focus();
            this.close();
        }
    });
}