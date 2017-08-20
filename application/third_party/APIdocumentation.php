<!DOCTYPE html>
<html>
    <head>
        <?php $this->load->view('header'); ?>
        <link rel="stylesheet" type="text/css" href="<?php echo base_url() ?>assets/index/css/dashboard.css" />
    </head>
    <body>
        <div class="container"  >
            <?php //$this->load->view("main_menu"); ?>
            <div class="main_menu">
                <div class="header-logo">
                    <div class="row">
                        <div class="col-md-1 col-sm-1 col-xs-1" style="padding-top:20px">
                            <a id="dashboard_btn" href="<?php echo base_url()?>index.php/site" style="display: none; font-size: 16px;color: #2895F1;margin-left:-50px;margin-right:10px;">< Dashboard</a>
                        </div>
                        <div class="col-md-5 col-sm-5 col-xs-5">
                            <img height="60" width="60"  src="<?php echo base_url()?>assets/index/images/logo.png" />
                            <p class="tradinos_name"> T R A D I N O S </p>
                        </div>

                        <div class="col-md-4 col-sm-4 col-xs-4">
                            <div class="user_side" style="display:none">
                                <img height="30" width="30" src="<?php echo base_url()?>assets/index/images/user_1.png" style="margin-right:5px"/> 
                                <div class="btn-group">
                                    <button type="button" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <p><?php echo $this->session->userdata('USERNAME')?> <span class="caret"></span></p>
                                    </button>
                                    <div class="dropdown-menu">
                                      <a class="dropdown-item" href="<?php echo site_url();?>/users/logout">Log Out</a>
                                    </div>
                                </div>                        
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="documentation">
                <h2 >API Documentation:</h2><br>
                <div class="procedures_area" style="padding-left: 70px;" >
                    <h2>Types:</h2>
                    <hr>
<?php foreach($types as $type) {?>
<h3><?php echo $type['name']?>:</h3>
<hr>

<?php
    foreach($type['methods'] as $method) {
    ?>
<table class="table">

<tr>
<td>Method:</td>
<td><?php echo $method['method']?></td>
</tr>
                                            <tr>
                            <td>URL:</td>
                            <td><?php echo site_url().$method['URL']?>
                            </td>
                        </tr>
                        <tr>
                            <td>HTTP:</td>
                            <td><?php echo $method['HTTP']?></td>
                        </tr>
                        <tr>
                            <td>Description:</td>
                            <td><?php echo $method['description']?></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <table class="table procedures_table"  >
                                    <thead>
                                        <tr>
                                            <th  style="width:25%">Parameter Name</th>
                                            <th style="width:25%">Type</th>
                                            <th style="width:50%">Description</th>

                                        </tr>
                                    </thead>
                                    <tbody id="procedures_section">
<?php
    $i=1;
    foreach($method['params'] as $param) {
        if($i % 2 == 0) echo '<tr>'; else echo '<tr class="odd">';
        ?>
                                            <td><?php echo $param['name']?></td>
                                            <td><?php echo $param['type']?></td>
                                            <td><?php echo $param['description']?></td>
                                        </tr>
<?php }?>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
</table>
<?php }?>

<?php }?>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
