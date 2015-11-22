package com.example.function.babysitter.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.function.babysitter.R;

import butterknife.InjectView;

/**
 * Created by KongFanyang on 2015/11/21.
 */
public class KeepTime extends BaseActivity {

    @InjectView(R.id.pic_default)
    View addPicture;
    @InjectView(R.id.iv_get)
    ImageView imageView;

    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final int GET_PHOTO = 1;
    public static final int PHOTO_CROP = 3;

    private Bitmap mBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keep_time_activity);


        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                startActivityForResult(intent, GET_PHOTO);
            }
        });
    }

    @Override
    public void setupToolBar() {
        if(getToolbar() != null) {
            setSupportActionBar(getToolbar());
            getToolbar().setNavigationIcon(R.drawable.ic_menu_done);
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case GET_PHOTO:
                Uri uri = data.getData();
                startPhotoZoom(uri);
            case PHOTO_CROP:
                if(data != null) {
                    setPicToView(data);
                }
                break;
            default:
                break;
        }
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 270);
        intent.putExtra("outputY", 270 * 0.618);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CROP);
    }

    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            imageView.setImageBitmap(mBitmap);
            //上传服务器
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBitmap != null) {
            destoryBitmap(mBitmap);
        }
    }

    public void destoryBitmap(Bitmap bitmap) {
        if(bitmap != null) {
            bitmap.recycle();
            mBitmap = null;
        }
    }
}
