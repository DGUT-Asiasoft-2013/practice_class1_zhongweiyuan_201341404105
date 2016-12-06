package com.example.administrator.myapplication.fragment.inputcells;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2016/12/5.
 */

public class PictureInputCellFragment extends BaseInputCellFragment{
    final int REQUESTCODE_CAMERA = 1;
    final int REQUESTCODE_PICTURE = 2;

    ImageView imageView;
    TextView labelText;
    TextView hintText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_inputcell_picture,container,false);
        imageView = (ImageView)view.findViewById(R.id.image);
        labelText = (TextView)view.findViewById(R.id.picture);
        hintText = (TextView)view.findViewById(R.id.hint);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageViewClicked();
            }
        });


        return  view;
    }

    void onImageViewClicked(){
        String[] items = {
                "拍照",
                "相册"
        };
        new AlertDialog.Builder(getActivity())
                .setTitle(labelText.getText())
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch(i){
                            case 0:
                                takePhote();
                                break;
                            case 1:
                                pickFromAlbum();
                                break;
                            default:
                                break;
                        }


                    }
                })
                .setNegativeButton("取消",null)
                .show();



    }

    void pickFromAlbum() {
        Intent itnt = new Intent(Intent.ACTION_GET_CONTENT);
        itnt.setType("image/*");
        startActivityForResult(itnt,REQUESTCODE_PICTURE);

    }

    void takePhote() {
        Intent itnt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(itnt,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity.RESULT_CANCELED){
            return;
        }

        if (requestCode == REQUESTCODE_CAMERA){
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bmp);
//            Log.d("camera capture",data.getExtras().keySet().toString());
//            Toast.makeText(getActivity(),data.getDataString(),Toast.LENGTH_LONG).show();
        }else if (requestCode == REQUESTCODE_PICTURE){

            Bitmap bmp;
            try{
                bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),data.getData());
                imageView.setImageBitmap(bmp);
            }catch (Exception e){
                e.printStackTrace();
            }

//            Uri dataUri = data.getData();
//
//            Object obj = data.getExtras().get("data");
//            Toast.makeText(getActivity(),data.getDataString(),Toast.LENGTH_LONG).show();
        }
    }

    public void setLabelText(String labelText) {
        this.labelText.setText(labelText);
    }

    public void setHintText(String hintText) {
        this.labelText.setText(hintText);
    }




}
