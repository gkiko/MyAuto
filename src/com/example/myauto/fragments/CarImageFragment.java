package com.example.myauto.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myauto.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CarImageFragment extends Fragment implements Serializable{
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_REQUEST_CODE = 200;
    private static final String IMAGE_DIRECTORY_NAME = "Loan Images";

    private Bitmap bitmap;
    private ImageView imgPreview;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.x_car_image_view, container, false);

        imgPreview = (ImageView)rootView.findViewById(R.id.image);
        imgPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        if(savedInstanceState != null){
            bitmap = savedInstanceState.getParcelable("bitmap");
            if(bitmap != null)
                previewCapturedImage(bitmap);
        }

        return rootView;
    }

    private void openDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.x_car_image_dialog_settings);
        dialog.setTitle("Image Type");

        Button captureImage = (Button) dialog.findViewById(R.id.capture);
        Button pickImage = (Button) dialog.findViewById(R.id.pick);

        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void captureImage(){
        Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    private void previewCapturedImage(Bitmap image){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap bitmap = image;
        imgPreview.setImageBitmap(bitmap);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                if(requestCode == getActivity().RESULT_OK) {
                    Uri photoUri = data.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(photoUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bitmap = BitmapFactory.decodeStream(imageStream);
                    previewCapturedImage(bitmap);
                } else if(resultCode == getActivity().RESULT_CANCELED) {
                    Toast.makeText(getActivity().getApplicationContext(), "User Cancelled Image Capture", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Sorry! Failed to capture Image", Toast.LENGTH_LONG).show();
                }
                break;
            case PICK_IMAGE_REQUEST_CODE:
                if(resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bitmap = BitmapFactory.decodeStream(imageStream);
                    previewCapturedImage(bitmap);
                }else if(resultCode == getActivity().RESULT_CANCELED) {
                    Toast.makeText(getActivity().getApplicationContext(), "User Cancelled Image Capture", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Sorry! Failed to capture Image", Toast.LENGTH_LONG).show();
                }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("bitmap", bitmap);
    }
}
