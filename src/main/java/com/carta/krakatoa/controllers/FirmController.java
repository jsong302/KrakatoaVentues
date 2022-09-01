package com.carta.krakatoa.controllers;

import com.carta.krakatoa.enums.ShareClass;
import com.carta.krakatoa.error.CartaException;
import com.carta.krakatoa.models.Associate;
import com.carta.krakatoa.models.Firm;
import com.carta.krakatoa.models.ManagingPartner;
import com.carta.krakatoa.models.Member;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * Represents the Firm Controller Object that contains all the firm share and proceeds business logic
 *
 * @author js
 * @version 1.0
 */
public class FirmController {

    /**
     * Adds a member to the firm and gives shares dependent on the member class
     *
     * @param f - The Firm
     * @param m - The Member
     */
    public static void addMember(Firm f, Member m) {
        if(m instanceof Associate) {
            addShares(m, ShareClass.A, 10, f.getShareDistMap());
        }

        if(m instanceof ManagingPartner) {
            addShares(m, ShareClass.C, 5, f.getShareDistMap());
        }

        f.addMember(m);
    }

    /**
     * Adds shares to the member's owned shares based on how much money they are investing.
     * Adds the amount invested to the firm's investment list. Left over money is not added to investments
     *
     * @param m - The member investing
     * @param f - The Firm being invested in
     * @param c - The class of the share being bought
     * @param n - The amount being invested
     * @return - Integer denoting the number of shares purchased
     * @throws CartaException
     */
    public static Integer purchaseShares(Member m, Firm f, ShareClass c, Double n) throws CartaException {
        if(!f.getSharePriceMap().containsKey(c)) throw new CartaException("No Share Price is available for this class");
        Double investment = n - (n % f.getSharePriceMap().get(c));
        Integer numShares = (int) (investment / f.getSharePriceMap().get(c));
        addShares(m, c, numShares, f.getShareDistMap());
        f.addInvestment(m, investment);
        return numShares;
    }

    private static void addShares(Member m, ShareClass c, Integer n, HashMap<ShareClass, Integer> shareDistMap) {
        m.addShares(c, n);
        shareDistMap.put(c, shareDistMap.getOrDefault(c, 0) + n);
    }

    /**
     * Distribute proceeds to the shareholders.
     * First it pays back any investments that were made by shareholders. If the amount of dues exceed the proceeds,
     * then the funds are distributed according to the ratio of shares that each investor holds
     * Once all the investments have been paid bac, it then equally distributes the remaining proceeds to other all shareholders.
     *
     * @param f - The firm that is distributing money
     */
    public static void distributeProceeds(Firm f) {
        HashMap<ShareClass, Integer> shareDistMap = f.getShareDistMap();
        HashMap<Member, Double> investments = f.getInvestments();
        Set<Member> shareholders = f.getShareholders();
        HashMap<Member, HashMap<ShareClass, Double>> proceedsDistMap = f.getProceedsDistMap();

        //Repays investments
        repayInvestment(f, shareDistMap, investments, shareholders, proceedsDistMap);

        int totalShares = 0;
        for(ShareClass c : ShareClass.values()) {
            totalShares += shareDistMap.get(c);
        }
        double pricePerShare = (f.getProceeds() >= 0 ? f.getProceeds() : 0.0) / totalShares;

        for(Member m : shareholders) {
            if(!proceedsDistMap.containsKey(m)) proceedsDistMap.put(m, new HashMap<>());
            HashMap<ShareClass, Double> shareClassMap = proceedsDistMap.get(m);
            for(ShareClass c : ShareClass.values()) {
                shareClassMap.put(c, shareClassMap.getOrDefault(c, 0.0) + (m.getOwnedShares().get(c) * pricePerShare));
            }
            proceedsDistMap.put(m, shareClassMap);
        }
        f.setProceeds(0.0);
    }

    private static void repayInvestment(Firm f, HashMap<ShareClass, Integer> shareDistMap, HashMap<Member, Double> investments,
                                        Set<Member> shareholders, HashMap<Member, HashMap<ShareClass, Double>> proceedsDistMap) {

        int totalShares = shareDistMap.get(ShareClass.B);
        double dues = investments.values().stream().mapToDouble(d -> d.doubleValue()).sum();
        if(dues > f.getProceeds()) {
            double pricePerShare = f.getProceeds() / totalShares;
            for(Member m : shareholders) {
                if(!proceedsDistMap.containsKey(m)) proceedsDistMap.put(m, new HashMap<>());
                HashMap<ShareClass, Double> shareClassMap = proceedsDistMap.get(m);
                shareClassMap.put(ShareClass.B, shareClassMap.getOrDefault(ShareClass.B, 0.0) + (m.getOwnedShares().get(ShareClass.B) * pricePerShare));
                proceedsDistMap.put(m, shareClassMap);
                if(investments.containsKey(m)) f.payProceeds(proceedsDistMap.get(m).get(ShareClass.B));
            }
        } else {
            for(Member m : shareholders) {
                if(!proceedsDistMap.containsKey(m)) proceedsDistMap.put(m, new HashMap<>());
                HashMap<ShareClass, Double> shareClassMap = proceedsDistMap.get(m);
                shareClassMap.put(ShareClass.B, shareClassMap.getOrDefault(ShareClass.B, 0.0) + investments.getOrDefault(m, 0.0));
                proceedsDistMap.put(m, shareClassMap);
                if(investments.containsKey(m)) f.payProceeds(investments.get(m));
            }
        }
    }
}
