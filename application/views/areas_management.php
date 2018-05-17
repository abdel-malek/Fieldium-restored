<style>
    html, body, #map-canvas  {
        margin: 0;
        padding: 0;
        height: 100%;
    }

    #map-canvas {
        width:500px;
        height:480px;
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
                <h1 class="title_page">
                    <?php echo $this->lang->line('areas_management') ?>:
                    <select id="country" class="form-control" 
                            value="<?php echo $country ?>" 
                            style="
                            width: 80px !important;
                            display: inline;
                            ">
                        <option value="<?php echo UAE ?>" 
                        <?php echo $country == UAE ? ' selected ' : '' ?>
                                >UAE</option>
                        <option 
                        <?php echo $country == SYRIA ? ' selected ' : '' ?>
                            value="<?php echo SYRIA ?>">SYRIA</option>
                    </select>
                </h1>
                <div style="clear: both"></div>

                <?php echo $output; ?>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $('#country').change(function () {
            location.href = '<?php echo site_url() ?>/areas/areas_management/' + $(this).val();
        });
    </script>
</body>

