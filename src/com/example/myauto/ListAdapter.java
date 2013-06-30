package com.example.myauto;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.myauto.item.CarFacade;
import com.example.myauto.item.CarItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter{
	private ArrayList<CarFacade> ls;
	private Context c;
	
	public ListAdapter(ArrayList<CarFacade> list, Context context){
		ls = list;
		c = context;
	}

	@Override
	public int getCount() {
		return ls.size();
	}

	@Override
	public Object getItem(int arg0) {
		return ls.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View v = null;
		Container cont;
		if (arg1 == null) {
			v = View.inflate(c, R.layout.component, null);
			cont = new Container();
			cont.img = (ImageView) v.findViewById(R.id.image);
			cont.name = (TextView) v.findViewById(R.id.product_name);
			cont.year = (TextView) v.findViewById(R.id.year);
			cont.wheel = (TextView) v.findViewById(R.id.wheel);
			cont.engine = (TextView) v.findViewById(R.id.engine);
			cont.fuel = (TextView) v.findViewById(R.id.fuel);
			cont.location = (TextView) v.findViewById(R.id.location);
			cont.customs = (TextView) v.findViewById(R.id.customs);
			cont.price = (TextView) v.findViewById(R.id.product_price);
			v.setTag(cont);
		} else {
			v = arg1;
			cont = (Container) v.getTag();
		}
		
		CarFacade cr = ls.get(arg0);
		if(cr.hasImage())
			(cont.img).setImageBitmap(cr.getImage());
		else {
			(cont.img).setImageResource(R.drawable.ic_launcher);
		}
		(cont.name).setText(cr.getValueFromProperty(CarItem.MAKE) + " " + cr.getValueFromProperty(CarItem.MODEL));
		(cont.year).setText(cr.getValueFromProperty(CarItem.YEAR));
		(cont.wheel).setText(cr.getValueFromProperty(CarItem.WHEEL));
		(cont.engine).setText(cr.getValueFromProperty(CarItem.ENGINE));
		(cont.fuel).setText(cr.getValueFromProperty(CarItem.FUEL));
		(cont.location).setText(cr.getValueFromProperty(CarItem.LOCATION));
		(cont.customs).setText(cr.getValueFromProperty(CarItem.CUSTOMS));
		(cont.price).setText(cr.getValueFromProperty(CarItem.PRICE));
		return v;
	}

	private class Container implements Serializable{
		private static final long serialVersionUID = 1L;
		ImageView img;
		TextView name;
		TextView year;
		TextView wheel;
		TextView engine;
		TextView fuel;
		TextView location;
		TextView customs;
		TextView price;
	}
	
	public ArrayList<CarFacade> getList(){
		return ls;
	}
}
