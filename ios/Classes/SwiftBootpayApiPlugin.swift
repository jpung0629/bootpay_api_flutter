import Flutter
import UIKit
import SwiftyBootpay

public class SwiftBootpayApiPlugin: NSObject, FlutterPlugin {
    
 public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "bootpay_api", binaryMessenger: registrar.messenger())
   let instance = SwiftBootpayApiPlugin()
   registrar.addMethodCallDelegate(instance, channel: channel) 
 }

 public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
   if(call.method == "bootpayRequest") {
       goBootpayController(call.arguments as! Dictionary<String, Any>, result: result)
   } else if(call.method == "getPlatformVersion") {
       result("i dont know")
   } else {
       result(FlutterMethodNotImplemented)
   } 
 }

 func goBootpayController(_ params: Dictionary<String, Any>, result: @escaping FlutterResult) {
 
   let rootViewController = UIApplication.shared.keyWindow?.rootViewController
   let navigationController = UINavigationController()
    
   let vc = BootpayViewController()
   vc.params = params;
   vc.flutterResult = result
   vc.modalPresentationStyle = .fullScreen
   rootViewController?.present(vc, animated: true, completion: nil)
 }
    
 @objc func popViewController(animated: Bool) {
    print(animated)
 }



 public func applicationWillResignActive(_ application: UIApplication) {
     Bootpay.sharedInstance.sessionActive(active: false)
 }

 public func applicationDidBecomeActive(_ application: UIApplication) {
     Bootpay.sharedInstance.sessionActive(active: true)
 }
}
