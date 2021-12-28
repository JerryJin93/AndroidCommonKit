package com.jerryjin.kit.utils.permission

import android.Manifest

/**
 * Author: Jerry
 *
 * Created at: 2021/12/28 14:58
 *
 * GitHub: https://github.com/JerryJin93
 *
 * Blog:
 *
 * Email: jerry93@foxmail.com

 * WeChat: AcornLake
 *
 * Version: 1.0.0
 *
 * Description: Lru mutable list.
 */
const val SIMPLE_REQUEST_CODE = 0x100

// <editor-fold desc="storage">
const val PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
const val PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
// </editor-fold>

// <editor-fold desc="bluetooth">
const val PERMISSION_BLUETOOTH = Manifest.permission.BLUETOOTH
const val PERMISSION_BLUETOOTH_ADMIN = Manifest.permission.BLUETOOTH_ADMIN
// </editor-fold>

// <editor-fold desc="location">
// need to be used with bluetooth
const val PERMISSION_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
const val PERMISSION_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
// </editor-fold>

// <editor-fold desc="wifi">
const val PERMISSION_ACCESS_WIFI_STATE = Manifest.permission.ACCESS_WIFI_STATE
const val PERMISSION_CHANGE_WIFI_STATE = Manifest.permission.CHANGE_WIFI_STATE
const val PERMISSION_CHANGE_WIFI_MULTICAST_STATE = Manifest.permission.CHANGE_WIFI_MULTICAST_STATE
// </editor-fold>

const val PERMISSION_ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE

const val PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE

const val PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO