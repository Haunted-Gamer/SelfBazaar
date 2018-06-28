package com.example.hrahm.selfbazaar;
import com.google.gson.annotations.SerializedName;

public class items {
    public items(int iId, String iName, String iDesc, String iPrice, String iPhoto, String iLocation, String iCategory, int uId) {
        this.iId = iId;
        this.iName = iName;
        this.iDesc = iDesc;
        this.iPrice = iPrice;
        this.iPhoto = iPhoto;
        this.iLocation = iLocation;
        this.iCategory = iCategory;
        this.uId = uId;
    }

    @SerializedName("i_id")
    int iId;

    @SerializedName("i_name")
    String iName;

    @SerializedName("i_desc")
    String iDesc;

    @SerializedName("i_price")
    String iPrice;

    @SerializedName("i_photo")
    String iPhoto;

    @SerializedName("i_location")
    String iLocation;

    @SerializedName("i_category")
    String iCategory;

    @SerializedName("u_id")
    int uId;


    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public String getiDesc() {
        return iDesc;
    }

    public void setiDesc(String iDesc) {
        this.iDesc = iDesc;
    }

    public String getiPrice() {
        return iPrice;
    }

    public void setiPrice(String iPrice) {
        this.iPrice = iPrice;
    }

    public String getiPhoto() {
        return iPhoto;
    }

    public void setiPhoto(String iPhoto) {
        this.iPhoto = iPhoto;
    }

    public String getiLocation() {
        return iLocation;
    }

    public void setiLocation(String iLocation) {
        this.iLocation = iLocation;
    }

    public String getiCategory() {
        return iCategory;
    }

    public void setiCategory(String iCategory) {
        this.iCategory = iCategory;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }
}