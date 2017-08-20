// jQuery(function($) {
//     // Asynchronously Load the map API
//     var script = document.createElement('script');
//     // script.src = "//maps.googleapis.com/maps/api/js?sensor=true&callback=initialize";
//     document.body.appendChild(script);
// });


var mark = [];

function initialize() {
    var map;
    var bounds = new google.maps.LatLngBounds();
    var mapOptions = {
        mapTypeId: 'roadmap'
    };

    map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
    map.setTilt(45);

    var geocoder = new google.maps.Geocoder();
    $("#dropdown").click(function () {
        address = $("#dropdown :selected")[0];
        var lat = $(address).attr("lat");
        var lng = $(address).attr("lng");
        var position = new google.maps.LatLng(lat, lng);
        map.setCenter(position);

    });




    // google.maps.event.addListener(address, 'selected', (function(address) {
    //                            return function() {
    //                                infoWindow.setContent(infoWindowContent[i][0]);
    //                                infoWindow.open(map, address);
    //                            }
    //                        })(address));




    //    function geocodeAddress(address, geocoder, resultsMap) {
    //     document.getElementById('info').innerHTML = address;
    //     geocoder.geocode({
    //         'address': address
    //     }, function(results, status) {
    //         if (status === google.maps.GeocoderStatus.OK) {
    //             resultsMap.fitBounds(results[0].geometry.viewport);
    //             document.getElementById('info').innerHTML += "<br>" + results[0].geometry.location.toUrlValue(6);
    //         } else {
    //             alert('Geocode was not successful for the following reason: ' + status);
    //         }
    //     });
    // }



    // $('#map_canvas, body').load(function () {
    // var map_c = $(this);
    $.ajax({
        url: 'index.php/companies/get_all',
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            if (response.status === true) {
                var markers = [[]];
                for (var i = 0; i < response.data.length; i++) {
                    markers = [[response.data[i].name, response.data[i].latitude, response.data[i].longitude]];
                    var infoWindowContent = [
                        ['<div class="info_content">' +
                                    '<img src="assets/images/basket.png" alt="">' +
                                    '<div class="field_desc">' +
                                    '<h3>' + response.data[i].name + '</h3>' +
                                    '<p>' + response.data[i].address + '</p>' +
                                    '</div>' + '</div>']];


                    var infoWindow = new google.maps.InfoWindow({
                        content: infoWindowContent,
                        maxWidth: 350
                    }), marker, i;



                }

                for (var i = 0; i < markers.length; i++) {

                    markers.push([response.data[i].name, response.data[i].latitude, response.data[i].longitude]);
                    infoWindowContent.push(['<div class="info_content">' +
                                '<img src="assets/images/basket.png" alt="">' +
                                '<div class="field_desc">' +
                                '<h3>' + response.data[i].name + '</h3>' +
                                '<p>' + response.data[i].address + '</p>' +
                                '</div>' + '</div>']);

                    var position = new google.maps.LatLng(markers[i][1], markers[i][2]);
                    bounds.extend(position);
                    marker = new google.maps.Marker({
                        position: position,
                        map: map,
                        title: markers[i][0]
                    });

                    // Allow each marker to have an info window
                    google.maps.event.addListener(marker, 'click', (function (marker, i) {
                        return function () {
                            infoWindow.setContent(infoWindowContent[i][0]);
                            infoWindow.open(map, marker);
                        }
                    })(marker, i));

                    mark.push(marker);


                    // var $company = $('#dropdown');
                    // $company.append('<option  onclick="myClick(id);" id=' + i + ' value=' + response.data[i].company_id + ' lng=' + response.data[i].longitude +' lat=' + response.data[i].latitude + '>' + response.data[i].name + '</option>'); 
                    map.fitBounds(bounds);
                    // console.log("here2", mark);

                }

                var boundsListener = google.maps.event.addListener((map), 'bounds_changed', function (event) {
                    this.setZoom(8);
                    google.maps.event.removeListener(boundsListener);
                });

                // for( var i = 0; i < response.data.length; i++ ){
                //      var $company = $('#dropdown');
                //      $company.append('<option  onclick="myClick(id);" id=' + i + ' value=' + response.data[i].company_id + ' lng=' + response.data[i].longitude +' lat=' + response.data[i].latitude + '>' + response.data[i].name + '</option>'); 

                //  }

            }


        }
    });
}



function myClick(i) {
    // console.log("here3", mark);

    google.maps.event.trigger(mark[i], "click");
    map.setCenter(mark[i].getPosition());
    // google.maps.event.trigger(mark[id], 'click');
}





// });



// var markers=[
//     ['Dubai, Dubai', 24.9822,55.4029],
//     [' Sharjah,  Sharjah', 25.3223,55.5136],
//     [' Al Ain,  Al Ain', 24.1302,],
//     [' Ras al-Khaimah,  Ras al-Khaimah', 25.8007,55.9762],
//     ['Emirate of Ajman, Emirate of Ajman', 25.4052,55.5136],
//     ['Fujairah, Fujairah',25.4111,56.2482],
//     ['Umm al-Quwain, Umm al-Quwain',25.5205,55.7134]
// ];
//
// // Info Window Content
// var infoWindowContent = [
//     ['<div class="info_content">' +
//         '<img src="./images/basket.png" alt="">'+
//         '<div class="field_desc">' +
//     '<h3>Dubai</h3>' +
//     '<p>Lorem ipsum dolor consect adipiscing elit, diamnonu nibh euismod dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim </p>'+
//     '</div>'+ '</div>'],
//
//     ['<div class="info_content">' +
//     '<img src="./images/soccer.png" alt="">'+
//     '<div class="field_desc">' +
//     '<h3>Sharjah</h3>' +
//     '<p>Lorem ipsum dolor consect adipiscing elit, diamnonu nibh euismod dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim </p>' +
//     '</div>'+'</div>'],
//
//     ['<div class="info_content">' +
//     '<img src="./images/cricket.png" alt="">'+
//     '<div class="field_desc">' +
//     '<h3>Al Ain</h3>' +
//     '<p>Lorem ipsum dolor consect adipiscing elit, diamnonu nibh euismod dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim </p>' +
//     '</div>'+'</div>'],
//
//     ['<div class="info_content">' +
//     '<img src="./images/tennis.png" alt="">'+
//     '<div class="field_desc">' +
//     '<h3>Ras al-Khaimah</h3>' +
//     '<p>Lorem ipsum dolor consect adipiscing elit, diamnonu nibh euismod dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim </p>' +
//     '</div>'+'</div>'],
//
//     ['<div class="info_content">' +
//     '<img src="./images/tennis.png" alt="">'+
//     '<div class="field_desc">' +
//     '<h3>Emirate of Ajman</h3>' +
//     '<p>Lorem ipsum dolor consect adipiscing elit, diamnonu nibh euismod dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim </p>' +
//     '</div>'+'</div>'],
//
//     ['<div class="info_content">' +
//     '<img src="./images/tennis.png" alt="">'+
//     '<div class="field_desc">' +
//     '<h3>Fujairah</h3>' +
//     '<p>Lorem ipsum dolor consect adipiscing elit, diamnonu nibh euismod dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim </p>' +
//     '</div>'+'</div>'],
//     ['<div class="info_content">' +
//     '<img src="./images/tennis.png" alt="">'+
//     '<div class="field_desc">' +
//     '<h3>Umm al-Quwain</h3>' +
//     '<p>Lorem ipsum dolor consect adipiscing elit, diamnonu nibh euismod dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim </p>' +
//     '</div>'+'</div>']
// ];
//
// // Display multiple markers on a map
// var infoWindow = new google.maps.InfoWindow({ content: infoWindowContent,
//     maxWidth: 350}), marker, i;
//
// // Loop through our array of markers & place each one on the map
// for( i = 0; i < markers.length; i++ ) {
//     var position = new google.maps.LatLng(markers[i][1], markers[i][2]);
//     bounds.extend(position);
//     marker = new google.maps.Marker({
//         position: position,
//         map: map,
//         title: markers[i][0]
//     });
//
//     // Allow each marker to have an info window
//     google.maps.event.addListener(marker, 'click', (function(marker, i) {
//         return function() {
//             infoWindow.setContent(infoWindowContent[i][0]);
//             infoWindow.open(map, marker);
//         }
//     })(marker, i));
//
//     // Automatically center the map fitting all markers on the screen
//     map.fitBounds(bounds);
// }
//
// // Override our map zoom level once our fitBounds function runs (Make sure it only runs once)
// var boundsListener = google.maps.event.addListener((map), 'bounds_changed', function(event) {
//     this.setZoom(8);
//     google.maps.event.removeListener(boundsListener);
// });


window.onload = initialize;
google.maps.event.addDomListener(window, "load", initialize);

function imgError(image) {
    image.onerror = "";
    image.src = "assets/images/Fieldium default image.jpg";
    return true;
}



// function initialize() {
//     var map = new google.maps.Map(document.getElementById("map_canvas"));
//     var geocoder = new google.maps.Geocoder();
//     $("#dropdown").click(function() {
//         address = $("#dropdown :selected")[0].text;
//         geocodeAddress(address, geocoder, map);
//     });
//     var address = $("#dropdown :selected")[0].text;
//     geocodeAddress(address, geocoder, map);
// }
// google.maps.event.addDomListener(window, "load", initialize);
//
//
//
// function geocodeAddress(address, geocoder, resultsMap) {
//     document.getElementById('info').innerHTML = address;
//     geocoder.geocode({
//         'address': address
//     }, function(results, status) {
//         if (status === google.maps.GeocoderStatus.OK) {
//             resultsMap.fitBounds(results[0].geometry.viewport);
//             document.getElementById('info').innerHTML += "<br>" + results[0].geometry.location.toUrlValue(6);
//         } else {
//             alert('Geocode was not successful for the following reason: ' + status);
//         }
//     });
// }

// function triggerClick(i) {
//     google.maps.event.trigger(markers[i],'click');
// }


$(document).ready(function () {
    // initialize();


    $(window).scroll(function () {
        if ($(document).scrollTop() > 700) {
            $(".navbar-default").addClass("navbar-fixed-top");
        } else {
            $(".navbar-default").removeClass("navbar-fixed-top");
        }
    });

    $("#bs-example-navbar-collapse-1 a").on('click', function (event) {
        if (this.hash !== "") {
            event.preventDefault();
            var hash = this.hash;
            $('html, body').animate({
                scrollTop: $(hash).offset().top
            }, 800, function () {

                window.location.hash = hash;
            });
        }
    });

    imgError();


});