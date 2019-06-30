package com.jerryjin.kit.graphics.point;

import android.graphics.Point;

/**
 * Author: Jerry
 * Generated at: 2019-06-29 00:07
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version:
 * Description:
 */
public class Point3D {

    private int x;
    private int y;
    private int z;

    public Point3D() {
    }

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(Point3D src) {
        this.x = src.x;
        this.y = src.y;
        this.z = src.z;
    }

    public Point3D(Point src) {
        this.x = src.x;
        this.y = src.y;
        this.z = 0;
    }


    public void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void negate() {
        x = -x;
        y = -y;
        z = -z;
    }

    public final void offset(int dx, int dy, int dz) {
        x += dx;
        y += dy;
        z += dz;
    }
}
