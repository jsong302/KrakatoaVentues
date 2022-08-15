package com.carta.krakatoa.models;

import com.carta.krakatoa.enums.ShareClass;

import java.util.HashMap;

public class Member {

    String name;
    protected HashMap<ShareClass, Integer> ownedShares;

    public Member(String name) {
        this.name = name;
        ownedShares = new HashMap<>();
        for(ShareClass c : ShareClass.values()) {
            ownedShares.put(c, 0);
        }
    }

    public void addShares(ShareClass c, Integer n) {
        ownedShares.put(c, ownedShares.get(c) + n);
    }

}
