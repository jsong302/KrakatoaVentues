package com.carta.krakatoa;

import com.carta.krakatoa.models.GeneralPartner;
import com.carta.krakatoa.models.KrakatoaVentures;
import com.carta.krakatoa.models.Member;

public class Main {

    public static void main(String[] args) {
        int proceeds = 0;
        try {
            proceeds = Integer.parseInt(args[1]);
        } catch(NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Please give a proper number for argument 1");
            return;
        }
        KrakatoaVentures kv = new KrakatoaVentures(proceeds);
        init(kv);
    }

    public static void init(KrakatoaVentures kv) {
        Member alex = new GeneralPartner("Alex");
    }
}
