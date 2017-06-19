
<div  class="modal" id="offer_modal" >
    <div class="modal-dialog" style="width: 90%">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="offer_header" style="display: inline;">New Offer</h3>
                <button type="button" class="close" data-dismiss="modal" >&times</button>
            </div>
            <div class="modal-body" >
                <input type="hidden" id="offer_id" />
                <div class="row margin-top-5px">
                    <div class="col-md-2">
                        <b>Title en: </b>
                    </div>
                    <div class="col-md-3">
                        <input id="title_en" type="text" class="form-control" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-2">
                        <b>Title ar: </b>
                    </div>
                    <div class="col-md-3">
                        <input id="title_ar" type="text" class="form-control" aria-describedby="sizing-addon1">
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-md-2">
                        <b>Description en: </b>
                    </div>
                    <div class="col-md-3">
                        <textarea id="description_en" type="text" class="form-control" ></textarea>
                    </div>
                    <div class="col-md-2">
                        <b>Description ar: </b>
                    </div>
                    <div class="col-md-3">
                        <textarea id="description_ar" type="text" class="form-control" ></textarea>
                    </div>
                </div>
                <br>
                <div class="row margin-top-5px">
                    <div class="col-md-2">
                        <b>Start Date: </b>
                    </div>
                    <div class="col-md-3">
                        <input id="start_date" type="text" class="form-control datepicker" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-2">
                        <b>Expiry Date: </b>
                    </div>
                    <div class="col-md-3">
                        <input id="expiry_date" type="text" class="form-control datepicker" aria-describedby="sizing-addon1">
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-2">
                        <b>Set of hours: </b>
                    </div>
                    <div class="col-md-2">
                        <input id="set_of_minutes" type="text" class="form-control" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-2">
                        <b>Reward Type: </b>
                    </div>
                    <div class="col-md-2" style="padding: 0px">
                        <select id="voucher_type" class="form-control">
                            <option value="1">Discount</option>
                            <option value="2">Free Hours</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <b>Reward Value: </b>
                    </div>
                    <div class="col-md-2">
                        <input id="voucher_value" type="text" class="form-control" aria-describedby="sizing-addon1">
                    </div>
                </div>
                <hr>
                <div class="row margin-top-5px">
                    <div class="col-md-3">
                        <b>Starts after: </b>
                        <input id="voucher_start_after" style="width:60% !important;display:inline"  type="text" class="form-control" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-3">
                        <b>Validity days: </b>
                        <input id="valid_days" style="width:60% !important;display:inline"  type="text" class="form-control" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-3">
                        <b>From: </b>
                        <input id="voucher_from_hour" style="width:60% !important;display:inline"  type="text" class="form-control timepicker" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-3">
                        <b>To: </b>
                        <input id="voucher_to_hour" style="width:60% !important;display:inline"  type="text" class="form-control timepicker" aria-describedby="sizing-addon1">
                    </div>
                </div>
                <hr>
                <div class="row margin-top-5px">
                    <div class="col-md-2">
                        <b>Country: </b>
                    </div>
                    <div class="col-md-2" style="padding: 0px">

                        <select id="Country" class="form-control">
                            <option value="1">UAE</option>
                            <option value="2">Syria</option>
                        </select>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-2">
                        <b>Game: </b>
                    </div>
                    <div class="col-md-10">
                        <label for="all_games">
                            <input id="all_games" type="checkbox"/> All games 
                        </label><br>
                        <select id="games" class="js-example-basic-multiple js-states form-control" multiple="multiple">

                        </select>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-md-2">
                        <label>Companies</label>
                    </div>
                    <div class="col-md-10">
                        <label for="all_fields">
                            <input id="all_fields" type="checkbox"/> All fields 
                        </label><br>
                        <select id="companies" class="js-example-basic-multiple js-states form-control" multiple="multiple">

                        </select>
                    </div>
                </div>
                <br>
                <div class="modal-footer">
                    <div class="col-md-6 left">
                        <button class="btn btn-info create-btn" onclick='save_offer()'>Save</button>
                    </div>

                    <div class="col-md-6 right">
                        <button class="btn btn-warning" class="close" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>