/*
 * 文件名: RegisterParams
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/4/29
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.hengrtech.carheadline.net.params;


/**
 * @author loonggg
 */
public class RegisterParams {
    private String memberId;
    private String mobileNo;
    private String password;

    public RegisterParams(String memberId, String mobileNo, String password) {
        this.memberId = memberId;
        this.mobileNo = mobileNo;
        this.password = password;
    }
}
