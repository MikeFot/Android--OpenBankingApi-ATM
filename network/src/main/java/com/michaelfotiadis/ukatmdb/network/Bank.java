package com.michaelfotiadis.ukatmdb.network;

public enum Bank {

    UNDEFINED(""),
    BANK_OF_IRELAND("IRELAND"),
    BANK_OF_SCOTLAND("SCOTLAND"),
    BARCLAYS("BARCLAYS"),
    DANSKE("DANSKE"),
    FIRST_TRUST("FIRST TRUST"),
    HALIFAX("HALIFAX"),
    HSBC("HSBC"),
    NATIONWIDE("NATIONWIDE"),
    NATWEST("NATWEST"),
    ROYAL_BANK_OF_SCOTLAND("ROYAL BANK OF SCOTLAND"),
    SANTANDER("SANTANDER"),
    ULSTER("ULSTER");

    private final String name;

    Bank(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Bank fromString(final String value) {

        if (value == null || value.length() == 0) {
            return UNDEFINED;
        }

        for (final Bank bank : values()) {

            if (bank.toString().equalsIgnoreCase(value)) {
                return bank;
            }

        }

        return UNDEFINED;
    }

}
