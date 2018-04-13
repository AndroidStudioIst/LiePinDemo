package com.angcyo.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        final int[] guid_text = new int[]{R.drawable.guid1_text, R.drawable.guid2_text, R.drawable.guid3_text, R.drawable.guid4_text};
        ((ViewPager) findViewById(R.id.view_pager)).setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return guid_text.length;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                //Log.e("angcyo", "instantiateItem:" + position);
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(guid_text[position]);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                //Log.e("angcyo", "destroyItem:" + position);
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        });
        final int[] guid = new int[]{R.drawable.guid1, R.drawable.guid2, R.drawable.guid3, R.drawable.guid4};
        ((RTransformImageView) findViewById(R.id.transform_image_view)).setImages(guid);
        ((RTransformImageView) findViewById(R.id.transform_image_view)).setupViewPager((ViewPager) findViewById(R.id.view_pager));
    }
}
