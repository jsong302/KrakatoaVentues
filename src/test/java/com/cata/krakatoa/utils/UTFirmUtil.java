package com.cata.krakatoa.utils;

import com.carta.krakatoa.enums.ShareClass;
import com.carta.krakatoa.models.*;
import com.carta.krakatoa.utils.FirmUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UTFirmUtil {

    Firm f;
    Member m;
    Member a;
    Member gp;
    Member mp;

    @BeforeEach
    void init() {
        f = new Firm("F", 100);
        m = new Member("M");
        a = new Associate("A");
        gp = new GeneralPartner("GP");
        mp = new ManagingPartner("MP");
    }

    @Test
    public void testAddMember() {
        FirmUtil.addMember(f, m);
        FirmUtil.addMember(f, a);
        FirmUtil.addMember(f, gp);
        FirmUtil.addMember(f, mp);
        assertTrue(m.getOwnedShares().get(ShareClass.A) == 0);
        assertTrue(m.getOwnedShares().get(ShareClass.B) == 0);
        assertTrue(m.getOwnedShares().get(ShareClass.C) == 0);
        assertTrue(a.getOwnedShares().get(ShareClass.A) == 10);
        assertTrue(a.getOwnedShares().get(ShareClass.B) == 0);
        assertTrue(a.getOwnedShares().get(ShareClass.C) == 0);
        assertTrue(gp.getOwnedShares().get(ShareClass.A) == 0);
        assertTrue(gp.getOwnedShares().get(ShareClass.B) == 0);
        assertTrue(gp.getOwnedShares().get(ShareClass.C) == 0);
        assertTrue(mp.getOwnedShares().get(ShareClass.A) == 0);
        assertTrue(mp.getOwnedShares().get(ShareClass.B) == 0);
        assertTrue(mp.getOwnedShares().get(ShareClass.C) == 5);
    }

}
