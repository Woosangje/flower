package com.flower.constant;


public enum MemberRole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    public String getValue() {
        return this.value;
    }

    private MemberRole(final String value) {
        this.value = value;
    }


}