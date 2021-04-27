import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:launch_mail_app/launch_mail_app.dart';

void main() {
  const MethodChannel channel = MethodChannel('launch_mail_app');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await LaunchMailApp.platformVersion, '42');
  });
}
