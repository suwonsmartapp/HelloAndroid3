
package com.jsoh.myfirstandroidapp.exam_coffee;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;

import com.jsoh.myfirstandroidapp.R;
import com.jsoh.myfirstandroidapp.databinding.ActivityCoffeeBinding;
import com.jsoh.myfirstandroidapp.utils.RandomAnimationUtil;

public class CoffeeActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int COFFEE_PRICE = 1500;
    public static final int CREAM_PRICE = 500;

    private int mQuantity = 0;

//    private CheckBox mCheckCream;
//    private EditText mEtCustomerName;
//    private TextView mTvQuantity;
//    private TextView mTvSummary;
    private Animation mShakeAnimation;

    ActivityCoffeeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_coffee);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_coffee);

        View rootView = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
        RandomAnimationUtil.randomAnimantionStart(this, rootView);

        mShakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        mShakeAnimation.setInterpolator(new CycleInterpolator(20));

        mBinding.customerNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.customerNameEdit.startAnimation(mShakeAnimation);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 버튼들 이벤트
        mBinding.minusBtn.setOnClickListener(this);
        mBinding.plusBtn.setOnClickListener(this);
        mBinding.orderBtn.setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            findViewById(android.R.id.content).startAnimation(mShakeAnimation);

            return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.minus_btn:
                mQuantity--;
                if (mQuantity < 0) {
                    mQuantity = 0;
                }
                mBinding.quantityText.setText("" + mQuantity);
                break;
            case R.id.plus_btn:
                Animation ani = AnimationUtils.loadAnimation(this, R.anim.rotate2);
                ani.setInterpolator(new CycleInterpolator(10));
                v.startAnimation(ani);
                mQuantity++;
                mBinding.quantityText.setText("" + mQuantity);
                break;
            case R.id.order_btn:
                printSummary();
                break;

        }
    }

    private void printSummary() {
        int totalPrice = COFFEE_PRICE * mQuantity;

        if (mBinding.creamCheck.isChecked()) {
            totalPrice += CREAM_PRICE;
        }

        StringBuilder sb = new StringBuilder("주문요약");
        sb.append("\n이름 : ").append(mBinding.customerNameEdit.getText().toString());
        sb.append("\n수량 : ").append(mQuantity);
        sb.append("\n휘핑크림 추가 여부 : ").append(mBinding.creamCheck.isChecked());
        sb.append("\n합계 : ").append(totalPrice);

        mBinding.summaryText.setText(sb);
    }
}
