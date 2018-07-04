package fr.inria.diverse.mobileprivacyprofiler.utils;

import android.app.Activity;
import android.app.AppOpsManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.security.Permission;
import java.util.Collections;
import java.util.List;

public class PhoneStateUtils {

    public static final int MY_PERMISSIONS_REQUEST= 100;

    /** check the permissions
     *  @return boolean
     */
    public static boolean hasPermission(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AppOpsManager appOps = (AppOpsManager)context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOps.checkOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(),
                    context.getPackageName()
                    );
            return mode == AppOpsManager.MODE_ALLOWED;
        }
        return true;
    }



    /** ask all the permissions
     */
    public static void requestPermissions(Activity activity, String[] permissions) {
        Toast.makeText(activity, "Please grant all the permissions to Mobiel Privacy Profiler", Toast.LENGTH_SHORT).show();
        ActivityCompat.requestPermissions(activity,permissions,MY_PERMISSIONS_REQUEST);
        activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_USAGE_ACCESS_SETTINGS), MY_PERMISSIONS_REQUEST);
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


    /**
     * check whether the bluetooth is active or not
     * @return boolean
     */
    public static boolean isBluetoothActive(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            return false;
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                return false;
            }
            return true;
        }
    }
}
