package com.jsoh.myfirstandroidapp.load_picture;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.jsoh.myfirstandroidapp.R;
import com.suwonsmartapp.abl.AsyncBitmapLoader;

public class PictureActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private PictureCursorAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        ListView listView = (ListView) findViewById(R.id.list);
        mAdapter = new PictureCursorAdapter(this, null);
        listView.setAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(PictureActivity.this,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    private static class PictureCursorAdapter extends CursorAdapter {

        private final LayoutInflater inflator;
        private AsyncBitmapLoader mAsyncBitmapLoader;

        public PictureCursorAdapter(Context context, Cursor c) {
            super(context, c, false);
            inflator = LayoutInflater.from(context);
            mAsyncBitmapLoader = new AsyncBitmapLoader(context);
            mAsyncBitmapLoader.setBitmapLoadListener(new AsyncBitmapLoader.BitmapLoadListener() {
                @Override
                public Bitmap getBitmap(String key) {
                    // Background Thread

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;   // 2의 배수, 큰 값일 수록 이미지 크기가 작아짐

                    return BitmapFactory.decodeFile(key, options);
                }
            });
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            View view = inflator.inflate(R.layout.item_image_list, parent, false);
            holder.imageView = (ImageView) view.findViewById(R.id.image);
            view.setTag(holder);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHolder holder = (ViewHolder) view.getTag();
            mAsyncBitmapLoader.loadBitmap(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)), holder.imageView);
        }

        static class ViewHolder {
            ImageView imageView;
        }
    }
}
