<?php

/**
 * @author Farah Etmeh
 */
class user_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/user');
    }

    public function login($email, $password) {

        $user = $this->user->login($email, $password);
        if ($user != NULL) {
            $newdata = array(
                'PHP_AUTH_USER' => $email,
                'LOGIN_USER_ID' => $user->user_id,
                'USERNAME' => $user->name,
                'USER_ROLE' => $user->role_id,
                'PHP_AUTH_PW' => $password
            );
            $this->session->set_userdata($newdata);
            return $user;
        } else {
            throw new Invalid_Login_Exception();
        }
    }
    
     /////////////////////////////////////////////////////////////
    public function register($phone, $token) {

       $user = $this->user->get_by_phone($phone);
       if ($user) {
           $user_id = $user->user_id;

           $code = $this->generate_activation_code();
           $this->send_sms($phone, $code);
           $this->user->update($user->user_id, array(
               'phone' => $phone,
               'token' => $token,
               'activation_code' => $code
           ));
       } else {
           $code = $this->generate_activation_code();
           $this->send_sms($phone, $code);
           $user_id = $this->user->register(array(
               'phone' => $phone,
               'token' => $token,
               'activation_code' => $code
           ));
       }
       $user = $this->user->get($user_id);
       return $user;
   }

   private function send_sms($mobile, $otp) {

       $message = 'Your activation code is:' . $otp;
      // $message = $this->ToUnicode($message);
       //var_dump($message); die();
       $_url = 'http://188.160.0.43/general/ConcatenatedSender.asp?User=Nahasbeeorder&Pass=Bee734&From=Limico&Gsm=963' . $mobile . '&Msg=' . $message . '&Lang=1';
       $_url = preg_replace("/ /", "%20", $_url);
       $result = file_get_contents($_url);
       return $result;
   }
   
    
     public function verify( $phone, $device_id) {
        $user= $this->user->verify( $phone , $device_id );
        if(!$user){
            throw  new Invalid_Activation_Code_Exception (); 
        }
        
        $this->user->update($user->user_id , array(
            'active' => 1
        ));
        $user= $this->user->get($user->user_id);
        return $user;
    }
    
    public function update ($user_id , $name,$email,$address){
        $this->user->update($user_id,array(
            'name' => $name,
            'email' => $email,
            'address' => $address
        ));
        $user= $this->user->get($user_id);
        return $user;
    }
    
    public function refresh_token($user_id, $token){
        $this->user->update($user_id,array(
            'token' => $token
        ));
        $user= $this->user->get($user_id);
        return $user;
    }
    
    

    function add_customer($name, $email, $password) {
        $user_id = $this->user->register(array(
            'name' => $name,
            'email' => $email,
            'password' => $password,
            'user_type_id' => "3",
            'active' => 1
        ));
        $user = $this->user->get($user_id);
        // $this->send_confirmation_email($email,"");
        return $user;
    }

    public function send_confirmation_email($email, $activation_code) {
        $activation_link = base_url("index.php/users/confirm_registration?email=" . $email . "&key=" . $activation_code);
        $message_body = "Welcome to BlueBrand .<br> Please click below on the link to activate your account.<br><br>" . $activation_link;
        $this->load->library('email');
        $this->email->from('tradinos_test@nova-ue.net', 'noreply');
        $this->email->to($email);
        $this->email->subject('BlueBrand - Activation Email');
        $this->email->message($message_body);
        $this->email->send();
    }

    public function send_reset_password_request($email) {
        $user = $this->user->get_by_mail($email);
        if (!$user)
            throw new User_Not_Found_Exception();
        $this->load->helper('code_generater_helper');
        $activation_code = generate_activation_code();
        
         $this->user->update($user->user_id, array(
            'activation_code' => $activation_code
        ));
        
        $this->send_reset_password_email($user->user_id, $user->email, $activation_code);
    }

    public function check_activation_code($user_id, $activation_code) {
        $user = $this->user->get_all_info($user_id);
        if ($user->activation_code != $activation_code)
            throw new Invalid_Activation_Code_Exception ();
        else 
            return true ; 
        
    }

        
    public function reset_password($user_id, $activation_code, $new_password) {
        $user = $this->user->get_all_info($user_id);
        if ($user->activation_code != $activation_code)
            throw new Invalid_Activation_Code_Exception ();

        $this->user->update($user_id, array(
            'password' => md5($new_password), 
            'activation_code'=>$activation_code 
        ));
        $user = $this->user->get($user_id);
        return $user;
    }

    public function send_reset_password_email($user_id, $email, $activation_code) {
        $activation_link = base_url("index.php/users/confirm_registration?user_id=" . $user_id . "&key=" . $activation_code);
         $message_body = "Welcome to BlueBrand .<br> Please click below on the link to reset your password.<br><br>" . $activation_link;
        $this->load->library('email');
        $this->email->from('tradinos_test@nova-ue.net', 'noreply');
        $this->email->to($email);
        $this->email->subject('BlueBrand - Reset Password');
        $this->email->message($message_body);
        $this->email->send();
    }

    public function logout() {
        $this->session->sess_destroy();
    }

    public function get($id) {
        $res = $this->user->get($id);
        if (!$res)
            throw new User_Not_Found_Exception ();
        return $res;
    }

    public function get_all_info($id) {
        $res = $this->user->get_all_info($id);
        if (!$res)
            throw new User_Not_Found_Exception ();
        return $res;
    }
    
    
    public function get_customers() {
        $res = $this->user->get_customers();
        return $res;
    }

    public function update_info($user_id, $name,$phone,$address) {
        $this->get($user_id);
        $this->user->update($user_id, array(
                                            'name' => $name,
                                            'phone' => $phone,
                                            'address' => $address,
                                            ));
        $user = $this->user->get_all_info($user_id);
        return $user;
    }
    
    
    public function active($user_id) {
        $this->get($user_id);
        $this->user->update($user_id, array(
            'active' => true
        ));
        $user = $this->get($user_id);
        return $user;
    }

    public function deactive($user_id) {
        $user = $this->get($user_id);
        $this->user->update($user_id, array(
            'active' => !$user->active
        ));
        $user = $this->get($user_id);
        return $user;
    }

    public function change_password($user_id, $current_password, $new_password) {
        $user = $this->get_all_info($user_id);

        if ($user->password != md5($current_password))
            throw new Invalid_Password_Exception ();
        $this->user->update($user_id, array(
            'password' => md5($new_password)
        ));
        $user = $this->get_all_info($user_id);
        return $user;
    }
    
     function generate_activation_code () {
        
    $digites = '0123456789';
    $randomString =  $digites[rand(0, strlen($digites) - 1)]
                    .$digites[rand(0, strlen($digites) - 1)]
                    .$digites[rand(0, strlen($digites) - 1)]
                    .$digites[rand(0, strlen($digites) - 1)]
                    .$digites[rand(0, strlen($digites) - 1)]
                    .$digites[rand(0, strlen($digites) - 1)]
                    ;
                    
    return $randomString;
}

}

?>
