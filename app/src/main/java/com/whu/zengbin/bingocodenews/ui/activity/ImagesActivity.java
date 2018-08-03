package com.whu.zengbin.bingocodenews.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.whu.zengbin.bingocodenews.R;
import com.whu.zengbin.bingocodenews.common.DownLoadImageService;
import com.whu.zengbin.bingocodenews.common.PermissionUtil;
import com.whu.zengbin.bingocodenews.ui.view.FloatViewPager;


/**
 * 图集
 * Created by lijian on 2017/11/21.
 */

public class ImagesActivity extends AppCompatActivity {

    private static final String TAG = "BC-ImagesActivity";
    public static final String URL_IMG = "img_url";
    public static final String CONTENT_TITLE = "content_title";

    RelativeLayout mYfBottomLayout;
    private View mBackground;
    FloatViewPager mViewPager;
    private PhotoView mCurrentPhoto;
    private TextView mTitletv;
    private ImageView mDownLoad;
    private int mSellectIndex = 0;
    private MotionEvent mLastMoveEvent;
    private SimpleTarget target;
    private String title;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yf_act_images);
        mYfBottomLayout = findViewById(R.id.yf_bottom_layout);
        mBackground = findViewById(R.id.background_view);
        mViewPager = findViewById(R.id.viewPager);
        mTitletv = (TextView) findViewById(R.id.desc_tv);
        mDownLoad = (ImageView) findViewById(R.id.download_img);
        Intent intent = getIntent();
        final String url = intent.getStringExtra(URL_IMG);
        title = intent.getStringExtra(CONTENT_TITLE);
        mTitletv.setText(title);
        mDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(PermissionUtil.isgetPurmission(ImagesActivity.this,PermissionUtil.PERMISSION_STORAGE)) {
                        downLoadImg(url, title);
                    } else {
                        PermissionUtil.requestionPermission(ImagesActivity.this,PermissionUtil.PERMISSION_STORAGE);
                    }
            }
        });
        mViewPager.setAdapter(new PagerAdapter() {

            public Object instantiateItem(ViewGroup container, int position) {
                View itemView = LayoutInflater.from(ImagesActivity.this).inflate(R.layout.item_image, container, false);
                final PhotoView photoView = itemView.findViewById(R.id.image);
                //photoView.setImageResource(image[index]);
                photoView.setTag(position);

                Glide.with(ImagesActivity.this)
                        .load(url)
                        .into(photoView);
                container.addView(itemView);
                return itemView;
            }


            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mSellectIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setPositionListener(new FloatViewPager.OnPositionChangeListener() {
            @Override
            public void onPositionChange(int initTop, int nowTop, float ratio) {
                float alpha = 1 - Math.min(1, ratio * 5);
                mYfBottomLayout.setAlpha(alpha);
                mBackground.setAlpha(Math.max(0, 1 - ratio));
            }

            @Override
            public void onFlingOutFinish() {
                finish();
            }
        });
        mViewPager.setDisallowInterruptHandler(new FloatViewPager.DisallowInterruptHandler() {
            @Override
            public boolean disallowInterrupt() {
                PhotoView view = mViewPager.findViewWithTag(mSellectIndex);
                return view.getScale() != 1;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void downLoadImg(String url, String title) {
        Log.i(TAG, "开始保存");
        DownLoadImageService service = new DownLoadImageService(this,
                url,title);
        //启动图片下载线程
        new Thread(service).start();
    }
}
