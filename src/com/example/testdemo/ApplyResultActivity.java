package com.example.testdemo;

import org.crazyit.net.GetPostUtil;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ApplyResultActivity extends Activity {
	private Bundle getBundle;
	private String User_Id,Org_Department_Id;
	private Button button1;
	private TextView textView1,textView2,textView3,textView4,textView5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_result);
		button1=(Button)findViewById(R.id.button1);
		textView1=(TextView)findViewById(R.id.textView1);
		textView2=(TextView)findViewById(R.id.textView2);
		textView3=(TextView)findViewById(R.id.textView3);
		textView4=(TextView)findViewById(R.id.textView4);
		textView5=(TextView)findViewById(R.id.textView5);
		Intent getIntent = this.getIntent();
		getBundle=getIntent.getExtras();
		User_Id=getBundle.getString("User_Id");
		Org_Department_Id=getBundle.getString("Org_Department_Id");
		String sendString = "User_Id="+User_Id+"&Org_Department_Id="+Org_Department_Id;
		String url=GetPostUtil.urlBase+"getApplyResultServlet";
		String response = GetPostUtil.sendPost(url, sendString);
		try{
			JSONObject json = new JSONObject(response);
			textView1.setText(json.getString("Org_Name"));
			textView2.setText(json.getString("Org_Department_Name"));
			textView3.setText(json.getString("State"));
			textView4.setText(json.getString("result"));
			textView5.setText(json.getString("message"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();	
			}
		});
	}
}
