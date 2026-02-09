package com.banana.visual.VO.echarts;
import lombok.Data;

@Data
public class UserBehaviorHourVo {
    private String time;
    private Long totalCount;
    private Long pvCount;
    private Long favCount;
    private Long cartCount;
    private Long buyCount;
}
