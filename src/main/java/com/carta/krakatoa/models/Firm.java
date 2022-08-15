package com.carta.krakatoa.models;


import com.carta.krakatoa.enums.ShareClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Firm {

    String name;
    int proceeds;
    Set<Member> shareholders;
    HashMap<Member, Double> investments;
    HashMap<ShareClass, Double> sharePriceMap;

    public Firm(String name) {
        this.name = name;
        this.shareholders = new HashSet<>();
        this.proceeds = 0;
        this.sharePriceMap = new HashMap<>();
        this.investments = new HashMap<>();
    }

    public Firm(String name, int proceeds) {
        this.name = name;
        this.shareholders = new HashSet<>();
        this.proceeds = proceeds;
        this.sharePriceMap = new HashMap<>();
        this.investments = new HashMap<>();
    }

    public void setProceeds(int proceeds) {
        this.proceeds = proceeds;
    }

    public int getProceeds() {
        return proceeds;
    }

    public void addMember(Member member) {
        shareholders.add(member);
    }

    public void removeMember(Member member) {
        shareholders.remove(member);
    }

    public void addSharePrice(ShareClass c, Double n) {
        sharePriceMap.put(c, n);
    }

    public void removeSharePrice(ShareClass c) {
        sharePriceMap.remove(c);
    }

    public HashMap<ShareClass, Double> getSharePriceMap() {
        return sharePriceMap;
    }

    public void addInvestment(Member m, Double num) {
        investments.put(m, investments.getOrDefault(m, 0.0) + num);
    }

//    public double repayInvestment(Member m, Double num) {
//        if(investments)
//        investments.put(m, investments.getOrDefault(m, 0.0) + num);
//
//    }
}
