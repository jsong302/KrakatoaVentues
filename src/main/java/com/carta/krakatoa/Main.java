package com.carta.krakatoa;

import com.carta.krakatoa.enums.ShareClass;
import com.carta.krakatoa.error.CartaException;
import com.carta.krakatoa.models.*;
import com.carta.krakatoa.utils.MemberUtil;

public class Main {

    public static void main(String[] args) {
        int proceeds = 0;
        try {
            proceeds = Integer.parseInt(args[1]);
        } catch(NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Please give an integer value for argument 1");
            return;
        }
        Firm f = new Firm("Krakatoa Ventures", proceeds);
        try {
            init(f);
        } catch(CartaException e) {
            System.err.println(e.getMessage());
            return;
        }

    }

    public static void init(Firm f) throws CartaException {

        Member alex = new GeneralPartner("Alex");
        Member becky = new ManagingPartner("Becky");
        Member david = new Associate("David");
        f.addSharePrice(ShareClass.B, 25.00);

        MemberUtil.addMember(f, alex);
        MemberUtil.addMember(f, becky);
        MemberUtil.addMember(f, david);
        MemberUtil.purchaseShares(alex, ShareClass.B, 250.00, f.getSharePriceMap());
        MemberUtil.purchaseShares(becky, ShareClass.B, 250.00, f.getSharePriceMap());
    }

    public static void printMemberCashAllocation() {

    }
}
