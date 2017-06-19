function fill_offer(offer) {
    var form = $('#offer_modal');
    $('#title_en').val(offer.title_en);
    $('#title_ar').val(offer.title_ar);
    $('#description_en').val(offer.description_en);
    $('#description_ar').val(offer.description_ar);
    $('#voucher_type').val(offer.voucher_type);
    $('#voucher_value').val(offer.voucher_value);
    $('#start_date').val(offer.start_date);
    $('#expiry_date').val(offer.expiry_date);
    $('#voucher_from_hour').val(offer.voucher_from_hour);
    $('#voucher_to_hour').val(offer.voucher_to_hour);
    $('#valid_days').val(offer.valid_days);
    $('#voucher_start_after').val(offer.voucher_start_after);
    $('#set_of_minutes').val(offer.set_of_minutes / 60);
    $('#all_fields').prop("checked", offer.public_field == 0 ? false : true);
    $('#all_games').prop("checked", offer.all_games == 0 ? false : true);
    $('#games').val("");
    $('#companies').val();
    $('#offer_id').val(offer.offer_id);
    $('#offer_header').text("Offer " + offer.title_en);
    var games = [];
    for (var i = 0; i < offer.games.length; i++) {
        if (offer.games[i].game_type_id != null)
            games.push(offer.games[i].game_type_id);
    }
    $('#games').val(games).change();
    var companies = [];
    for (var i = 0; i < offer.companies.length; i++) {
        if (offer.companies[i].company_id != null)
            companies.push(offer.companies[i].company_id);
    }
    $('#companies').val(companies).change();
    form.find('.create-btn').hide();
}

function clear_offer() {
    var form = $('#offer_modal');
    form.find('input').val("");
    form.find('textarea').val("");
    form.find('textarea').html("");
    form.find('select option').prop("selected", false);
    form.find('[type=checkbox]').prop("checked", false);
    $("#games").select2({
        placeholder: "Select game",
        maximumSelectionLength: 1
    });
    $("#companies").select2({
        placeholder: "Select company"
    });
    $('#offer_header').text("New Offer");
    form.find('.create-btn').show();
}

function edit_offer(offer_id) {
    $.ajax({
        url: site_url + '/offers/show/format/json?offer_id=' + offer_id,
        type: 'GET',
        async: false,
        success: function (response) {
            if (response.status == true) {
                var data = response.data;
                clear_offer();
                fill_offer(data);
                $('#offer_modal').modal("show");
            } else
                show_error("Error in loading offer");
        }
    });
}
$('.datepicker').datepicker({dateFormat: 'yy-mm-dd'});
$('.timepicker').timepicker();
function delete_offer(offer_id) {
    alertify.defaults.glossary.title = 'Offer ' + offer_id;
    alertify.confirm("Are you sure you want to delete this offer ?",
            function () {
                $.ajax({
                    url: site_url + '/offers/delete/format/json?offer_id=' + offer_id,
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

function save_offer() {
    var data = {
        'title_en': $('#title_en').val(),
        'title_ar': $('#title_ar').val(),
        'description_en': $('#description_en').val(),
        'description_ar': $('#description_ar').val(),
        'set_of_minutes': $('#set_of_minutes').val() * 60,
        'voucher_type': $('#voucher_type').val(),
        'voucher_value': $('#voucher_value').val() * 60,
        'voucher_start_after': $('#voucher_start_after').val(),
        'valid_days': $('#valid_days').val(),
        'start_date': $('#start_date').val(),
        'expiry_date': $('#expiry_date').val(),
        'voucher_from_hour': $('#voucher_from_hour').val(),
        'voucher_to_hour': $('#voucher_to_hour').val(),
        'all_fields': $('#all_fields').prop("checked") == true ? 1 : 0,
        'all_games': $('#all_games').prop("checked") == true ? 1 : 0,
        'games': $('#games').val(),
        'companies': $('#companies').val()
    };
    HoldOn.open({
        theme: "sk-bounce"
    });
    var url = "";
    if ($('#offer_id').val() == "")
        url = site_url + '/offers/create/format/json';
    else {
        url = site_url + '/offers/update/format/json';
        data['offer_id'] = $('#offer_id').val();
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
            $("#games").select2({
                placeholder: "Select game",
                maximumSelectionLength: 1,
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

