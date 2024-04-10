package emuladores.undetecter.file;

import static emuladores.undetecter.file.RootHelper.copyLibToDest;
import static emuladores.undetecter.file.RootHelper.getAppPath;
import static emuladores.undetecter.file.RootHelper.renameLib;
import static emuladores.undetecter.file.RootHelper.setPermissions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import eu.chainfire.libsuperuser.Shell;

public class MainActivity extends Activity {
    private final String appPackage = "com.dts.freefireth";
    private Handler handler;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Shell.SU.available()) {
            Toast.makeText(MainActivity.this, "Root not found", Toast.LENGTH_SHORT).show();
            return;
        }

        handler = new Handler();

        final String appPath = getAppPath(this, appPackage);

        if (appPath != null) {
            long delayConfigLib = 2000;
            handler.postDelayed(() -> configLibMain(appPath), delayConfigLib);
        }

        long delayOpenApp = 5000;
        handler.postDelayed(this::openApp, delayOpenApp);
    }

    private void configLibMain(String appPath) {
        // 1º
        renameLib(appPath);

        // 2º
        long delayCopyLibToDest = 2000;
        handler.postDelayed(() -> copyLibToDest(getApplicationContext(), appPath), delayCopyLibToDest);

        // 3º
        setPermissions(appPath);
    }

    private void openApp() {
        PackageManager pm = getBaseContext().getPackageManager();
        Intent launchIntent = pm.getLaunchIntentForPackage(appPackage);
        if (launchIntent != null) {
            getBaseContext().startActivity(launchIntent);
        } else {
            Log.e("Error", "Failed to launch app: " + appPackage);
        }
    }
}