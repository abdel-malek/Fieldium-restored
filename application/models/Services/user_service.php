<?php

/**
 * @author Amal Abdulraouf
 */
class user_service extends CI_Model {

    public function __construct() {
        parent::__construct();
        $this->load->model('DataSources/user');
    }

    public function create(
    $username, $name, $password, $phone, $email, $company_id, $role_id
    ) {
        $user_id = $this->user->create(
                array(
                    'username' => $username,
                    'name' => $name,
                    'password' => $password,
                    'phone' => $phone,
                    'email' => $email,
                    'company_id' => $company_id,
                    'role_id' => $role_id
                )
        );
        $user = $this->get($user_id);
        return $user;
    }

    public function get($id, $lang = "en") {
        $res = $this->user->get($id, $lang);
        if (!$res)
            throw new User_Not_Found_Exception($lang);
        if ($res->profile_picture != "" && $red->profile_picture != null)
            $res->profile_picture_url = base_url() . UPLOADED_IMAGES_PATH_URL . $res->profile_picture;
        return $res;
    }

    public function update(
    $user_id, $name, $phone, $email, $role_id, $lang
    ) {
        $this->get($user_id, $lang);
        $user = $this->user->update($user_id, array(
            'name' => $name,
            'phone' => $phone,
            'email' => $email,
            'role_id' => $role_id
                )
        );
        $user = $this->get($user_id, $lang);
        return $user;
    }

    public function login($username, $password) {

        $user = $this->user->login($username, $password);
        if ($user != NULL) {
            $newdata = array(
                'PHP_AUTH_USER' => $username,
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

    public function logout() {
        $this->session->sess_destroy();
    }

    public function active($user_id) {
        $this->get($user_id);
        $this->user->update($user_id, array(
            'active' => 1
        ));
        $user = $this->get($user_id);
        return $user;
    }

    public function deactive($user_id) {
        $user = $this->get($user_id);
        $this->user->update($user_id, array(
            'active' => 0
        ));
        $user = $this->get($user_id);
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

    public function check_password($user_id, $str) {
        $user = $this->user->check_password($user_id, md5($str));
        if(!$user)
            return false;
        else
            return true;
    }
    
    public function change_password($user_id, $new_password, $lang) {
        $user = $this->get($user_id);

        $this->user->update($user_id, array(
            'password' => $new_password
        ));
        $user = $this->get($user_id, $lang);
        return $user;
    }

    public function get_company_users($company_id) {
        return $this->db->get_where('user', array('company_id' => $company_id, 'active' => 1, 'role_id' => ROLE::ADMIN))->result();
    }
    
    public function get_support_users() {
        return $this->db->get_where('user', array('active' => 1, 'role_id' => ROLE::SUPPORT))->result();
    }

    public function save_token(
    $user_id, $token, $os, $lang
    ) {
        $user = $this->get($user_id, $lang);
        $tok = $this->user->check_token($user_id, $token);
        if (!$tok) {
            $this->user->save_token(array(
                'user_id' => $user_id,
                'token' => $token,
                'os' => $os
            ));
        }
        $user = $this->get($user_id, $lang);
        return $user;
    }

    public function get_tokens($user_id) {
        return $this->db->get_where('tokens', array('user_id' => $user_id))->result();
    }

    public function delete_token($token) {
        $this->db->where('token', $token)
                ->delete('tokens');
    }

}

?>
