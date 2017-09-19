package com.michaelfotiadis.ukatmdb.network;

import java.util.HashMap;
import java.util.Map;

public class ApiStore {

    private static final String ENDPOINT_BANK_OF_IRELAND = "https://openapi.bankofireland.com/open-banking/v1.2/";
    private static final String ENDPOINT_BANK_OF_SCOTLAND = "https://api.bankofscotland.co.uk/open-banking/v1.2/";
    private static final String ENDPOINT_BARCLAYS = "https://atlas.api.barclays/open-banking/v1.3/";
    private static final String ENDPOINT_DANSKE = "https://obp-api.danskebank.com/open-banking/v1.2/";
    private static final String ENDPOINT_FIRST_TRUST = "https://api.firsttrustbank.co.uk/open-banking/v1.2/";
    private static final String ENDPOINT_HALIFAX = "https://api.halifax.co.uk/open-banking/v1.2/";
    private static final String ENDPOINT_HSBC = "https://api.hsbc.com/open-banking/v1.2/";
    private static final String ENDPOINT_NATIONWIDE = "https://openapi.nationwide.co.uk/open-banking/v1.2/";
    private static final String ENDPOINT_NATWEST = "https://openapi.natwest.com/open-banking/v1.3/";
    private static final String ENDPOINT_ROYAL_BANK_OF_SCOTLAND = "https://openapi.rbs.co.uk/open-banking/v1.3/";
    private static final String ENDPOINT_SANTANDER = "https://api.santander.co.uk/retail/open-banking/v1.2/";
    private static final String ENDPOINT_ULSTER = "https://openapi.ulsterbank.co.uk/open-banking/v1.3/";

    private final Map<Bank, String> apiMap;

    public ApiStore() {
        apiMap = new HashMap<>();

        apiMap.put(Bank.BANK_OF_IRELAND, ENDPOINT_BANK_OF_IRELAND);
        apiMap.put(Bank.BANK_OF_SCOTLAND, ENDPOINT_BANK_OF_SCOTLAND);
        apiMap.put(Bank.BARCLAYS, ENDPOINT_BARCLAYS);
        apiMap.put(Bank.DANSKE, ENDPOINT_DANSKE);
        apiMap.put(Bank.FIRST_TRUST, ENDPOINT_FIRST_TRUST);
        apiMap.put(Bank.HALIFAX, ENDPOINT_HALIFAX);
        apiMap.put(Bank.HSBC, ENDPOINT_HSBC);
        apiMap.put(Bank.NATIONWIDE, ENDPOINT_NATIONWIDE);
        apiMap.put(Bank.NATWEST, ENDPOINT_NATWEST);
        apiMap.put(Bank.ROYAL_BANK_OF_SCOTLAND, ENDPOINT_ROYAL_BANK_OF_SCOTLAND);
        apiMap.put(Bank.SANTANDER, ENDPOINT_SANTANDER);
        apiMap.put(Bank.ULSTER, ENDPOINT_ULSTER);
    }

    public String getEndpointForBank(final Bank bank) {
        switch (bank) {
            case UNDEFINED:
                throw new IllegalStateException("Unsupported Bank endpoint operation: " + bank);
            default:
                return apiMap.get(bank);
        }
    }

    public boolean isCertificateStrict(final Bank bank) {

        final boolean isStrict;

        switch (bank) {
            case UNDEFINED:
                throw new IllegalStateException("Unsupported Bank Certificate operation: " + bank);
            case BANK_OF_IRELAND:
                isStrict = true;
                break;
            default:
                isStrict = false;
        }

        return isStrict;

    }

}
