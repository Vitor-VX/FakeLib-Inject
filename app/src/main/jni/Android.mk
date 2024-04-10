LOCAL_PATH := $(call my-dir)

LOCAL_C_INCLUDES += $(LOCAL_PATH)/Includes

include $(CLEAR_VARS)
LOCAL_MODULE := dobby
LOCAL_SRC_FILES := libs/$(TARGET_ARCH_ABI)/libdobby.a
include $(PREBUILT_STATIC_LIBRARY)

MAIN_LOCAL_PATH := $(call my-dir)

LOCAL_C_INCLUDES += $(MAIN_LOCAL_PATH)

LOCAL_CFLAGS := -Wno-error=format-security -fpermissive -DLOG_TAG=\"undetect\"
LOCAL_CFLAGS += -fno-rtti -fno-exceptions
LOCAL_LDLIBS := -llog -landroid -lz

include $(CLEAR_VARS)
LOCAL_MODULE := libmain

LOCAL_SRC_FILES := libmain/libmain.cpp \
                   KittyMemory/KittyMemory.cpp \
                   KittyMemory/MemoryPatch.cpp \
                   KittyMemory/MemoryBackup.cpp \
                   KittyMemory/KittyUtils.cpp \

LOCAL_STATIC_LIBRARIES := libdobby
LOCAL_LDLIBS += -llog -landroid -lz

include $(BUILD_SHARED_LIBRARY)
