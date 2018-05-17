
<div  class="modal" id="voucher_modal" >
    <div class="modal-dialog" style="width: 90%">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="voucher_header" style="display: inline;"><?php echo $this->lang->line('voucher'); ?></h3>
                <button type="button" class="close" data-dismiss="modal" >&times</button>
            </div>
            <input type="hidden" name="booking_id" />
            <div class="modal-body" >
                <input type="hidden" id="voucher_id" />
                <div class="row margin-top-5px">
                    <div class="col-md-2">
                        <b><?php echo $this->lang->line('voucher_code'); ?>: </b>
                    </div>
                    <div class="col-md-3">
                        <input id="voucher" style="width:60% !important;display:inline" type="text" class="form-control" aria-describedby="sizing-addon1">
                        <button class="btn btn-warning" onclick="generate_voucher()"><?php echo $this->lang->line('generate'); ?></button>
                    </div>
                    <div class="col-md-1">
                        <b><?php echo $this->lang->line('type'); ?>: </b>
                    </div>
                    <div class="col-md-2" style="padding: 0px">
                        <select id="type" class="form-control">
                            <option value="1"><?php echo $this->lang->line('discount'); ?></option>
                            <option value="2"><?php echo $this->lang->line('free_hours'); ?></option>
                        </select>
                    </div>
                    <div class="col-md-1">
                        <b><?php echo $this->lang->line('value'); ?> (%,h): </b>
                    </div>
                    <div class="col-md-2">
                        <input id="value" type="text" class="form-control" aria-describedby="sizing-addon1">
                    </div>
                    <label for="valid_once">
                        <input id="valid_once" type="checkbox"/> <?php echo $this->lang->line('valid_once'); ?>
                    </label>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-2">
                        <b><?php echo $this->lang->line('description_en'); ?>: </b>
                    </div>
                    <div class="col-md-3">
                        <textarea id="description_en" type="text" class="form-control" ></textarea>
                    </div>
                    <div class="col-md-2">
                        <b><?php echo $this->lang->line('description_ar'); ?>: </b>
                    </div>
                    <div class="col-md-3">
                        <textarea id="description_ar" type="text" class="form-control" ></textarea>
                    </div>
                </div>
                <hr>
                <div class="row margin-top-5px">
                    <div class="col-md-2">
                        <b><?php echo $this->lang->line('start_date'); ?>: </b>
                    </div>
                    <div class="col-md-2">
                        <input id="start_date" type="text" class="form-control datepicker" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-2">
                        <b><?php echo $this->lang->line('expiry_date'); ?>: </b>
                    </div>
                    <div class="col-md-2">
                        <input id="expiry_date" type="text" class="form-control datepicker" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-2">
                        <b><?php echo $this->lang->line('from'); ?>: </b>
                        <input id="from_hour" style="width:60% !important;display:inline"  type="text" class="form-control timepicker" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-2">
                        <b><?php echo $this->lang->line('to'); ?>: </b>
                        <input id="to_hour" style="width:60% !important;display:inline"  type="text" class="form-control timepicker" aria-describedby="sizing-addon1">
                    </div>
                </div>
                <hr>
                <div class="row margin-top-5px">
                    <div class="col-md-2">
                        <b><?php echo $this->lang->line('country'); ?>: </b>
                    </div>
                    <div class="col-md-2" style="padding: 0px">

                        <select id="Country" class="form-control">
                            <option value="1"><?php echo $this->lang->line('uae'); ?></option>
                            <option value="2"><?php echo $this->lang->line('syria'); ?></option>
                        </select>
                    </div>
                </div>
                <br>
                <div class="row margin-top-5px">
                    <div class="col-md-2">
                        <b><?php echo $this->lang->line('players'); ?>: </b>
                    </div>
                    <div class="col-md-10">
                        <label for="all_users">
                            <input id="all_users" type="checkbox"/> <?php echo $this->lang->line('all_users'); ?> 
                        </label><br>
                        <label><?php echo $this->lang->line('registered_players'); ?></label>
                        <select id="players" class="js-example-basic-multiple js-states form-control" multiple="multiple">

                        </select>
                        <label><?php echo $this->lang->line('add_by_phone_number'); ?></label>
                        <input id="phones" type="text" value="" data-role="tagsinput" />

                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-2">
                        <b><?php echo $this->lang->line('game'); ?>: </b>
                    </div>
                    <div class="col-md-10">
                        <label for="all_games">
                            <input id="all_games" type="checkbox"/> <?php echo $this->lang->line('all_games'); ?> 
                        </label><br>
                        <select id="games" class="js-example-basic-multiple js-states form-control" multiple="multiple">

                        </select>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-md-2">
                        <label><?php echo $this->lang->line('companies'); ?></label>
                    </div>
                    <div class="col-md-10">
                        <label for="all_fields">
                            <input id="all_fields" type="checkbox"/> <?php echo $this->lang->line('all_fields'); ?>
                        </label><br>
                        <select id="companies" class="js-example-basic-multiple js-states form-control" multiple="multiple">

                        </select>
                    </div>
                </div>
                <br>
                <div class="modal-footer">
                    <div class="col-md-6 left">
                        <button class="btn btn-info create-btn" onclick='save_voucher()'><?php echo $this->lang->line('save'); ?></button>
                    </div>

                    <div class="col-md-6 right">
                        <button class="btn btn-warning"class="close" data-dismiss="modal"><?php echo $this->lang->line('close'); ?></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>