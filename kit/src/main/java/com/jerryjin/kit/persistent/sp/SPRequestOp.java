package com.jerryjin.kit.persistent.sp;

import com.jerryjin.kit.network.pojo.KVPair;

import java.util.Objects;

/**
 * Author: Jerry
 * Generated at: 2019-06-25 01:45
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description: Request operation for {@link SPFH}.
 */
@SuppressWarnings("WeakerAccess")
public class SPRequestOp {

    public static final int PUT_BOOLEAN = 0xf1;
    public static final int PUT_INT = 0xf2;
    public static final int PUT_FLOAT = 0xf3;
    public static final int PUT_LONG = 0xf4;
    public static final int PUT_String = 0xf5;
    public static final int PUT_STRING_SET = 0xf6;
    public static final int REMOVE = 0xf7;
    public static final int CLEAR = 0xf8;

    private KVPair<String, Object> kvPair;
    private int op;

    public SPRequestOp(KVPair<String, Object> kvPair, int op) {
        this.kvPair = kvPair;
        this.op = op;
    }

    public KVPair<String, Object> getKvPair() {
        return kvPair;
    }

    public void setKvPair(KVPair<String, Object> kvPair) {
        this.kvPair = kvPair;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SPRequestOp op1 = (SPRequestOp) o;
        return op == op1.op &&
                kvPair.equals(op1.kvPair);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kvPair, op);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return "SPRequestOp{" +
                "kvPair=" + kvPair +
                ", op=" + op +
                '}';
    }
}
