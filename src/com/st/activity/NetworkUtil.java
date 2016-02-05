package com.st.activity;

import java.io.InputStream;

import com.st.http.AsyncHttpClient;
import com.st.http.AsyncHttpResponseHandler;
import com.st.http.BinaryHttpResponseHandler;
import com.st.http.JsonHttpResponseHandler;
import com.st.http.PersistentCookieStore;
import com.st.http.RequestParams;

import android.content.Context;

public class NetworkUtil {

	private static AsyncHttpClient client = new AsyncHttpClient();
	private static PersistentCookieStore cookieStore;
	//private static String BASE_URL = "http://192.168.0.3/jiexinhui/public/";
	//private static String FILE_SERVER = "http://192.168.0.110/jiexinhui/public/";
	//private static String BASE_URL = "http://192.168.0.110/jiexinhui/public/";
	private static String FILE_SERVER = "http://jiexinhui.china6d.com/";
	private static String BASE_URL = "http://jiexinhui.china6d.com";

	public static void initNetwork(Context context) {
		cookieStore = new PersistentCookieStore(context);
		client.setCookieStore(cookieStore);
		client.setTimeout(10000);
	}

	public static String getBaseUrl() {
		return BASE_URL;
	}

	public static void get(String urlString, AsyncHttpResponseHandler res) 
	{

		client.get(BASE_URL + urlString, res);

	}

	public static void get(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) 
	{

		client.get(BASE_URL + urlString, params, res);

	}

	public static void get(String urlString, JsonHttpResponseHandler res) 
	{

		client.get(BASE_URL + urlString, res);

	}

	public static void get(String urlString, RequestParams params,
			JsonHttpResponseHandler res) 
	{

		client.get(BASE_URL + urlString, params, res);

	}

	public static void get(String uString, BinaryHttpResponseHandler bHandler) 
	{

		client.get(BASE_URL + uString, bHandler);

	}

	public static void post(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) 
	{

		client.post(BASE_URL + urlString, params, res);
	}

	public static void post(String urlString, RequestParams params,
			JsonHttpResponseHandler res) 
	{

		client.post(BASE_URL + urlString, params, res);

	}

	public static void uploadfile(InputStream fileInputStream, String fileName,
			JsonHttpResponseHandler res) {
		RequestParams requestParams = new RequestParams();
		requestParams.put("file", fileInputStream, fileName);
		client.post(FILE_SERVER + "uploadfile.php?do=upload",requestParams, res);
	}
	
	public static String getFileServer(){
		return FILE_SERVER;
	}
	public static AsyncHttpClient getClient() {

		return client;

	}

}
