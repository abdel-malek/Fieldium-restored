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
                    Declined Reservations:
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
                    <div class="col-md-1">
                        <label class="date_label">Field: </label>
                    </div>
                    <div class="col-md-2">
                        <select id="field" class="form-control">
                            <option value="0">Not Selected</option>
                            <?php
                            foreach ($fields as $value) {
                                echo '<option value="' . $value->field_id . '">' . $value->name . '</option>';
                            }
                            ?>
                        </select>
                    </div>
                    <div class="col-md-2 pull-right">
                        <input class="form-control btn btn-warning" style="padding: 0px" onclick="get_data()" type="button" id="search" value="Get"/>  
                    </div>
                </div>
                <div style="clear: both"></div>
                <br><br>
                <table id="report" class="display groceryCrudTable dataTable" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Field</th>
                            <th>Player</th>
                            <th>Game</th>
                            <th>Date</th>
                            <th>Start</th>
                            <th>Duration</th>
                            <th>Hourly Rate</th>
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
                            <th></th>
                            <th></th>
                            <th></th>
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
                                if ($('#field').val() == 0) {
                                    alert("Field is required");
                                } else {
                                    $.ajax({
                                        type: 'ajax',
                                        method: 'get',
                                        url: site_url + "/reports/declined_reservations_report/format/json?from_date=" + $('#from_date').val() + "&to_date=" + $('#to_date').val() + "&field_id=" + $('#field').val(),
                                        success: function (data) {
                                            if (data.status)
                                            {
                                                if (table != null)
                                                    table.destroy();
                                                var html1 = '';
                                                if (data['data'].length != 0) {
                                                    for (var i = 0; i < data['data']['details'].length; i++)
                                                    {
                                                        html1 +=
                                                                '<tr>' +
                                                                '<td>' + data['data']['details'][i].booking_id + '</td>' +
                                                                '<td>' + data['data']['details'][i].field_name + '</td>' +
                                                                '<td>' + data['data']['details'][i].player_name + '</td>' +
                                                                '<td>' + data['data']['details'][i].game_type_name + '</td>' +
                                                                '<td>' + data['data']['details'][i].date + '</td>' +
                                                                '<td>' + data['data']['details'][i].start + '</td>' +
                                                                '<td>' + data['data']['details'][i].duration + ' Min</td>' +
                                                                '<td>' + data['data']['details'][i].hour_rate + ' AED</td>' +
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
                            }
//                            get_data();
</script>