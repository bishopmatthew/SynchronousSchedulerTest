package com.example.synchronousschedulertest;

import android.os.Looper;
import android.os.NetworkOnMainThreadException;

import rx.Observable;
import rx.functions.Func0;

/**
 * Created by mabishop on 2/17/17.
 */

public class Api {
    private static Api sInstance;

    private Api () {
        // private singleton constructor
    }


    /**
     * Get the singleton instance of this class.
     * @return Get the singleton instance.
     */
    public static Api getInstance() {
        return sInstance == null ? sInstance = new Api() : sInstance;
    }

    public Observable<Data> getData (final String key) {
        return Observable.defer(new Func0<Observable<Data>>() {
            @Override
            public Observable<Data> call () {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    throw new NetworkOnMainThreadException();
                }
                try {
                    Thread.sleep(5000);
                    return Observable.just(new Data("Network Value"));
                } catch (InterruptedException e) {
                    return Observable.error(e);
                }
            }
        });
    }
}
