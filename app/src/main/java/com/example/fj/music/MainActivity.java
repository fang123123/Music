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
    private int Position=0;//当前音乐序号
    private String Path=null;  //当前音乐路径
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
        initView();
        //上一曲
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Position>=1) {
                    Position = Position - 1;
                    MusicItem item = (MusicItem) adapter.getItem(Position);
                    Path=item.getPath();
                    Log.d("123",String.valueOf(Position)+Path);
                    SongInfo.setText(item.getTitle()+"--"+item.getArtist());
                    TotalTime.setText(MusicUtil.formatTime(item.getDuration()));

                    try {
                        newThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (mediaPlayer != null) {
                                    seekBar.setProgress((int)((double)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration()*100));
                                    CurTime.setText(MusicUtil.formatTime(mediaPlayer.getCurrentPosition()));
                                    // 每次延迟100毫秒再启动线程
                                    handler.postDelayed(newThread, 100);
                                }
                            }
                        });
                        newThread.start(); //启动线程
                        flag=false;
                        buttonStart.setBackground(getResources().getDrawable(R.drawable.start,null));
                        Log.d("123", "previous");
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(Path);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
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
                               // Log.d("123", MusicUtil.formatTime(mediaPlayer.getCurrentPosition()));
                                // 每次延迟100毫秒再启动线程
                                handler.postDelayed(newThread, 100);
                            }
                        }
                    });
                    newThread.start(); //启动线程
                    if(flag) {
                        flag=false;
                        buttonStart.setBackground(getResources().getDrawable(R.drawable.start,null));
                        Log.d("123", "restart");
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
                }
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Position = Position+1;
                    MusicItem item = (MusicItem) adapter.getItem(Position);
                    Path=item.getPath();
                    Log.d("123",String.valueOf(Position)+Path);
                    SongInfo.setText(item.getTitle()+"--"+item.getArtist());
                    TotalTime.setText(MusicUtil.formatTime(item.getDuration()));

                    newThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (mediaPlayer != null) {
                                seekBar.setProgress((int)((double)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration()*100));
                                CurTime.setText(MusicUtil.formatTime(mediaPlayer.getCurrentPosition()));
                                // 每次延迟100毫秒再启动线程
                                handler.postDelayed(newThread, 100);
                            }
                        }
                    });
                    newThread.start(); //启动线程
                    flag=false;
                    buttonStart.setBackground(getResources().getDrawable(R.drawable.start,null));
                    Log.d("123", "previous");
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(Path);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                // 当拖动条的滑块位置发生改变时触发该方法,在这里直接使用参数progress，即当前滑块代表的进度值
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("123", "开始滑动！");
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("123", "停止滑动！");
                //进度条改变音乐播放进度
                mediaPlayer.seekTo((int)((double)seekBar.getProgress()/100*mediaPlayer.getDuration()));
                CurTime.setText(String.valueOf(MusicUtil.formatTime((int)((double)seekBar.getProgress()/100*mediaPlayer.getDuration()))));
            }
        });
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicItem item=(MusicItem) adapter.getItem(position);
                Position=position;
                Path=item.getPath();
                Log.d("123",String.valueOf(Position)+Path);
                SongInfo.setText(item.getTitle()+"--"+item.getArtist());
                TotalTime.setText(MusicUtil.formatTime(item.getDuration()));
                //启动音乐
                try {
                    newThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (mediaPlayer != null) {
                                seekBar.setProgress((int)((double)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration()*100));
                                CurTime.setText(MusicUtil.formatTime(mediaPlayer.getCurrentPosition()));
                                // 每次延迟100毫秒再启动线程
                                handler.postDelayed(newThread, 100);
                            }
                        }
                    });
                    newThread.start(); //启动线程
                    flag=false;
                    buttonStart.setBackground(getResources().getDrawable(R.drawable.start,null));
                    Log.d("123", "start");
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(Path);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
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
}