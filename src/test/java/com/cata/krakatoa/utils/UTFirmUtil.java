package com.cata.krakatoa.utils;

import com.carta.krakatoa.enums.ShareClass;
import com.carta.krakatoa.models.*;
import com.carta.krakatoa.utils.FirmUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UTFirmUtil {

    @Test
    public void testAddMember() {
        Member m = new Member("M");
        Member a = new Associate("A");
        Member gp = new GeneralPartner("GP");
        Member mp = new ManagingPartner("MP");
        Firm f = new Firm("F", 100);
        FirmUtil.addMember(f, a);
        assertTrue(a.getOwnedShares().get(ShareClass.A) == 10);
    }

}
