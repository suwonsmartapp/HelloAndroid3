
package com.jsoh.myfirstandroidapp.exam_fragment.exam2;

import com.jsoh.myfirstandroidapp.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Exam212Activity extends AppCompatActivity implements
        ButtonSixFragment.OnFragmentInteractionListener {

    private TextView mScore1TextView;
    private TextView mScore2TextView;

    private int mScore1 = 0;
    private int mScore2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam212);

        mScore1TextView = (TextView) findViewById(R.id.score1);
        mScore2TextView = (TextView) findViewById(R.id.score2);
    }

    @Override
    public void onFragmentInteraction(View view) {
        switch (view.getId()) {
            case R.id.button_plus1:
                mScore1++;
                mScore1TextView.setText("" + mScore1);
                break;
            case R.id.button_plus2:
                mScore2++;
                mScore2TextView.setText("" + mScore2);
                break;
            case R.id.button_minus1:
                mScore1--;
                mScore1TextView.setText("" + mScore1);
                break;
            case R.id.button_minus2:
                mScore2--;
                mScore2TextView.setText("" + mScore2);
                break;
            case R.id.button_reset1:
                mScore1 = 0;
                mScore1TextView.setText("" + mScore1);
                break;
            case R.id.button_reset2:
                mScore2 = 0;
                mScore2TextView.setText("" + mScore2);
                break;

        }
    }
}
