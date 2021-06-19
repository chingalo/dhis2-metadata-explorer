package com.example.android.dhis2explorer.data.service;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.dhis2explorer.data.Sdk;
import com.example.android.dhis2explorer.ui.login.LoginActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LogOutService {

    public static Disposable logOut(AppCompatActivity activity) {
        return Sdk.d2().userModule().logOut()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> ActivityStarter.startActivity(activity, LoginActivity.getLoginActivityIntent(activity.getApplicationContext()), true),
                        Throwable::printStackTrace);
    }
}
