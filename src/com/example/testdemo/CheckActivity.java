package com.example.testdemo;

import java.util.Iterator;

import org.crazyit.net.GetPostUtil;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CheckActivity extends Activity {
	private LinearLayout ll;
	private  int WC = LinearLayout.LayoutParams. WRAP_CONTENT ;
	private int MP =LinearLayout.LayoutParams.MATCH_PARENT;
	private LinearLayout.LayoutParams layoutP;
	private LinearLayout.LayoutParams layoutP1,layoutP2,layoutP3;
	private static Bundle getBundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check);
		Intent getIntent = this.getIntent();
		getBundle = getIntent.getExtras();
		String User_Id=getBundle.getString("User_Id");
		ll=(LinearLayout)findViewById(R.id.check_Comunity);
		if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		layoutP=  new LinearLayout.LayoutParams(MP, WC);
		layoutP.setMargins(2, 2, 2, 2);
		layoutP1=  new LinearLayout.LayoutParams(WC, WC);
		layoutP1.setMargins(2, 2,2, 2);
		layoutP2=  new LinearLayout.LayoutParams(MP, MP);
		layoutP2.setMargins(0, 0, 0, 0);
		layoutP3=  new LinearLayout.LayoutParams(WC, MP);
		layoutP3.setMargins(2, 2,2, 2);
		String sendString ="User_Id="+User_Id;
		String url = GetPostUtil.urlBase+"getMyOrgServlet";
		
		String response = GetPostUtil.sendPost(url, sendString);
//		Toast.makeText(CheckActivity.this,response,Toast.LENGTH_SHORT).show();
		try{
			JSONObject jsonObj=new JSONObject(response);
			Iterator iterator = jsonObj.keys();
			while(iterator.hasNext()){
			        String   key = (String) iterator.next();
			        JSONObject json=jsonObj.getJSONObject(key);      
			        LinearLayout ll1= new LinearLayout(this);
			        ll1.setLayoutParams(layoutP);
			        ll1.setOrientation(LinearLayout.HORIZONTAL);
			        ll1.setBackgroundResource(R.color.heihuang);
			        TextView tv1= new TextView(this); 
			        tv1.setLayoutParams(layoutP3);
			        tv1.setText(json.getString("Org_Name"));
			        tv1.setTextSize(22);
			        tv1.setGravity(Gravity.CENTER);
			        tv1.setBackgroundResource(R.color.hese);
			        ll1.addView(tv1);
			        LinearLayout ll2=new LinearLayout(this);
			        ll2.setLayoutParams(layoutP);
			        ll2.setOrientation(LinearLayout.VERTICAL);
			        TextView tv2= new TextView(this); 
			        tv2.setLayoutParams(layoutP);
			        tv2.setText("部门:"+json.getString("Org_Department_Name"));
			        tv2.setTextSize(22);
			        tv2.setBackgroundResource(R.color.hese);
			        ll2.addView(tv2);
			        TextView tv3= new TextView(this); 
			        
			        tv3.setLayoutParams(layoutP);
			        tv3.setText("简介:"+json.getString("Org_Intro"));
			        tv3.setTextSize(22);
			        tv3.setBackgroundResource(R.color.hese);
			        ll2.addView(tv3);
			        ll1.addView(ll2);
			        ll.addView(ll1);
//			        Toast.makeText(CheckActivity.this,json.getString("Org_Name"),Toast.LENGTH_SHORT).show();
			        ll1.setClickable(true);
			        ll1.setTag(json.getString("Org_Department_Id"));
			        ll1.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							intent.setClass(CheckActivity.this, CheckApplyActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("User_Id", getBundle.getString("User_Id"));
							bundle.putString("Org_Department_Id",v.getTag().toString());
							intent.putExtras(bundle);
							startActivity(intent);
						}
					});
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
