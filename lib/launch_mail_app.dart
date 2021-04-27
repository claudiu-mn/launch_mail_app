import 'dart:async';

import 'package:flutter/services.dart';

class LaunchMailApp {
  static const MethodChannel _channel = const MethodChannel('launch_mail_app');

  static Future<bool> get canLaunch async {
    return await _channel.invokeMethod('canLaunchMailApp');
  }

  static Future<void> launch() async {
    await _channel.invokeMethod('launchMailApp');
  }
}
