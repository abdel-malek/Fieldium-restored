<style>
    #outer-dropzone {
        height: 200px;
    }

    #inner-dropzone {
        height: 80px;
    }

    .dropzone {
        background-color: #ccc;
        border: dashed 4px transparent;
        border-radius: 4px;
        margin: 10px auto 30px;
        padding: 10px;
        width: 85%;
        transition: background-color 0.3s;
    }

    .drop-active {
        border-color: #aaa;
    }

    .drop-target {
        background-color: #29e;
        border-color: #fff;
        border-style: solid;
    }

    .drag-drop {
        display: inline-block;
        min-width: 40px;
        padding: 2em 0.5em;

        color: #fff;
        background-color: #29e;
        border: solid 2px #fff;

        -webkit-transform: translate(0px, 0px);
        transform: translate(0px, 0px);

        transition: background-color 0.3s;
    }

    .drag-drop.can-drop {
        color: #000;
        background-color: #4e4;
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
                 width:90%; 
                 margin-left:5%;

                 "> 

                <h1 style="text-align: left">
                    <?php echo $company->name ?> fields:
                </h1>
                <!--<a style="cursor: pointer" href="<?php echo site_url('companies/companies_management') ?>">< Back to companies list</a>-->
                <div style="clear: both"></div>
                <?php echo $output; ?>
            </div>
        </div>
    </div>
    <div  class="modal" id="fields_modal" >
        <div class="modal-dialog" style="width: 90%">
            <div class="modal-content">
                <div class="modal-header">
                    <h3  style="display: inline;">Aggregate Field</h3>
                    <button type="button" class="close" data-dismiss="modal" >&times</button>
                </div>
                <div class="modal-body" >
                    <?php foreach ($fields as $field) { ?>
                        <div field_id="<?php echo $field->field_id ?>" class="draggable drag-drop"> <?php echo $field->name ?></div>
                    <?php } ?>
                    <div id="outer-dropzone" class="dropzone">

                    </div>
                </div>
                <div class="modal-footer">
                    <div class="col-md-6 left">
                        <button class="btn btn-info create-btn" onclick='create_aggregate_field()'>Create</button>
                    </div>

                    <div class="col-md-6 right">
                        <button class="btn btn-warning"class="close" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
     <div  class="modal" id="fields_show_modal" >
        <div class="modal-dialog" style="width: 90%">
            <div class="modal-content">
                <div class="modal-header">
                    <h3  style="display: inline;" id="aggregate_field_name">Aggregate Field</h3>
                    <button type="button" class="close" data-dismiss="modal" >&times</button>
                </div>
                <div class="modal-body" >
                    <?php foreach ($fields as $field) { ?>
                        <div field_id="<?php echo $field->field_id ?>" class="draggable drag-drop"> <?php echo $field->name ?></div>
                    <?php } ?>
                    <div id="outer-dropzone" class="dropzone">

                    </div>
                </div>
                <div class="modal-footer">
                    <div class="col-md-6 left">
                        <button class="btn btn-info create-btn" onclick='create_aggregate_field()'>Create</button>
                    </div>

                    <div class="col-md-6 right">
                        <button class="btn btn-warning"class="close" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var company_id = "<?php echo $company->company_id ?>";
        $('.datatables-add-button').append('<a onclick="select_fields()" ' +
                'role="button" class="add_button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-primary" ' +
//                'href="http://localhost/fieldium-server/index.php/fields/fields_management/2/add">' +
                '<span class="ui-button-icon-primary ui-icon ui-icon-circle-plus"></span>' +
                '<span class="ui-button-text">Add aggregate field</span>' +
                '</a>');
        function select_fields() {
            $('#fields_modal').modal("show");
        }


    </script>
    <script type="text/javascript" src="<?php echo base_url('assets/js/interact.js') ?>"></script>
    <script type="text/javascript" src="<?php echo base_url('assets/js/aggregate_fields.js') ?>"></script>

</body>

