package com.example.myauto.filter;

import java.util.ArrayList;
import java.util.List;

import com.example.myauto.R;
import com.example.myauto.R.layout;
import com.example.myauto.SearchPageActivity;
import com.example.myauto.database.DBManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CarMarkFilterPage extends Activity{
	
	private static final int MODEL_FILTER = 1002;
	private ListView list;
	private Adapter adapter;
	private String mark;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_car_mark_filter);
		super.onCreate(savedInstanceState);
		
		mark = "";
		List <String> ls = DBManager.getManufacturers();
		
		list = (ListView) findViewById(R.id.mark_listView);
		adapter = new Adapter(this, ls);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View v, int position,
					long id) {
				
				String manufacturer = (String) adapter.getItem(position);
				mark = manufacturer;
				Intent modelIntent = new Intent(CarMarkFilterPage.this, CarModelFilterPage.class);
				modelIntent.putExtra("Manufacturer", manufacturer);
				startActivityForResult(modelIntent, MODEL_FILTER);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch(requestCode){
		case (MODEL_FILTER):
			if(resultCode == Activity.RESULT_OK){
				String model = (String) data.getSerializableExtra("Model");
				String [] markAndModel = new String [2];
				markAndModel[0] = mark;
				markAndModel[1] = model;
				Intent backToSearch = new Intent (CarMarkFilterPage.this, SearchPageActivity.class);
				backToSearch.putExtra("MarkAndModel", markAndModel);
				setResult(RESULT_OK, backToSearch);
				finish();
			}
		}
	}
	
	
	private class Adapter extends BaseAdapter {

		private ArrayList <String> array;
		private Context ctx;
		
		public Adapter(Context c, List<String> ls){
			this.ctx = c;
			this.array = (ArrayList<String>) ls;
		}
		
		@Override
		public int getCount() {
			return array.size();
		}

		@Override
		public Object getItem(int position) {
			return array.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parentView) {
			View newView;
			
			if(convertView == null){
				newView = View.inflate(ctx, R.layout.mark_item_layout, null);
			}else{
				newView = convertView;
			}
			
			String st = (String) this.getItem(position);
			
			((TextView) newView.findViewById(R.id.mark_item)).setText(st);
			
			return newView;
		}
		
	}
	
}
