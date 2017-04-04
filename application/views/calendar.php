<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/fullcalendar/fullcalendar.min.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/jquery-ui.min.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/calendar.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/incoming_booking_sideMenu.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/bootstrap-datepicker.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/jquery.timepicker.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/card.css') ?>" />
<link rel="stylesheet" href="<?php echo base_url() ?>assets/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/fullcalendar/fullcalendar.print.css') ?>" media="print"/>
<script type="text/javascript" src="<?php echo base_url('assets/js/jquery-ui.min.js') ?>"></script>
<script type="text/javascript" src="<?php echo base_url('assets/fullcalendar/moment.min.js') ?>"></script>
<script type="text/javascript" src="<?php echo base_url('assets/fullcalendar/fullcalendar.min.js') ?>"></script>
<script type="text/javascript" src="<?php echo base_url('assets/fullcalendar/materialize.js') ?>"></script>
<script type="text/javascript" src="<?php // echo base_url('assets/fullcalendar/fullcalendar-columns.js')        ?>"></script>
<script type="text/javascript">
    var fields = '<?php echo json_encode($fields) ?>';
    var approved = "<?php echo BOOKING_STATE::APPROVED ?>";
</script>
<body> 
    <div class="container" style="">
        <?php $this->load->view('sideMenu') ?>
        <?php $this->load->view('incoming_booking') ?>
        <div class="main-content">
            <div class="swipe-area"></div>
            <a href="#" data-toggle=".container" id="sidebar-toggle">
                <span class="bar"></span>
                <span class="bar"></span>
                <span class="bar"></span>
            </a>
            <div style="
                 width:90%; 
                 margin-left:5%;

                 "> 
                <h1 style="text-align: left">
                    Bookings:
                </h1>
                <button onclick="window.print()">print</button>
                <div id='calendar'></div>
            </div>
        </div>
        <div  class="modal" id="booking_modal" >
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
                            <div class="col-md-6">
                                <b>Player Phone: </b>
                            </div>
                            <div class="col-md-6">
                                <p id="player_phone"></p>
                            </div>
                            <div class="col-md-6">
                                <b>Game: </b>
                            </div>
                            <div class="col-md-6"><p id="game_label"></p></div>
                            <div class="col-md-6">
                                <b>Date: </b>
                            </div>
                            <div class="col-md-6"><p id="date_label"></p></div>
                            <div class="col-md-6">
                                <b>Start Time: </b>
                            </div>
                            <div class="col-md-6">
                                <p id="start_label"></p>
                            </div>
                            <div class="col-md-6">
                                <b>End Time: </b>
                            </div>
                            <div class="col-md-6">
                                <p id="duration_label">Duration</p>
                            </div>
                            <div class="col-md-6">
                                <b>Cost Per Hour: </b>
                            </div>
                            <div class="col-md-6">
                                <p id="cost_label"></p>
                            </div>
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
                                <button class="btn btn-primary close-btn" onclick="show_booking_model()">Cancel</button>
                            </div>
                            <div class="col-md-6 right">
                                <button class="btn btn-warning" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div  class="modal" id="new_booking_modal" >
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 style="display: inline;">New Booking</h3>
                    </div>
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
                                <input id="player_phone" type="text" class="form-control" placeholder="Player Phone`" aria-describedby="sizing-addon1">
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
                            <div class="col-md-8" id="booking_date_div">
                                <label id="booking_date">2017-7-7</label>
                                <input type="hidden" id="booking_datepicker" onclick="open_booking_datepicker()">
                                </input>
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
                                <button class="btn btn-primary create-btn" onclick="booking_create();">Create</button>
                            </div>
                            <div class="col-md-6 right">
                                <button class="btn btn-warning" id="close">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

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
                        <div class="row margin-top-5px center">
                            <input id="new-cancel-msg" type="text" class="" placeholder="new refuse message" aria-describedby="sizing-addon1">
                        </div>
                        <br>
                        <div class="modal-footer">
                            <div class="col-md-6 left">
                                <button class="btn btn-danger create-btn" onclick="booking_cancel();">Cancel</button>
                            </div>
                            <div class="col-md-6 right">
                                <button class="btn btn-warning" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript" src="<?php echo base_url('assets/js/jquery.timepicker.js') ?>"></script>
        <script type="text/javascript" src="<?php echo base_url('assets/js/calendar.js') ?>"></script>
</body>

