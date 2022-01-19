package com.example.pos_040;

import java.io.Serializable;

// dashboard 탭 구성요소
public class cardModel implements Serializable {

    private int id1;
    private int id2;
    private int id3;
    private String image;
    private String image2;
    private String image3;
    private String descrip;
    private int star;
    private int color1;
    private int color2;
    private int color3;

    public cardModel(String image, String image2, String image3, int id1, int id2, int id3, int color1, int color2, int color3, String descrip, int star) {
        this.image = image;
        this.image2 = image2;
        this.image3 = image3;
        this.id1 = id1;
        this.id2 = id2;
        this.id3 = id3;
        this.descrip = descrip;
        this.star = star;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public int getId3() {return id3;}

    public void setId3(int id3) {this.id3 = id3;}

    public String getDescrip() {return descrip;}

    public void setDescrip(String descrip) {this.descrip = descrip;}

    public int getStar() {return star;}

    public void setStar(int star) {this.star = star;}

    public int getColor1() {return color1; }

    public void setColor1(int color1) {this.color1 = color1;}

    public int getColor2() {return color2;}

    public void setColor2(int color2) {this.color2 = color2;}

    public int getColor3() {return color3;}

    public void setColor3(int color3) {this.color3 = color3;}
}
