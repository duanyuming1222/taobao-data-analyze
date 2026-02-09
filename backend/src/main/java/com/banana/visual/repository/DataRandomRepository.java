package com.banana.visual.repository;

import com.banana.visual.entity.mysql.DataRandom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DataRandomRepository extends JpaRepository<DataRandom, Long> {
    @Query(nativeQuery = true, value = "select count(*) from data_random")
    Long countDataRandomNum();

    @Query(nativeQuery = true, value =
            "SELECT Category_ID,浏览量,购买量,concat(round(购买量*100/浏览量,3),'%') as 转化率,商品数量\n" +
                    "FROM\n" +
                    "(SELECT Category_ID,count(*) as 浏览量,\n" +
                    "sum(case when Behavior_type='buy' then 1 else 0 end) as 购买量,\n" +
                    "count(DISTINCT item_id) as 商品数量\n" +
                    "from data_random\n" +
                    "GROUP BY Category_ID\n" +
                    "ORDER BY 购买量 desc\n" +
                    "LIMIT 10) data_random")
    List<Map<String,String>> findBuyTop10();

    @Query(nativeQuery = true, value =
            "SELECT\n" +
                    "SUM(case WHEN 点击量>0 THEN 1 else 0 END) as 点击\n" +
                    "FROM(\n" +
                    "SELECT User_ID,count(Behavior_type) as '用户行为总数',\n" +
                    "SUM(case WHEN Behavior_type = 'pv' THEN 1 ELSE 0 END) as '点击量',\n" +
                    "SUM(case WHEN Behavior_type = 'fav' THEN 1 ELSE 0 END) as '收藏',\n" +
                    "SUM(case WHEN Behavior_type = 'cart' THEN 1 ELSE 0 END) as '加购物车',\n" +
                    "SUM(case WHEN Behavior_type = 'buy' THEN 1 ELSE 0 END) as '购买'\n" +
                    "FROM data_random\n" +
                    "GROUP BY User_ID\n" +
                    "ORDER BY User_ID DESC) a ")
    List<Map<String,String>> countClick();

    @Query(nativeQuery = true, value =
            "SELECT\n" +
                    "SUM(case WHEN 购买>0 THEN 1 else 0 END) as 购买\n" +
                    "FROM(\n" +
                    "SELECT User_ID,count(Behavior_type) as '用户行为总数',\n" +
                    "SUM(case WHEN Behavior_type = 'pv' THEN 1 ELSE 0 END) as '点击量',\n" +
                    "SUM(case WHEN Behavior_type = 'fav' THEN 1 ELSE 0 END) as '收藏',\n" +
                    "SUM(case WHEN Behavior_type = 'cart' THEN 1 ELSE 0 END) as '加购物车',\n" +
                    "SUM(case WHEN Behavior_type = 'buy' THEN 1 ELSE 0 END) as '购买'\n" +
                    "FROM data_random\n" +
                    "GROUP BY User_ID\n" +
                    "ORDER BY User_ID DESC) a\n" +
                    "WHERE 收藏=0 and 加购物车=0")
    List<Map<String,String>> findDirectBuyUsers();

    @Query(nativeQuery = true, value =
            "SELECT\n" +
                    "SUM(case WHEN 收藏>0 THEN 1 else 0 END) as 收藏,\n" +
                    "SUM(case WHEN 购买>0 THEN 1 else 0 END) as 购买\n" +
                    "FROM(\n" +
                    "SELECT User_ID,count(Behavior_type) as '用户行为总数',\n" +
                    "SUM(case WHEN Behavior_type = 'pv' THEN 1 ELSE 0 END) as '点击量',\n" +
                    "SUM(case WHEN Behavior_type = 'fav' THEN 1 ELSE 0 END) as '收藏',\n" +
                    "SUM(case WHEN Behavior_type = 'cart' THEN 1 ELSE 0 END) as '加购物车',\n" +
                    "SUM(case WHEN Behavior_type = 'buy' THEN 1 ELSE 0 END) as '购买'\n" +
                    "FROM data_random\n" +
                    "GROUP BY User_ID\n" +
                    "ORDER BY User_ID DESC) a\n" +
                    "WHERE 收藏!=0 and 加购物车=0")
    List<Map<String,String>> findFavBuyUsers();

    @Query(nativeQuery = true, value =
            "SELECT\n" +
                    "SUM(case WHEN 加购物车>0 THEN 1 else 0 END) as 加购物车,\n" +
                    "SUM(case WHEN 购买>0 THEN 1 else 0 END) as 购买\n" +
                    "FROM(\n" +
                    "SELECT User_ID,count(Behavior_type) as '用户行为总数',\n" +
                    "SUM(case WHEN Behavior_type = 'pv' THEN 1 ELSE 0 END) as '点击量',\n" +
                    "SUM(case WHEN Behavior_type = 'fav' THEN 1 ELSE 0 END) as '收藏',\n" +
                    "SUM(case WHEN Behavior_type = 'cart' THEN 1 ELSE 0 END) as '加购物车',\n" +
                    "SUM(case WHEN Behavior_type = 'buy' THEN 1 ELSE 0 END) as '购买'\n" +
                    "FROM data_random\n" +
                    "GROUP BY User_ID\n" +
                    "ORDER BY User_ID DESC) a\n" +
                    "WHERE 收藏=0 and 加购物车!=0")
    List<Map<String,String>> findCartBuyUsers();

    @Query(nativeQuery = true, value =
            "SELECT\n" +
                    "(SUM(case WHEN 收藏>0 THEN 1 ELSE 0 END)+\n" +
                    "SUM(case WHEN 加购物车>0 THEN 1 ELSE 0 END)) as 收藏且加购物车,\n" +
                    "SUM(case WHEN 购买>0 THEN 1 ELSE 0 END) as 购买\n" +
                    "FROM(\n" +
                    "SELECT User_ID,count(Behavior_type) as '用户行为总数',\n" +
                    "SUM(case WHEN Behavior_type = 'pv' THEN 1 ELSE 0 END) as '点击量',\n" +
                    "SUM(case WHEN Behavior_type = 'fav' THEN 1 ELSE 0 END) as '收藏',\n" +
                    "SUM(case WHEN Behavior_type = 'cart' THEN 1 ELSE 0 END) as '加购物车',\n" +
                    "SUM(case WHEN Behavior_type = 'buy' THEN 1 ELSE 0 END) as '购买'\n" +
                    "FROM data_random\n" +
                    "GROUP BY User_ID\n" +
                    "ORDER BY User_ID DESC) a\n" +
                    "WHERE 收藏!=0 and 加购物车!=0")
    List<Map<String,String>> findBothBuyUsers();
}
