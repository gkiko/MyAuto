package com.example.myauto.requests;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

import com.example.myauto.net.HttpClient;

public class PostRequest extends AsyncTask<List<NameValuePair>, Void, Void>{
	private final String url = "http://www.myauto.ge/car_insert.php";
	
	@Override
	protected Void doInBackground(List<NameValuePair>... params) {
		try {
			HttpResponse response = HttpClient.doPost(url, params[0]);
			HttpEntity entity = response.getEntity();
			String responseText = EntityUtils.toString(entity);

			System.out.println("77777 "+responseText);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
	}
}
