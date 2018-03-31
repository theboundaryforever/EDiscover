package com.kest.ediscover;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.multi.qrcode.detector.MultiDetector;
import com.kest.ediscover.utils.DebugLog;
import com.kest.ediscover.utils.DensityUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class QRCodeActivity extends BaseActivity {

    @BindView(R.id.qrcodeimg)
    ImageView qrcodeImg;
    @BindView(R.id.back)
    LinearLayout back;
    String url;
    Activity context;
    @BindView(R.id.headimg)
    ImageView headImg;
    @BindView(R.id.username)
    TextView nameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
        //qrcodeImg = (ImageView) findViewById(R.id.qrcodeimg);
        //back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int width = DensityUtil.dip2px(this, 300);
        context = this;

        //Bitmap bitmap = EncodingUtils.createQRCode(getIntent().getStringExtra("url"),
        //      width, width, null);

        createEnglishQRCode(getIntent().getStringExtra("url"));
        // qrcodeImg.setImageBitmap(encodeAsBitmap(getIntent().getStringExtra("url")));
        String userName= sp1.getUserName();
        if (userName.length() >=6)
            userName = userName.substring(0,5);
        nameTxt.setText(userName);


        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Bitmap bmp = getURLimage(sp1.getImgUrl());
                Message msg = new Message();
                msg.what = 0;
                msg.obj = bmp;
                handle.sendMessage(msg);
            }
        }).start();


    }

    //加载图片
    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bitmap bmp = (Bitmap) msg.obj;
                    headImg.setImageBitmap(bmp);
                    break;
            }
        }

        ;
    };

    private void createEnglishQRCode(final String s) {
        /*
        这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
        请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
         */
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return QRCodeEncoder.syncEncodeQRCode(s, BGAQRCodeUtil.dp2px(QRCodeActivity.this, 150), Color.parseColor("#000000"));
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    qrcodeImg.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(QRCodeActivity.this, "生成英文二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

}
