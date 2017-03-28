

<div id="sidebar">
    <ul>
        <li><a href="<?php echo site_url() ?>/companies/companies_management">Companies</a></li>
        <li><a href="<?php echo site_url() ?>/bookings/bookings_management">Bookings</a></li>
        <li><a href="<?php echo site_url() ?>/amenities/amenities_management">Amenities</a></li>
        <li><a href="<?php echo site_url() ?>/games/games_management">Games</a></li>
        <li><a href="<?php echo site_url() ?>/areas/areas_management">Areas</a></li>
        <li><a href="<?php echo site_url() ?>/players/players_management">Players</a></li>
        <li><a href="<?php echo site_url() ?>/users/users_management">Users</a></li>
        <li><a href="<?php echo site_url() ?>/users/edit_profile/edit/<?php echo $this->session->userdata('LOGIN_USER_ID')?>">Edit Profile</a></li>
        <li><a href="<?php echo site_url() ?>/users/web_logout">Sign Out</a></li>
    </ul>
</div>
