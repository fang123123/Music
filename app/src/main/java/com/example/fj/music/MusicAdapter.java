package com.example.fj.music;

/**
 * Created by FJ on 2018/5/6.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MusicAdapter extends ArrayAdapter{
    private final int resourceId;

    public MusicAdapter(Context context, int textViewResourceId, List<MusicItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MusicItem musicItem = (MusicItem) getItem(position); // 获取当前项的Music实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        TextView musicId=(TextView)view.findViewById(R.id.MusicId);
        TextView musicTitle = (TextView) view.findViewById(R.id.MusicTitle);//获取该布局内的文本视图
        TextView musicArtist=(TextView)view.findViewById(R.id.MusicArtist);
        musicId.setText(String.valueOf(position+1));
        musicTitle.setText(musicItem.getTitle());//为文本视图设置文本内容
        musicArtist.setText(musicItem.getArtist()+musicItem.getAblum());
        return view;
    }
}