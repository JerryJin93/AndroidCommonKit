package com.jerryjin.kit.log.journal;

import java.io.Serializable;

/**
 * Author: Jerry
 * Generated at: 2019/6/24 17:33
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version:
 * Description: Builder strategy.
 */
public class Journal implements Serializable {

    private static final long serialVersionUID = -7101782895329682806L;
    private String timeStamp;
    private String content;
    private int status;

    public Journal(Builder builder) {

    }

    public @interface Status {
    }

    static class Builder {
        private String createdAt;
    }
}
