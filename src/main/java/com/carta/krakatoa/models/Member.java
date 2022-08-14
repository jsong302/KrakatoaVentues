package com.carta.krakatoa.models;

import com.carta.krakatoa.enums.ShareClass;

import java.util.HashMap;

public class Member {

    protected HashMap<ShareClass, Integer> ownedShares;

    public Member() {
        ownedShares = new HashMap<>();
        for(ShareClass c : ShareClass.values()) {
            ownedShares.put(c, 0);
        }
    }

}
