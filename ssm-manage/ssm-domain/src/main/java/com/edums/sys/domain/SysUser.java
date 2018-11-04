package com.edums.sys.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name="sys_user")
public class SysUser implements Serializable {
    /**
     *	主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     *	用户名
     */
    private String username;
    /**
     *	密码
     */
    private String passwd;
    /**
     *	手机号码
     */
    private String phone;
    /**
     *	帐号类型
     */
    private Byte accountType;
    /**
     *	邮箱
     */
    private String email;
    /**
     *	创建时间
     */
    private String createTime;
    /**
     *	最后修改时间
     */
    private String modifyTime;
    /**
     *	状态，0、已删除 1、正常  2、无效
     */
    private Byte statusId;
    /**
     *	真实姓名
     */
    private String realName;
    /**
     *	头像
     */
    private String userImg;
    /**
     *	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private Byte sex;
    /**
     *	所在地区id
     */
    private String areaIds;
    /**
     *	省
     */
    private String areaProvince;
    /**
     *	市
     */
    private String areaCity;
    /**
     *	区
     */
    private String areaDistrict;
    /**
     *	用户来源类型
     */
    private Byte sourceType;
    /**
     *	是否已经绑定手机号码 0：否 1：是
     */
    private Byte isBindMobile;
    /**
     *	备注
     */
    private String remark;
    /**
     *	微信授权openid
     */
    private String wxOpenid;
    /**
     *	微信unionid
     */
    private String wxUnionid;
    /**
     *	微信授权refreshToken
     */
    private String wxRefreshToken;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getAccountType() {
        return accountType;
    }

    public void setAccountType(Byte accountType) {
        this.accountType = accountType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Byte getStatusId() {
        return statusId;
    }

    public void setStatusId(Byte statusId) {
        this.statusId = statusId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

    public String getAreaProvince() {
        return areaProvince;
    }

    public void setAreaProvince(String areaProvince) {
        this.areaProvince = areaProvince;
    }

    public String getAreaCity() {
        return areaCity;
    }

    public void setAreaCity(String areaCity) {
        this.areaCity = areaCity;
    }

    public String getAreaDistrict() {
        return areaDistrict;
    }

    public void setAreaDistrict(String areaDistrict) {
        this.areaDistrict = areaDistrict;
    }

    public Byte getSourceType() {
        return sourceType;
    }

    public void setSourceType(Byte sourceType) {
        this.sourceType = sourceType;
    }

    public Byte getIsBindMobile() {
        return isBindMobile;
    }

    public void setIsBindMobile(Byte isBindMobile) {
        this.isBindMobile = isBindMobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public String getWxUnionid() {
        return wxUnionid;
    }

    public void setWxUnionid(String wxUnionid) {
        this.wxUnionid = wxUnionid;
    }

    public String getWxRefreshToken() {
        return wxRefreshToken;
    }

    public void setWxRefreshToken(String wxRefreshToken) {
        this.wxRefreshToken = wxRefreshToken;
    }
}
