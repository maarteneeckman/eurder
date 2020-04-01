package com.switchfully.eurder.security.authentication.feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Feature {
    ADD_ITEM(EurderRole.ADMIN);




    private EurderRole[] roles;

    Feature(EurderRole... roles){
        this.roles = roles;
    }

    public List<EurderRole> getRoles(){
        return List.of(roles);
    }

    public static List<Feature> getFeaturesForRoles(List<EurderRole> rolesOfUser){

        List<Feature> listOfAllFeatures = List.of(Feature.values());
        List<Feature> allowedFeatures = new ArrayList<>();
        for(Feature feature : listOfAllFeatures){
            if(!Collections.disjoint(feature.getRoles(), rolesOfUser)){
                allowedFeatures.add(feature);
            }
        }
        return allowedFeatures;
    }

}
