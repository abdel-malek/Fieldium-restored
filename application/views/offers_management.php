<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/jquery.timepicker.css') ?>" />
<link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/bootstrap-tagsinput.css') ?>" />
<link href="<?php echo base_url() ?>assets/css/select2.min.css" rel="stylesheet" />
<script src="<?php echo base_url() ?>assets/js/select2.min.js"></script>
<style>
    .btn {
        height: 30px;
        padding-top: 5px;
        margin-right:3px
    }
    .btn-danger{
        background-color: #f0ad4e !important;
        border:none
    }
    tag {
        background-color: #e5e5e5;
        border-radius: 5px;
    }
    .select2, .bootstrap-tagsinput  {
        border: 2px solid #e4930d !important;
        border-radius: 4px;
        width:100% !important;
        font-size: 14px
    }
    .select2 input, .bootstrap-tagsinput input {
        border: none !important
    }
    ul.tagit li.tagit-choice .tagit-close .text-icon{
        display: inline
    }
    .label-info{
        font-size: 13px
    }
    .tagit-choice{
        width: auto
    }
    label{
        font-size: 15px
    }
    .select2-container--default .select2-selection--multiple, .select2-container--default.select2-container--focus .select2-selection--multiple{
        border: none
    }
    .title_page{
        text-align: left;
    }
</style>

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
                 /*                 width:90%; 
                                  margin-left:5%;*/

                 "> 
                <br>
                <h1 class="title_page" >
                    <?php echo $this->lang->line('offers_management') ?>: <button class="btn btn-info" onclick="clear_offer();$('#offer_modal').modal('show')"><?php echo $this->lang->line('new_offer'); ?></button>
                </h1>
                <div style="clear: both"></div>
                <?php echo $output; ?>
            </div>
        </div>
    </div>
    <?php $this->load->view('offer_modal') ?>
    <script type="text/javascript" src="<?php echo base_url('assets/js/jquery.timepicker.js') ?>"></script>
    <script type="text/javascript" src="<?php echo base_url('assets/js/bootstrap-tagsinput.js') ?>"></script>
    <script type="text/javascript" src="<?php echo base_url('assets/js/typehead.js') ?>"></script>
    <script type="text/javascript" src="<?php echo base_url('assets/js/offers.js') ?>"></script>
</body>

