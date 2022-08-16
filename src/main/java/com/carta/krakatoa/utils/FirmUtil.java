package com.carta.krakatoa.utils;

import com.carta.krakatoa.enums.ShareClass;
import com.carta.krakatoa.error.CartaException;
import com.carta.krakatoa.models.Associate;
import com.carta.krakatoa.models.Firm;
import com.carta.krakatoa.models.ManagingPartner;
import com.carta.krakatoa.models.Member;

import java.util.HashMap;

public class FirmUtil {

    public static void addMember(Firm f, Member m) {
        if(m instanceof Associate) {
            addShares(m, ShareClass.A, 10, f.getShareDistMap());
        }

        if(m instanceof ManagingPartner) {
            addShares(m, ShareClass.C, 5, f.getShareDistMap());
        }

        f.addMember(m);
    }

    public static Integer purchaseShares(Member m, Firm f, ShareClass c, Double n) throws CartaException {
        if(!f.getSharePriceMap().containsKey(c)) throw new CartaException("No Share Price is available for this class");
        Double investment = n - (n % f.getSharePriceMap().get(c));
        Integer numShares = (int) (investment / f.getSharePriceMap().get(c));
        addShares(m, c, numShares, f.getShareDistMap());
        f.addInvestment(m, investment);
        return numShares;
    }

    public static void addShares(Member m, ShareClass c, Integer n, HashMap<ShareClass, Integer> shareDistMap) {
        m.addShares(c, n);
        shareDistMap.put(c, shareDistMap.getOrDefault(c, 0) + n);
    }
}
