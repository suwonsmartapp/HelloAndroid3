
package com.jsoh.myfirstandroidapp.exam_fab_dialog;

import com.jsoh.myfirstandroidapp.R;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class FabAndDialogActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_and_dialog);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 다이얼로그
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("제목");
        builder.setIcon(R.mipmap.ic_launcher);
//        builder.setMessage("메세지");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == AlertDialog.BUTTON_POSITIVE) {
                    Toast.makeText(FabAndDialogActivity.this, "확인", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("닫기", null);
//        builder.setSingleChoiceItems(new String[]{"a", "b", "c"}, 0, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(FabAndDialogActivity.this, "" + which, Toast.LENGTH_SHORT).show();
//            }
//        });
        builder.setMultiChoiceItems(new String[]{"a", "b", "c"}, new boolean[]{false, false, false}, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                Toast.makeText(FabAndDialogActivity.this, "" + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

}
