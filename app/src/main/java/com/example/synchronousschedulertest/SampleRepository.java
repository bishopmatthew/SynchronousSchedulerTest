package com.example.synchronousschedulertest;


import android.util.Log;
import android.util.LruCache;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by mabishop on 2/17/17.
 */

class SampleRepository {

    private static final String TAG = SampleRepository.class.getSimpleName();

    private static SampleRepository sInstance;

    private SampleRepository () {
        // private singleton constructor
    }

    /**
     * Get the singleton instance of this class.
     * @return Get the singleton instance.
     */
    public static SampleRepository getInstance() {
        return sInstance == null ? sInstance = new SampleRepository() : sInstance;
    }

    private LruCache<String, Data> mMemoryCache = new LruCache<>(20);

    public Observable<Data> getData (final String key) {
        return getCachedData(key)
                .observeOn(Schedulers.io())
                .concatWith(requestFreshData(key))
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Data> requestFreshData (final String key) {
        return Api.getInstance().getData(key)
                .doOnNext(new Action1<Data>() {
                    @Override
                    public void call (final Data data) {
                        Log.d(TAG, "Thread.currentThread()=" + Thread.currentThread());
                        mMemoryCache.put(key, data);
                    }
                });
    }

    private Observable<Data> getCachedData (final String key) {
        final Data cachedData = mMemoryCache.get(key);
        if (cachedData != null) {
            return Observable.just(cachedData);
        } else {
            return Observable.empty();
        }
    }

}