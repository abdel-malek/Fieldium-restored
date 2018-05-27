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
                <br><br>
                <h1 class="title_page">
                    <?php echo $this->lang->line('summary_report'); ?>:
                </h1>
                <div class=" row">   
                    <div class="col-md-1">
                        <label class="date_label"> <?php echo $this->lang->line('from'); ?>: </label> 
                    </div>
                    <div class="col-md-2">
                        <input class="theme date_label form-control" type="text" id="from_date"/>
                    </div>
                    <div class="col-md-1">
                        <label class="date_label"> <?php echo $this->lang->line('to'); ?>: </label>
                    </div>
                    <div class="col-md-2">
                        <input class="theme date_label form-control" type="text" id="to_date"  />
                    </div>
                    <div class="col-md-2 pull-right">
                        <input class="form-control btn btn-custom" style="padding: 0px" onclick="get_data()" type="button" id="search" value="<?php echo $this->lang->line('get'); ?>" />  
                    </div>
                </div>
                <div style="clear: both"></div>
                <br><br>
                <div class="row">

                    <div class="col-md-2 well" id="bookings_number">

                    </div>
                    <div class="col-md-2">
                    </div>
                    <div class="col-md-2 well" id="total">

                    </div>
                    <div class="col-md-3">
                    </div>
                    <div class="col-md-3">
                    </div>
                </div>
                <table id="report" class="display groceryCrudTable dataTable" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th><?php echo $this->lang->line('field'); ?></th>
                            <th><?php echo $this->lang->line('bookings'); ?></th>
                            <th><?php echo $this->lang->line('total'); ?></th>
                        </tr>
                    </thead>
                    <tbody>

                    </tbody>
                    <tfoot>
                        <tr> 
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</body>
<?php $this->load->view('reports/reports_header') ?>
<?php
if ($this->session->userdata('lang') == 'arabic') {
    ?>
    <script src="<?php echo base_url() ?>assets/js/report_ar.js"></script>

    <?php
} else {
    ?>
    <script src="<?php echo base_url() ?>assets/js/report.js"></script>
    <?php
}
?>
<script type="text/javascript">

                            function get_data() {
                                $.ajax({
                                    type: 'ajax',
                                    method: 'get',
                                    url: site_url + "/reports/reservations_report/format/json?from_date=" + $('#from_date').val() + "&to_date=" + $('#to_date').val(),
                                    success: function (data) {
                                        if (data.status)
                                        {
                                            if (table != null)
                                                table.destroy();
                                            var html1 = '';
                                            $('#bookings_number').html("<?php echo $this->lang->line('total_bookings') ?>: 0");
                                            $('#total').html("<?php echo $this->lang->line('total') ?> : 0 AED");
                                            if (data['data'].length != 0) {
                                                $('#bookings_number').html("<?php echo $this->lang->line('total_bookings') ?>: " + data["data"]["bookings_number"] + " AED");
                                                $('#total').html("<?php echo $this->lang->line('total') ?> : " + data["data"]["total"] + " AED");
                                                for (var i = 0; i < data['data']['details'].length; i++)
                                                {
                                                    html1 +=
                                                            '<tr>' +
                                                            '<td>' + data['data']['details'][i].field_name + '</td>' +
                                                            '<td>' + data['data']['details'][i].bookings_number + '</td>' +
                                                            '<td>' + data['data']['details'][i].total + ' AED</td>'
                                                    '</tr>';
                                                }
                                            }
                                            $('#report tbody').html(html1);
                                            create_table();
                                        }
                                    },
                                    error: function () {
                                        alert("Faild");
                                    },
                                });
                            }
                            get_data();
</script>