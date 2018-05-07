package com.example.fj.music;

        import android.service.quicksettings.Tile;

/**
 * Created by FJ on 2018/5/6.
 */

public class MusicItem {
    private String Title;
    private String Ablum;
    private String Artist;
    private String Path;
    private int Duration;
    private long Size;
    public MusicItem(String Title,String Ablum,String Artist,String Path,int Duration,long Size)
    {
        this.Title= Title;
        this.Ablum=Ablum;
        this.Artist=Artist;
        this.Path=Path;
        this.Duration=Duration;
        this.Size=Size;
    }
    public String getTitle(){
        return Title;
    }
    public void setTitle(String Title){
        this.Title=Title;
    }
    public String getAblum(){
        return Ablum;
    }
    public void setAblum(String Ablum){
        this.Ablum=Ablum;
    }
    public String getArtist(){
        return Artist;
    }
    public void setArtist(String Artist){
        this.Artist=Artist;
    }
    public String getPath(){
        return Path;
    }
    public void setPath(String Path){
        this.Path=Path;
    }
    public int getDuration(){
        return Duration;
    }
    public void setDuration(int Duration){
        this.Duration=Duration;
    }
    public long getSize(){
        return Size;
    }
    public void setSize(long Size){
        this.Size=Size;
    }
}
