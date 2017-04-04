<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/fullcalendar/fullcalendar.min.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/calendar.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/incoming_booking_sideMenu.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/bootstrap-datepicker.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/jquery.timepicker.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/card.css') ?>" />
<link rel="stylesheet" href="<?php echo base_url() ?>assets/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/fullcalendar/fullcalendar.print.css') ?>" media="print"/>
<script type="text/javascript" src="<?php echo base_url('assets/fullcalendar/moment.min.js') ?>"></script>
<script type="text/javascript" src="<?php echo base_url('assets/fullcalendar/fullcalendar.min.js') ?>"></script>
<script type="text/javascript" src="<?php echo base_url('assets/fullcalendar/materialize.js') ?>"></script>
<script type="text/javascript" src="<?php // echo base_url('assets/fullcalendar/fullcalendar-columns.js')         ?>"></script>
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
                <button onclick="notify_me()"></button>
                <button onclick="window.print()">print</button>
                <div id='calendar'></div>
            </div>
        </div>
        <?php $this->load->view("show_booking_modal") ?>
        <?php $this->load->view("new_booking_modal") ?>
        <?php $this->load->view("cancel_booking_modal") ?>
        <script type="text/javascript" src="<?php echo base_url('assets/js/jquery.timepicker.js') ?>"></script>
        <script type="text/javascript" src="<?php echo base_url('assets/js/calendar.js') ?>"></script>
        <script type="text/javascript" src="<?php echo base_url('assets/js/push.min.js') ?>"></script>
        <script type="text/javascript" src="<?php echo base_url('assets/js/check_reservations.js') ?>"></script>
</body>

