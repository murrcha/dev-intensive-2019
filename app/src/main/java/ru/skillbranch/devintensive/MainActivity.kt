package ru.skillbranch.devintensive

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

private const val QUESTION_BUNDLE = "Question"
private const val STATUS_BUNDLE = "Status"
private const val ERRORS_BUNDLE = "Errors"

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var benderImage: ImageView
    private lateinit var text: TextView
    private lateinit var send: ImageView
    private lateinit var message: EditText

    private lateinit var bender: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        benderImage = iv_bender
        text = tv_text
        send = iv_send
        message = et_message

        bender = Bender()

        savedInstanceState?.let { bundle ->
            bender.apply {
                status = Bender.Status.valueOf(bundle.getString(STATUS_BUNDLE)
                        ?: Bender.Status.NORMAL.name)
                question = Bender.Question.valueOf(bundle.getString(QUESTION_BUNDLE)
                        ?: Bender.Question.NAME.name)
                errors = bundle.getInt(ERRORS_BUNDLE)
            }
        }

        text.text = bender.askQuestion()
        send.setOnClickListener(this)

        val (r, g, b) = bender.status.color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(STATUS_BUNDLE, bender.status.name)
        outState.putString(QUESTION_BUNDLE, bender.question.name)
        outState.putInt(ERRORS_BUNDLE, bender.errors)
    }

    @SuppressLint("DefaultLocale")
    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_send -> {
                val (phrase, color) = bender.listenAnswer(message.text.toString().toLowerCase())
                message.setText("")
                val (r, g, b) = color
                benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
                text.text = phrase
                hideKeyboard()
            }
        }
    }
}
