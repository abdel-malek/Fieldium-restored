package com.bluebrand.fieldiumadmin.View;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.bluebrand.fieldiumadmin.Model.AppInfo;
import com.bluebrand.fieldiumadmin.Model.RefusedMsg;
import com.bluebrand.fieldiumadmin.controller.ReservationController;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tradinos.core.network.Code;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.SuccessCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bluebrand.fieldiumadmin.Model.Amenity;
import com.bluebrand.fieldiumadmin.Model.Area;
import com.bluebrand.fieldiumadmin.Model.Company;
import com.bluebrand.fieldiumadmin.Model.Field;
import com.bluebrand.fieldiumadmin.Model.Game;
import com.bluebrand.fieldiumadmin.Model.User;
import com.bluebrand.fieldiumadmin.MyApplication;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.controller.CurrentAndroidUser;
import com.bluebrand.fieldiumadmin.controller.FieldController;
import com.bluebrand.fieldiumadmin.controller.UserController;

public class LoginActivity extends MasterActivity {

	private EditText editText_email;
	private EditText editText_password;
	private Intent intent;
	private boolean connected;
	CurrentAndroidUser user;
	Controller controller;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);
		BitmapFactory.Options myOptions = new BitmapFactory.Options();
		myOptions.inDither = true;
		myOptions.inScaled = false;
		myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
		myOptions.inDither = false;
		myOptions.inPurgeable = true;
		Bitmap preparedBitmap = BitmapFactory.decodeResource(getResources(),
				R.mipmap.background, myOptions);
		Drawable background = new BitmapDrawable(preparedBitmap);
		((LinearLayout) findViewById(R.id.login_background))
				.setBackgroundDrawable(background);
		editText_email=(EditText)findViewById(R.id.editText_email);
		editText_password=(EditText)findViewById(R.id.editText_password);
		user=new CurrentAndroidUser(this);
		connected=isNetworkAvailable();
		checkPlayServices();
		controller= new Controller(this, new FaildCallback() {
			@Override
			public void OnFaild(Code errorCode, String Message) {
				if (findViewById(R.id.parentPanel) != null)
					Snackbar.make(findViewById(R.id.parentPanel), Html.fromHtml(Message), Snackbar.LENGTH_LONG).show();
				else
					Toast.makeText(getApplicationContext(), Html.fromHtml(Message), Toast.LENGTH_LONG).show();
				HideProgressDialog();
			}
		});

		if(user.IsLogged())
		{
			editText_email.setText(user.Get().getUsername());
			editText_password.setText(user.Get().getPassword());
			if(connected) {
				ShowProgressDialog();
				AfterLogin(user.Get());
			}
		}

	}

	@Override
	public void getData() {

	}

	@Override
	public void showData() {

	}

	@Override
	void assignUIReferences() {

	}

	@Override
	void assignActions() {

	}

	@Override
	public void onClick(View v) {

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	private Boolean ValidLoginInput(String email, String password) {
		if (TextUtils.isEmpty(email)) {
			showMessageInSnackbar(getResources().getString(R.string.error_invalid_email));
			return false;
		} else if (TextUtils.isEmpty(password)) {
			showMessageInSnackbar(getResources().getString(R.string.error_incorrect_password));
			return false;
		}
		return true;
	}


	public  void onLoginClick(View view) {
		final String email = editText_email.getText().toString();
		final String password = editText_password.getText().toString();
		connected=isNetworkAvailable();
		if (!ValidLoginInput(email, password)) {
			return;
		}
		connected=isNetworkAvailable();
		if (connected&&checkPlayServices()) {
			if(user.Get()!=null) {
				if (password.equals(user.Get().getPassword())) {
					ShowProgressDialog();
					AfterLogin(user.Get());
				}
				else {
					ShowProgressDialog();
					new UserController(controller).Login(email, password,
							new SuccessCallback<User>() {
								@Override
								public void OnSuccess(User result) {
									AfterLogin(result);
								}
							});
				}
			}
			else {
				ShowProgressDialog();
				new UserController(controller).Login(email, password,
						new SuccessCallback<User>() {
							@Override
							public void OnSuccess(User result) {
								AfterLogin(result);
							}
						});
			}
		}
		else
		{
			showMessageInSnackbar(R.string.connection_problem);
		}
	}

	void AfterLogin(final User user) {
		new CurrentAndroidUser(this).Save(user);
		intent = new Intent(this, MainActivity.class);
		FieldController.getInstance(controller).getFields(user.getCompany_id(), new SuccessCallback<List<Field>>() {
			@Override
			public void OnSuccess(List<Field> result) {
				((MyApplication)getApplication()).setMyFields(result);
				FieldController.getInstance(controller).getAmenities(new SuccessCallback<List<Amenity>>() {
					@Override
					public void OnSuccess(List<Amenity> result) {
						((MyApplication)getApplication()).setMyAmenities(result);
						FieldController.getInstance(controller).getGames(new SuccessCallback<List<Game>>() {
							@Override
							public void OnSuccess(final List<Game> result) {
								((MyApplication)getApplication()).setMyGames(result);
									final Map<Integer,Bitmap> bitmaps=new HashMap<Integer, Bitmap>();
									for (int i = 0; i < result.size(); i++) {
										final Game game=result.get(i);
										Picasso.with(LoginActivity.this)
												.load(game.getImage().getUrl())
												.into(new Target() {
													@Override
													public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
														bitmaps.put(game.getGame_type_id(),bitmap);
														if(bitmaps.size()==result.size()){
															((MyApplication)getApplication()).setGamesBitmap(bitmaps);
															FieldController.getInstance(controller).getCompanyInfo(user.getCompany_id(), new SuccessCallback<Company>() {
																@Override
																public void OnSuccess(Company result) {
																	((MyApplication)getApplication()).setMyCompanyInfo(result);
																	UserController.getInstance(controller).getInfo(new SuccessCallback<AppInfo>() {
																		@Override
																		public void OnSuccess(AppInfo result) {
																			new CurrentAndroidUser(LoginActivity.this).SaveAppInfo(result);
																			ReservationController.getInstance(controller).getRefusedMsgs(new SuccessCallback<List<RefusedMsg>>() {
																				@Override
																				public void OnSuccess(List<RefusedMsg> result) {
																					((MyApplication)getApplication()).setRefusedMsgs(result);
																					FieldController.getInstance(controller).getAreas(new SuccessCallback<List<Area>>() {
																						@Override
																						public void OnSuccess(List<Area> result) {
																							((MyApplication)getApplication()).setMyAreas(result);
																							try {
																								final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
																								UserController.getInstance(controller).updateToken(user.getId(),refreshedToken==null?"1":refreshedToken, new SuccessCallback<String>() {
																									@Override
																									public void OnSuccess(String result) {
																										SharedPreferences preferences = getSharedPreferences("UserToken", Context.MODE_PRIVATE);
																										SharedPreferences.Editor editor = preferences.edit();
																										editor.putString("token", refreshedToken);
																										editor.commit();
																										HideProgressDialog();
																										startActivity(intent);
																										finish();
																									}
																								});
																							} catch (Exception e) {
																								HideProgressDialog();
																								showMessageInToast("Error with UserToken");
																							}
																						}
																					});
																				}
																			});

																		}
																	});
																}
															});
														}
													}

													@Override
													public void onBitmapFailed(Drawable errorDrawable) {

													}

													@Override
													public void onPrepareLoad(Drawable placeHolderDrawable) {

													}
												});
									}

							}
						});
					}
				});

			}
		});
	}



	private void sendToken(){

	}

}
