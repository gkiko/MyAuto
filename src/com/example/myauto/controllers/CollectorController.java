package com.example.myauto.controllers;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by g.vakhtangishvili on 9/19/13.
 */
public class CollectorController {


    /**
     * Metodit gadavuvli gadmocemul Views-s yvela shvils da vigeb matgan Data-s
     * Romelsac vagroveb List-shi da vabruneb.
     *
     * @param rootView
     * @return
     */
    public List<BasicNameValuePair> collectViewValues (View rootView) {
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        ArrayList<View> allViews = recursion(rootView);
        BasicNameValuePair pair;

        for(int i = 0; i < allViews.size(); i++) {
            pair = getValueOfChild(allViews.get(i));
            if(pair != null)
                list.add(pair);
        }
        return list;
    }

    /**
     * Gadmocemuli View-dan vigeb View-s Tag-s da Value-s
     *
     * @param view
     * @return
     */
    private BasicNameValuePair getValueOfChild(View view){
        String value = null;
        String key = null;

        if(view == null)
            return null;

        if(view.getTag() != null)
            key = String.valueOf(view.getTag());

        if(view instanceof EditText){
            value = String.valueOf(((EditText) view).getText());
        } else if(view instanceof Spinner) {
            value = String.valueOf(((Spinner) view).getSelectedItemPosition());
        } else if (view instanceof CheckBox) {
            if(((CheckBox) view).isChecked())
                value = String.valueOf(1);
            else
                value = String.valueOf(0);
        }

        if(value == null)
            return null;

        BasicNameValuePair pair = new BasicNameValuePair(key, value);
        return pair;
    }

    /**
     * Gadmoecema metods View da rekursiit mis yvela shvils da qveshvilebs
     * Vabruneb View-ebs arrayList-is saxit.
     *
     * @param v
     * @return
     */
    private static ArrayList<View> recursion(View v) {
        if(!isViewGroup(v)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup vg = (ViewGroup) v;
        for(int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(recursion(child));
            result.addAll(viewArrayList);
        }
        return result;
    }

    /**
     * Vamocmeb View ViewGroup-ia tu ara
     *
     * @param view
     * @return
     */
    private static boolean isViewGroup(View view) {
        if (view instanceof ViewGroup) return true;
        return false;
    }
}
