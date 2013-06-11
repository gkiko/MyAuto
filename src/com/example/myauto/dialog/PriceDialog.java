package com.example.myauto.dialog;

import com.example.myauto.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class PriceDialog extends DialogFragment implements OnEditorActionListener{
	
	public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }
	
	private EditText field1;
	private EditText field2;

    public PriceDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_price, container);
        field1 = (EditText) view.findViewById(R.id.editText1);
        field2 = (EditText) view.findViewById(R.id.editText2);
        getDialog().setTitle("Select Price");
        
        // Show soft keyboard automatically
        field1.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        field2.setOnEditorActionListener((OnEditorActionListener) this);

        return view;
    }

	@Override
	public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
		if (EditorInfo.IME_ACTION_DONE == arg1) {
            // Return input text to activity
            EditNameDialogListener activity = (EditNameDialogListener) getActivity();
            activity.onFinishEditDialog(field1.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
	}
}
