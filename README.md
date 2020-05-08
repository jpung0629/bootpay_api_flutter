![payment window_1](https://docs.bootpay.co.kr/assets/online/onestore-145efaf06e9a3b1a93d07bbe174b2394f50373e9334a3205174676a181acf5b0.png)

# bootpay_api

부트페이에서 관리하는 공식 플러터 플러그인입니다. 
기존의 [bootpay_flutter](https://pub.dev/packages/bootpay_flutter) 모듈을 fork 하여 만들었습니다.
부트페이 개발매뉴얼은 [이곳](https://docs.bootpay.co.kr) 을 참조해주세요.

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

```dart
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:bootpay_api/bootpay_api.dart';
import 'package:bootpay_api/model/payload.dart';
import 'package:bootpay_api/model/extra.dart';
import 'package:bootpay_api/model/user.dart';
import 'package:bootpay_api/model/item.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Test",
      home: TestPage(),
    );
  }
}

class TestPage extends StatefulWidget {
  @override
  TestPageState createState() => TestPageState();
}

class TestPageState extends State<TestPage> {
//  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
//    initPlatformState();
  }
 

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Container(
          child:  RaisedButton(
            onPressed: () {
              goBootpayRequest(context);
            },
            child: Text("부트페이 결제요청"),
          ),
        )
    );
  }

  void goBootpayRequest(BuildContext context) async {
    Payload payload = Payload();
    payload.androidApplicationId = '5b8f6a4d396fa665fdc2b5e8';
    payload.iosApplicationId = '5b8f6a4d396fa665fdc2b5e9';

    payload.pg = 'danal';
//    payload.method = 'card';
    payload.methods = ['card', 'phone', 'vbank', 'bank'];
    payload.name = 'testUser';
    payload.price = 2000.0;
    payload.orderId = DateTime.now().millisecondsSinceEpoch.toString();
//    payload.params = {
//      "callbackParam1" : "value12",
//      "callbackParam2" : "value34",
//      "callbackParam3" : "value56",
//      "callbackParam4" : "value78",
//    };

    User user = User();
    user.username = "사용자 이름";
    user.email = "user1234@gmail.com";
    user.area = "서울";
    user.phone = "010-1234-4567";

    Extra extra = Extra();
    extra.appScheme = 'bootpayFlutterSample';

    Item item1 = Item();
    item1.itemName = "미\"키's 마우스"; // 주문정보에 담길 상품명
    item1.qty = 1; // 해당 상품의 주문 수량
    item1.unique = "ITEM_CODE_MOUSE"; // 해당 상품의 고유 키
    item1.price = 1000; // 상품의 가격

    Item item2 = Item();
    item2.itemName = "키보드"; // 주문정보에 담길 상품명
    item2.qty = 1; // 해당 상품의 주문 수량
    item2.unique = "ITEM_CODE_KEYBOARD"; // 해당 상품의 고유 키
    item2.price = 1000; // 상품의 가격
    List<Item> itemList = [item1, item2];

    BootpayApi.request(
      context,
      payload,
      extra: extra,
      user: user,
      items: itemList,
      onDone: (String json) {
        print('onDone: $json');
      },
      onReady: (String json) {
        print('onReady: $json');
      },
      onCancel: (String json) {
        print('onCancel: $json');
      },
      onError: (String json) {
        print('onError: $json');
      },
    );
  }
}
```
