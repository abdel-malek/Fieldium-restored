<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script type="text/javascript" src="<?php echo base_url('assets/js/jquery.min.js') ?>"></script>
        <title>Fieldium</title>
        <link rel="shortcut icon" href="<?php echo base_url("assets/images/logo.png"); ?>" type="image/x-icon" />
        <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/index_style.css') ?>" />     
    </head>
    <body>
        <div class="login-page" style="text-align: center">
            <img src="<?php echo base_url('assets/images/logo.png') ?>" /> 
            <div class="form">

                <form id="login_form" class="login-form">
                    <input type="text" name="username" placeholder="email" required="" />
                    <input type="password" name="password" placeholder="password" required="" />
                    <input type="submit" value="login" />
                    <div class="error"></div>
                </form>
            </div>
        </div>
        <script type="text/javascript">
            var site_url = "<?php echo site_url() ?>";
            $('#login_form').submit(function (evnt) {
                evnt.preventDefault();
                evnt.stopImmediatePropagation();
                $(".error").html("");
                var url = site_url + "/users/web_login";
                $.ajax({
                    type: "POST",
                    url: url,
                    dataType: "json",
                    data: $(this).serialize(),
                    success: function (data) {
                        if (data["status"] == true)
                            window.location = site_url;
                        else
                            $('.error').html(data["message"]);
                    },
                    error: function (response) {

                    }
                });
            });
        </script>
    </body>

</html>
