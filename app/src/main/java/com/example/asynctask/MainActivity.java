package com.example.asynctask;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetPathAsyncTask.LoadImageListener {
    public static final int REQUEST_STORAGE_PERMISSION = 1;
    public static final int NUMBER_OF_COLUMN = 2;
    private RecyclerView mRecyclerImage;
    private ImageAdapter mImageAdapter;
    private GetPathAsyncTask mGetPathAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getControls();
        requestPermission();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initUi();
                } else {
                    Toast.makeText(MainActivity.this, R.string.text_permission_denied, Toast.LENGTH_SHORT).show();
                }
                return;
            }
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mGetPathAsyncTask = null;
        super.onDestroy();
    }

    @Override
    public void onLoadImage(List<String> paths) {
        mImageAdapter.setData(paths);
        mRecyclerImage.setAdapter(mImageAdapter);
    }

    public void getControls() {
        mRecyclerImage = findViewById(R.id.recycler);
    }

    public void initUi() {
        mRecyclerImage.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMN);
        mRecyclerImage.setLayoutManager(gridLayoutManager);
        mImageAdapter = new ImageAdapter(this);
        mGetPathAsyncTask = new GetPathAsyncTask(this, this);
        mGetPathAsyncTask.execute();
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_STORAGE_PERMISSION);
    }
}
