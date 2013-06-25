package com.example.myauto;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MasterPageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_master_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.myauto_menu, menu);
		return true;
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent nextIntent = null;
		switch(item.getItemId()){
		case R.id.menu_main:
			nextIntent = new Intent(MasterPageActivity.this, FirstPageActivity.class);
			break;
		case R.id.menu_carList:
			nextIntent = new Intent(MasterPageActivity.this, MainActivity.class);
			break;
		case R.id.menu_search:
			nextIntent = new Intent(MasterPageActivity.this, SearchPageActivity.class);
			break;
		case R.id.menu_catalog:
			nextIntent = new Intent(MasterPageActivity.this, CatalogPageActivity.class);
			break;
		case R.id.menu_about:
			nextIntent = new Intent(MasterPageActivity.this, AboutPageActivity.class);
			break;
		case R.id.menu_login:
			createLoginBox();
			return super.onMenuItemSelected(featureId, item);
		default:
			break;
		}
		startActivity(nextIntent);
		return super.onMenuItemSelected(featureId, item);
	}

	/**
	 * აგდებს ლოგინის დიალოგბოქსს, სადაც მომხმარებელს შეეძლება დალოგინება.
	 *
	 */
	private void createLoginBox() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		//builder.setIcon(R.drawable.dialog_question);
		builder.setTitle(getString(R.string.login_dialog_title));
		builder.setInverseBackgroundForced(true);
		LayoutInflater inflater = getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.login_dialog_layout, null);
		builder.setView(dialoglayout);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		  @Override
		  public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		  }
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  @Override
		  public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		  }
		});
		AlertDialog alert = builder.create();
		alert.show();
		
	}
}
