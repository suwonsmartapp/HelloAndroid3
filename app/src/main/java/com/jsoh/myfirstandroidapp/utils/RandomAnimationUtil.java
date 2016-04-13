package com.jsoh.myfirstandroidapp.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.jsoh.myfirstandroidapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junsuk on 16. 4. 12..
 */
public class RandomAnimationUtil {

    public static void randomAnimantionStart(Context context, View rootView) {
        List<Animation> animationList = new ArrayList<>();
        animationList.add(AnimationUtils.loadAnimation(context, R.anim.translation_right_to_left));
        animationList.add(AnimationUtils.loadAnimation(context, R.anim.translation));
        animationList.add(AnimationUtils.loadAnimation(context, R.anim.translation_up));
        animationList.add(AnimationUtils.loadAnimation(context, R.anim.shake));
        animationList.add(AnimationUtils.loadAnimation(context, R.anim.scale));

        if (rootView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)rootView;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childView = viewGroup.getChildAt(i);

                int index = (int)(Math.random() * animationList.size());
                childView.startAnimation(animationList.get(index));
            }
        } else {
            int index = (int)(Math.random() * animationList.size());
            rootView.startAnimation(animationList.get(index));
        }
    }
}
