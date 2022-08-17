package com.carta.krakatoa.models;


import com.carta.krakatoa.enums.ShareClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class Firm {

    private String name;
    private double proceeds;
    private Set<Member> shareholders;
    private HashMap<Member, Double> investments;
    private HashMap<ShareClass, Double> sharePriceMap;
    private HashMap<ShareClass, Integer> shareDistMap;
    private HashMap<Member, HashMap<ShareClass, Double>> proceedsDistMap;

    /**
     *
     * @param name
     * @param proceeds
     */
    public Firm(String name, double proceeds) {
        this.name = name;
        this.shareholders = new HashSet<>();
        this.proceeds = proceeds >= 0 ? proceeds : 0.0;
        this.sharePriceMap = new HashMap<>();
        this.investments = new HashMap<>();
        this.shareDistMap = new HashMap<>();
        for(ShareClass c : ShareClass.values()) {
            shareDistMap.put(c, 0);
        }
        this.proceedsDistMap = new HashMap<>();
    }

    public void addMember(Member member) {
        shareholders.add(member);
    }

    public void addSharePrice(ShareClass c, Double n) {
        sharePriceMap.put(c, n);
    }

    public HashMap<ShareClass, Double> getSharePriceMap() {
        return sharePriceMap;
    }

    public double getProceeds() {
        return proceeds;
    }

    public void payProceeds(double d) {
        this.proceeds -= d;
    }

    public void setProceeds(double proceeds) {
        this.proceeds = proceeds;
    }

    public HashMap<ShareClass, Integer> getShareDistMap() {
        return shareDistMap;
    }

    public void addInvestment(Member m, Double num) {
        investments.put(m, investments.getOrDefault(m, 0.0) + num);
    }

    public HashMap<Member, HashMap<ShareClass, Double>> getProceedsDistMap() {
        return proceedsDistMap;
    }

    public HashMap<Member, Double> getInvestments() {
        return investments;
    }

    public void setInvestments(HashMap<Member, Double> investments) {
        this.investments = investments;
    }

    public Set<Member> getShareholders() {
        return shareholders;
    }

    public void setShareholders(Set<Member> shareholders) {
        this.shareholders = shareholders;
    }
}
