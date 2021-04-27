package mn.claudiu.launch_mail_app

import android.content.Context
import android.content.Intent
import android.content.pm.LabeledIntent
import android.net.Uri
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result


/** LaunchMailAppPlugin */
class LaunchMailAppPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var context: Context


  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "launch_mail_app")
    channel.setMethodCallHandler(this)
    context = flutterPluginBinding.applicationContext
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    val intent = emailAppIntent()
    when (call.method) {
      "canLaunchMailApp" -> {
        result.success(intent != null)
      }
      "launchMailApp" -> {
        context.startActivity(intent)
        result.success(null)
      }
      else -> {
        result.notImplemented()
      }
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  /// Taken from https://stackoverflow.com/a/58880268
  private fun emailAppIntent(): Intent? {
    val emailIntent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"))

    val activitiesHandlingEmails = context.packageManager.queryIntentActivities(emailIntent, 0)
    if (activitiesHandlingEmails.isNotEmpty()) {
      // use the first email package to create the chooserIntent
      val firstEmailPackageName = activitiesHandlingEmails.first().activityInfo.packageName
      val firstEmailInboxIntent = context.packageManager.getLaunchIntentForPackage(firstEmailPackageName)
      val emailAppChooserIntent = Intent.createChooser(firstEmailInboxIntent, "")

      // created UI for other email packages and add them to the chooser
      val emailInboxIntents = mutableListOf<LabeledIntent>()
      for (i in 1 until activitiesHandlingEmails.size) {
        val activityHandlingEmail = activitiesHandlingEmails[i]
        val packageName = activityHandlingEmail.activityInfo.packageName
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        emailInboxIntents.add(
                LabeledIntent(
                        intent,
                        packageName,
                        activityHandlingEmail.loadLabel(context.packageManager),
                        activityHandlingEmail.icon
                )
        )
      }
      val extraEmailInboxIntents = emailInboxIntents.toTypedArray()
      return emailAppChooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraEmailInboxIntents)
    } else {
      return null
    }
  }
}
