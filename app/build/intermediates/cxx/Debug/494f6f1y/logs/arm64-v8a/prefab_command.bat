@echo off
"C:\\Program Files\\Android\\Android Studio1\\jbr\\bin\\java" ^
  --class-path ^
  "C:\\Users\\robbi\\.gradle\\caches\\modules-2\\files-2.1\\com.google.prefab\\cli\\2.0.0\\f2702b5ca13df54e3ca92f29d6b403fb6285d8df\\cli-2.0.0-all.jar" ^
  com.google.prefab.cli.AppKt ^
  --build-system ^
  cmake ^
  --platform ^
  android ^
  --abi ^
  arm64-v8a ^
  --os-version ^
  30 ^
  --stl ^
  c++_shared ^
  --ndk-version ^
  23 ^
  --output ^
  "C:\\Users\\robbi\\AppData\\Local\\Temp\\agp-prefab-staging14790420784093197072\\staged-cli-output" ^
  "C:\\Users\\robbi\\.gradle\\caches\\transforms-3\\9fd563ef17e6261e5c01b33a144623f5\\transformed\\jetified-oboe-1.7.0\\prefab"
