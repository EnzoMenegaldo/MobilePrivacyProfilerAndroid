package fr.inria.diverse.mobileprivacyprofiler.utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class PhoneStateUtils {

    /** check the permission
     *  @return boolean
     */
    public static boolean hasPermission(Context context) {
        /*AppOpsManager appOps = (AppOpsManager)context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), context.getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;*/
        return true;
    }

    /** check whether network is connected or not
     *  @return boolean
     */
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * check a network interface by name
     *
     * @param networkInterfaceName Network interface Name on Linux, for example tun0
     * @return true if interface exists and is active
     * @throws Exception throws Exception
     */
    public static boolean checkForActiveInterface(String networkInterfaceName) throws Exception {
        List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
        for (NetworkInterface networkInterface : interfaces) {
            if (networkInterface.getName().equals(networkInterfaceName)) {
                return networkInterface.isUp();
            }
        }
        return false;
    }
}
