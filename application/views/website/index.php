
<!DOCTYPE HTML>
<html>
<head>
    <title>Fieldium Website Template | Home :: w3layouts</title>
    <link href="<?php echo base_url() ?>assets/website/css/bootstrap.css" rel='stylesheet' type='text/css' />
    <link href="<?php echo base_url() ?>assets/website/css/style.css" rel='stylesheet' type='text/css' />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">

    <!--<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800' rel='stylesheet' type='text/css'>-->
    <!--<link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,700' rel='stylesheet' type='text/css'>-->
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    <!--<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />-->
    <script src="<?php echo base_url() ?>assets/website/js/jquery.min.js"></script>
    <script src="<?php echo base_url() ?>assets/website/js/jquery.js" type="text/javascript"></script>
    <!--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>-->
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="<?php echo base_url() ?>assets/website/js/bootstrap.js"></script>
    <script type="text/javascript">
        jQuery(document).ready(function($) {
            $(".scroll").click(function(event){
                event.preventDefault();
                $('html,body').animate({scrollTop:$(this.hash).offset().top},1200);
            });
        });
    </script>
    <!-- grid-slider -->
    <script type="text/javascript" src="<?php echo base_url() ?>assets/website/js/jquery.mousewheel.js"></script>
    <script type="text/javascript" src="<?php echo base_url() ?>assets/website/js/jquery.contentcarousel.js"></script>
    <script type="text/javascript" src="<?php echo base_url() ?>assets/website/js/jquery.easing.1.3.js"></script>
    <script
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDGaJpZLCvQ4SibJOM1dCbcZuFYwZ94HJ4&sensor=false&callback=initialize">
    </script>
<!--    key=AIzaSyDGaJpZLCvQ4SibJOM1dCbcZuFYwZ94HJ4-->
    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>-->
    <script type="text/javascript" src="<?php echo base_url() ?>assets/website/js/script.js"></script>

    <!-- //grid-slider -->
</head>
<body data-spy="scroll" data-target=".navbar" data-offset="100">
<!-- start header_top -->
<div id="section1" class="header">
    <div class="container clearfix">
                <div class="header-text pull-left">
                    <h1>Create Your Glory</h1>
                    <h2>Best Choice For your site</h2>
                    <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna</p>
                    <div class="store">
                        <a href="#"><img src="<?php echo base_url() ?>assets/website/images/appstore.png" alt=""></a>
						<a href="#"><img src="<?php echo base_url() ?>assets/website/images/playstore.png" alt=""></a>
                    </div>
                </div>

            <div class="pull-right">
                <img src="<?php echo base_url() ?>assets/website/images/fieldium%20mobile%20mockup.png" alt="">

            </div>



        <!--<div class="header-arrow">-->
            <!--<a href="#bs-example-navbar-collapse-1" class="class scroll"><span> </span> </a>-->
        <!--</div>-->
    </div>
</div>
<!-- end header_top -->
<!-- start header_bottom -->
<div class="header-bottom">
    <div class="container">
        <div class="header-bottom_left">
            <i class="phone"> </i><span>1-200-346-2986</span>
        </div>
        <div class="social">
            <ul>
                <li class="facebook"><a href="#"><span> </span></a></li>
                <li class="twitter"><a href="#"><span> </span></a></li>
                <li class="pinterest"><a href="#"><span> </span></a></li>
                <li class="google"><a href="#"><span> </span></a></li>
                <li class="tumblr"><a href="#"><span> </span></a></li>
                <li class="instagram"><a href="#"><span> </span></a></li>
                <li class="rss"><a href="#"><span> </span></a></li>
            </ul>
        </div>
        <div class="clear"></div>
    </div>
</div>
<!-- end header_bottom -->


<nav id="myScrollspy" class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><img src="<?php echo base_url() ?>assets/website/images/fieldium_logo.png" alt=""></a>
        </div>
        <div id="bs-example-navbar-collapse-1" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="#section1">About App</a></li>
                <li><a href="#section2">sport available</a></li>
                <li><a href="#section3">Featured Fields</a></li>
                <li><a href="#section4">Map</a></li>
                <li><a href="#section5">Contact</a>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>
<!-- end menu -->

<div class="main">
    <div class="container">
        <div id="section2" class="about_trainer">
            <h3 class="m_2">sports available</h3>
            <div class="row about_box">

                <?php
                $x=1;
                foreach ($games as $game): ?>
                    <?php

                    switch ($x) {
                        case 1:
                            $class ='box1';
                            $boxRight = 'box1_right';
                            $desc = 'desc1';
                            break;

                            case 2:
                            $class ='box2';
                            $boxRight = 'box2_right';
                            $desc = 'desc2';
                            break;
                            case 3:
                            $class ='box2';
                            $boxRight = 'box2_right';
                            $desc = 'desc2';
                            break;
                            case 4:
                            $class ='box1';
                            $boxRight = 'box1_right';
                            $desc = 'desc1';
                            break;
                        
                        default:
                           $class ='box1';
                            $boxRight = 'box1_right';
                            $desc = 'desc1';
                            break;
                    }
                 
                    // $class = ($x % 2 == 0)? 'box1': 'box2 ';
                    // $boxRight = ($x % 2 == 0)? 'box1_right': 'box2_right';
                    // $desc = ($x % 2 == 0)? 'desc1': 'desc2';
                    ?>
                <div class="col-md-6 sp_av">
                    <div class="<?php echo $class?> box">
                        <div class="box1_left">
                            <a href="<?php echo $game->game_type_id?>"><img style="width: 100px; height: 160px" src="<?php echo base_url() ?>assets/website/images/<?php echo $game->image ?>" class="img-responsive" alt=""/></a>
                            <div class="<?php echo $desc?>">
                                <h3><?php echo $game->name ?><br><span class="m_text"></span></h3>
                                <p>Lorem ipsum dolor sit amet, consectetuer.</p>
<!--                                <div class="coursel_list">-->
<!--                                    <i class="a_heart"> </i>-->
<!--                                    <i class="like1"> </i>-->
<!--                                </div>-->
<!--                                <div class="coursel_list1">-->
<!--                                    <i class="a_twt"> </i>-->
<!--                                    <i class="a_fb"> </i>-->
<!--                                </div>-->
                                <div class="clear"></div>
                            </div>
                        </div>
                        <div class="<?php echo $boxRight?>">
                            <h4>Qualifications</h4>
                            <p>Lorem ipsum dolor consect adipiscing elit, diamnonu nibh euismod dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim </p>
                            <h4>Speciality</h4>
                            <p class="para1">diam nonummy euismod tincidunt ut laoreet dolore magna aliquam volutpat.</p>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
                <?php  $x++;    endforeach; ?>
<!--                <div class="col-md-6 sp_av">-->
<!--                    <div class="box2">-->
<!--                        <div class="box1_left">-->
<!--                            <img src="--><?php //echo base_url() ?><!--assets/website/images/cricket.png" class="img-responsive" alt=""/>-->
<!--                            <div class="desc2">-->
<!--                                <h3>Cricket<br><span class="m_text"></span></h3>-->
<!--                                <p>Lorem ipsum dolor sit amet, consectetuer.</p>-->
<!--                                <div class="coursel_list">-->
<!--                                    <i class="heart1"> </i>-->
<!--                                    <i class="like"> </i>-->
<!--                                </div>-->
<!--                                <div class="coursel_list1">-->
<!--                                    <i class="twt"> </i>-->
<!--                                    <i class="fb"> </i>-->
<!--                                </div>-->
<!--                                <div class="clear"></div>-->
<!--                            </div>-->
<!--                        </div>-->
<!--                        <div class="box1_right">-->
<!--                            <h4 class="right_text">Qualifications</h4>-->
<!--                            <p class="right_text1">Lorem ipsum dolor consect adipiscing elit, diamnonu nibh euismod dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim </p>-->
<!--                            <h4 class="right_text">Speciality</h4>-->
<!--                            <p class="right_text2">diam nonummy euismod tincidunt ut laoreet dolore magna aliquam volutpat.</p>-->
<!--                        </div>-->
<!--                        <div class="clear"></div>-->
<!--                    </div>-->
<!--                </div>-->
<!--                <div class="col-md-6 sp_av">-->
<!--                    <div class="box2">-->
<!--                        <div class="box1_left">-->
<!--                            <img src="--><?php //echo base_url() ?><!--assets/website/images/tennis.png" class="img-responsive" alt=""/>-->
<!--                            <div class="desc2">-->
<!--                                <h3>Tennis<br><span class="m_text"></span></h3>-->
<!--                                <p>Lorem ipsum dolor sit amet, consectetuer.</p>-->
<!--                                <div class="coursel_list">-->
<!--                                    <i class="heart1"> </i>-->
<!--                                    <i class="like"> </i>-->
<!--                                </div>-->
<!--                                <div class="coursel_list1">-->
<!--                                    <i class="twt"> </i>-->
<!--                                    <i class="fb"> </i>-->
<!--                                </div>-->
<!--                                <div class="clear"></div>-->
<!--                            </div>-->
<!--                        </div>-->
<!--                        <div class="box1_right">-->
<!--                            <h4 class="right_text">Qualifications</h4>-->
<!--                            <p class="right_text1">Lorem ipsum dolor consect adipiscing elit, diamnonu nibh euismod dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim </p>-->
<!--                            <h4 class="right_text">Speciality</h4>-->
<!--                            <p class="right_text2">diam nonummy euismod tincidunt ut laoreet dolore magna aliquam volutpat.</p>-->
<!--                        </div>-->
<!--                        <div class="clear"></div>-->
<!--                    </div>-->
<!--                </div>-->
<!--                <div class="col-md-6 sp_av">-->
<!--                    <div class="box1">-->
<!--                        <div class="box1_left">-->
<!--                            <img src="--><?php //echo base_url() ?><!--assets/website/images/soccer.png" class="img-responsive" alt=""/>-->
<!--                            <div class="desc1">-->
<!--                                <h3>Soccer <br><span class="m_text"></span></h3>-->
<!--                                <p>Lorem ipsum dolor sit amet, consectetuer.</p>-->
<!--                                <div class="coursel_list">-->
<!--                                    <i class="a_heart"> </i>-->
<!--                                    <i class="like1"> </i>-->
<!--                                </div>-->
<!--                                <div class="coursel_list1">-->
<!--                                    <i class="a_twt"> </i>-->
<!--                                    <i class="a_fb"> </i>-->
<!--                                </div>-->
<!--                                <div class="clear"></div>-->
<!--                            </div>-->
<!--                        </div>-->
<!--                        <div class="box1_right">-->
<!--                            <h4>Qualifications</h4>-->
<!--                            <p>Lorem ipsum dolor consect adipiscing elit, diamnonu nibh euismod dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim </p>-->
<!--                            <h4>Speciality</h4>-->
<!--                            <p class="para1">diam nonummy euismod tincidunt ut laoreet dolore magna aliquam volutpat.</p>-->
<!--                        </div>-->
<!--                        <div class="clear"></div>-->
<!--                    </div>-->
<!--                </div>-->
            </div>
        </div>
        <!-- end content-middle -->

        <hr style="color: #666">

        <div id="section3" class="row content-middle">
            <h3 class="m_2">Featured Fields</h3>
            <!-- start content-middle -->

            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <!-- Indicators -->
<!--                <ol class="carousel-indicators">-->
<!--                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>-->
<!--                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>-->
<!--                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>-->
<!--                </ol>-->

                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <?php $count = 0; ?>
                  <!-- <?php foreach ($featured_places as $key => $featured_place):?> -->
                    <div class="item <?php $count ++; if($count == 1){echo ' active';}else{echo 'item';}?>">
                        <div class="row">
                        <?php foreach ($featured_places as $i => $featured_place):?>
                            <?php   if (($i > 0) && ($i % 3 == 0)) { ?>
                            </div>
                            </div>
                            <div class="item">
                             <div class="row">
                            <?php } ?>
                        <div class="col-sm-4"><a href="#">
                            <ul class="spinning">
                                <li class="live"><?php echo $featured_place->name?></li>
                                <li class="room"></li>
                                <div class="clear"></div>
                            </ul>
                            <div class="view view-fifth">
                                <img src="<?php echo base_url() ?>assets/website/images/pi.jpg" class="img-responsive2"  onerror="imgError(this);"/>
                                <div class="mask">
                                   <ul class="desc_info">
                                       <li>Phone: <?php echo $featured_place->phone?> </li>
                                       <li>Hour Rate: <?php echo $featured_place->hour_rate?></li>
                                       <li>Open Time: <?php echo $featured_place->open_time?></li>
                                       <li>Close Time:  <?php echo $featured_place->close_time?></li>

                                   </ul>

                                </div>
                            </div>
                        </a>

                        </div>
                              
                        <?php endforeach;?>
                            </div>

                    </div>
                      

                

                  <!-- <?php endforeach;?> -->
                </div>
                

                <!-- <div class="carousel-inner">
  <div class="item active">
    <div class="row">
<?php foreach ($featured_places as $i => $featured_place) { ?>
<?php   if (($i > 0) && ($i % 4 == 0)) { ?>
    </div>
  </div>
  <div class="item">
    <div class="row">
<?php   } ?>
      <div class="col-lg-3">
        <img src="<?php echo base_url() ?>assets/website/images/pi.jpg" class="img-responsive2"  onerror="imgError(this);"/>
      </div>
<?php } ?>
    </div>
  </div>
</div> -->

                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>






            <div class="clear"></div>
        </div>

        <hr style="color: #666">

        </div>

   

    <div id="section4" class="map">
        <div id="map_wrapper">
            <div id="map_canvas" class="mapping"></div>
        </div>

        <div class="opening_hours">
            <ul   class="times">
                <h3>Activities <span class="opening"></span></h3>

                <script type="text/javascript">
                 $.ajax({
                url:'index.php/companies/get_all',
                type: 'GET',
                // data: map_c.serialize(),
                dataType: 'json',
                success:function(response) {
                    var $company = $('#dropdown');
                    console.log("here",mark);
                    console.log(response);
                    // alert("done");

               for (var i = 0; i < response.data.length; i++) {
                $company.append('<option  onclick="myClick(id);" id=' + (1+i) +  ' lng=' + response.data[i].longitude +' lat=' + response.data[i].latitude + '>' + response.data[i].name + '</option>');
            }

                }

            });
                </script>

              <select id="dropdown" size="10"  multiple="true"></select>
           
                    <!-- <li><i class="calender"> </i><span class="week">Saturday</span><div class="hours">h.6:00-h.24:00</div>  <div class="clear"></div></li> -->
                
    <div id="info"></div>
<!--
                <li value="1" > </i><span class="week">Monday</span><div class="hours">h.6:00-h.24:00</div><div class="clear"></div></li>
                <li value="2"> </i><span class="week">Sunday</span><div class="hours">h.6:00-h.24:00</div>  <div class="clear"></div></li>
                <li value="3"><i class="calender"> </i><span class="week">Tuesday</span><div class="hours">h.6:00-h.24:00</div>  <div class="clear"></div></li>
                <li value="4"><i class="calender"> </i><span class="week">Wednesday</span><div class="hours">h.6:00-h.24:00</div>  <div class="clear"></div></li>
                <li value="5"><i class="calender"> </i><span class="week">Thursday</span><div class="hours">h.6:00-h.24:00</div>  <div class="clear"></div></li>
                <li value="6"><i class="calender"> </i><span class="week">Friday</span><div class="hours">h.6:00-h.24:00</div>  <div class="clear"></div></li>
                <li value="7"><i class="calender"> </i><span class="week">Saturday</span><div class="hours">h.6:00-h.24:00</div>  <div class="clear"></div></li>
-->


                <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet</p>
                <h4>Enjoy it!</h4>
            </ul>
        </div>
    </div>


    <div class="about_banner_wrap">
        <h1 class="m_11">Contact</h1>
    </div>
    <div class="border"> </div>
    <div id="section5" class="rwo contact">
        <div class="container">
            <div class="col-md-8 contact-top">
                <h3>Send us a message</h3>
                <p class="contact_msg">Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy</p>

                <form method="post" action="<?php echo base_url()?>index.php/site/send_message" id="contactForm">
                    <div class="to">
                        <input type="text" class="text" id="Name" name="name" value="Name" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Name';}">
                        <input type="email" class="text" id="Email" name="email" value="Email" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Email';}" style="margin-left:20px">
                        <input type="text" class="text" id="Subject" name="subject" value="Subject" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Subject';}" style="margin-left:20px">
                    </div>
                    <div class="text">
                        <textarea id="Text" name="text" value="Message:" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Message';}">Message:</textarea>
                    </div>
                    <div class="form-submit1">
                        <input name="submit" type="submit" id="submit" value="Submit Your Message" onclick="save_email()"><br>
                        <p class="m_msg"> </p>
                    </div>
                    <div class="clear"></div>
                </form>
                <script>
                    function save_email() {
                        $("#contactForm").unbind('submit').bind('submit', function() {
                            var form = $(this);
                            $.ajax({
                                url: form.attr('action'),
                                type: form.attr('method'),
                                data: form.serialize(),
                                dataType: 'json',
                                success:function(response) {
                                    if (response.status === true) {
                                        $(".m_msg").html('<p class="m_msg">'+response.message+'</p>');
                                        document.getElementById("Name").value="Name";
                                        document.getElementById("Email").value="Email";
                                        document.getElementById("Subject").value="Subject ";
                                        document.getElementById("Text").value="Message";
                                    }else{
                                        $(".m_msg").html('<p class="m_msg">'+response.message+'</p>');
                                    }
                                }
                            });
                            return false;
                        });
                    }
                </script>
            </div>
            <div class="col-md-4 contact-top_right">
                <h3>contact info</h3>
                <p>diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis.</p>
                <ul class="contact_info">
                    <li><i class="mobile"> </i><span>+1-900-235-2456</span></li>
                    <li><i class="message"> </i><span class="msg">info(at)fieldium.com</span></li>
                </ul>
            </div>
        </div>
    </div>



    </div>

<div class="footer-bottom">
    <div class="container">
        <div class="row section group">
            <div class="col-md-4">
                <h4 class="m_7">Newsletter Signup</h4>
                <p class="m_8">Lorem ipsum dolor sit amet, consectetuer adipiscing elit sed diam nonummy.</p>
                <form class="subscribe">
                    <input type="text" value="Insert Email" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Insert Email';}">
                </form>
                <div class="subscribe1">
                    <a href="#">Submit Email<i class="but_arrow"> </i></a>
                </div>
            </div>
            <div class="col-md-4">
                <div class="f-logo">
                    <img src="<?php echo base_url() ?>assets/website/images/fieldium_logo_white.png" alt=""/>
                </div>
                <p class="m_9">Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis</p>
                <p class="address">Phone : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="m_10">(00) 222 666 444</span></p>
                <p class="address">Email : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="m_10">info[at]mycompany.com</span></p>
            </div>
            <div class="col-md-4">
                <div class="join_us">
                    <h3> Join Us Now  </h3>
                    <p>lius quod ii legunt saepius. Claritas est etiam processus dynamicus, qui sequitur mutationem consuetudium lectorum.</p>

                    <div class="subscribe1">
                        <a href="#">Join Us<i class="but_arrow2"> </i></a>
                    </div>
                </div>

                    <!--<div class="clear"></div>-->
                </div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
</div>
<div class="copyright">
    <div class="container">
        <div class="copy">
            <p>© 2017  <a href="#" target="_blank"> FIELDIUM</a></p>
        </div>
        <div class="social">
            <ul>
                <li class="facebook"><a href="#"><span> </span></a></li>
                <li class="twitter"><a href="#"><span> </span></a></li>
                <li class="pinterest"><a href="#"><span> </span></a></li>
                <li class="google"><a href="#"><span> </span></a></li>
                <li class="tumblr"><a href="#"><span> </span></a></li>
                <li class="instagram"><a href="#"><span> </span></a></li>
                <li class="rss"><a href="#"><span> </span></a></li>
            </ul>
        </div>
        <div class="clear"></div>
    </div>
</div>











</body>
</html>