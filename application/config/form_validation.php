<?php

$config = array(
    'login' => array(
        array(
            'field' => 'login_email',
            'label' => 'Email',
            'rules' => 'trim|required|valid_email'
        ),
        array(
            'field' => 'login_password',
            'label' => 'Password',
            'rules' => 'trim|required'
        ),
    ),
    'register' => array(
        array(
            'field' => 'register_email',
            'label' => 'Email',
            'rules' => 'trim|required|valid_email|callback__is_unique_email'
        ),
        array(
            'field' => 'register_password',
            'label' => 'Password',
            'rules' => 'trim|required|min_length[6]'
        ),
        array(
            'field' => 'confirm_password',
            'label' => 'Confirm Password',
            'rules' => 'trim|required|matches[register_password]'
        ),
        array(
            'field' => 'organization_name',
            'label' => 'Organization Name',
            'rules' => 'trim'
        ),
        array(
            'field' => 'first_name',
            'label' => 'First Name',
            'rules' => 'trim|required'
        ),
        array(
            'field' => 'last_name',
            'label' => 'Last Name',
            'rules' => 'trim|required'
        ),
    ),
    'db_object' => array(
        array(
            'field' => 'name',
            'label' => 'Name',
            'rules' => 'trim|required'
        ),
        array(
            'field' => 'body',
            'label' => 'Body',
            'rules' => 'trim|required'
        )
    )
);

