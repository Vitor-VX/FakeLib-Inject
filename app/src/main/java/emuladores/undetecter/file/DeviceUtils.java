package emuladores.undetecter.file;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;

public class DeviceUtils {
    public static String getDeviceId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    public static String getBuildID() {
        return Build.ID;
    }
}