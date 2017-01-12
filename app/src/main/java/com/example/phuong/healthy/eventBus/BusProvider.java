package com.example.phuong.healthy.eventBus;

import com.squareup.otto.Bus;

/**
 * Copyright © 2016 AsianTech Inc.
 * Created by TanHN on 5/12/16.
 */
public final class BusProvider {
    private static Bus mBus;

    private BusProvider() {
    }

    public static synchronized Bus getInstance() {
        if (mBus == null) {
            mBus = new Bus();
        }
        return mBus;
    }
}
