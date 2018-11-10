package com.sap.cloud.mobile.fiori.demo.onboarding;

import com.sap.cloud.mobile.onboarding.passcode.PasscodePolicy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PasscodePolicyFuture implements Future<PasscodePolicy> {
    private final CountDownLatch latch = new CountDownLatch(1);
    private PasscodePolicy value;
    private boolean cancelled = false;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (this.cancelled || value != null) {
            return false;
        } else {
            this.cancelled = true;
            return true;
        }
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public boolean isDone() {
        return this.cancelled || this.value != null;
    }

    @Override
    public PasscodePolicy get() throws InterruptedException {
        latch.await();
        return value;
    }

    @Override
    public PasscodePolicy get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        if (latch.await(timeout, unit)) {
            return value;
        } else {
            throw new TimeoutException();
        }
    }

    void put(PasscodePolicy result) {
        value = result;
        latch.countDown();
    }
}