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
                <h1 style="text-align: left">
                    Summary Report:
                </h1>
                <div class=" row">   
                    <div class="col-md-1">
                        <label class="date_label">From: </label> 
                    </div>
                    <div class="col-md-2">
                        <input class="theme date_label form-control" type="text" id="from_date"/>
                    </div>
                    <div class="col-md-1">
                        <label class="date_label">To: </label>
                    </div>
                    <div class="col-md-2">
                        <input class="theme date_label form-control" type="text" id="to_date"  />
                    </div>
                    <div class="col-md-2 pull-right">
                        <input class="form-control btn btn-warning" style="padding: 0px" onclick="get_data()" type="button" id="search" value="Get"/>  
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
                            <th>Field</th>
                            <th>Bookings</th>
                            <th>Total</th>
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
<script src="<?php echo base_url() ?>assets/js/report.js"></script>
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
                                            $('#bookings_number').html("Total Bookings: 0");
                                            $('#total').html("Total : 0 AED");
                                            if (data['data'].length != 0) {
                                                $('#bookings_number').html("Total Bookings: " + data["data"]["bookings_number"] + " AED");
                                                $('#total').html("Total : " + data["data"]["total"] + " AED");
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