package com.example.myauto.custom_views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.myauto.R;

public class XCarImage extends LinearLayout {
    private static final int CAMERA_REQUEST = 100;
    private Context context;
    private LayoutInflater inflater;
    private View view;
    private ImageView imgPreview;
    private Activity activity;


    public XCarImage(Context context, AttributeSet attrs) {
        super(context, attrs);
//        this.context = context;
        initViews();
    }

    public String getXValue(){
        getImageViewValue();
        return null;
    }

    public void setXValue(String xValue){

    }

    public void setXId(Integer id){
        this.setId(id);
    }

    private void initViews(){
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.x_fragment_car_image, this, true);
//        Fragment opa = new PersonsImageFragment();
//        Fragment parent = ((Activity)context).getFragmentManager().findFragmentByTag("client");
//        FragmentTransaction transaction = parent.getChildFragmentManager().beginTransaction();
//        transaction.add(R.id.fragment, opa).commit();
    }

    private void getImageViewValue(){
//        activity = (Activity)context;
//        Fragment frag = (PersonsImageFragment)activity.getFragmentManager().findFragmentById(R.id.fragment);
//        View rootView = frag.getView();
//        imgPreview = (ImageView) rootView.findViewById(R.id.image);
    }
}
