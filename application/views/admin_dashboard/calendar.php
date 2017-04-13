<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/fullcalendar/fullcalendar.min.css') ?>" />
<link href="<?php echo base_url('assets/fullcalendar/fullcalendar.print.css') ?>" rel="stylesheet" type="text/css" id="fullcalendar_print" disabled="true"/>
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/calendar.css') ?>" />
<script type="text/javascript" src="<?php echo base_url('assets/js/jquery-ui.min.js') ?>"></script>
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/jquery-ui.min.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/incoming_booking_sideMenu.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/bootstrap-datepicker.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/jquery.timepicker.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/card.css') ?>" />
<link rel="stylesheet" href="<?php echo base_url() ?>assets/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/fullcalendar/fullcalendar.print.css') ?>" media="print"/>
<script type="text/javascript" src="<?php echo base_url('assets/fullcalendar/moment.min.js') ?>"></script>
<script type="text/javascript" src="<?php echo base_url('assets/js/FileSaver.js') ?>"></script>
<script src="<?php echo base_url('assets/js/jspdf.js')?>"></script>
<script type="text/javascript" src="<?php echo base_url('assets/fullcalendar/fullcalendar.min.js') ?>"></script>
<script type="text/javascript" src="<?php // echo base_url('assets/fullcalendar/fullcalendar-columns.js')           ?>"></script>
<script type="text/javascript">
    var fields = '<?php echo json_encode($fields) ?>';
    var approved = "<?php echo BOOKING_STATE::APPROVED ?>";
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
            <?php $this->load->view('admin_dashboard/incoming_booking') ?>
            <div style="
                 width: 96%;
                 margin-left: 2%;
                 "> 
                <h1 style="text-align: left">
                    <?php echo $fields[0]->company_name?>:
                    <span onclick="print_calender();" class="export fc-button fc-button-today fc-state-default fc-corner-left fc-corner-right">Export</span>
                </h1>
                <!--<button onclick="window.print()">print</button>-->
                
                <div id='calendar'></div>
            </div>
        </div>
        <?php $this->load->view("admin_dashboard/show_booking_modal") ?>
        <?php $this->load->view("admin_dashboard/new_booking_modal") ?>
        <?php $this->load->view("admin_dashboard/cancel_booking_modal") ?>
        <script type="text/javascript" src="<?php echo base_url('assets/js/jquery.timepicker.js') ?>"></script>
        <script type="text/javascript" src="<?php echo base_url('assets/js/calendar.js') ?>"></script>
        <script type="text/javascript" src="<?php echo base_url('assets/js/push.min.js') ?>"></script>
        <script type="text/javascript" src="<?php echo base_url('assets/js/check_reservations.js') ?>"></script>
</body>

