package com.example.testdemo;

import org.json.JSONObject;
import org.crazyit.net.GetPostUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.Loader.ForceLoadContentObserver;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ApplyInfoActivity extends Activity {
	private Button btn_return,submit;
	private Bundle getBundle;
	private TextView userid,name,sex,phone,like,college,reason;
	private Spinner spinner2;
	private ArrayAdapter<String> adapter;
	private static final String[] m={"通过","未通过"};
	private EditText et_message;
	private String User_Id,Org_Department_Id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_info);
		et_message =(EditText)findViewById(R.id.et_message);
		userid=(TextView)findViewById(R.id.userid);
		name=(TextView)findViewById(R.id.tv_name1);
		sex=(TextView)findViewById(R.id.tv_sex);
		phone=(TextView)findViewById(R.id.phone);
		like = (TextView)findViewById(R.id.like);
		reason=(TextView)findViewById(R.id.reason);
		college = (TextView)findViewById(R.id.college);
		submit = (Button)findViewById(R.id.submit);
		btn_return=(Button)findViewById(R.id.btn_return);
		spinner2 = (Spinner)findViewById(R.id.spinner2);
		adapter=new ArrayAdapter<String>(this, R.layout.myspinner, m);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter);
		spinner2.setVisibility(View.VISIBLE);
		
		Intent getIntent = this.getIntent();
		getBundle =getIntent.getExtras();
		User_Id = getBundle.getString("User_Id");
		Org_Department_Id=getBundle.getString("Org_Department_Id");
		userid.setText(User_Id);
		String url = GetPostUtil.urlBase+"applyInfoServlet";
		String sendString="User_Id="+User_Id+"&Org_Department_Id="+Org_Department_Id;
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		String response=GetPostUtil.sendPost(url, sendString);
		
		try{
			org.json.JSONObject json = new org.json.JSONObject(response);
			if(json.getString("message").equals("成功")){
				String User_Name=json.getString("User_Name");
				String User_Sex=json.getString("User_Sex");
				String User_Phone=json.getString("User_Phone");
				String Org_Apply_Reason=json.getString("Org_Apply_Reason");
				String Col_Name=json.getString("Col_Name");
				String User_Like=json.getString("User_Like");
				name.setText(User_Name);
				sex.setText(User_Sex);
				college.setText(Col_Name);
				phone.setText(User_Phone);
				reason.setText(Org_Apply_Reason);
				like.setText(User_Like);
			}
			else{
				Toast.makeText(ApplyInfoActivity.this,json.getString("message"), Toast.LENGTH_SHORT).show();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		btn_return.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String message=et_message.getText().toString();
				String result=spinner2.getSelectedItem().toString();
				String sendString="User_Id="+User_Id+"&Org_Department_Id="+Org_Department_Id+"&result="+result+"&message="+message;
				if(android.os.Build.VERSION.SDK_INT>9){
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
					StrictMode.setThreadPolicy(policy);
				}
				String url = GetPostUtil.urlBase+"submitResultServlet";
				
				String response = GetPostUtil.sendPost(url, sendString);
				try{
					JSONObject json = new JSONObject(response);
					if(json.getString("message").equals("审核成功")){
						Toast.makeText(ApplyInfoActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
						finish();
					}
					else{
						Toast.makeText(ApplyInfoActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
}
