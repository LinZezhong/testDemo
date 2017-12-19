package com.example.testdemo;

import java.util.Iterator;

import net.sf.json.JSONArray;

import org.json.JSONObject;
import org.crazyit.net.GetPostUtil;








import bean.OrgInfo;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity {
	private Button btn_user,btn_check,myApply;
	private OrgInfo orgInfo;
	private LinearLayout ll;
	private  int WC = LinearLayout.LayoutParams. WRAP_CONTENT ;
	private int MP =LinearLayout.LayoutParams.MATCH_PARENT;
	private LinearLayout.LayoutParams layoutP;
	private LinearLayout.LayoutParams layoutP1;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		btn_user=(Button)findViewById(R.id.btn_user);
		btn_check=(Button)findViewById(R.id.btn_check);
		myApply=(Button)findViewById(R.id.myApply);
		ll = (LinearLayout)findViewById(R.id.menu);
		Intent myIntent = this.getIntent();
		final Bundle myBundle = myIntent.getExtras();	
		if(android.os.Build.VERSION.SDK_INT>9){
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
		}
		layoutP=  new LinearLayout.LayoutParams(MP, WC);
		layoutP.setMargins(1,2, 1, 0);
	    layoutP1=  new LinearLayout.LayoutParams(WC, WC);
	    layoutP1.setMargins(1, 2, 1, 0);
		String sendString ="";
		String url = GetPostUtil.urlBase+"getOrgServlet";
		String response = GetPostUtil.sendPost(url, sendString);
		try{
			JSONObject json = new JSONObject(response);
			Iterator iterator = json.keys();
			int i=0;
			while(iterator.hasNext()){
			        String   key = (String) iterator.next();
			        JSONObject jsonObj=json.getJSONObject(key);      
			        LinearLayout ll1= new LinearLayout(this);
			        ll1.setLayoutParams(layoutP);
			        ll1.setOrientation(LinearLayout.HORIZONTAL);
			        ll1.setBackgroundResource(R.color.heihuang);
			        
//			        TextView tv1= new TextView(this); 
//			        tv1.setLayoutParams(layoutP1);
//			        tv1.setText(jsonObj.getString("Org_Id"));
//			        tv1.setTextSize(0);
//			        tv1.setId(i++);
//			        ll1.addView(tv1);
			        TextView tv2= new TextView(this); 
			        tv2.setLayoutParams(layoutP);
			        tv2.setText(jsonObj.getString("Org_Name"));
			        tv2.setTextSize(22);
			        tv2.setBackgroundResource(R.color.hese);
			        ll1.addView(tv2);
			        ll.addView(ll1);
			        ll1.setClickable(true);
			        ll1.setTag(jsonObj.getString("Org_Id"));
			        ll1.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							intent.setClass(MenuActivity.this, OrgInfoActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("User_Id", myBundle.getString("User_Id"));
							bundle.putString("Org_Id",v.getTag().toString());
							intent.putExtras(bundle);
							startActivity(intent);
						}
					});
			}
			
			
			}
			catch(Exception e){
				e.printStackTrace();
			}	
		btn_user.setOnClickListener(new OnClickListener() {
				
			@Override
			public void onClick(View v) {
				Intent userIntent = new Intent();
				userIntent.putExtras(myBundle);
				userIntent.setClass(MenuActivity.this,UserInfoActivity.class);
				startActivity(userIntent);
			}
		});
		btn_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent checkIntent = new Intent();
				checkIntent.putExtras(myBundle);
				checkIntent.setClass(MenuActivity.this, CheckActivity.class);
				startActivity(checkIntent);
			}
		});
		myApply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent checkIntent = new Intent();
				checkIntent.putExtras(myBundle);
				checkIntent.setClass(MenuActivity.this, MyApplyActivity.class);
				startActivity(checkIntent);
			}
		});
	}
}
