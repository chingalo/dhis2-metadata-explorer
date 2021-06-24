package com.example.android.dhis2explorer.data.service;

import com.example.android.dhis2explorer.data.Sdk;

public class SyncStatusHelper {

    public static int programCount() {
        return Sdk.d2().programModule()
                .programs()
                .blockingCount();
    }

    public static int dataSetCount() {
        return Sdk.d2().dataSetModule()
                .dataSets()
                .blockingCount();
    }

    public static int dataElementCount() {
        return Sdk.d2().dataElementModule()
                .dataElements()
                .blockingCount();
    }

    public static int indicatorCount() {
        return Sdk.d2().indicatorModule()
                .indicators()
                .blockingCount();
    }

    public static int optionSetCount() {
        return Sdk.d2().optionModule()
                .optionSets()
                .blockingCount();
    }

    public static int optionGroupCount() {
        return Sdk.d2().optionModule()
                .optionGroups()
                .blockingCount();
    }

    public static int optionCount() {
        return Sdk.d2().optionModule()
                .options()
                .blockingCount();
    }

    public static int programIndicatorCount() {
        return Sdk.d2().programModule()
                .programIndicators()
                .blockingCount();
    }
}
