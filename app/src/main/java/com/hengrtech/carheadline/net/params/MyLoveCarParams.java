package com.hengrtech.carheadline.net.params;

/**
 * 作者：loonggg on 2016/9/14 09:11
 */

public class MyLoveCarParams {
    private int serialId;

    public MyLoveCarParams(int serialId) {
        this.serialId = serialId;
    }


    public int getCarSerialId() {
        return serialId;
    }

    public void setCarSerialId(int serialId) {
        this.serialId = serialId;
    }

}
