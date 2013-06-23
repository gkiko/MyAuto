package com.example.myauto.filter;

import java.util.ArrayList;
import java.util.List;

import com.example.myauto.R;
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

public class CarModelFilterPage extends Activity{
	
	private ListView list;
	private Adapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_model_filter);
		
		Bundle myBundle = getIntent().getExtras();
		String mark = myBundle.getString("Manufacturer");
		
		((TextView) findViewById(R.id.markName)).setText(mark);
		
	//	List <String> ls = DBManager.getManufacturers();
		List<String> ls = new ArrayList<String>();
		Cursor cursor = DBManager.filterModelsByManufacturersRaw(mark);
		if (cursor.moveToFirst()) {
			do {
				ls.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		
		
		list = (ListView) findViewById(R.id.model_listView);
		adapter = new Adapter(this, ls);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View v, int position,
					long id) {
				String model = (String) adapter.getItem(position);
				Intent backToMark = new Intent(CarModelFilterPage.this, CarMarkFilterPage.class);
				backToMark.putExtra("Model", model);
				setResult(RESULT_OK, backToMark);
				finish();
			}	
		});
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
