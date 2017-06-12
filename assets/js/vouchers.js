function fill_voucher(voucher) {
    var form = $('#voucher_modal');
    $('#voucher').val(voucher.voucher);
    $('#voucher').prop("disabled", true);
    $('#type').val(voucher.type);
    $('#value').val(voucher.value);
    $('#start_date').val(voucher.start_date);
    $('#expiry_date').val(voucher.expiry_date);
    $('#from_hour').val(voucher.from_hour);
    $('#to_hour').val(voucher.to_hour);
    $('#all_users').prop("checked", voucher.public_user==0?false:true);
    $('#all_fields').prop("checked", voucher.public_field==0?false:true);
    $('#players').val();
    $('#phones').tagsinput('items');
    $('#game').val(voucher.game_type_id);
    $('#companies').val();
    $('#voucher_id').val(voucher.voucher_id);
    $('#voucher_header').text("Voucher " + voucher.voucher);
    var players = [];
    for (var i = 0; i < voucher.users.length; i++) {
        if (voucher.users[i].player_id != null)
            players.push(voucher.users[i].player_id);
        else
            $('#phones').tagsinput('add', voucher.users[i].phone);
    }
    $('#players').val(players).change();
    var companies = [];
    for (var i = 0; i < voucher.companies.length; i++) {
        if (voucher.companies[i].company_id != null)
            companies.push(voucher.companies[i].company_id);
    }
    $('#companies').val(companies).change();
    form.find('.create-btn').hide();
}

function clear_voucher() {
    var form = $('#voucher_modal');
    form.find('input').val("");
    $('#voucher').prop("disabled", false);
    form.find('select option').prop("selected", false);
    form.find('[type=checkbox]').prop("checked", false);
    $("#players").select2({
        placeholder: "Select player"
    });
    $("#companies").select2({
        placeholder: "Select company"
    });
    $('#phones').tagsinput('removeAll');
    $('#voucher_header').text("Voucher");
    form.find('.create-btn').show();
}

function edit_voucher(voucher) {
    $.ajax({
        url: site_url + '/vouchers/show/format/json?voucher=' + voucher,
        type: 'GET',
        async: false,
        success: function (response) {
            if (response.status == true) {
                var data = response.data;
                clear_voucher();
                fill_voucher(data);
                $('#voucher_modal').modal("show");
            } else
                show_error("Error in loading bookings");
        }
    });
}
$('.datepicker').datepicker({dateFormat: 'yy-mm-dd'});
$('.timepicker').timepicker();
function delete_voucher(voucher) {
    alertify.defaults.glossary.title = 'Voucher ' + voucher;
    alertify.confirm("Are you sure you want to delete this voucher ?",
            function () {
                $.ajax({
                    url: site_url + '/vouchers/delete/format/json?voucher=' + voucher,
                    type: 'GET',
                    async: false,
                    success: function (response) {
                        if (response.status == true) {
                            location.reload();
                        } else
                            show_error("Error deleting");
                    }
                });
            },
            function () {
                show_error("Error deleting");
            }
    );
}
function show_error(msg) {
    alertify.defaults.glossary.title = 'Error';
    alertify.error(msg);
}

function generate_voucher() {
    $.ajax({
        url: site_url + '/vouchers/generate_voucher/format/json',
        type: 'GET',
        async: false,
        success: function (response) {
            if (response.status == true) {
                $('#voucher').val(response.data);
            } else
                show_error("Error deleting");
        }
    });
}

function save_voucher() {
    var data = {
        'voucher': $('#voucher').val(),
        'type': $('#type').val(),
        'value': $('#value').val(),
        'start_date': $('#start_date').val(),
        'expiry_date': $('#expiry_date').val(),
        'from_hour': $('#from_hour').val(),
        'to_hour': $('#to_hour').val(),
        'all_users': $('#all_users').prop("checked"),
        'all_fields': $('#all_fields').prop("checked"),
        'users': $('#players').val(),
        'phones': $('#phones').tagsinput('items'),
        'game_type_id': $('#game').val(),
        'companies': $('#companies').val()
    };
    console.log(data);
    HoldOn.open({
        theme: "sk-bounce"
    });
    var url = "";
    if ($('#voucher_id').val() == "")
        url = site_url + '/vouchers/create/format/json';
    else {
        url = site_url + '/vouchers/update/format/json';
        data['voucher_id'] = $('#voucher_id').val();
    }
    $.ajax({
        url: url,
        type: 'POST',
        data: data,
        async: false,
        success: function (response) {
            if (response.status == true) {
                HoldOn.close();
                location.reload();
                console.log(date);
            } else {
                HoldOn.close();
                show_error(response.message);
            }
        }
    });
}
$.ajax({
    url: site_url + '/games/get_all/format/json',
    type: 'GET',
    success: function (response) {
        if (response.status == true) {
            $('#games').append('<option value="0" selected>All</option>');
            for (var i = 0; i < response.data.length; i++)
                $('#games').append('<option value="' + response.data[i].game_type_id + '">' + response.data[i].name + '</option>');
        } else
            show_error(response.message);
    }
});

$.ajax({
    url: site_url + '/players/get_all/format/json',
    type: 'GET',
    success: function (response) {
        if (response.status == true) {
            var players = [];

            $("#players").select2({
                placeholder: "Select players",
                data: response.data
            });

        } else
            show_error(response.message);
    }
});

$.ajax({
    url: site_url + '/companies/get_all/format/json',
    type: 'GET',
    success: function (response) {
        if (response.status == true) {
            $("#companies").select2({
                placeholder: "Select Company",
                data: response.data
            });

        } else
            show_error(response.message);
    }
});
