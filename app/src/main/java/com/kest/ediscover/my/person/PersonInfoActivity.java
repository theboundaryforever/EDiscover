package com.kest.ediscover.my.person;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kest.ediscover.BuildConfig;
import com.kest.ediscover.R;
import com.kest.ediscover.utils.RealPathFromUriUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.io.IOException;
import java.security.Provider;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人信息
 * Created by Administrator on 2018/4/3 0003.
 */
public class PersonInfoActivity extends AppCompatActivity{
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    //头像
    @BindView(R.id.rl_person_img)
    RelativeLayout rl_person_img;
    //生日
    @BindView(R.id.rl_birth)
    RelativeLayout rl_birth;
    //名字
    @BindView(R.id.rl_name)
    RelativeLayout rl_name;
    //身份认证
    @BindView(R.id.rl_vertify)
    RelativeLayout rl_vertify;
    //我的二维码
    @BindView(R.id.rl_person_qr)
    RelativeLayout rl_person_qr;
    //性别
    @BindView(R.id.rl_sex)
    RelativeLayout rl_sex;
    @BindView(R.id.iv_person_img)
    CircleImageView iv_person_img;
    Unbinder unbinder;
    private final int PICTURE_CODE=100;
    private final int PHOTO_CODE=101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_person);
        unbinder= ButterKnife.bind(this);
        tv_title.setText("个人信息");
    }
    @OnClick({R.id.iv_back,R.id.rl_person_qr,R.id.rl_vertify,R.id.rl_name,R.id.rl_person_img})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_person_qr:
                startActivity(new Intent(PersonInfoActivity.this,MyQrCodeActivity.class));
                break;
            case R.id.rl_vertify:
                startActivity(new Intent(PersonInfoActivity.this,AuthencationActivity.class));
                break;
            case R.id.rl_name:
                startActivity(new Intent(PersonInfoActivity.this,PersonInfoNameActivity.class));
                break;
            case R.id.rl_person_img:
                showPop();
                break;
        }
    }


    private void showPop(){
        View view= LayoutInflater.from(this).inflate(R.layout.activity_settings_photo,null);
        View parentView =LayoutInflater.from(this).inflate(R.layout.activity_settings_person,null);
        final PopupWindow popupWindow=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView tv_take_picture=view.findViewById(R.id.tv_take_picture);
         TextView   tv_photo=view.findViewById(R.id.tv_photo);
        LinearLayout ll_cancel=view.findViewById(R.id.ll_cancel);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(false);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        if(view.getBackground()!=null){
            view.getBackground().setAlpha(80);
        }
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM,0,0);
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
            }
        });
        tv_take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeCameraPicthre();
                if(popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
            }
        });
        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto();
                if(popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
            }
        });
    }

    private void takeCameraPicthre(){
        AndPermission.with(this).permission(Permission.CAMERA,Permission.WRITE_EXTERNAL_STORAGE).onGranted(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                takePicture();

            }
        }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {

            }
        }).start();
    }
    private void getPhoto(){
        AndPermission.with(this).permission(Permission.READ_EXTERNAL_STORAGE).onGranted(new Action() {
            @Override
            public void onAction(List<String> permissions) {
               getImage();

            }
        }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {

            }
        }).start();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICTURE_CODE && resultCode== RESULT_OK){
            File picture = new File(getExternalCacheDir() + "/img.jpg");
            Uri uri;
            System.out.println("-------------path:"+picture.getAbsolutePath());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){

                 uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID+".fileprovider", picture);

            }else {
                //裁剪照片

            }

        }else if(requestCode==PHOTO_CODE && resultCode==RESULT_OK){
            if(data!=null){
               String imgPath= RealPathFromUriUtils.getRealPathFromUri(this,data.getData());

            }

        }
    }
    //获取相册
    private void getImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                    PHOTO_CODE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, PHOTO_CODE);
        }
    }
    //调用相机
    private void  takePicture(){
            File file = new File(getExternalCacheDir(), "img.jpg");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID+".fileprovider", file));
            }else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            }
            startActivityForResult(intent, PICTURE_CODE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
