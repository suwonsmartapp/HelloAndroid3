
package com.jsoh.myfirstandroidapp.exam_viewpager;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.exam_fragment.ColorFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class ScreenSlideActivity extends AppCompatActivity implements
        TabLayout.OnTabSelectedListener {
    // TabLayout
    private TabLayout mTabLayout;

    // View
    private ViewPager mViewPager;

    // Adapter
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab);

        // Tab 설정
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab 1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab 2"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab 3"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab 4"));
        // Tab 리스너 설정
        mTabLayout.setOnTabSelectedListener(this);

        // Adapter
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());

        // View 에 Adapter 붙이기
        mViewPager.setAdapter(mAdapter);

        // ViewPager 와 TabLayout연결
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        // Tab을 선택했을 때 ViewPager 의 페이지를 이동
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public static final int PAGE_COUNT = 4;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ScreenSlidePageFragment();
                case 1:
                    return ColorFragment.newInstance(Color.BLUE);
                case 2:
                    return ColorFragment.newInstance(Color.YELLOW);
                case 3:
                    return ColorFragment.newInstance(Color.RED);
                default:
                    return new ScreenSlidePageFragment();
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }
}
