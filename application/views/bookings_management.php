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
                 width:90%; 
                 margin-left:5%;

                 "> 
                <br><br>
                <h1 class="title_page">
                    <?php echo $this->lang->line('bookings'); ?>:
                </h1>
                <!--<a style="cursor: pointer" href="<?php echo site_url('companies/companies_management') ?>">< Back to companies list</a>-->

                <div style="clear: both"></div>
                <br>
                <?php echo $output; ?>
            </div>
        </div>
    </div>
</body>

