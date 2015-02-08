package com.akka.transaction;

/**
 * Created by root on 15-2-8.
 */
public class TransferMsg {
    private Float AmtToTransferred;

    public TransferMsg(Float amtToTransferred) {
        AmtToTransferred = amtToTransferred;
    }

    public Float getAmtToTransferred() {
        return AmtToTransferred;
    }
}
