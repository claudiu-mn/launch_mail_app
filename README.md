# launch_mail_app

Flutter package for opening a mail app on iOS or Android.

## Installation

Open the `pubspec.yaml` file and look for the `dependencies` section. Add the `launch_mail_app 1.0.0` dependency: 

```yaml
dependencies:
  launch_mail_app:
    git:
      url: https://github.com/claudiu-mn/launch_mail_app
      ref: 1.0.0
```

## Usage

1. Import the library:

```dart
import 'package:launch_mail_app/launch_mail_app.dart';
```

2. Use the library:

- Check if a mail app can be opened:

```dart
bool canOpenMail = await LaunchMailApp.canLaunch;
```

- Open a mail app:

```dart
LaunchMailApp.launch();
```
