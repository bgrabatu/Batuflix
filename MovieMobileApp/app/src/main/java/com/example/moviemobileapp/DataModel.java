package com.example.moviemobileapp;

public class DataModel {
    private String Ttitle;
    private String Turl;
    private String Tvid;
    private String Tfeature1;
    private String Tfeature2;
    private String Tfeature3;
    private String Tfeature4;





    //For Firebase Database
    public DataModel() {
    }

    public DataModel(String ttitle, String turl, String tvid, String tfeature1, String tfeature2, String tfeature3, String tfeature4) {
        Ttitle = ttitle;
        Turl = turl;
        Tvid = tvid;
        Tfeature1 = tfeature1;
        Tfeature2 = tfeature2;
        Tfeature3 = tfeature3;
        Tfeature4 = tfeature4;

    }



    public String getTfeature1() {
        return Tfeature1;
    }

    public void setTfeature1(String tfeature1) {
        Tfeature1 = tfeature1;
    }

    public String getTfeature2() {
        return Tfeature2;
    }

    public void setTfeature2(String tfeature2) {
        Tfeature2 = tfeature2;
    }

    public String getTfeature3() {
        return Tfeature3;
    }

    public void setTfeature3(String tfeature3) {
        Tfeature3 = tfeature3;
    }

    public String getTfeature4() {
        return Tfeature4;
    }

    public void setTfeature4(String tfeature4) {
        Tfeature4 = tfeature4;
    }

    public String getTtitle() {
        return Ttitle;
    }

    public void setTtitle(String ttitle) {
        Ttitle = ttitle;
    }

    public String getTurl() {
        return Turl;
    }

    public void setTurl(String turl) {
        Turl = turl;
    }

    public String getTvid() {
        return Tvid;
    }

    public void setTvid(String tvid) {
        Tvid = tvid;
    }
}
