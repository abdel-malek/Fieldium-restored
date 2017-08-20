package com.bluebrand.fieldiumadmin.controller;

import com.bluebrand.fieldiumadmin.Model.User;

public interface CurrentUser {

	void Save(User customer) ;
	Boolean IsLogged() ;
	User Get();
	void SignOut();
	
	
}
