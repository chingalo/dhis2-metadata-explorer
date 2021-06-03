package com.example.android.androidskeletonapp.data.service;

import com.example.android.androidskeletonapp.data.Sdk;

public class SyncStatusHelper {

    public static int programCount() {
        return Sdk.d2().programModule().programs().blockingCount();
    }

    public static int dataSetCount() {
        return Sdk.d2().dataSetModule().dataSets().blockingCount();
    }
}
