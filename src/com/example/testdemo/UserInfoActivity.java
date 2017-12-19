package com.example.testdemo;

import org.crazyit.net.GetPostUtil;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoActivity extends Activity {
	private EditText Et_User_Name,Et_User_Sex,Et_User_Col,Et_User_Like,Et_User_Pho;
	private TextView tv_User_Id;
	private Button btn_change_UserInfo,btn_change_User;
	private Button btn_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		Intent myIntent = this.getIntent();
		final Bundle myBundle = myIntent.getExtras();
		tv_User_Id=(TextView)findViewById(R.id.tv_User_Id);
		Et_User_Name=(EditText)findViewById(R.id.Et_User_Name);
		Et_User_Sex=(EditText)findViewById(R.id.Et_User_Sex);
		Et_User_Col=(EditText)findViewById(R.id.Et_User_Col);
		Et_User_Like=(EditText)findViewById(R.id.Et_User_Like);
		Et_User_Pho=(EditText)findViewById(R.id.Et_User_Pho);
		btn_change_UserInfo=(Button)findViewById(R.id.btn_change_UserInfo);
		btn_change_User=(Button)findViewById(R.id.btn_change_User);
		tv_User_Id.setText(myBundle.getString("User_Id"));
		
		btn_back=(Button)findViewById(R.id.btn_back);
		if(android.os.Build.VERSION.SDK_INT>9){
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
		}
		String sendString = "User_Id="+myBundle.getString("User_Id");
		String url = GetPostUtil.urlBase+"getUserInfoServlet";
		String response = GetPostUtil.sendPost(url, sendString);
		try{
			JSONObject json = new JSONObject(response);
			Et_User_Name.setText(json.getString("User_Name"));
			Et_User_Col.setText(json.getString("User_Col"));
			Et_User_Like.setText(json.getString("User_Like"));
			Et_User_Pho.setText(json.getString("User_Pho"));
			Et_User_Sex.setText(json.getString("User_Sex"));
			}
			catch(Exception e){
				e.printStackTrace();
			}
		btn_back.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btn_change_User.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent();
				newIntent.setClass(UserInfoActivity.this, MainActivity.class);
				startActivity(newIntent);
				finish();
			}
		});
		btn_change_UserInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					if(android.os.Build.VERSION.SDK_INT>9){
						StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
						StrictMode.setThreadPolicy(policy);
					}
					String name=Et_User_Name.getText().toString().trim();
					String pho=Et_User_Pho.getText().toString().trim();
					String sex=Et_User_Sex.getText().toString().trim();
					String like=Et_User_Like.getText().toString().trim();
					String college=Et_User_Col.getText().toString().trim();
					String sendString="User_Id="+myBundle.getString("User_Id")+"&User_Name="+name+"&User_Sex="+sex+"&User_Like="+like+"&User_Col="+college
							+"&User_Pho="+pho;
					String url = GetPostUtil.urlBase+"chanegUserInfoServlet";
					String response = GetPostUtil.sendPost(url, sendString);
					try{
					JSONObject json = new JSONObject(response);
//					Toast.makeText(UserInfoActivity.this,"11111",Toast.LENGTH_SHORT).show();
					if(json.getString("message").equals("修改成功")){
						
						Toast.makeText(UserInfoActivity.this,json.getString("message"),Toast.LENGTH_SHORT).show();
					}
					else{
						Toast.makeText(UserInfoActivity.this,json.getString("message"),Toast.LENGTH_SHORT).show();
					}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				
			}
		});
	}
}
