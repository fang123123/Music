package com.example.fj.music;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<MusicItem> list;
    private MusicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mListView = (ListView) findViewById(R.id.ListMusic);
        list = new ArrayList<>();
        //把扫描到的音乐赋值给list
        list = MusicUtil.getMusicData(this);
        adapter = new MusicAdapter(this, R.layout.layout_musicitem,list);
        mListView.setAdapter(adapter);
    }
}