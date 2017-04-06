var events = [];
var colors = [["blue", "#23b8d1"], ["red", "#f2653a"], ["green", "#47b85d"], ["orange", "#f89732"]];
fields = JSON.parse(fields);
var fieldscolors = [];
var resources = [];
var j = 0;
var bookings = [];
var bookings_ids = [];
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
            show_error("Error in loading bookings");
    }
});

function append_new_booking(booking) {
    bookings.push(booking);
    bookings_ids.push(booking.booking_id);
    if (booking.state_id == approved) {
        render_event(booking);
    } else {
        $('#badge').html(parseInt($('#badge').html()) + 1);
        $('#pending_bookings_list').append(
                '<div class="col s12 m3" id="booking_' + booking.booking_id + '" onclick="open_pending_booking_modal(' + booking.booking_id + ')">' +
                '<div class="card horizontal">' +
                '<div class="card-image"><div class="circle">' +
                '<span>' + booking.booking_id + '</span></div>' +
                '</div>' +
                '<div class="card-stacked">' +
                '<div class="card-content">' +
                '<p>' + booking.player_name + '</p>' +
                '<p>' + booking.date + ' ' + booking.start + '</p>' +
                '<p>' + booking.field_name + '</p>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>');
    }
}
function render_event(booking) {
    start = new Date(booking.start);
    end = moment(booking.start, "HH:mm:ss").add(parseInt(booking.duration), "minutes").format("HH:mm:ss");
    new_booking = {
        id: booking.booking_id,
        booking_id: booking.booking_id,
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
    events.push(new_booking);
    $('#calendar').fullCalendar('renderEvent', new_booking, true);
}
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
    lazyFetching: true,
    events: events,
    titleFormat: 'DD/MM/YYYY',
    allDaySlot: false,
    eventClick: function (calEvent, jsEvent, view) {
        $('#new_btns').hide();
        $('#show_btns').show();
        fill_booking_info(calEvent);
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
$.ajax({
    url: site_url + '/bookings/company_bookings/format/json',
    type: 'GET',
    async: false,
    success: function (response) {
        if (response.status == true) {
            var booking;
            $('#pending_bookings_list').html('');
            result = response.data;
            for (var i = 0; i < result.length; i++) {
                append_new_booking(result[i]);
            }
        } else
            show_error("Error in loading bookings");
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
                render_event(booking);
            } else
                show_error(response.message);
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
        show_error('Please select a message or write one!');
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
                remove_booking(booking_id);
            } else
                show_error(response.message);
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

function find_book_by_id(id) {
    for (var i = 0; i < bookings.length; i++) {
        if (bookings[i].booking_id == id)
            return bookings[i];
    }
}
function fill_booking_info(calEvent) {
    $('#booking_num').html("Booking #" + calEvent.booking_id);
    $('#booking_num').attr("book_id", calEvent.booking_id);
    $('#player_name').html(calEvent.player_name);
    $('#player_phone').html(calEvent.player_phone);
    $('#start_label').html(moment((calEvent.start_time?calEvent.start_time:calEvent.start), "HH:mm:ss").format("HH:mm A"));
    $('#cost_label').html(calEvent.hour_rate + " AED");
    $('#total_label').html(calEvent.total + " AED");
    $('#duration_label').html(moment((calEvent.start_time?calEvent.start_time:calEvent.start), "HH:mm:ss").add(parseInt(calEvent.duration), "minutes").format("HH:mm A"));
    $('#date_label').html(calEvent.date);
    $('#game_label').html(calEvent.game_name);
    $('#note_label').html(calEvent.note);
    $('#field_label').html(calEvent.company_name + " - " + calEvent.field_name);
    $('#booking_modal').modal("show");
}
function open_pending_booking_modal(id) {
    var calEvent = find_book_by_id(id);
    $('#new_btns').show();
    $('#show_btns').hide();
    fill_booking_info(calEvent);
}
function booking_approve() {
    var booking_id = $('#booking_num').attr("book_id");
    $.ajax({
        url: site_url + '/bookings/approve/format/json?booking_id=' + booking_id,
        type: 'GET',
        async: false,
        success: function (response) {
            if (response.status == true) {
                var booking = response.data;
                $('#booking_modal').modal("hide");
                $('#booking_' + booking_id).remove();
                render_event(booking);
            } else
                show_error(response.message);
        }
    });
}
function decline_booking() {
    var booking_id = $('#booking_num').attr("book_id");
    $.ajax({
        url: site_url + '/bookings/decline/format/json?booking_id=' + booking_id,
        type: 'GET',
        async: false,
        success: function (response) {
            if (response.status == true) {
                var booking = response.data;
                $('#booking_modal').modal("hide");
                remove_booking(booking_id);
                $('#badge').html(parseInt($('#badge').html()) - 1);
            } else
                show_error(response.message);
        }
    });
}
function show_error(msg) {
    alertify.defaults.glossary.title = 'Error';
    alertify
            .alert(msg, function () {
                alertify.message('OK');
            });
}
function confirm_decline() {
    var booking_id = $('#booking_num').attr("book_id");
    alertify.defaults.glossary.title = 'Booking #' + booking_id;
    alertify.confirm("Are you sure you want to decline booking no." + booking_id + " ?",
            function () {
                decline_booking();
            }
    ,
            function () {

            }
    );
}
function remove_booking(booking_id) {
    $('#booking_' + booking_id).remove();
    $('#calendar').fullCalendar('removeEvents', booking_id);
    var index = $.inArray(booking_id, bookings_ids);
    if (index != -1)
    {
        bookings_ids.splice(index, 1);
    }
}