package com.hengrtech.carheadline.net.model;

/**
 * 作者：loonggg on 2016/9/2 10:23
 */

public class CarSerialModel {


    /**
     * serialId : 4038
     * brandId : 20012
     * serialName : 高尔夫Plus
     * dealerPrice : 停销
     * picture :
     * uv : 27
     * saleStatus : -1
     */

    private int serialId;
    private int brandId;
    private String serialName;
    private String dealerPrice;
    private String picture;
    private int uv;
    private int saleStatus;

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public String getDealerPrice() {
        return dealerPrice;
    }

    public void setDealerPrice(String dealerPrice) {
        this.dealerPrice = dealerPrice;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public int getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(int saleStatus) {
        this.saleStatus = saleStatus;
    }
}
