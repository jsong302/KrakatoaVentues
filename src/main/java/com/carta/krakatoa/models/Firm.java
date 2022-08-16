package com.carta.krakatoa.models;


import com.carta.krakatoa.enums.ShareClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Firm {

    String name;
    double proceeds;
    Set<Member> shareholders;
    HashMap<Member, Double> investments;
    HashMap<ShareClass, Double> sharePriceMap;
    HashMap<ShareClass, Integer> shareDistMap;
    HashMap<Member, HashMap<ShareClass, Double>> proceedsDistMap;


    public Firm(String name, double proceeds) {
        this.name = name;
        this.shareholders = new HashSet<>();
        this.proceeds = proceeds;
        this.sharePriceMap = new HashMap<>();
        this.investments = new HashMap<>();
        this.shareDistMap = new HashMap<>();
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

    public HashMap<ShareClass, Integer> getShareDistMap() {
        return shareDistMap;
    }

    public void addInvestment(Member m, Double num) {
        investments.put(m, investments.getOrDefault(m, 0.0) + num);
    }

    private void repayInvestment() {
        int totalShares = shareDistMap.get(ShareClass.B);
        double dues = investments.values().stream().mapToDouble(f -> f.doubleValue()).sum();
        if(dues > proceeds) {
            double pricePerShare = proceeds / totalShares;
            for(Member m : shareholders) {
                if(!proceedsDistMap.containsKey(m)) proceedsDistMap.put(m, new HashMap<>());
                HashMap<ShareClass, Double> shareClassMap = proceedsDistMap.get(m);
                shareClassMap.put(ShareClass.B, shareClassMap.getOrDefault(ShareClass.B, 0.0) + (m.getOwnedShares().get(ShareClass.B) * pricePerShare));
                proceedsDistMap.put(m, shareClassMap);
                if(investments.containsKey(m))proceeds -= proceedsDistMap.get(m).get(ShareClass.B);
            }
        } else {
            for(Member m : shareholders) {
                if(!proceedsDistMap.containsKey(m)) proceedsDistMap.put(m, new HashMap<>());
                HashMap<ShareClass, Double> shareClassMap = proceedsDistMap.get(m);
                shareClassMap.put(ShareClass.B, shareClassMap.getOrDefault(ShareClass.B, 0.0) + investments.getOrDefault(m, 0.0));
                proceedsDistMap.put(m, shareClassMap);
                if(investments.containsKey(m))proceeds -= investments.get(m);
            }
        }
    }

    public void distributeProceeds() {
        repayInvestment();
        int totalShares = shareDistMap.get(ShareClass.A) + shareDistMap.get(ShareClass.B) + shareDistMap.get(ShareClass.C);
        double pricePerShare = proceeds / totalShares;
        for(Member m : shareholders) {
            if(!proceedsDistMap.containsKey(m)) proceedsDistMap.put(m, new HashMap<>());
            HashMap<ShareClass, Double> shareClassMap = proceedsDistMap.get(m);
            shareClassMap.put(ShareClass.A, shareClassMap.getOrDefault(ShareClass.A, 0.0) + (m.getOwnedShares().get(ShareClass.A) * pricePerShare));
            shareClassMap.put(ShareClass.B, shareClassMap.getOrDefault(ShareClass.B, 0.0) + (m.getOwnedShares().get(ShareClass.B) * pricePerShare));
            shareClassMap.put(ShareClass.C, shareClassMap.getOrDefault(ShareClass.C, 0.0) + (m.getOwnedShares().get(ShareClass.C) * pricePerShare));
            proceedsDistMap.put(m, shareClassMap);
        }
        proceeds = 0;
    }

    public HashMap<Member, HashMap<ShareClass, Double>> getProceedsDistMap() {
        return proceedsDistMap;
    }
}
