package fr.inria.diverse.mobileprivacyprofiler.utils;

import android.app.Activity;
import android.app.AppOpsManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.VpnService;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.security.Permission;
import java.util.Collections;
import java.util.List;

import fr.inria.diverse.mobileprivacyprofiler.R;
import fr.inria.diverse.mobileprivacyprofiler.activities.Home_CustomViewActivity;

import static android.app.Activity.RESULT_OK;

public class PhoneStateUtils {

    public static final int MY_PERMISSIONS_REQUEST= 100;
    public static final int REQUEST_CODE_VPN = 0;

    /**
     * First we check the basic permissions. If they are all granted then we check the App Op permissions.
     * If at least one basic permission is not granted then we ask the user to grant it. Then we check the App Op permission from the onRequestPermissionsResult.
     * This allows us to always ask basic permissions first to avoid starting 2 activities in the same time and so reduce the latency.
     */
    public static void checkAllPermissions(List<String> permissions, Activity activity) {
        String[] array = permissions.toArray(new String[permissions.size()]);
        if(!hasBasicPermissions(activity,array)) {
            requestBasicPermissions(activity,array);
        }else if(!hasAppOpPermissions(activity)){
                requestAppOpsPermissions(activity);
        }else{
            setupVpn(activity);
        }
    }


    /**
     * Check all the permissions contained in the String array
     * @param context
     * @param permissions
     * @return true if all the permissions are granted
     */
    public static boolean hasBasicPermissions(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if the permission PACKAGE_USAGE_STATS is granted
     * @param context
     * @return true if it's granted
     */
    public static boolean hasAppOpPermissions(Context context){
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


    /** ask all basic permissions
     */
    public static void requestBasicPermissions(Activity activity, String[] permissions) {
        Toast.makeText(activity, "Please grant all the permissions to Mobiel Privacy Profiler", Toast.LENGTH_SHORT).show();
        ActivityCompat.requestPermissions(activity,permissions,MY_PERMISSIONS_REQUEST);
    }

    /** ask App usage permissions
     */
    public static void requestAppOpsPermissions(Activity activity) {
        Toast.makeText(activity, "Please grant all the permissions to Mobiel Privacy Profiler", Toast.LENGTH_SHORT).show();
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

    /**
     * Setup the VPN. Make sure that another vpn is not running and prepare the VPN
     */
    public static void setupVpn(Activity activity) {
        // check for VPN already running
        try {
            if (!PhoneStateUtils.checkForActiveInterface(activity.getString(R.string.vpn_interface))) {
                // get user permission for VPN
                Intent intent = VpnService.prepare(activity);
                if (intent != null) {
                    // ask user for VPN permission
                    activity.startActivityForResult(intent, REQUEST_CODE_VPN);
                }
            }
        } catch (Exception e) {
            Log.e("Setup VPN", "Exception checking network interfaces :" + e.getMessage());
            e.printStackTrace();
        }
    }
}
