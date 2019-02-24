package com.example.asynctask;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class GetPathAsyncTask extends AsyncTask <Void, Void, List<String>> {
    private WeakReference<Context> mContext;
    private LoadImageListener mLoadImageListener;

    public GetPathAsyncTask(Context context, LoadImageListener loadImageListener) {
        mContext = new WeakReference<>(context);
        mLoadImageListener = loadImageListener;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        return getAllShownImagesPath();
    }

    @Override
    protected void onPostExecute(List<String> paths) {
        if (mLoadImageListener != null) mLoadImageListener.onLoadImage(paths);
    }

    public List<String> getAllShownImagesPath() {
        Uri uri;
        Cursor cursor;
        int column_index_data;
        List<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        cursor = mContext.get().getContentResolver().query(uri, projection, null,
                null, null);
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(mContext.get().getString(R.string.text_pre_url) + absolutePathOfImage);
        }
        cursor.close();
        return listOfAllImages;
    }

    public interface LoadImageListener {
        void onLoadImage(List<String> paths);
    }
}
