var events = [];
var colors = [["blue", "#23b8d1"], ["red", "#f2653a"], ["green", "#47b85d"], ["orange", "#f89732"]];
fields = JSON.parse(fields);
var fieldscolors = [];
var resources = [];
var j = 0;
for (var i = 0; i < fields.length; i++) {
    if (j == colors.length)
        j = 0;
    fieldscolors[fields[i].field_id] = colors[j][1];
    resources.push({
        id: fields[i].field_id,
        name: fields[i].name,
        className: colors[j][0]
    });
    j++;
}
$.ajax({
    url: site_url + '/bookings/company_bookings/format/json',
    type: 'GET',
    async: false,
    success: function (response) {
        if (response.status == true) {
            var booking;
            bookings = response.data;
            for (var i = 0; i < bookings.length; i++) {
//                if (bookings[i].state_id == approved) {
                    start = new Date(bookings[i].start);
                    end = moment(bookings[i].start, "HH:mm:ss").add(parseInt(bookings[i].duration), "minutes").format("HH:mm:ss");
                    booking = {
                        id: bookings[i].booking_id,
                        start: bookings[i].date + "T" + bookings[i].start,
                        end: bookings[i].date + "T" + end,
                        color: fieldscolors[bookings[i].field_id],
                        resources: [bookings[i].field_id],
                        player_name: bookings[i].player_name,
                        title: "#" + bookings[i].booking_id + " " + bookings[i].player_name,
                        player_phone: bookings[i].player_phone,
                        field_name: bookings[i].field_name,
                        company_name: bookings[i].company_name,
                        game_name: bookings[i].game_type_name,
                        hour_rate: bookings[i].hour_rate,
                        total: bookings[i].total,
                        duration: bookings[i].duration,
                        start_time: bookings[i].start,
                        date: bookings[i].date,
                        note: bookings[i].note,
                    };
                    events.push(booking);
//                }
            }
        } else
            alert("Error in loading bookings");
    }
});
console.log(events);
var calendar = $('#calendar').fullCalendar({
    header: {
        right: 'title',
        center: '',
        left: 'today prev,next',
    },
//    title: 
    theme: false,
    defaultView: 'resourceDay',
    resources: resources,
    axisFormat: 'h(:mm)a',
    allDaySlot: false,
    slotEventOverlap: false,
    events: events,
    titleFormat: 'DD/MM/YYYY',
    allDaySlot: false,
    eventClick: function (calEvent, jsEvent, view) {
        $('#booking_num').html("Booking #" + calEvent.id);
        $('#player_name').html(calEvent.player_name);
        $('#player_phone').html(calEvent.player_phone);
        $('#start_label').html(moment(calEvent.start_time, "HH:mm:ss").format("HH:mm A"));
        $('#cost_label').html(calEvent.hour_rate + " AED");
        $('#total_label').html(calEvent.total + " AED");
        $('#duration_label').html(moment(calEvent.start_time, "HH:mm:ss").add(parseInt(calEvent.duration), "minutes").format("HH:mm A"));
        $('#date_label').html(calEvent.date);
        $('#game_label').html(calEvent.game_name);
        $('#note_label').html(calEvent.note);
        $('#field_label').html(calEvent.company_name + " - " + calEvent.field_name);
        $('#booking_modal').modal("show");

    }
});
$('.fc-header-title').prepend('<input type="hidden" id="datepicker"></input>');
$('.fc-header-title h2').attr("onclick", "open_datepicker()");
$('#datepicker').datepicker({
    buttonImage: base_url + 'assets/images/calendar.png',
    buttonImageOnly: true,
//    changeMonth: true,
//    changeYear: true,
    showOn: 'both',
    onSelect: function (value) {
        calendar.fullCalendar('gotoDate', value);
    }
});
function open_datepicker() {
    $('#datepicker').focus().hide();
}
