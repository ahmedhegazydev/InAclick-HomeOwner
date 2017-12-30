package com.example.hp.in_a_click.code;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by hp on 11/22/2017.
 */

public class GetComplexCode {


    private static Context context;

    public GetComplexCode(Context context) {
        GetComplexCode.context = context;
    }


    public String getComplexCode() {
        String code;
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);


        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifiManager != null;
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        @SuppressLint("HardwareIds") String macAddress = wInfo.getMacAddress();


        String ipAddress = getLocalIpAddress();
//
//        Log.e("android_id", android_id);
//        Log.e("macAddress", macAddress);
//        Log.e("idAddress", ipAddress);


        return android_id + macAddress + ipAddress;


    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return null;
    }

}
