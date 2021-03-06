package com.example.myauto.filter;

import java.util.ArrayList;

import com.example.myauto.R;
import com.example.myauto.SearchPageActivity;
import com.example.myauto.database.DBManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CarMarkFiltPage extends Activity {
	private ListView list;
	private Adapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_car_mark_filter);
		super.onCreate(savedInstanceState);

		ArrayList<String[]> ls = (ArrayList<String[]>) DBManager
				.getManufacturers();

		list = (ListView) findViewById(R.id.mark_listView);
		adapter = new Adapter(this, ls);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View v, int position,
					long id) {

				String[] manufacturer = (String[]) adapter.getItem(position);
				Intent backToSearch = new Intent(CarMarkFiltPage.this,
						SearchPageActivity.class);
				backToSearch.putExtra("Manufacturer", manufacturer);
				setResult(RESULT_OK, backToSearch);
				finish();
			}
		});
	}

	private class Adapter extends BaseAdapter {

		private ArrayList<String[]> array;
		private Context ctx;

		public Adapter(Context c, ArrayList<String[]> ls) {
			this.ctx = c;
			this.array = ls;
		}

		@Override
		public int getCount() {
			return array.size();
		}

		@Override
		public Object getItem(int position) {
			return array.get(position);
		}

		public String getManID(int position) {
			return array.get(position)[0];
		}

		public String getManName(int position) {
			return array.get(position)[1];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parentView) {
			View newView;

			if (convertView == null) {
				newView = View.inflate(ctx, R.layout.mark_item_layout, null);
			} else {
				newView = convertView;
			}

			String st = (String) this.getManName(position);

			((TextView) newView.findViewById(R.id.mark_item)).setText(st);

			return newView;
		}

	}
}
