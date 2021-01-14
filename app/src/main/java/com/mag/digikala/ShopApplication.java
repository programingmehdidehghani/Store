package com.mag.digikala;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ShopApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        realmInitialization();

    }

    private void realmInitialization() {
        Realm.init(this);
        RealmConfiguration realmAppConfiguration = new RealmConfiguration.Builder()
                .name("shop.realm")
                .build();
        Realm.setDefaultConfiguration(realmAppConfiguration);

    }

}
