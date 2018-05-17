
<div class="block_language">
    <button type="button" onclick="language('arabic')" >ar</button> 
    <button type="button" onclick="language('english')" >en</button>
</div>
<div id="sidebar">
    <ul>
        <?php if ($this->session->userdata('USER_ROLE') == ROLE::SUPPORT) { ?>
            <li><a href="<?php echo site_url() ?>/companies/companies_management/<?php echo UAE ?>"><?php echo $this->lang->line('companies'); ?></a></li>
            <li><a href="<?php echo site_url() ?>/bookings/bookings_management"><?php echo $this->lang->line('bookings'); ?></a></li>
            <li><a href="<?php echo site_url() ?>/amenities/amenities_management"><?php echo $this->lang->line('amenities'); ?></a></li>
            <li><a href="<?php echo site_url() ?>/games/games_management"><?php echo $this->lang->line('games'); ?></a></li>
            <li><a href="<?php echo site_url() ?>/areas/areas_management/<?php echo UAE ?>"><?php echo $this->lang->line('areas'); ?></a></li>
            <li><a href="<?php echo site_url() ?>/players/players_management"><?php echo $this->lang->line('players'); ?></a></li>
            <li><a href="<?php echo site_url() ?>/vouchers/vouchers_management"><?php echo $this->lang->line('vouchers'); ?></a></li>
            <li><a href="<?php echo site_url() ?>/offers/offers_management"><?php echo $this->lang->line('offers'); ?></a></li>
            <li><a href="<?php echo site_url() ?>/users/users_management"><?php echo $this->lang->line('users'); ?></a></li>

        <?php } else if ($this->session->userdata('USER_ROLE') == ROLE::ADMIN) { ?>
            <li><a href="<?php echo site_url() ?>/bookings/calendar"><?php echo $this->lang->line('calendar'); ?></a></li>
            <li><a href="<?php echo site_url() ?>/reports/show_report/reservations_report"><?php echo $this->lang->line('summary_report'); ?></a></li>
            <li><a href="<?php echo site_url() ?>/reports/show_report/declined_reservations"><?php echo $this->lang->line('declined_reservations'); ?></a></li>
            <li><a href="<?php echo site_url() ?>/reports/show_report/field_reservations"><?php echo $this->lang->line('approved_report'); ?></a></li>            
    <!--<li><a href="<?php echo site_url() ?>/bookings/company_pending_bookings">Pending Reservations</a></li>-->
            <li><a href="<?php echo site_url() ?>/companies/companies_management/<?php echo $this->user_country . "/edit/" . $this->session->userdata('company_id'); ?>"> <?php echo $this->lang->line('company_profile'); ?></a></li>
            <li><a href="<?php echo site_url() ?>/fields/fields_management/<?php echo $this->session->userdata('company_id'); ?>"><?php echo $this->lang->line('my_fields'); ?></a></li>
        <?php } ?>
        <li><a href="<?php echo site_url() ?>/users/edit_profile/edit/<?php echo $this->session->userdata('LOGIN_USER_ID') ?>"><?php echo $this->lang->line('user_profile'); ?></a></li>
        <li><a href="<?php echo site_url() ?>/users/web_logout"><?php echo $this->lang->line('sign_out'); ?></a></li>
    </ul>
</div>
<script>
    function language($lang) {
        var data = {
            'lang': $lang
        };
        var url = site_url + '/dashboard/language';
        $.ajax({
            url: url,
            type: 'POST',
            data: data,
            async: false,
            success: function (response) {
                console.log(response.data);
                location.reload();
            }
        });
    }
</script>