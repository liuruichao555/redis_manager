package com.focus_pay.shop.controller.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * PermissionDto
 *
 * @author liuruichao
 * @date 15/9/12 下午4:07
 */
public class PermissionDto implements Serializable{
    private static final long serialVersionUID = 1L;

    private List<MemberDto> MemberDtos;
    private Map<Integer, Integer> goodsIds;

    public List<MemberDto> getMemberDtos() {
        return MemberDtos;
    }

    public void setMemberDtos(List<MemberDto> memberDtos) {
        MemberDtos = memberDtos;
    }

    public Map<Integer, Integer> getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(Map<Integer, Integer> goodsIds) {
        this.goodsIds = goodsIds;
    }
}
