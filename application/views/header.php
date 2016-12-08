<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script type="text/javascript" src="<?php echo base_url('assets/js/jquery.min.js') ?>"></script>
    <title>Fieldium</title>
    <link rel="shortcut icon" href="<?php echo base_url("assets/images/logo.png"); ?>" type="image/x-icon" />
    <link type="text/css" rel="stylesheet" href="<?php echo base_url('assets/css/scrolltabs.css') ?>" />
    <link type="text/css" rel="stylesheet" href="<?php echo base_url('assets/css/bootstrap.css') ?>" />
    <link type="text/css" rel="stylesheet" href="<?php echo base_url('assets/css/catalog.css') ?>" />  
    <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/loader.css') ?>" />
    <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/multiple-select.css') ?>" />
    <?php
    if (!empty($output)) {
        foreach ($css_files as $file):
            ?>
            <link type="text/css" rel="stylesheet" href="<?php echo $file; ?>" />
        <?php endforeach; ?>
        <?php foreach ($js_files as $file): ?>
            <script src="<?php echo $file; ?>"></script>
            <?php
        endforeach;
    }
    ?>

    <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/sideMenu.css') ?>" />
    <link rel="stylesheet" type="text/css" href="<?php echo base_url('assets/css/style.css') ?>" />
    <style type='text/css'>
        body
        {
            font-family: Arial;
            font-size: 14px;
        }
        a {
            color: blue;
            text-decoration: none;
            font-size: 14px;
        }
        a:hover
        {
            text-decoration: underline;
        }
    </style>
</head>

