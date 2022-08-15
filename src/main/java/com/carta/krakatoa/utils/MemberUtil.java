package com.carta.krakatoa.utils;

import com.carta.krakatoa.enums.ShareClass;
import com.carta.krakatoa.error.CartaException;
import com.carta.krakatoa.models.Associate;
import com.carta.krakatoa.models.Firm;
import com.carta.krakatoa.models.ManagingPartner;
import com.carta.krakatoa.models.Member;

import java.util.HashMap;

public class MemberUtil {

    public static void addMember(Firm f, Member m) {
        if(m instanceof Associate) {
            m.addShares(ShareClass.A, 10);
        }

        if(m instanceof ManagingPartner) {
            m.addShares(ShareClass.C, 5);
        }

        f.addMember(m);
    }

    public static Integer purchaseShares(Member m, ShareClass c, Double n, HashMap<ShareClass, Double> sharePriceMap) throws CartaException {
        if(!sharePriceMap.containsKey(c)) throw new CartaException("No Share Price is available for this class");
        Integer numShares = (int) (n / sharePriceMap.get(c));
        m.addShares(c, numShares);
        return numShares;
    }
}
