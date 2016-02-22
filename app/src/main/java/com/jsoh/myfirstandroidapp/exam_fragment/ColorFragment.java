
package com.jsoh.myfirstandroidapp.exam_fragment;

import com.jsoh.myfirstandroidapp.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ColorFragment extends Fragment {

    private ImageView mImageView;

    public ColorFragment() {
        // Required empty public constructor
    }

    public static ColorFragment newInstance() {
        return new ColorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_color, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mImageView = (ImageView) view.findViewById(R.id.color_image);
    }

    public void setColor(int color) {
        mImageView.setBackgroundColor(color);
    }
}
