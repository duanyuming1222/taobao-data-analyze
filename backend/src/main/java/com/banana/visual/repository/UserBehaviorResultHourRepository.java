package com.banana.visual.repository;

import com.banana.visual.entity.mysql.DataRandom;
import com.banana.visual.entity.mysql.UserBehaviorResultHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserBehaviorResultHourRepository extends JpaRepository<UserBehaviorResultHour, Long> {

    @Query(nativeQuery = true, value =
            "SELECT *, CAST(time AS UNSIGNED) AS timeInt FROM user_behavior_result_hour order by timeInt")
    List<Map<String,String>> getUserBehaviorResultHour();

}
