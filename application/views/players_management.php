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
                <h1 style="text-align: left">
                    Players Management:
                </h1>
                <div style="clear: both"></div>
                <!--<br><br>-->
                <?php echo $output; ?>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">

    $('.hide_tr').each(function () {
        $(this).parent('td').parent('tr').find('.actions a:first').find("span:last").text('Activate');
    });
</script>
