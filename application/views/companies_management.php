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
                 direction: <?php echo ($this->session->userdata('language') == "arabic") ? "rtl" : "ltr"; ?>
                 "> 
                <br>
                <h1 class="title_page">
                    <?php //echo $this->lang->line('companies_management') 
//                    echo $lang['companies_management'];
                    echo $this->lang->line('companies_management');
                    ?>:
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
    <div  class="modal" id="map_modal" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 style="display: inline;"><?php echo '' ?></h3>
                    <button type="button" class="close" data-dismiss="modal" >&times</button>
                </div>
                <div class="modal-body" >
                    <p id="addressText"></p>
                    <div id="map-canvas"></div>
                </div>
                <a style="margin-left:30px;" role="button" class="add_button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-icon-primary" onclick="save_location()">
                    <span class="ui-button-icon-primary ui-icon ui-icon-circle-plus"></span>
                    <span class="ui-button-text">Save</span>
                </a>
                <br><br>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDgM1aEGmWi1lLqYbNYqkS8lxlhDpP-1lI&sensor=false&language=ar" ></script>
    <script type="text/javascript">

                    $(document).ready(function () {
                        $('.read-icon').attr("onclick", "console.log('clicked');");
                        $('.hide_tr').each(function () {
                            $(this).parent('td').parent('tr').find('.actions a').not(':first').hide();
                        });
                    });
                    var map;
                    var myCenter = new google.maps.LatLng(36.275447979569435, 33.51073851345376);
                    var marker = new google.maps.Marker({
                        position: myCenter
                    });

                    function initialize() {
                        var mapProp = {
                            center: myCenter,
                            zoom: 14,
                            draggable: true,
                            scrollwheel: false,
                            mapTypeId: google.maps.MapTypeId.ROADMAP
                        };

                        map = new google.maps.Map(document.getElementById("map-canvas"), mapProp);

                        google.maps.event.addListener(marker, 'click', function () {

                            infowindow.setContent(contentString);
                            infowindow.open(map, marker);

                        });
                    }
                    ;
                    google.maps.event.addDomListener(window, 'load', initialize);

                    google.maps.event.addDomListener(window, "resize", resizingMap());

                    $('#map_modal').on('show.bs.modal', function () {
                        resizeMap();
                    })

                    function resizeMap() {
                        if (typeof map == "undefined")
                            return;
                        setTimeout(function () {
                            resizingMap();
                        }, 400);
                    }

                    function resizingMap() {
                        if (typeof map == "undefined")
                            return;
                        var center = map.getCenter();
                        google.maps.event.trigger(map, "resize");
                        map.setCenter(center);
                    }

                    var latt, longg, company_id;
                    var gmarkers = [];
                    function pan(lng, lat, company) {
                        $('#addressText').html("");
                        var latLng = new google.maps.LatLng(lat, lng);
                        map.setCenter(latLng);
                        for (i = 0; i < gmarkers.length; i++) {
                            gmarkers[i].setMap(null);
                        }
                        var marker = new google.maps.Marker({
                            position: latLng,
                            title: 'Point A',
                            map: map,
                            draggable: true
                        });
                        gmarkers.push(marker);
                        google.maps.event.addListener(marker, 'dragend', function (ev) {
                            latt = marker.getPosition().lat();
                            longg = marker.getPosition().lng();
                            company_id = company;
                        });
                    }

                    function save_location() {
                        location.href = site_url + "/companies/update_location/" + company_id + "/" + longg + "/" + latt;
                    }
                    $('#country').change(function () {
                        location.href = '<?php echo site_url() ?>/companies/companies_management/' + $(this).val();
                    });
    </script>
</body>

