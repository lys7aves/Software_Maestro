package com.example.pos_040;

// main 탭 구성요소
public class albumele {
    private int id;
    private int memo;
    private String image;
    private int sort;

    public albumele(int memo, String image, int sort, int id) {
        this.memo = memo;
        this.image = image;
        this.sort = sort;
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
}
