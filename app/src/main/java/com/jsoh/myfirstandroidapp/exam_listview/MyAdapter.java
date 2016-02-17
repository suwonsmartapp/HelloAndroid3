
package com.jsoh.myfirstandroidapp.exam_listview;

import com.jsoh.myfirstandroidapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by junsuk on 16. 2. 17..
 */
public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private List<MyItem> mData;

    public MyAdapter(Context context, List<MyItem> data) {
        mContext = context;
        mData = data;
    }

    // 아이템의 갯수
    @Override
    public int getCount() {
        return mData.size();
    }

    // position 번째 아이템 리턴
    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    // position 번째 아이템 id 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 아이템 한 개의 View 를 완성 하는 곳
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // 최초 로드 할 때

            // 레이아웃을 가져오게 하는 객체
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_list, parent, false);
        }

        // 데이터를 연결 할 View
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageview);
        TextView title = (TextView) convertView.findViewById(R.id.title_text);
        TextView description = (TextView) convertView.findViewById(R.id.description_text);

        // 데이터
        MyItem item = (MyItem) getItem(position);

        // 데이터 설정
        imageView.setImageResource(item.getImageRes());
        title.setText(item.getTitle());
        description.setText(item.getDescription());

        return convertView;
    }
}
