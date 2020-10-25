package com.example.textfield_demo

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable
import io.flutter.plugin.common.EventChannel.EventSink
import androidx.annotation.Nullable
import io.flutter.plugin.platform.PlatformView

interface CustomTextWatcher: TextWatcher{
  override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
  override fun afterTextChanged(p0: Editable?) {}
}

internal class FlutterEditText(context: Context, id: Int, @Nullable creationParams: Map<String?, Any?>?) : PlatformView {

    companion object {
      var eventSink: EventSink? = null
    }

    private val editTextLayout: ViewGroup
    private var lastText: String = ""

    override fun getView(): View {
        return editTextLayout
    }

    override fun dispose() {}

    init {
      editTextLayout = (View.inflate(context, R.layout.flutter_edit_text, null) as ViewGroup)
      var editText: EditText = editTextLayout.getChildAt(0) as EditText
      editText.addTextChangedListener(object: CustomTextWatcher{
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
          if (!s.toString().equals(lastText)) {
            eventSink?.success(s.toString())
            lastText = s.toString()


          }
        }
      })
    }
}
