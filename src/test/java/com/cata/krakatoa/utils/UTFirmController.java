package com.cata.krakatoa.utils;

import com.carta.krakatoa.enums.ShareClass;
import com.carta.krakatoa.error.CartaException;
import com.carta.krakatoa.models.*;
import com.carta.krakatoa.controllers.FirmController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UTFirmController {

    Firm f;
    Member m;
    Member a;
    Member gp;
    Member mp;

    @BeforeEach
    void init() {
        f = new Firm("F", 10250);
        f.addSharePrice(ShareClass.B, 25.00);
        m = new Member("M");
        a = new Associate("A");
        gp = new GeneralPartner("GP");
        mp = new ManagingPartner("MP");
    }

    @Test
    public void testAddMember() {
        FirmController.addMember(f, m);
        assertTrue(m.getOwnedShares().get(ShareClass.A) == 0);
        assertTrue(m.getOwnedShares().get(ShareClass.B) == 0);
        assertTrue(m.getOwnedShares().get(ShareClass.C) == 0);
    }

    @Test
    public void testAddMemberAssociate() {
        FirmController.addMember(f, a);
        assertTrue(a.getOwnedShares().get(ShareClass.A) == 10);
        assertTrue(a.getOwnedShares().get(ShareClass.B) == 0);
        assertTrue(a.getOwnedShares().get(ShareClass.C) == 0);
    }

    @Test
    public void testAddMemberGeneralPartner() {
        FirmController.addMember(f, gp);
        assertTrue(gp.getOwnedShares().get(ShareClass.A) == 0);
        assertTrue(gp.getOwnedShares().get(ShareClass.B) == 0);
        assertTrue(gp.getOwnedShares().get(ShareClass.C) == 0);
    }

    @Test
    public void testAddMemberManagingPartner() {
        FirmController.addMember(f, mp);
        assertTrue(mp.getOwnedShares().get(ShareClass.A) == 0);
        assertTrue(mp.getOwnedShares().get(ShareClass.B) == 0);
        assertTrue(mp.getOwnedShares().get(ShareClass.C) == 5);
    }

    @Test
    public void testPurchaseShares() throws CartaException {
        FirmController.purchaseShares(m, f, ShareClass.B, 250.0);
        assertTrue(m.getOwnedShares().get(ShareClass.A) == 0);
        assertTrue(m.getOwnedShares().get(ShareClass.B) == 10);
        assertTrue(m.getOwnedShares().get(ShareClass.C) == 0);
    }

    @Test
    public void testPurchaseSharesException(){

        Exception exception = assertThrows(CartaException.class, () -> {
            FirmController.purchaseShares(m, f, ShareClass.A, 250.0);
        });

        String expectedMessage = "No Share Price is available for this class";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testDistributeProceedsMoreThanInvestments() throws CartaException {
        FirmController.purchaseShares(m, f, ShareClass.B, 125.0);
        FirmController.purchaseShares(a, f, ShareClass.B, 125.0);
        FirmController.addMember(f, m);
        FirmController.addMember(f, a);
        FirmController.distributeProceeds(f);
        assertTrue(f.getProceedsDistMap().get(m).values().stream().mapToDouble(d -> d.doubleValue()).sum() == 2625.0);
        assertTrue(f.getProceedsDistMap().get(a).values().stream().mapToDouble(d -> d.doubleValue()).sum() == 7625.0);
        assertTrue(f.getProceedsDistMap().values().stream().mapToDouble(d -> d.get(ShareClass.A).doubleValue()).sum() == 5000.0);
        assertTrue(f.getProceedsDistMap().values().stream().mapToDouble(d -> d.get(ShareClass.B).doubleValue()).sum() == 5250.0);
        assertTrue(f.getProceedsDistMap().values().stream().mapToDouble(d -> d.get(ShareClass.C).doubleValue()).sum() == 0.0);

    }
    @Test
    public void testDistributeProceedsLessThanInvestments() throws CartaException {
        Firm f2 = new Firm("Test", 100);
        f2.addSharePrice(ShareClass.B, 25.00);
        FirmController.purchaseShares(m, f2, ShareClass.B, 100.0);
        FirmController.purchaseShares(a, f2, ShareClass.B, 300.0);
        FirmController.addMember(f2, m);
        FirmController.addMember(f2, a);
        FirmController.distributeProceeds(f2);
        assertTrue(f2.getProceedsDistMap().get(m).values().stream().mapToDouble(d -> d.doubleValue()).sum() == 25.0);
        assertTrue(f2.getProceedsDistMap().get(a).values().stream().mapToDouble(d -> d.doubleValue()).sum() == 75.0);
        assertTrue(f2.getProceedsDistMap().values().stream().mapToDouble(d -> d.get(ShareClass.A).doubleValue()).sum() == 0.0);
        assertTrue(f2.getProceedsDistMap().values().stream().mapToDouble(d -> d.get(ShareClass.B).doubleValue()).sum() == 100.0);
        assertTrue(f2.getProceedsDistMap().values().stream().mapToDouble(d -> d.get(ShareClass.C).doubleValue()).sum() == 0.0);
    }


}
