package com.jsoh.myfirstandroidapp.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by junsuk on 16. 3. 28..
 */
public class CircleImageView extends ImageView {

    private static final String TAG = CircleImageView.class.getSimpleName();

    // 하드코딩으로 생성시 호출 되는 생성자
    public CircleImageView(Context context) {
        this(context, null, 0);
        Log.d(TAG, "1");
    }

    // XML 에 정의할 때 호출 되는 생성자
    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Log.d(TAG, "2");
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "3");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Bitmap bitmap = ((BitmapDrawable)getDrawable()).getBitmap();
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);

        if (bitmap == null) {
            Log.d(TAG, "bitmap null");
        } else {
            Log.d(TAG, "bitmap not null");
            int radius = bitmap.getWidth() / 2;

            // radius ^ 2 = (x - radius) ^ 2 + (y - radius) ^ 2

            canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, radius, paint);
        }
    }
}
