package com.focus_pay.shop.controller.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * MemberDto
 *
 * @author liuruichao
 * @date 15/9/12 下午4:08
 */
public class MemberDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer memberType;
    private Date expireTime;
    private Integer source;

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
