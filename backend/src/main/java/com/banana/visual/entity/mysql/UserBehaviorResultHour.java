package com.banana.visual.entity.mysql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_behavior_result_hour")
public class UserBehaviorResultHour {
        @Id
        private String time;
        private Long totalCount;
        private Long pvCount;
        private Long favCount;
        private Long cartCount;
        private Long buyCount;
}
