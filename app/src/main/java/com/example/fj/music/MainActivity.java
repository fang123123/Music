package com.example.fj.music;

import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private ListView mListView;
    private List<MusicItem> list;
    private MusicAdapter adapter;
    private static final String TAG="myTag";
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private String Path=null;
    private boolean flag=true;
    private TextView SongInfo;
    private TextView CurTime;
    private TextView TotalTime;
    private ImageButton txtLoopState;
    private ImageButton buttonPrevious;
    private ImageButton buttonStart;
    private ImageButton buttonNext;
    private SeekBar seekBar;
    private Thread newThread;         //声明一个子线程
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("123", "main");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SongInfo=(TextView)findViewById(R.id.SongInfo);
        SongInfo.setSelected(true);
        CurTime=(TextView)findViewById(R.id.CurTime);
        TotalTime=(TextView)findViewById(R.id.TotalTime);
        txtLoopState = (ImageButton) findViewById(R.id.PlayerState);
        buttonPrevious = (ImageButton) findViewById(R.id.MainButton_Previous);
        buttonStart = (ImageButton) findViewById(R.id.MainButton_pause);
        buttonNext = (ImageButton) findViewById(R.id.MainButton_next);
        seekBar=(SeekBar)findViewById(R.id.MainSeekBar);
 //       SeekBar.setOnSeekBarChangeListener(this);
        initView();
        //开始播放
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    newThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (mediaPlayer != null) {
                                seekBar.setProgress((int)((double)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration()*100));
                                CurTime.setText(MusicUtil.formatTime(mediaPlayer.getCurrentPosition()));
                                Log.d("123", MusicUtil.formatTime(mediaPlayer.getCurrentPosition()));
                                // 每次延迟100毫秒再启动线程
                                handler.postDelayed(newThread, 100);
                            }
                        }
                    });
                    newThread.start(); //启动线程
                    if(flag) {
                        flag=false;
                        buttonStart.setBackground(getResources().getDrawable(R.drawable.start,null));
                        Log.d("123", "start");
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(Path);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    }else {
                        flag=true;
                        buttonStart.setBackground(getResources().getDrawable(R.drawable.pause,null));
                        Log.d("123", "pause");
                        mediaPlayer.pause();
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
/*    Runnable updateThread = new Runnable() {
        public void run() {
            // 获得歌曲现在播放位置并设置成播放进度条的值
            if (mediaPlayer != null) {
                Log.d("123", "seekbar");
                seekBar.setProgress((int)((double)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration())*100);
                // 每次延迟100毫秒再启动线程
                handler.postDelayed(updateThread, 100);
            }
        }
    };*/
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicItem item=(MusicItem) adapter.getItem(position);
                Path=item.getPath();
                Log.d("123",String.valueOf(position)+Path);
                SongInfo.setText(item.getTitle()+"--"+item.getArtist());
                TotalTime.setText(MusicUtil.formatTime(item.getDuration()));
            }
        });
    }
}