
package com.jsoh.myfirstandroidapp.exam_listview;

import com.jsoh.myfirstandroidapp.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, AdapterView.OnItemSelectedListener {

    private ListView mListView;
    private List<String> mData;
    private ArrayAdapter<String> mAdapter;

    private GridView mGridView;

    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // View
        mListView = (ListView) findViewById(R.id.list);
        mGridView = (GridView) findViewById(R.id.grid);
        mSpinner = (Spinner) findViewById(R.id.spinner);

        // Data
        mData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mData.add("data " + i);
        }

        // Adapter
        mAdapter = new ArrayAdapter<String>(this,
                R.layout.item_list,
                mData);

        mListView.setAdapter(mAdapter);
        mGridView.setAdapter(mAdapter);
        mSpinner.setAdapter(mAdapter);

        // 클릭 이벤트
        mListView.setOnItemClickListener(this);

        // 롱 클릭 이벤트
        mListView.setOnItemLongClickListener(this);

        mSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(ListViewActivity.this, "position : " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(ListViewActivity.this, "longClick : " + position, Toast.LENGTH_SHORT).show();

        // 데이터 삭제
        mData.remove(position);

        // 화면 갱신 : Adapter에게 데이터 변경을 알려준다
        // -> ListView에 새로운 내용을 반영
        mAdapter.notifyDataSetChanged();

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
