package com.example.myauto.adapter;

import com.example.myauto.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailAdapter extends BaseAdapter{
	String[] arrKey;
	String[] arrVal;
	Context context;
	
	public DetailAdapter(String[] details, Context context){
		arrVal = details;
		arrKey = context.getResources().getStringArray(R.array.details);
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return arrVal.length;
	}

	@Override
	public Object getItem(int arg0) {
		return arrVal[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		Container cont;
		if (convertView == null) {
			v = View.inflate(context, com.example.myauto.R.layout.activity_detail_component, null);
			cont = new Container();
			cont.desc = (TextView) v.findViewById(R.id.description);
			cont.valText = (TextView) v.findViewById(R.id.valueT);
			cont.valImg = (ImageView) v.findViewById(R.id.img);
			v.setTag(cont);
		} else {
			v = convertView;
			cont = (Container) v.getTag();
		}

		(cont.desc).setText(arrKey[position]);
		
		String val = arrVal[position];
		if(!isSign(val)){
			(cont.valText).setText(arrVal[position]);
			(cont.valImg).setVisibility(View.GONE);
		}else{
			(cont.valText).setText("");
			(cont.valImg).setVisibility(View.VISIBLE);
			if(isPosotive(val))
				(cont.valImg).setImageResource(R.drawable.plus_9);
			if(isNegative(val))
				(cont.valImg).setImageResource(R.drawable.minus_9);
		}

		return v;
	}
	
	private boolean isSign(String val){
		return isPosotive(val) || isNegative(val);
	}
	
	private boolean isPosotive(String val){
		return val.equals("+");
	}
	
	private boolean isNegative(String val){
		return val.equals("-");
	} 

	private class Container{
		TextView desc;
		TextView valText;
		ImageView valImg;
	}
}
