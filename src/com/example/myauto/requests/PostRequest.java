package com.example.myauto.requests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import android.os.AsyncTask;

import com.example.myauto.net.HttpClient;

public class PostRequest extends AsyncTask<List<NameValuePair>, Void, Void>{
	private final String url = "http://www.myauto.ge/car_insert.php";
	
	@Override
	protected Void doInBackground(List<NameValuePair>... params) {
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        for(int index=0; index < params[0].size(); index++) {
            if(params[0].get(index).getName().equalsIgnoreCase("photo1")) {
                // If the key equals to "image", we use FileBody to transfer the data
            	File img = new File (params[0].get(index).getValue());
                builder.addPart(params[0].get(index).getName(), new FileBody(img));
            } else {
                // Normal string data
                builder.addTextBody(params[0].get(index).getName(), params[0].get(index).getValue());
            }
        }
        try {
			HttpClient.doPost(url, builder.build());
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
