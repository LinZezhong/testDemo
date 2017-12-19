package com.example.testdemo;

import net.sf.json.JSONObject;

import org.crazyit.net.GetPostUtil;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.AndroidCharacter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OrgInfoActivity extends Activity {
	private static final String[] m={"A型","B型","O型","AB型","其他"};
	private static String[] data;
	private TextView tv_Org_Name,tv_Org_Level,tv_Org_Intro,tv_Col_Name;
	private EditText et_ApplyReason;
	private Spinner spinner1;
	private ArrayAdapter<String> adapter;
	private Button btn_Org_Apply;
	private static String User_Id,Org_Id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_org_info);
		tv_Org_Name=(TextView)findViewById(R.id.tv_Org_Name);
		tv_Org_Level=(TextView)findViewById(R.id.tv_Org_Level);
		tv_Org_Intro=(TextView)findViewById(R.id.tv_Org_Intro);
		tv_Col_Name=(TextView)findViewById(R.id.tv_Col_Name);
		et_ApplyReason = (EditText)findViewById(R.id.et_ApplyReason);
		btn_Org_Apply = (Button)findViewById(R.id.btn_Org_Apply);
		spinner1 = (Spinner)findViewById(R.id.spinner1);
		Intent getIntent = this.getIntent();
		Bundle getBundle = getIntent.getExtras();
		User_Id = getBundle.getString("User_Id");
		Org_Id = getBundle.getString("Org_Id");
		String url=GetPostUtil.urlBase+"Org_Detail";
		String sendString = "User_Id="+User_Id+"&Org_Id="+Org_Id;
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		String response=GetPostUtil.sendPost(url,sendString);
		try{
			org.json.JSONObject json = new org.json.JSONObject(response);
			
			tv_Org_Name.setText(json.getString("Org_Name"));
			tv_Org_Intro.setText(json.getString("Org_Intro"));
			tv_Org_Level.setText(json.getString("Org_Level"));
			tv_Col_Name.setText(json.getString("Col_Name"));
			int count=json.getInt("count");
			
			
			data=new String[count];
			int i=0;
			for(;i<count;i++){
				data[i]=json.getString("Org_Depart_Name"+String.valueOf(i));
			}
		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter);
		spinner1.setVisibility(View.VISIBLE);
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		btn_Org_Apply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String reason = et_ApplyReason.getText().toString();
				String depart=spinner1.getSelectedItem().toString();
				String sendString1="User_Id="+User_Id+"&Org_Id="+Org_Id+"&Org_Apply_Reason="+reason+"&Org_Department_Name="+depart;
				String response1 = GetPostUtil.sendPost(GetPostUtil.urlBase+"applyCommitServlet",sendString1);
				try{
					org.json.JSONObject json = new org.json.JSONObject(response1);
					String success=json.getString("message");
					if(success.equals("申请已提交成功")){
						Toast.makeText(OrgInfoActivity.this, success, Toast.LENGTH_SHORT).show();
						finish();
					}
					else{
						Toast.makeText(OrgInfoActivity.this, "您已申请过", Toast.LENGTH_SHORT).show();
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
//	tv_show.setText(getBundle.getString("Org_Id")+":"+getBundle.getString("User_Id"));
	}
}
