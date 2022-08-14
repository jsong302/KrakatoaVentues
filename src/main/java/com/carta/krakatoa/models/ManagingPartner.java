package com.carta.krakatoa.models;

import com.carta.krakatoa.enums.ShareClass;

public class ManagingPartner extends GeneralPartner {

    public ManagingPartner() {
        super();
        ownedShares.put(ShareClass.C, ownedShares.get(ShareClass.C) + 5);
    }
}
