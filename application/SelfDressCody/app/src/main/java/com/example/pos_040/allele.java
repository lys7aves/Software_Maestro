package com.example.pos_040;

public class allele {
    private int id;
    private int memo;
    private String image;
    private int color;
    private int sort;
    private int feel;
    private int hv;

    public allele(int memo, String image, int color, int sort, int feel, int hv, int id) {
        this.memo = memo;
        this.image = image;
        this.color = color;
        this.sort = sort;
        this.feel = feel;
        this.hv = hv;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemo() {
        return memo;
    }

    public void setMemo(int name) {
        this.memo = memo;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getFeel() {
        return feel;
    }

    public void setFeel(int feel) {
        this.feel = feel;
    }

    public int getHv() {return hv;}

    public void setHv(int hv) {this.hv = hv;}
}
