
<div  class="modal" id="cancel_booking_modal" style="margin-top: 10%;" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 style="display: inline;">Select a message to send to the client</h3>
            </div>
            <div class="modal-body" >
                <div class="row margin-top-5px center">
                    <select id="refuse_msgs-list">
                    </select>
                </div>
                <br>
                <div class="row margin-top-5px center">
                    <input id="new-cancel-msg" type="text" class="" placeholder="new refuse message" aria-describedby="sizing-addon1">
                </div>
                <br>
                <div class="modal-footer">
                    <div class="col-md-6 left">
                        <button class="btn btn-danger create-btn" onclick="booking_cancel();">Cancel Reservation</button>
                    </div>
                    <div class="col-md-6 right">
                        <button class="btn btn-warning" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>