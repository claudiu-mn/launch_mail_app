#import "LaunchMailAppPlugin.h"
#if __has_include(<launch_mail_app/launch_mail_app-Swift.h>)
#import <launch_mail_app/launch_mail_app-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "launch_mail_app-Swift.h"
#endif

@implementation LaunchMailAppPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftLaunchMailAppPlugin registerWithRegistrar:registrar];
}
@end
