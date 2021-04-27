import Flutter
import UIKit

public class SwiftLaunchMailAppPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "launch_mail_app", binaryMessenger: registrar.messenger())
    let instance = SwiftLaunchMailAppPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    let mailUrl = URL(string: "message:")
    
    switch call.method {
    case "launchMailApp":
        guard let url = mailUrl else {
            result(nil)
            return
        }
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url)
        } else {
            UIApplication.shared.openURL(url)
        }
        result(nil)
        break
        
    case "canLaunchMailApp":
        guard let url = mailUrl else {
            result(false)
            return
        }
        
        result(UIApplication.shared.canOpenURL(url))
        break
    
    default:
        result(FlutterMethodNotImplemented)
        break
    }
  }
}
