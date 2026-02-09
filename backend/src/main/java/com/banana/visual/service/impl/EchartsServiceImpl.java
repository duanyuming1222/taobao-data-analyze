package com.banana.visual.service.impl;

import com.alibaba.fastjson.JSON;
import com.banana.visual.VO.echarts.EchartsVo;
import com.banana.visual.VO.echarts.UserBehaviorHourVo;
import com.banana.visual.VO.echarts.UserRetentionVo;
import com.banana.visual.repository.DataRandomRepository;
import com.banana.visual.repository.UserBehaviorResultHourRepository;
import com.banana.visual.service.EchartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EchartsServiceImpl implements EchartsService {
    @Autowired
    private DataRandomRepository dataRandomRepository;
    @Autowired
    private UserBehaviorResultHourRepository userBehaviorResultHourRepository;
    @Override
    public List<EchartsVo> getEchartsList() {
        List<Map<String,String>> buyTop10 = dataRandomRepository.findBuyTop10();
        String str = JSON.toJSONString(buyTop10);
        List<EchartsVo> echartsVoList = JSON.parseArray(str, EchartsVo.class);
        return echartsVoList;
    }

    @Override
    public List<UserRetentionVo> getClickUsers() {
        String str = JSON.toJSONString(dataRandomRepository.countClick());
        List<UserRetentionVo> clickUsers = JSON.parseArray(str, UserRetentionVo.class);
        return clickUsers;
    }

    @Override
    public List<UserRetentionVo> getDirectBuyUsers() {
        String str = JSON.toJSONString(dataRandomRepository.findDirectBuyUsers());
        List<UserRetentionVo> directBuyUsers = JSON.parseArray(str, UserRetentionVo.class);
        return directBuyUsers;
    }

    @Override
    public List<UserRetentionVo> getFavBuyUsers() {
        String str = JSON.toJSONString(dataRandomRepository.findFavBuyUsers());
        List<UserRetentionVo> favBuyUsers = JSON.parseArray(str, UserRetentionVo.class);
        return favBuyUsers;
    }

    @Override
    public List<UserRetentionVo> getCartBuyUsers() {
        String str = JSON.toJSONString(dataRandomRepository.findCartBuyUsers());
        List<UserRetentionVo> cartBuyUsers = JSON.parseArray(str, UserRetentionVo.class);
        return cartBuyUsers;
    }

    @Override
    public List<UserRetentionVo> getBothBuyUsers() {
        String str = JSON.toJSONString(dataRandomRepository.findBothBuyUsers());
        List<UserRetentionVo> favAddCartUsers = JSON.parseArray(str, UserRetentionVo.class);
        return favAddCartUsers;
    }

    @Override
    public List<UserBehaviorHourVo> getUserBehaviorHour() {
        String str = JSON.toJSONString(userBehaviorResultHourRepository.getUserBehaviorResultHour());
        List<UserBehaviorHourVo> userBehaviorHourVos = JSON.parseArray(str, UserBehaviorHourVo.class);
        return userBehaviorHourVos;
    }
}
