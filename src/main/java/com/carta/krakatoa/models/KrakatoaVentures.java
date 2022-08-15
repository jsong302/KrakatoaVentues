package com.carta.krakatoa.models;


import java.util.HashSet;
import java.util.Set;

public class KrakatoaVentures {

    int proceeds;
    Set<Member> memberSet;

    public KrakatoaVentures() {
        this.memberSet = new HashSet<>();
        this.proceeds = 0;
    }

    public KrakatoaVentures(int proceeds) {
        this.memberSet = new HashSet<>();
        this.proceeds = proceeds;
    }

    public void setProceeds(int proceeds) {
        this.proceeds = proceeds;
    }

    public int getProceeds() {
        return proceeds;
    }

    public void addMember(Member member) {
        memberSet.add(member);
    }

    public void removeMember(Member member) {
        memberSet.remove(member);
    }
}
