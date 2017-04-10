
<div id="incoming_booking_Sidenav" class="sidenav">
<!--    <li href="javascript:void(0)" onclick="closeNav();"
        class="closebtn icon-align-justify ui-tabs-active ui-state-active" 
        style="color: white;font-size: 30px;text-align: right;float:right"></li>-->
    <button type="button" class="close close_pending" onclick="closeNav();" >&times</button>
    <div style="clear: both"></div>
    <h2 id="incoming_header">
        Incoming Bookings
    </h2>
    <div id="pending_bookings_list">
    </div>
</div>
<!--<span class="badge" 
      style="    
      color: #23b8d1; 
      font-size: 30px;
      text-align: right;
      margin-right: -8px;
      margin-top: -10px;
      " 
      >10</span>-->
<div class="item" onclick="openNav();">
    <a href="#">
        <span class="badge" id="badge">0</span>
        <img id="fieldium_logo" src="<?php echo base_url()?>assets/images/logo.png" width="30px" height="45px"  alt="" />
    </a>
</div>
<div style="clear: both"></div>
