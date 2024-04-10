package emuladores.undetecter.file;

import static emuladores.undetecter.file.ArchitectureHelper.detectArchitecture;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class RootHelper {

    public static String getAppPath(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            return new File(applicationInfo.sourceDir).getParent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void copyLibToDest(Context context, String destPath) {
        try {
            String libName = "libmain.so";
            String abiSubDir = detectArchitecture();

            if (abiSubDir.isEmpty()) {
                Log.e("RootApp", "abiSubDir empty");
                return;
            }

            String sourcePath = context.getApplicationInfo().nativeLibraryDir + "/" + libName;
            String destinationPath = destPath + "/lib/" + abiSubDir + "/";
            String cmd = "cp " + sourcePath + " " + destinationPath;
            exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPermissions(String filePath) {
        String abiSubDir = detectArchitecture();

        if (abiSubDir.isEmpty()) {
            Log.e("RootApp", "abiSubDir empty");
        }

        exec("chmod 755 " + filePath + "/lib/" + abiSubDir + "/libmain.so");
    }

    public static void renameLib(String appPath) {
        try {
            String abiSubDir = detectArchitecture();
            String libDir = appPath + "/lib/" + abiSubDir + "/";
            File realMainLib = new File(libDir, "librealmain.so");

            if (!realMainLib.exists()) {
                String command = "mv " + libDir + "libmain.so " + libDir + "librealmain.so";
                exec(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void exec(final String cmd) {
        new Thread(() -> {
            Process process = null;
            DataOutputStream os = null;
            BufferedReader is = null;
            StringBuilder result = new StringBuilder();
            try {
                process = Runtime.getRuntime().exec("su");
                os = new DataOutputStream(process.getOutputStream());
                is = new BufferedReader(new InputStreamReader(process.getInputStream()));
                os.writeBytes(cmd + "\n");
                os.writeBytes("exit\n");
                os.flush();
                String line;
                while ((line = is.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                    if (process != null) {
                        process.destroy();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String commandOutput = result.toString();
        }).start();
    }
}