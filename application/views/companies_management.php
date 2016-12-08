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
                 width:90%; 
                 margin-left:5%;
                 direction: <?php echo ($this->session->userdata('language') == "arabic") ? "rtl" : "ltr"; ?>
                 "> 
                <br><br>
                <h1 style="text-align: left">
                    <?php echo $this->lang->line('companies_management') ?>:
                </h1>
                <div style="clear: both"></div>
                <br><br>
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
            </div>
        </div>
    </div>

    <script 
        type="text/javascript" 
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDgM1aEGmWi1lLqYbNYqkS8lxlhDpP-1lI&sensor=false&language=ar"
        >
    </script>
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
                draggable: false,
                scrollwheel: false,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };

            map = new google.maps.Map(document.getElementById("map-canvas"), mapProp);
            marker.setMap(map);

            google.maps.event.addListener(marker, 'click', function () {

                infowindow.setContent(contentString);
                infowindow.open(map, marker);

            });
        }
        ;
        google.maps.event.addDomListener(window, 'load', initialize);

        google.maps.event.addDomListener(window, "resize", resizingMap());

        $('#map_modal').on('show.bs.modal', function () {
            //Must wait until the render of the modal appear, thats why we use the resizeMap and NOT resizingMap!! ;-)
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

        function pan(lng, lat, address) {
            $('#addressText').html(address);
            console.log(lat, lng);
            var latLng = new google.maps.LatLng(lat, lng);
            map.setCenter(latLng);
            var marker = new google.maps.Marker({
                position: latLng,
                title: 'Point A',
                map: map,
                draggable: true
            });
        }
    </script>
</body>

