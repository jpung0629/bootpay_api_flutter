![payment window_1](https://docs.bootpay.co.kr/assets/online/onestore-145efaf06e9a3b1a93d07bbe174b2394f50373e9334a3205174676a181acf5b0.png)

# bootpay_api

부트페이에서 관리하는 공식 플러터 플러그인입니다. 
기존의 bootpay_flutter 모듈을 fork 하여 만들었습니다.

### 지원하는 PG사 
	1. 이니시스
	2. 나이스페이
	3. 다날
	4. KCP
	5. EasyPay (KICC)
	6. TPay (JTNet)
	7. LG U+
	8. 페이레터
	9. 네이버페이
	10. 카카오페이
	11. 페이코
	

## Getting Started
Add the module to your project ``pubspec.yaml``:
```yaml
...
dependencies:
 ...
 bootpay_api: last_version
...
```
And install it using ``flutter packages get`` on your project folder. After that, just import the module and use it:

## Settings

### Android
No configuration required.

### iOS
** {your project root}/ios/Runner/Info.plist **

```xml
<key>NSAppTransportSecurity</key>
    <dict>
        <key>NSAllowsArbitraryLoads</key>
        <true/>
    </dict>
    <key>CFBundleURLTypes</key>
    <array>
        <dict>
            <key>CFBundleTypeRole</key>
            <string>Editor</string>
            <key>CFBundleURLName</key>
            <string>kr.co.bootpaySample</string> // 사용하고자 하시는 앱의 bundle url name
            <key>CFBundleURLSchemes</key>
            <array>
                <string>bootpaySample</string> // 사용하고자 하시는 앱의 bundle url scheme
            </array>
        </dict>
    </array>
```

Done!

## Getting Started

This project is a starting point for a Flutter
[plug-in package](https://flutter.dev/developing-packages/),
a specialized package that includes platform-specific implementation code for
Android and/or iOS.

For help getting started with Flutter, view our 
[online documentation](https://flutter.dev/docs), which offers tutorials, 
samples, guidance on mobile development, and a full API reference.
