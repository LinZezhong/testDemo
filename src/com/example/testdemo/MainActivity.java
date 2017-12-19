package com.example.testdemo;

import java.util.HashMap;
import java.util.Map;

import org.crazyit.net.GetPostUtil;



import org.json.JSONObject;

import util.HttpUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;;


public class MainActivity extends Activity {
	private EditText User_Id,User_Pwd;
	private Button Btn_Login;
	private TextView tv_regist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		User_Id=(EditText)findViewById(R.id.User_Id);
		User_Pwd=(EditText)findViewById(R.id.User_Pwd);
		tv_regist = (TextView)findViewById(R.id.tv_regist);
		tv_regist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, RegistActivity.class);
				Bundle userInfo=new Bundle();
				intent.putExtras(userInfo);
				startActivity(intent);			
			}
		});
		Btn_Login=(Button)findViewById(R.id.Btn_Login);
		Btn_Login.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(android.os.Build.VERSION.SDK_INT>9){
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
				}
				String sendString = "User_Id="+User_Id.getText().toString().replace(" ", "")+"&"+"User_Pwd="+User_Pwd.getText().toString().replace(" ", "");
				String url = GetPostUtil.urlBase+"testServlet";
				String response = GetPostUtil.sendPost(url, sendString);
				try{
				JSONObject json = new JSONObject(response);
				if(json.getString("loginPer").equals("登录成功")){
					Intent intent=new Intent();
					intent.setClass(MainActivity.this, MenuActivity.class);
					Bundle userInfo=new Bundle();
					userInfo.putString("User_Id", User_Id.getText().toString().replace(" ", ""));
					userInfo.putString("User_Pwd", User_Pwd.getText().toString().replace(" ", ""));
					
					String User_Sex=json.getString("User_Sex");
				    userInfo.putString("User_Sex",User_Sex);
				    String User_Name=json.getString("User_Name");
					userInfo.putString("User_Name",User_Name);

					userInfo.putString("User_Col", json.getString("User_Col"));
					userInfo.putString("User_Like", json.getString("User_Like"));
					userInfo.putString("User_Pho", json.getString("User_Pho"));
					intent.putExtras(userInfo);
					startActivity(intent);
					finish();
				}
				else{
					Toast.makeText(MainActivity.this,json.getString("loginPer"),Toast.LENGTH_SHORT).show();
				}
				}
				catch(Exception e){
					e.printStackTrace();
				}
//				if(validate()){
//					if(loginPro()){
//						Intent intent=new Intent();
//						intent.setClass(MainActivity.this, MenuActivity.class);
//						Bundle userInfo=new Bundle();
//						userInfo.putString("User_Id", User_Id.getText().toString().replace(" ", ""));
//						userInfo.putString("User_Pwd", User_Pwd.getText().toString().replace(" ", ""));
//						intent.putExtras(userInfo);
//						startActivity(intent);
//						finish();
//					}
//					else{
//						Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
//					}
//				}
			}
		});
	}
	private boolean loginPro()
	{
		String userid = User_Id.getText().toString().trim();
		String password = User_Pwd.getText().toString().trim();
		JSONObject jsonObj;
		try{
			jsonObj = query(userid, password);
			if(jsonObj.getInt("loginPer")>0)
			{
				return true;
			}
		}
		catch(Exception e)
		{
			Toast.makeText(MainActivity.this, "服务器响应异常，请稍后再试", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return false;
	}
	private boolean validate()
	{
		String userid = User_Id.getText().toString().trim();
		String password = User_Pwd.getText().toString().trim();
		if(userid.equals("")){
//			DialogUtil.showDialog(this,"用户账号是必填项",false);
			Toast.makeText(MainActivity.this,"用户账号是必填项",Toast.LENGTH_SHORT).show();
			return false;
		}
		if(password.equals("")){
			Toast.makeText(MainActivity.this,"用户口令是必填项",Toast.LENGTH_SHORT).show();
//			DialogUtil.showDialog(this,"用户口令是必填项",false);
			return false;
		}
		return true;
	}
	private JSONObject query(String userid,String password) throws Exception
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("password", password);
		String url = HttpUtil.BASE_URL+"LoginServlet.jsp";
		return new JSONObject(HttpUtil.postRequest(url, map));
	}
}
