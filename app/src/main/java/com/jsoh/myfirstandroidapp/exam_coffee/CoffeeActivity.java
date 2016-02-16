
package com.jsoh.myfirstandroidapp.exam_coffee;

import com.jsoh.myfirstandroidapp.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class CoffeeActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int COFFEE_PRICE = 1500;
    public static final int CREAM_PRICE = 500;

    private int mQuantity = 0;

    private CheckBox mCheckCream;
    private EditText mEtCustomerName;
    private TextView mTvQuantity;
    private TextView mTvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);

        mCheckCream = (CheckBox) findViewById(R.id.cream_check);
        mEtCustomerName = (EditText) findViewById(R.id.customer_name_edit);
        mTvQuantity = (TextView) findViewById(R.id.quantity_text);
        mTvSummary = (TextView) findViewById(R.id.summary_text);

        // 버튼들 이벤트
        findViewById(R.id.minus_btn).setOnClickListener(this);
        findViewById(R.id.plus_btn).setOnClickListener(this);
        findViewById(R.id.order_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.minus_btn:
                mQuantity--;
                if (mQuantity < 0) {
                    mQuantity = 0;
                }
                mTvQuantity.setText("" + mQuantity);
                break;
            case R.id.plus_btn:
                mQuantity++;
                mTvQuantity.setText("" + mQuantity);
                break;
            case R.id.order_btn:
                printSummary();
                break;

        }
    }

    private void printSummary() {
        int totalPrice = COFFEE_PRICE * mQuantity;

        if (mCheckCream.isChecked()) {
            totalPrice += CREAM_PRICE;
        }

        StringBuilder sb = new StringBuilder("주문요약");
        sb.append("\n이름 : ").append(mEtCustomerName.getText().toString());
        sb.append("\n수량 : ").append(mQuantity);
        sb.append("\n휘핑크림 추가 여부 : ").append(mCheckCream.isChecked());
        sb.append("\n합계 : ").append(totalPrice);

        mTvSummary.setText(sb);
    }
}
