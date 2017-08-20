package com.bluebrand.fieldiumadmin.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.bluebrand.fieldiumadmin.Model.AppInfo;
import com.bluebrand.fieldiumadmin.Model.User;

public class CurrentAndroidUser implements CurrentUser {

	Context context ; 
	public CurrentAndroidUser(Context context) {
		this.context = context ; 
	}
	
	public static CurrentAndroidUser getInstance(Context  context) {
		return new CurrentAndroidUser(context);
	}
	
	@Override
	public void Save( User customer) {
		SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
		Editor editor = preferences.edit(); 
		editor.putInt("id", customer.getId());
		editor.putInt("company_id", customer.getCompany_id());
		editor.putString("name", customer.getName());
		editor.putString("username", customer.getUsername());
		editor.putString("password", customer.getPassword());
		editor.putString("profile_picture",customer.getProfile_picture());

		editor.commit();
	}

	@Override
	public Boolean IsLogged() {
		SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
		return !(preferences == null || preferences.getInt("id", -1) == -1);
	}

	@Override
	public User Get() {
		SharedPreferences preferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
		if (preferences == null || preferences.getInt("id",-1)==-1)
			return null;
		else {
			User user = new User();
			user.setId(preferences.getInt("id", -1));
			user.setCompany_id(preferences.getInt("company_id", -1));
			user.setName(preferences.getString("name", ""));
			user.setUsername(preferences.getString("username", ""));
			user.setPassword(preferences.getString("password", ""));
			user.setProfile_picture(preferences.getString("profile_picture", ""));

			return user;
		}
	}
	public AppInfo GetAppInfo() {
		SharedPreferences preferences = context.getSharedPreferences("appInfo", Context.MODE_PRIVATE);
		if (preferences == null)
			return null;
		else {
			AppInfo appInfo = new AppInfo();
			appInfo.setPhone(preferences.getString("phone", ""));
			appInfo.setMobile(preferences.getString("mobile",""));
			appInfo.setEmail(preferences.getString("email", ""));
			return appInfo;
		}
	}


	public void SaveAppInfo( AppInfo appInfo) {
		SharedPreferences preferences = context.getSharedPreferences("appInfo", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("phone", appInfo.getPhone());
		editor.putString("mobile", appInfo.getMobile());
		editor.putString("email", appInfo.getEmail());

		editor.commit();
	}
	@Override
	public void SignOut() {
		SharedPreferences mSharedPreference = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
		Editor editor = mSharedPreference.edit();
		editor.clear();
		editor.commit();
	}

	
}
