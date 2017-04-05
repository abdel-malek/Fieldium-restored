

<div id="sidebar">
    <ul>
        <?php if ($this->session->userdata('USER_ROLE') == ROLE::SUPPORT) { ?>
            <li><a href="<?php echo site_url() ?>/companies/companies_management">Companies</a></li>
            <li><a href="<?php echo site_url() ?>/bookings/bookings_management">Bookings</a></li>
            <li><a href="<?php echo site_url() ?>/amenities/amenities_management">Amenities</a></li>
            <li><a href="<?php echo site_url() ?>/games/games_management">Games</a></li>
            <li><a href="<?php echo site_url() ?>/areas/areas_management">Areas</a></li>
            <li><a href="<?php echo site_url() ?>/players/players_management">Players</a></li>
            <li><a href="<?php echo site_url() ?>/users/users_management">Users</a></li>

        <?php } else if ($this->session->userdata('USER_ROLE') == ROLE::ADMIN) { ?>
            <li><a href="<?php echo site_url() ?>/bookings/calendar">Calendar</a></li>
            <li><a href="<?php echo site_url() ?>/reports/show_report/reservations_report">Summary Report</a></li>
            <li><a href="<?php echo site_url() ?>/reports/show_report/declined_reservations">Declined Reservations</a></li>
            <li><a href="<?php echo site_url() ?>/reports/show_report/field_reservations">Approved Report</a></li>            
    <!--<li><a href="<?php echo site_url() ?>/bookings/company_pending_bookings">Pending Reservations</a></li>-->
            <li><a href="<?php echo site_url() ?>/companies/companies_management/edit/<?php echo $this->session->userdata('company_id'); ?>"> Company Profile</a></li>
            <li><a href="<?php echo site_url() ?>/fields/fields_management/<?php echo $this->session->userdata('company_id'); ?>">My Fields</a></li>
        <?php } ?>
        <li><a href="<?php echo site_url() ?>/users/edit_profile/edit/<?php echo $this->session->userdata('LOGIN_USER_ID') ?>">User Profile</a></li>
        <li><a href="<?php echo site_url() ?>/users/web_logout">Sign Out</a></li>
    </ul>
</div>
