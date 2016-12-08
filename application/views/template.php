<!DOCTYPE html>
<html>
    <?php 
    $this->load->view('header');
    if(!isset($output)) $output = '';
    if(!isset($data)) $data = '';
    $this->load->view($view, array('output'=>$output, 'data'=>$data));
    $this->load->view('footer');
    ?>
</html>
