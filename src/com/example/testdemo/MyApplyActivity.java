package com.example.testdemo;

import java.util.Iterator;

import org.crazyit.net.GetPostUtil;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyApplyActivity extends Activity {
	private static Bundle getBundle;
	private String User_Id;
	private  int WC = LinearLayout.LayoutParams. WRAP_CONTENT ;
	private int MP =LinearLayout.LayoutParams.MATCH_PARENT;
	private LinearLayout.LayoutParams layoutP;
	private LinearLayout.LayoutParams layoutP1,layoutP2,layoutP3;
	private LinearLayout MyApplyLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_apply);
		MyApplyLayout=(LinearLayout)findViewById(R.id.MyApplyLayout);
		
		layoutP=  new LinearLayout.LayoutParams(MP, WC);
		layoutP.setMargins(2, 2, 2, 2);
		layoutP1=  new LinearLayout.LayoutParams(WC, WC);
		layoutP1.setMargins(2, 2,2, 2);
		layoutP2=  new LinearLayout.LayoutParams(MP, MP);
		layoutP2.setMargins(0, 0, 0, 2);
		layoutP3=  new LinearLayout.LayoutParams(WC, MP);
		layoutP3.setMargins(2, 2,2, 2);
		
		Intent getIntent = this.getIntent();
		getBundle=getIntent.getExtras();
		User_Id=getBundle.getString("User_Id");
		String sendString = "User_Id="+User_Id;
		String url=GetPostUtil.urlBase+"getMyApplyServlet";
		String response = GetPostUtil.sendPost(url, sendString);
		
		try{
			JSONObject jsonObj=new JSONObject(response);
			Iterator iterator = jsonObj.keys();
			while(iterator.hasNext()){
			        String   key = (String) iterator.next();
			        JSONObject json=jsonObj.getJSONObject(key); 
			        LinearLayout ll4= new LinearLayout(this);
			        ll4.setLayoutParams(layoutP);
			        ll4.setOrientation(LinearLayout.VERTICAL);
			        ll4.setBackgroundResource(R.color.heihuang);
			        LinearLayout ll1= new LinearLayout(this);
			        ll1.setLayoutParams(layoutP);
			        ll1.setOrientation(LinearLayout.HORIZONTAL);
			        ll1.setBackgroundResource(R.color.heihuang);
			        TextView tv1= new TextView(this); 
			        tv1.setLayoutParams(layoutP1);
			        tv1.setText("社团");
			        tv1.setTextSize(22);
			        tv1.setGravity(Gravity.CENTER);
			        tv1.setBackgroundResource(R.color.hese);
			        ll1.addView(tv1);
			        TextView tv2= new TextView(this); 
			        tv2.setLayoutParams(layoutP);
			        tv2.setText(json.getString("Org_Name"));
			        tv2.setTextSize(22);
			        tv2.setBackgroundResource(R.color.hese);
			        tv2.setGravity(Gravity.CENTER);
			        ll1.addView(tv2);
			        ll4.addView(ll1);
			        
			        LinearLayout ll2 = new LinearLayout(this);
			        ll2.setLayoutParams(layoutP);
			        ll2.setOrientation(LinearLayout.HORIZONTAL);
			        ll2.setBackgroundResource(R.color.heihuang);
			        TextView tv3= new TextView(this);
			        tv3.setLayoutParams(layoutP1);
			        tv3.setText("部门");
			        tv3.setTextSize(22);
			        tv3.setBackgroundResource(R.color.hese); 
			        tv3.setGravity(Gravity.CENTER);
			        ll2.addView(tv3);
			        TextView tv4= new TextView(this); 
			        tv4.setLayoutParams(layoutP);
			        tv4.setText(json.getString("Org_Department_Name"));
			        tv4.setTextSize(22);
			        tv4.setBackgroundResource(R.color.hese);
			        tv4.setGravity(Gravity.CENTER);
			        ll2.addView(tv4);   
			        ll4.addView(ll2);
			        
			        LinearLayout ll3 = new LinearLayout(this);
			        ll3.setLayoutParams(layoutP);
			        ll3.setOrientation(LinearLayout.HORIZONTAL);
			        ll3.setBackgroundResource(R.color.heihuang);
			        TextView tv5= new TextView(this); 
			        tv5.setLayoutParams(layoutP1);
			        tv5.setText("状态");
			        tv5.setTextSize(22);
			        tv5.setBackgroundResource(R.color.hese);
			        tv5.setGravity(Gravity.CENTER);
			        TextView tv6= new TextView(this); 
			        tv6.setLayoutParams(layoutP);
			        tv6.setText(json.getString("State"));
			        tv6.setTextSize(22);
			        tv6.setBackgroundResource(R.color.hese);
			        tv6.setGravity(Gravity.CENTER);
			        ll3.addView(tv5);
			        ll3.addView(tv6);
			        ll4.addView(ll3);
			        
			        MyApplyLayout.addView(ll4);
			        ll4.setClickable(true);
			        ll4.setTag(json.getString("Org_Department_Id"));
			        ll4.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							intent.setClass(MyApplyActivity.this, ApplyResultActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("User_Id", User_Id);
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
//		Toast.makeText(MyApplyActivity.this,getBundle.getString("User_Id"), Toast.LENGTH_SHORT).show();
	}
}
