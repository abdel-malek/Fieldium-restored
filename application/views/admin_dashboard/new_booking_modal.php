
<div  class="modal" id="new_booking_modal" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 style="display: inline;">New Booking</h3>
                 <button type="button" class="close" data-dismiss="modal" >&times</button>
            </div>
            <input type="hidden" name="booking_id" />
            <div class="modal-body" >
                <div class="row margin-top-5px">
                    <div class="col-md-3">
                        <b>Player Name: </b>
                    </div>
                    <div class="col-md-8">
                        <input id="player_name" type="text" class="form-control" placeholder="Player Name" aria-describedby="sizing-addon1">
                    </div>
                </div>
                <div class="row margin-top-5px">
                    <div class="col-md-3">
                        <b>Player Phone: </b>
                    </div>
                    <div class="col-md-8">
                        <input id="player_phone" type="text" class="form-control" placeholder="Player Phone" aria-describedby="sizing-addon1">
                    </div>
                </div>
                <div class="row margin-top-5px"> 
                    <div class="col-md-3">
                        <b>Field: </b>
                    </div>
                    <div class="col-md-8">
                        <select class="form-control" id="fields-list" onchange="get_current_field_games();">
                        </select>
                    </div>
                </div>
                <div class="row margin-top-5px"> 
                    <div class="col-md-3">
                        <b>Game: </b>
                    </div>
                    <div class="col-md-8">
                        <select class="form-control" id="games-list" onchange="init_minutes_select();">
                        </select>
                    </div>
                </div>
                <div class="row margin-top-5px">
                    <div class="col-md-3">
                        <b>Date: </b>
                    </div>
                    <div class="col-md-8" id="booking_date_div" style="text-align: center">
                        <input type="hidden" id="booking_datepicker" onclick="open_booking_datepicker()">
                        <h4 style="margin-left:0px" id="booking_date">2017-7-7</h4>
                    </div>
                </div>
                <div class="row margin-top-5px">
                    <div class="col-md-3">
                        <b>Start Time: </b>
                    </div>
                    <div class="col-md-8 right" id="timepicker_div">
                    </div>
                </div>
                <div class="row margin-top-5px">
                    <div class="col-md-3">
                        <b>Duration: </b>
                    </div>
                    <div class="col-md-8 right">
                        <div class="col-md-6">
                            <label>Hours</label>
                            <select  class="time-list" id="hours-list" onchange="update_total();">
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label>Minutes</label>
                            <select  class="time-list" id="minutes-list" onchange="update_total();">
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row margin-top-5px">
                    <div class="col-md-3">
                        <b>Note: </b>
                    </div>
                    <div class="col-md-8">
                        <input id="note" type="text" class="form-control" placeholder="Note" aria-describedby="sizing-addon1">
                    </div>
                </div>
                <div class="row margin-top-5px">
                    <div class="col-md-3">
                        <b>Total: </b>
                    </div>
                    <div class="col-md-8">
                        <p id="book_total_label"></p>
                    </div>
                    <div class="col-md-8">
                    </div>
                </div>
                <br>
                <div class="modal-footer">
                    <div class="col-md-6 left">
                        <button class="btn btn-info create-btn" onclick="booking_create();">Save</button>
                    </div>
                  
                    <div class="col-md-6 right">
                        <button class="btn btn-warning" id="close">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>