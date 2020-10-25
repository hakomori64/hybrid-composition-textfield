package com.example.textfield_demo

import android.os.Handler
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.EventChannel.StreamHandler
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity: FlutterActivity() {
  override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
    GeneratedPluginRegistrant.registerWith(flutterEngine)

    flutterEngine
        .platformViewsController
        .registry
        .registerViewFactory("edit-text", EditTextFactory(flutterEngine.dartExecutor.binaryMessenger))

    var channel: EventChannel = EventChannel(flutterEngine.dartExecutor.binaryMessenger, "events/edit-text")
    channel.setStreamHandler(
      object: StreamHandler {
        override fun onListen(arguments: Any?, events: EventSink) {
          FlutterEditText.eventSink = events
          Handler().postDelayed({
            events.success("Android")
          }, 500)
        }
        override fun onCancel(arguments: Any?) {}
      }
    )
  }
}
