var events = [];
var colors = [["blue", "#23b8d1"], ["red", "#f2653a"], ["green", "#47b85d"], ["orange", "#f89732"]];
fields = JSON.parse(fields);
var fieldscolors = [];
var resources = [];
var j = 0;
var pending_bookings = [];
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
    url: site_url + '/bookings/get_cancellation_reasons/format/json',
    type: 'GET',
    async: false,
    success: function (response) {
        if (response.status == true) {
            var msgs = response.data;
            $select = $('#refuse_msgs-list');
            $select.html("");
            $select.append($("<option></option>")
                    .attr("value", "Not Selected")
                    .attr("id", -1)
                    .text("Not Selected"));
            for (var i = 0; i < msgs.length; i++) {
                $select.append($("<option></option>")
                        .attr("value", msgs[i].message)
                        .attr("id", msgs[i].id)
                        .text(msgs[i].message));
            }
        } else
            alert("Error in loading bookings");
    }
});
$.ajax({
    url: site_url + '/bookings/company_bookings/format/json',
    type: 'GET',
    async: false,
    success: function (response) {
        if (response.status == true) {
            var booking;
            bookings = response.data;
            for (var i = 0; i < bookings.length; i++) {
                if (bookings[i].state_id == approved) {
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
                } else {
                    pending_bookings.push(bookings[i].booking_id);
                }
            }
        } else
            alert("Error in loading bookings");
    }
});
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
        $('#booking_num').attr("book_id", calEvent.id);
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

    },

    dayClick: function (date, jsEvent, view) {

        //alert('Clicked on: ' + date.format());
        get_all_fields();
        init_hours_select();
        init_minutes_select();
        $('#timepicker_div').html('<input class="timepicker form-control" type="text" >');

        $('.timepicker').timepicker({
            timeFormat: 'H:mm',
            interval: 60,
            minTime: date.format("H:mm"),
            maxTime: '11:00pm',
            defaultTime: date.format("H:mm"),
            startTime: date.format("H:mm"),
            dynamic: false,
            dropdown: true,
            scrollbar: true,
        });

        $('#booking_date').text($.datepicker.formatDate('yy-mm-dd', new Date(date)));
        $('#new_booking_modal').modal("show");

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
function get_all_fields() {
    $select = $('#fields-list');
    $select.html("");
    for (var i = 0; i < fields.length; i++) {
        $select.append($("<option></option>")
                .attr("value", fields[i].name)
                .attr("id", fields[i].field_id)
                .attr("index", i)
                .text(fields[i].name));
    }
    get_current_field_games();
}

function get_current_field_games() {
    var index = $('#fields-list').find(":selected").attr("index");
    $select = $('#games-list');
    $select.html("");
    for (var i = 0; i < fields[index].games.length; i++) {
        $select.append($("<option></option>")
                .attr("value", fields[index].games[i].name)
                .attr("id", fields[index].games[i].game_type_id)
                .attr("index", i)
                .text(fields[index].games[i].name));
    }
    init_minutes_select();
}

function init_hours_select() {
    $select = $('#hours-list');
    $select.html("");
    for (var i = 0; i < 24; i++) {
        $select.append($("<option></option>")
                .attr("value", i)
                .text(i));
    }
}

function init_minutes_select() {
    var f_index = $('#fields-list').find(":selected").attr("index");
    var g_index = $('#games-list').find(":selected").attr("index");
    $select = $('#minutes-list');
    $select.html("");
    for (var i = 0; fields[f_index].games[g_index].increament_factor * i < 60; i++) {
        $select.append($("<option></option>")
                .attr("value", fields[f_index].games[g_index].increament_factor * i)
                .text(fields[f_index].games[g_index].increament_factor * i));
    }
    update_total()
}

function update_total() {
    var f_index = $('#fields-list').find(":selected").attr("index");
    var total = fields[f_index].hour_rate / (60 / $('#minutes-list').find(":selected").attr("value"));
    total += fields[f_index].hour_rate * $('#hours-list').find(":selected").attr("value");
    $('#book_total_label').html(total + " AED");
}


$('#booking_datepicker').datepicker({
    buttonImage: base_url + 'assets/images/calendar.png',
    buttonImageOnly: true,
//    changeMonth: true,
//    changeYear: true,
    showOn: 'both',
    onSelect: function (value) {
        $('#booking_date').text($.datepicker.formatDate('yy-mm-dd', new Date(value)));
    }
});
function open_booking_datepicker() {
    $('#booking_datepicker').focus().hide();
}



function booking_create() {
    var Data = {};

    Data['field_id'] = $('#fields-list').find(":selected").attr("id");
    Data['game_type'] = $('#games-list').find(":selected").attr("id");
    Data['player_phone'] = $('#new_booking_modal #player_phone').val();
    Data['player_name'] = $('#new_booking_modal #player_name').val();
    Data['start'] = $('.timepicker').val() + ":00";
    Data['duration'] = parseFloat($('#hours-list').find(":selected").attr("value") * 60) + parseFloat($('#minutes-list').find(":selected").attr("value"));
    Data['date'] = $('#new_booking_modal #booking_date').html();
    Data['notes'] = $('#new_booking_modal #note').val();

    $.ajax({
        url: site_url + '/bookings/create_manually/format/json',
        type: 'POST',
        data: Data,
        async: false,
        success: function (response) {
            if (response.status == true) {
                close();
                var booking = response.data;
                start = new Date(booking.start);
                end = moment(booking.start, "HH:mm:ss").add(parseInt(booking.duration), "minutes").format("HH:mm:ss");
                booking = {
                    id: booking.booking_id,
                    start: booking.date + "T" + booking.start,
                    end: booking.date + "T" + end,
                    color: fieldscolors[booking.field_id],
                    resources: [booking.field_id],
                    player_name: booking.player_name,
                    title: "#" + booking.booking_id + " " + booking.player_name,
                    player_phone: booking.player_phone,
                    field_name: booking.field_name,
                    company_name: booking.company_name,
                    game_name: booking.game_type_name,
                    hour_rate: booking.hour_rate,
                    total: booking.total,
                    duration: booking.duration,
                    start_time: booking.start,
                    date: booking.date,
                    note: booking.note,
                };
                events.push(booking);
                $('#calendar').fullCalendar('renderEvent', booking);
            } else
                alert(response.message);
        }
    });
}

function show_booking_model() {
    $('#cancel_booking_modal').modal("show");
}

function booking_cancel() {
    var booking_id = $('#booking_num').attr('book_id');
    var reason_id = $('#refuse_msgs-list').find(":selected").attr("id");
    var note = $('#new-cancel-msg').val();
    var data = "";
    if (reason_id == -1 && note == '') {
        alert('Please select a message or write one!');
        return;
    }
    if (reason_id != -1) {
        data = '&reason_id=' + reason_id;
    }
    if (note != "") {
        data = data + '&note=' + note;
    }
    $.ajax({
        url: site_url + '/bookings/cancel/format/json?booking_id=' + booking_id + data,
        type: 'GET',
        async: false,
        success: function (response) {
            if (response.status == true) {
                $('#cancel_booking_modal').modal("hide");
                $('#booking_modal').modal("hide");
                $('#calendar').fullCalendar('removeEvents', booking_id);
            } else
                alert(response.message);
        }
    });
}

$("#close").click(function () {
    close();
});

function close() {
    $('#fields-list').html('');
    $('#games-list').html('');
    $('#new_booking_modal #player_phone').val('');
    $('#new_booking_modal #player_name').val('');
    $('#new_booking_modal #booking_date').html('');
    $('#new_booking_modal #note').val('');
    $('#book_total_label').html("");
    $('#new_booking_modal').modal("hide");
}
$("#booking_date_div").click(function () {

});
function openNav() {
    document.getElementById("incoming_booking_Sidenav").style.width = "400px";
}

/* Set the width of the side navigation to 0 */
function closeNav() {
    document.getElementById("incoming_booking_Sidenav").style.width = "0";
}