package com.carta.krakatoa;

import com.carta.krakatoa.enums.ShareClass;
import com.carta.krakatoa.error.CartaException;
import com.carta.krakatoa.models.*;
import com.carta.krakatoa.controllers.FirmController;
import com.google.gson.Gson;
import org.apache.commons.math3.util.Precision;

import java.util.HashMap;

/**
 * Main application for this project
 */
public class Main {

    public static void main(String[] args) {
        double proceeds = 0;
        try {
            proceeds = Double.parseDouble(args[0]);
        } catch(ArrayIndexOutOfBoundsException e) {
            System.err.println("Please give an integer value for argument 1");
            return;
        } catch(NumberFormatException e) {
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
        FirmController.distributeProceeds(f);
        printMemberCashAllocation(f);
        printShareCashAllocation(f);
    }

    private static void init(Firm f) throws CartaException {

        Member alex = new GeneralPartner("Alex");
        Member becky = new ManagingPartner("Becky");
        Member david = new Associate("David");
        f.addSharePrice(ShareClass.B, 25.00);

        FirmController.addMember(f, alex);
        FirmController.addMember(f, becky);
        FirmController.addMember(f, david);
        FirmController.purchaseShares(alex, f, ShareClass.B, 250.00);
        FirmController.purchaseShares(becky, f, ShareClass.B, 250.00);
    }

    private static void printMemberCashAllocation(Firm f) {
        HashMap<String, Double> cashAllocation = new HashMap<>();
        for(Member m : f.getProceedsDistMap().keySet()) {
            cashAllocation.put(m.getName(), Precision.round(f.getProceedsDistMap().get(m).values().stream().mapToDouble(p -> p.doubleValue()).sum(), 2));
        }
        Gson gson = new Gson();
        System.out.println(gson.toJson(cashAllocation));
    }

    private static void printShareCashAllocation(Firm f) {
        HashMap<ShareClass, Double> cashAllocation = new HashMap<>();
        for(ShareClass c : ShareClass.values()) {
            cashAllocation.put(c, Precision.round(f.getProceedsDistMap().values().stream().mapToDouble(p -> p.get(c).doubleValue()).sum(), 2));
        }

        Gson gson = new Gson();
        System.out.println(gson.toJson(cashAllocation));
    }
}
