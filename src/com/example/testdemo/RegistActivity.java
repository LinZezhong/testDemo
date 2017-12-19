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
import android.widget.Toast;

public class RegistActivity extends Activity {
	private EditText regist_User_Id,regist_User_Pwd;
	private Button btn_regist;
	private String userid,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		regist_User_Id=(EditText)findViewById(R.id.regist_User_Id);
		regist_User_Pwd=(EditText)findViewById(R.id.regist_User_Pwd);
		btn_regist=(Button)findViewById(R.id.btn_regist);
		btn_regist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				userid=regist_User_Id.getText().toString().trim();
				password=regist_User_Pwd.getText().toString().trim();
				if(android.os.Build.VERSION.SDK_INT>9){
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
				}
				if(validate()){
					String url=GetPostUtil.urlBase+"registServlet";
					String sendString = "User_Id="+userid+"&"+"User_Pwd="+password;
					String response = GetPostUtil.sendPost(url, sendString);
					try{
						JSONObject jsonObj = new JSONObject(response);
						String success=jsonObj.getString("success");
						if(success.equals("true")){
							Intent myIntent = new Intent();
							Bundle myBundle = new Bundle();
							myBundle.putString("User_Id", userid);
							myBundle.putString("User_Pwd", password);
							myIntent.putExtras(myBundle);
							myIntent.setClass(RegistActivity.this, MenuActivity.class);
							startActivity(myIntent);
						}
						else{
							Toast.makeText(RegistActivity.this, success, Toast.LENGTH_SHORT).show();
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		});
	}
	private boolean validate(){
		if(userid.length()==12 && password.length()>=8 && password.length()<=14){
			if(userid.matches("[0-9]+")){
				return true;
			}
			else{
				Toast.makeText(RegistActivity.this, "账号为纯数字", Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		else{
			Toast.makeText(RegistActivity.this,"账号为12位，密码为8-14位", Toast.LENGTH_SHORT).show();
			return false;
		}
		
	}
}
