
<div  class="modal" id="offer_modal" >
    <div class="modal-dialog" style="width: 90%">
        <div class="modal-content">
            <div class="modal-header">
               
          
                <h3 id="offer_header" style="display: inline;">
              <?php echo $this->lang->line('new_offer'); ?>
                </h3>
               
                <button type="button" class="close" data-dismiss="modal" >&times</button>
            </div>
            <div class="modal-body" >
                <input type="hidden" id="offer_id" />
                <div class="row margin-top-5px">
                    <div class="col-md-2">
                  <b>
                  <?php
                       echo $this->lang->line('title_en');
                            ?>
                        </b>

                    </div>
                    <div class="col-md-3">
                        <input id="title_en" type="text" class="form-control" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-2">
                        <b>
                  <?php
                       echo $this->lang->line('title_ar');
                            ?>
                        </b>
                    </div>
                    <div class="col-md-3">
                        <input id="title_ar" type="text" class="form-control" aria-describedby="sizing-addon1">
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-md-2">
                      <b>
                            <?php
                            echo $this->lang->line('description_en');
                            ?>
                        </b>
                    </div>
                    <div class="col-md-3">
                        <textarea id="description_en" type="text" class="form-control" ></textarea>
                    </div>
                    <div class="col-md-2">
                <b>
                            <?php
                            echo $this->lang->line('description_ar');
                            ?>
                        </b>
                    </div>
                    <div class="col-md-3">
                        <textarea id="description_ar" type="text" class="form-control" ></textarea>
                    </div>
                </div>
                <br>
                <div class="row margin-top-5px">
                    <div class="col-md-2">
            <b>
                            <?php
                            echo $this->lang->line('start_date');
                            ?>
                        </b>
                    </div>
                    <div class="col-md-3">
                        <input id="start_date" type="text" class="form-control datepicker" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-2">
                        <b>
                            <?php
                            echo $this->lang->line('expiry_date');
                            ?>
                        </b>
                    </div>
                    <div class="col-md-3">
                        <input id="expiry_date" type="text" class="form-control datepicker" aria-describedby="sizing-addon1">
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-2">
                        <b>
                            <?php
                            echo $this->lang->line('set_of_hours');
                            ?>
                        </b>
                    </div>
                    <div class="col-md-2">
                        <input id="set_of_minutes" type="text" class="form-control" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-2">
                        <b>
                            <?php
                            echo $this->lang->line('reward_type');
                            ?>
                        </b>
                    </div>
                    <div class="col-md-2" style="padding: 0px">
                        <select id="voucher_type" class="form-control">
                            <option value="1"><?php echo $this->lang->line('discount'); ?></option>
                            <option value="2"><?php echo $this->lang->line('free_hours'); ?></option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <b>
                            <?php
                            echo $this->lang->line('reward_value');
                            ?>
                        </b>
                    </div>
                    <div class="col-md-2">
                        <input id="voucher_value" type="text" class="form-control" aria-describedby="sizing-addon1">
                    </div>
                </div>
                <hr>
                <div class="row margin-top-5px">
                    <div class="col-md-3">
                        <b>
                            <?php
                            echo $this->lang->line('starts_after');
                            ?>
                        </b>
                        
                        <input id="voucher_start_after" style="width:60% !important;display:inline"  type="text" class="form-control" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-3">
                        <b>
                            <?php echo $this->lang->line('validity_days'); ?>
                        </b>
                        <input id="valid_days" style="width:60% !important;display:inline"  type="text" class="form-control" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-3">
                        <b>
                            <?php
                            echo $this->lang->line('from');
                            ?>
                        </b>
                        <input id="voucher_from_hour" style="width:60% !important;display:inline"  type="text" class="form-control timepicker" aria-describedby="sizing-addon1">
                    </div>
                    <div class="col-md-3">
                        <b>
                            <?php
                            echo $this->lang->line('to');
                            ?>
                        </b>
                        <input id="voucher_to_hour" style="width:60% !important;display:inline"  type="text" class="form-control timepicker" aria-describedby="sizing-addon1">
                    </div>
                </div>
                <hr>
                <div class="row margin-top-5px">
                    <div class="col-md-2">
                        <b>
                            <?php
                            echo $this->lang->line('country');
                            ?>
                        </b>
                    </div>
                    <div class="col-md-2" style="padding: 0px">

                        <select id="Country" class="form-control">
                  
                            <option value="1">
                                                  <?php
                 echo $this->lang->line('uae');
                 ?>
                            </option>
                            <option value="2">
                                                  <?php
                 echo $this->lang->line('syria');
                 ?>
                            </option>
                        
                        </select>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-md-2">
                        <b>
                                              <?php
                 echo $this->lang->line('game');
                 ?>
                        </b>
                    </div>
                    <div class="col-md-10">
                        <label for="all_games">
                            <input id="all_games" type="checkbox"/> 
                                      <?php
                 echo $this->lang->line('all_games');
                 ?>
                        </label><br>
                        <select id="games" class="js-example-basic-multiple js-states form-control" multiple="multiple">

                        </select>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-md-2">
             
                        <label>
                                <?php
                 echo $this->lang->line('companies');
                 ?>
                        </label>
                    </div>
                    <div class="col-md-10">
                        <label for="all_fields">
                            <input id="all_fields" type="checkbox"/>
                                <?php
                                echo $this->lang->line('all_fields');
                                ?>
                        </label><br>
                        <select id="companies" class="js-example-basic-multiple js-states form-control" multiple="multiple">

                        </select>
                    </div>
                </div>
                <br>
             
                    <div class="modal-footer">
                        <div class="col-md-6 left">
                            <button class="btn btn-info create-btn" onclick='save_offer()'> 
      <?php
                                echo $this->lang->line('save');
                                ?>
                            </button>
                        </div>

                        <div class="col-md-6 right">
                            <button class="btn btn-warning" class="close" data-dismiss="modal">
                                       <?php
                                echo $this->lang->line('close');
                                ?>
                            </button>
                        </div>
                    </div>
      
            </div>
        </div>
    </div>
</div>