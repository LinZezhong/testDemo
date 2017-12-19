package org.crazyit.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import util.netUtil;

public class GetPostUtil {
	public static final String urlBase="http://111.230.230.93:8080/LinkMySQL/servlet/";
	public static String sendPost(String url,String params){
		PrintWriter out = null;
		BufferedReader in = null;
		String json=null;
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			
			//设置通用的请求属性
			conn.setRequestProperty("accept","*/*");
			conn.setRequestProperty("connecttion","Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0(compatible;MSIE 6.0;Windows NT 5.1;SV1)");
			//发送post请求必须设置的两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			//获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			//发送请求参数
			out.print(params);
			//flush缓冲流的缓冲
			out.flush();
			//定义BufferdReader输入流来读取URL的响应

			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
//			String result = EntityUtils.toString(httpResponse.getEntity());
			InputStream is = conn.getInputStream();
			json = netUtil.readString(is);
			
			return json;
//			result = jsonObject.getString("loginPer");
//			String line;
//			while((line=in.readLine())!=null){
//				result +=line;
//			}
			
		} catch (Exception e) {
			System.out.println("发送post请求出现异常！"+e);
			e.printStackTrace();
		}
		finally{
			try{
				if(out !=null){
					out.close();
				}
				if(in != null){
					in.close();
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		return json;
	}
}
