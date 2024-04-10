#include "jni.h"
#include <iostream>
#include <fstream>
#include <string>
#include <unistd.h>
#include <dlfcn.h>
#include <algorithm>
#include <cstring>
#include "KittyMemory/KittyMemory.h"
#include <thread>
#include <dirent.h>
#include "Includes/Logger.h"
#include "obfuscate.h"
#include "Includes/Utils.h"
#include "Includes/Dobby/dobby.h"
#include "KittyMemory/MemoryPatch.h"

using namespace KittyMemory;

extern "C"
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    const char *libPath = "librealmain.so";

    void *handle = dlopen(libPath, 0);
    if (handle) {
        void *JNI_OnLoad = dlsym(handle, OBFUSCATE("JNI_OnLoad"));
        if (JNI_OnLoad) {
            ((void (*)(JavaVM *, void *)) JNI_OnLoad)(vm, nullptr);

            LOGI("Jni onLoad!");
        }
    }

    return JNI_VERSION_1_6;
}