package com.zh.springo.service.impl;

import com.zh.springo.config.Result;
import com.zh.springo.mapper.FlightMapper;
import com.zh.springo.mapper.UserBehaviorMapper;
import com.zh.springo.pojo.UserBehaviorDomain;
import com.zh.springo.service.RecAlgorithmService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericItemPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RecAlgorithmImpl implements RecAlgorithmService {


    @Resource
    private UserBehaviorMapper userBehaviorMapper;

    @Resource
    private FlightMapper flightMapper;


    @Override
    public Result cityRecommend(Long userId) {
        List<UserBehaviorDomain> aLlBehaviors = userBehaviorMapper.getALlBehaviors();
        DataModel dataModel = this.createDataModel(aLlBehaviors);
        List<Long> collect;
        try {
            UserSimilarity userSimilarity = new UncenteredCosineSimilarity(dataModel);
            //构建最近邻居集
            UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(3, userSimilarity, dataModel);
            //构建推荐器
            Recommender recommender = new GenericBooleanPrefUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
            //推荐五个
            List<RecommendedItem> recommend = recommender.recommend(userId, 5);
            //
            collect = recommend.stream().map(RecommendedItem::getItemID).collect(Collectors.toList());
            List<Map<Long, Object>> cities = flightMapper.getCities(collect);
            return new Result(200, cities);
        } catch (TasteException e) {
            throw new RuntimeException(e);
        }
    }


    private DataModel createDataModel(List<UserBehaviorDomain> userBehaviors) {
        FastByIDMap<PreferenceArray> fastByIDMap = new FastByIDMap<>();
        Map<Long, List<UserBehaviorDomain>> map =
                userBehaviors.stream().collect(Collectors.groupingBy(UserBehaviorDomain::getUserId));
        Collection<List<UserBehaviorDomain>> values = map.values();
        for (List<UserBehaviorDomain> value : values) {
            GenericPreference[] preferences = new GenericPreference[value.size()];
            for (int i = 0; i < value.size(); i++) {
                UserBehaviorDomain domain = value.get(i);
                GenericPreference item = new GenericPreference(domain.getUserId(), domain.getCityId(), domain.getValue());
                preferences[i] = item;
            }
            fastByIDMap.put(preferences[0].getUserID(), new GenericUserPreferenceArray(List.of(preferences)));
        }
        return new GenericDataModel(fastByIDMap);
    }
}
