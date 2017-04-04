<div  class="modal" id="pending_booking_modal" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 style="display: inline;" id="booking_num"></h3><br>
                <h4 id="field_label"></h4>
                <button type="button" class="close" data-dismiss="modal" >&times</button>
            </div>
            <div class="modal-body" >
                <div class="row">
                    <div class="col-md-6">
                        <b>Player Name: </b>
                    </div>
                    <div class="col-md-6">
                        <p id="player_name"></p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-6">
                        <b>Player Phone: </b>
                    </div>
                    <div class="col-md-6">
                        <p id="player_phone"></p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-6">
                        <b>Game: </b>
                    </div>
                    <div class="col-md-6"><p id="game_label"></p></div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-6">
                        <b>Date: </b>
                    </div>
                    <div class="col-md-6"><p id="date_label"></p></div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-6">
                        <b>Start Time: </b>
                    </div>
                    <div class="col-md-6">
                        <p id="start_label"></p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-6">
                        <b>End Time: </b>
                    </div>
                    <div class="col-md-6">
                        <p id="duration_label">Duration</p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-6">
                        <b>Cost Per Hour: </b>
                    </div>
                    <div class="col-md-6">
                        <p id="cost_label"></p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-6">
                        <b>Total: </b>
                    </div>
                    <div class="col-md-6">
                        <p id="total_label"></p>
                    </div>
                    <div class="col-md-6">
                    </div>
                </div>
                <br>
                <div class="modal-footer">
                    <div class="col-md-6 left">
                        <button class="btn btn-primary close-btn" onclick="booking_approve()">approve</button>
                    </div>
                    <div class="col-md-6 right">
                        <button class="btn btn-warning" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>