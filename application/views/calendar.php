<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/fullcalendar/fullcalendar.min.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/jquery-ui.min.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/calendar.css') ?>" />
<link rel="stylesheet" href="<?php echo base_url() ?>assets/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/fullcalendar/fullcalendar.print.css') ?>" media="print"/>
<script type="text/javascript" src="<?php echo base_url('assets/js/jquery-ui.min.js') ?>"></script>
<script type="text/javascript" src="<?php echo base_url('assets/fullcalendar/moment.min.js') ?>"></script>
<script type="text/javascript" src="<?php echo base_url('assets/fullcalendar/fullcalendar.min.js') ?>"></script>
<script type="text/javascript" src="<?php // echo base_url('assets/fullcalendar/fullcalendar-columns.js')      ?>"></script>
<script type="text/javascript">
    var fields = '<?php echo json_encode($fields) ?>';
    var approved = "<?php echo BOOKING_STATE::APPROVED?>";
</script>
<body> 
    <div class="container" style="">
        <?php $this->load->view('sideMenu') ?>
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
                            <button class="btn btn-warning" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
            </body>
            <script type="text/javascript" src="<?php echo base_url('assets/js/calendar.js') ?>"></script>
