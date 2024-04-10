# FakeLib Inject - VX

FakeLib is a project designed to inject your custom library (`libmain.so`) into games or other Android applications at runtime. This allows you to make modifications within the target game/application as if they were native to it, hence the name "FakeLib".

## Features

- Injects a custom `libmain.so` library into target applications.
- Requires superuser (root) access for runtime manipulation.
- Tested successfully on various platforms including Bluestacks with Magisk.

## Architectures - Tested

ARM, ARM64, and x86_64

## Usage

To use FakeLib, follow these steps:

- Ensure that you have Android Studio installed on your development machine.
- Clone or download the FakeLib project from this repository.
- Open the project in Android Studio.
- Build the project to compile the necessary binaries.
- Install the FakeLib application on your rooted device or emulator.
- Grant superuser access when prompted.
- Run the FakeLib application and follow the instructions to inject your custom library into the desired target application.

### Please note the following:

- FakeLib requires Android Studio for compilation and build process.
- In the `MainActivity`, you will find a variable named `appPath`, where you should specify the package name of the target application you wish to inject FakeLib into.
- In the C++ code (`libmain/libmain.cpp`), you can insert your custom code for injection, ensuring not to modify the `JNI_OnLoad` function already present, as it may lead to conflicts.

## Notes

- While FakeLib has been successfully tested on several platforms, it may not work on all emulators. Testing on different environments is recommended.
- Use FakeLib responsibly and ethically, respecting the privacy and rights of other users and developers.
