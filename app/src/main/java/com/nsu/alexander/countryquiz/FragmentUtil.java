package com.nsu.alexander.countryquiz;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import java.util.logging.Logger;
public final class FragmentUtil {
    private FragmentUtil() {
        throw new UnsupportedOperationException("Trying to create instance of utility class");
    }

    public static void replaceFragment(Fragment fragment, int resId, FragmentManager manager, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(resId, fragment, tag);
        transaction.commit();
    }

    public static void addFragment(Fragment fragment, int resId, FragmentManager manager, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(resId, fragment, tag);
        transaction.commit();
    }

    public static void forceAddFragment(Fragment fragment, int resId, FragmentManager manager, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(resId, fragment, tag);
        transaction.commit();

        manager.executePendingTransactions();
    }

}