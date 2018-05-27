<script type="text/javascript" src="<?php echo base_url('assets/js/bootstrap.js') ?>"></script>
<script type="text/javascript" src="<?php echo base_url('assets/js/jquery.touchSwipe.min.js') ?>"></script>
<script type="text/javascript">
    $(window).load(function () {
        $("#sidebar-toggle").click(function () {
            var toggle_el = $(this).data("toggle");
            $(toggle_el).toggleClass("open-sidebar");
            lang = "<?php echo $this->session->userdata('lang') ?>";
//alert("<?php echo $this->session->userdata('lang') ?>");
            if (lang == "arabic") {
                if ($('#sidebar').css("right") == "0px") {
                    $('#sidebar').animate(
                            {
                                right: "-240px"
                            },
                            500
                            );
                } else {
                    $('#sidebar').animate(
                            {
                                right: "0px"
                            },
                            900
                            );
                }
            } else {
                if ($('#sidebar').css("left") == "0px") {
                    $('#sidebar').animate(
                            {
                                left: "-240px"
                            },
                            500
                            );
                } else {
                    $('#sidebar').animate(
                            {
                                left: "0px"
                            },
                            900
                            );
                }
            }
        });
        $(".swipe-area").swipe({
            swipeStatus: function (event, phase, direction, distance, duration, fingers)
            {
lang = "<?php echo $this->session->userdata('lang') ?>";
                if (lang == "arabic") {
                    if (phase == "move" && direction == "left") {
                        $(".container").addClass("open-sidebar");
                        $('#sidebar').animate(
                                {
                                    right: "0px"
                                },
                                500
                                );
                        return false;
                    }
                    if (phase == "move" && direction == "right") {
                        $(".container").removeClass("open-sidebar");
                        $('#sidebar').animate(
                                {
                                    right: "-240px"
                                },
                                500
                                );
                        return false;
                    }
                } else {
                    if (phase == "move" && direction == "right") {
                        $(".container").addClass("open-sidebar");
                        $('#sidebar').animate(
                                {
                                    left: "0px"
                                },
                                500
                                );
                        return false;
                    }
                    if (phase == "move" && direction == "left") {
                        $(".container").removeClass("open-sidebar");
                        $('#sidebar').animate(
                                {
                                    left: "-240px"
                                },
                                500
                                );
                        return false;
                    }
                }
            }
        });
    });

</script>

