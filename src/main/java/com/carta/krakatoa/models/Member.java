package com.carta.krakatoa.models;

import com.carta.krakatoa.enums.ShareClass;

import java.util.HashMap;

/**
 *
 * Represents a Member in a firm
 *
 * @author Joshua Song
 * @version 1.0
 */
public class Member {


    private String name;

    /**
     * Represents the shares owned by this member
     */
    protected HashMap<ShareClass, Integer> ownedShares;

    /**
     * Constructs a Member object with owned shares defaulted to 0
     *
     * @param name - The name of the member
     */
    public Member(String name) {
        this.name = name;
        ownedShares = new HashMap<>();
        for(ShareClass c : ShareClass.values()) {
            ownedShares.put(c, 0);
        }
    }

    /**
     * Adds shares to the map of owned shares
     *
     * @param c - The class of the share to be added
     * @param n - The number of shares to add
     */
    public void addShares(ShareClass c, Integer n) {
        ownedShares.put(c, ownedShares.get(c) + n);
    }

    /**
     * Returns the map of the member's owned shares
     *
     * @return owned shares map
     */
    public HashMap<ShareClass, Integer> getOwnedShares() {
        return ownedShares;
    }

    /**
     * Returns the name of the member
     *
     * @return name of the member
     */
    public String getName() {
        return name;
    }

}
