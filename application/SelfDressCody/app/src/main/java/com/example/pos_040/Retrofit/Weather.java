package com.example.pos_040.Retrofit;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("main")
    String icon;

    public String getIcon() {return icon;}

    public void setIcon(String icon) {this.icon = icon;}
}
