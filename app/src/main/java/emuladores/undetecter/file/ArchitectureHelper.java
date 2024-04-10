package emuladores.undetecter.file;

import android.util.Log;

public class ArchitectureHelper {

    public static String detectArchitecture() {
        String isArchitecture = System.getProperty("os.arch");
        String abi;

        if (isArchitecture == null) return "";

        if (isArchitecture.contains("arm")) {
            abi = "arm";
        } else if (isArchitecture.contains("aarch64")) {
            abi = "arm64";
        } else if (isArchitecture.contains("x86_64")) {
            abi = "x86_64";
        } else {
            Log.e("RootHelper", "Unsupported architecture");
            return "";
        }
        return abi;
    }
}