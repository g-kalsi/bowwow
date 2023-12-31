package com.codeninjas.bowwow.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public final class NetworkUtils {

    private static int TYPE_NOT_CONNECTED = 0;
    private static int TYPE_IS_CONNECTING = 1;


    private static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return 1;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return  1;
                    }
                }
            }else {
                return TYPE_NOT_CONNECTED;
            }
        } else {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                int TYPE_WIFI = 1;
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI;

                int TYPE_MOBILE = 2;
                if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_WIFI;

                NetworkInfo[] info = cm.getAllNetworkInfo();

                for (int i = 0; i < info.length; i++) {
                    if (info[i].getDetailedState() == NetworkInfo.DetailedState.CONNECTING) {
                        return TYPE_IS_CONNECTING;
                    }
                }
            }
        }

        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtils.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtils.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
//        else if (conn == NetworkUtils.TYPE_IS_CONNECTING) {
//            status = "Connected to internet connection";
//        }
        return status;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
