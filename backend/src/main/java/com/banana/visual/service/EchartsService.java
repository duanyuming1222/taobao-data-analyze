package com.banana.visual.service;

import com.banana.visual.VO.echarts.EchartsVo;
import com.banana.visual.VO.echarts.UserBehaviorHourVo;
import com.banana.visual.VO.echarts.UserRetentionVo;

import java.util.List;

public interface EchartsService {
    List<EchartsVo> getEchartsList();

    List<UserRetentionVo> getClickUsers();

    List<UserRetentionVo> getDirectBuyUsers();

    List<UserRetentionVo> getFavBuyUsers();


    List<UserRetentionVo> getCartBuyUsers();

     List<UserRetentionVo> getBothBuyUsers();

    List<UserBehaviorHourVo> getUserBehaviorHour();
}
