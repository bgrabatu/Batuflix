package com.example.moviemobileapp;

public class CartoonModel {
    private String Ccast;
    private String Ccover;
    private String Cdes;
    private String Clink;
    private String Cthumb;
    private String Ctitle;
    private String Tlink;

    public CartoonModel() {
    }

    public CartoonModel(String ccast, String ccover, String cdes, String clink, String cthumb, String ctitle, String tlink) {
        Ccast = ccast;
        Ccover = ccover;
        Cdes = cdes;
        Clink = clink;
        Cthumb = cthumb;
        Ctitle = ctitle;
        Tlink = tlink;
    }

    public String getCcast() {
        return Ccast;
    }

    public void setCcast(String ccast) {
        Ccast = ccast;
    }

    public String getCcover() {
        return Ccover;
    }

    public void setCcover(String ccover) {
        Ccover = ccover;
    }

    public String getCdes() {
        return Cdes;
    }

    public void setCdes(String cdes) {
        Cdes = cdes;
    }

    public String getClink() {
        return Clink;
    }

    public void setClink(String clink) {
        Clink = clink;
    }

    public String getCthumb() {
        return Cthumb;
    }

    public void setCthumb(String cthumb) {
        Cthumb = cthumb;
    }

    public String getCtitle() {
        return Ctitle;
    }

    public void setCtitle(String ctitle) {
        Ctitle = ctitle;
    }

    public String getTlink() {
        return Tlink;
    }

    public void setTlink(String tlink) {
        Tlink = tlink;
    }
}
