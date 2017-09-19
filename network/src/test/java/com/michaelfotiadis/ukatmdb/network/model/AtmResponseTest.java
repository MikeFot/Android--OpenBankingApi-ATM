package com.michaelfotiadis.ukatmdb.network.model;

import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AtmResponseTest {

    @Test
    public void testBankOfScotland() throws Exception {

        final AtmResponse response = new Gson().fromJson(JsonConstants.JSON_BANK_OF_SCOTLAND, AtmResponse.class);

        assertNotNull(response);

        assertNotNull(response.getMeta());
        assertNotNull(response.getMeta().getAgreement());
        assertNotNull(response.getMeta().getLastUpdated());
        assertNotNull(response.getMeta().getLicense());
        assertNotNull(response.getMeta().getTermsOfUse());

        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
        assertNotNull(response.getData().get(0).getAccessibilityTypes());
        assertNotNull(response.getData().get(0).getAdditionalATMServices());
        assertNotNull(response.getData().get(0).getAddress());
        assertNotNull(response.getData().get(0).getAtmId());
        assertNotNull(response.getData().get(0).getBranchIdentification());
        assertNotNull(response.getData().get(0).getCurrency());
        assertNotNull(response.getData().get(0).getGeographicLocation());
        assertNotNull(response.getData().get(0).getLocationCategory());
        assertNotNull(response.getData().get(0).getMinimumValueDispensed());
        assertNotNull(response.getData().get(0).getOrganisation());
        assertNotNull(response.getData().get(0).getSiteID());
        assertNotNull(response.getData().get(0).getSupportedLanguages());

    }

    @Test
    public void testBankOfIreland() throws Exception {

        final AtmResponse response = new Gson().fromJson(JsonConstants.JSON_BANK_OF_IRELAND, AtmResponse.class);

        assertNotNull(response);

        assertNotNull(response.getMeta());
        assertNotNull(response.getMeta().getAgreement());
        assertNotNull(response.getMeta().getLastUpdated());
        assertNotNull(response.getMeta().getLicense());
        assertNotNull(response.getMeta().getTermsOfUse());

        assertNotNull(response.getData());
        assertEquals(3, response.getData().size());
        assertNotNull(response.getData().get(0).getAddress());
        assertNotNull(response.getData().get(0).getAtmId());
        assertNotNull(response.getData().get(0).getCurrency());
        assertNotNull(response.getData().get(0).getGeographicLocation());
        assertNotNull(response.getData().get(0).getLocationCategory());
        assertNotNull(response.getData().get(0).getMinimumValueDispensed());
        assertNotNull(response.getData().get(0).getOrganisation());
        assertNotNull(response.getData().get(0).getSupportedLanguages());
        assertNotNull(response.getData().get(0).getSiteName());

    }

    @Test
    public void testBarclays() throws Exception {

        final AtmResponse response = new Gson().fromJson(JsonConstants.JSON_BARCLAYS, AtmResponse.class);

        assertNotNull(response);

        assertNotNull(response.getMeta());
        assertNotNull(response.getMeta().getAgreement());
        assertNotNull(response.getMeta().getLastUpdated());
        assertNotNull(response.getMeta().getLicense());
        assertNotNull(response.getMeta().getTermsOfUse());

        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
        assertNotNull(response.getData().get(0).getAddress());
        assertNotNull(response.getData().get(0).getAtmId());
        assertNotNull(response.getData().get(0).getCurrency());
        assertNotNull(response.getData().get(0).getAccessibilityTypes());
        assertNotNull(response.getData().get(0).getGeographicLocation());
        assertNotNull(response.getData().get(0).getSupportedLanguages());

    }

    @Test
    public void testDanske() throws Exception {

        final AtmResponse response = new Gson().fromJson(JsonConstants.JSON_DANSKE, AtmResponse.class);

        assertNotNull(response);

        assertNotNull(response.getMeta());
        assertNotNull(response.getMeta().getAgreement());
        assertNotNull(response.getMeta().getLastUpdated());
        assertNotNull(response.getMeta().getLicense());
        assertNotNull(response.getMeta().getTermsOfUse());

        assertNotNull(response.getData());
        assertEquals(3, response.getData().size());
        assertNotNull(response.getData().get(0).getAddress());
        assertNotNull(response.getData().get(0).getAtmId());
        assertNotNull(response.getData().get(0).getCurrency());
        assertNotNull(response.getData().get(0).getGeographicLocation());
        assertNotNull(response.getData().get(0).getSupportedLanguages());

    }

}