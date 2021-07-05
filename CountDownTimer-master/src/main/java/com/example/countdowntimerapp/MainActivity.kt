package com.example.countdowntimerapp

import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.NumberPicker
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var textViewProgress: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonStart: Button
    private lateinit var buttonReset: Button
    private lateinit var countDownTimer: CountDownTimer
    private var millisUntilFinishedLeft: Long = 0L
    private var ringtone: Ringtone? = null
    private var mProgress: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        viewFindViewById()
        buttonStartSetOnClickListener()
        buttonResetSetOnClickListener()
        textViewProgressSetOnClickListener()
    }

    private fun viewFindViewById() {
        textViewProgress = findViewById(R.id.textViewProgress)
        progressBar = findViewById(R.id.progressBar)
        buttonStart = findViewById(R.id.buttonStart)
        buttonReset = findViewById(R.id.buttonReset)
    }

    private fun buttonStartSetOnClickListener() {
        buttonStart.setOnClickListener {
            if ((it as Button).text.toString() == resources.getString(R.string.start)) {
                if (textViewProgress.text.toString() != resources.getString(R.string._00_00)) {
                    if (mProgress == 0) {
                        progressBar.progress = 100
                    } else {
                        progressBar.progress = mProgress
                    }
                    startCountDownTimer(progressBar.progress)
                    buttonStart.text = resources.getString(R.string.pause)
                }
            } else {
                buttonStart.text = resources.getString(R.string.start)
                stopCountDownTimer()
            }
            stopPlayingSound()
        }
    }

    private fun buttonResetSetOnClickListener() {
        buttonReset.setOnClickListener {
            stopCountDownTimer()
            stopPlayingSound()
            millisUntilFinishedLeft = 0L
            progressBar.progress = 0
            textViewProgress.text = getString(R.string._00_00)
            buttonStart.text = resources.getString(R.string.start)
        }
    }

    private fun textViewProgressSetOnClickListener() {
        textViewProgress.setOnClickListener {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)

        val view = layoutInflater.inflate(R.layout.alert_dialog, null)

        val buttonCancel: Button = view.findViewById(R.id.button_cancel)
        val buttonOk: Button = view.findViewById(R.id.button_ok)

        alertDialogBuilder.setView(view)

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

        val numberPickerMinutes: NumberPicker = view.findViewById(R.id.number_picker_min)
        val numberPickerSeconds: NumberPicker = view.findViewById(R.id.number_picker_sec)

        val stringArrayMinutes: Array<String> = resources.getStringArray(R.array.string_array_min)
        val stringArraySeconds: Array<String> = resources.getStringArray(R.array.string_array_sec)

        numberPickerMinutes.minValue = 0
        numberPickerMinutes.maxValue = stringArrayMinutes.size - 1

        numberPickerSeconds.minValue = 0
        numberPickerSeconds.maxValue = stringArraySeconds.size - 1

        numberPickerMinutes.displayedValues = stringArrayMinutes
        numberPickerSeconds.displayedValues = stringArraySeconds

        buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        buttonOk.setOnClickListener {
            val minutes: String = stringArrayMinutes[numberPickerMinutes.value]
            val seconds: String = stringArrayMinutes[numberPickerSeconds.value]

            val stringBuilder = StringBuilder()

            textViewProgress.text = stringBuilder
                .append(minutes)
                .append(getString(R.string.colon))
                .append(seconds)

            alertDialog.dismiss()
        }
    }

    private fun startCountDownTimer(progress: Int) {
        val separator = resources.getString(R.string.colon)

        val charArray = textViewProgress.text.toString().toCharArray()

        val minutes: String = charArray[0].toString() + charArray[1].toString()
        val seconds: String = charArray[3].toString() + charArray[4].toString()

        val millisInFuture: Long = if (millisUntilFinishedLeft == 0L) {
            (minutes.toInt() * 60000 + seconds.toInt() * 1000).toLong()
        } else {
            millisUntilFinishedLeft
        }

        countDownTimer = object : CountDownTimer(millisInFuture, 1) {
            override fun onTick(millisUntilFinished: Long) {
                millisUntilFinishedLeft = millisUntilFinished

                progressBar.progress =
                    ((millisUntilFinished - 1000) * progress / millisInFuture).toInt()
                mProgress = progressBar.progress

                val minutesLong: Long = millisUntilFinished / 1000 / 60
                val secondsInt: Int = (millisUntilFinished / 1000 % 60).toInt()

                val minutesString: String = if (minutesLong <= 9) {
                    "0$minutesLong"
                } else {
                    minutesLong.toString()
                }

                val secondsString: String = if (secondsInt <= 9) {
                    "0$secondsInt"
                } else {
                    secondsInt.toString()
                }

                val stringBuilder: StringBuilder = StringBuilder()

                textViewProgress.text = stringBuilder
                    .append(minutesString)
                    .append(separator)
                    .append(secondsString)
            }

            override fun onFinish() {
                startPlayingSound()

                buttonStart.text = resources.getString(R.string.start)

                millisUntilFinishedLeft = 0L

                val stringBuilder: StringBuilder = StringBuilder()

                textViewProgress.text = stringBuilder
                    .append(minutes)
                    .append(separator)
                    .append(seconds)

                progressBar.progress = 0
            }
        }
        countDownTimer.start()
    }

    private fun stopCountDownTimer() {
        countDownTimer.cancel()
    }

    private fun startPlayingSound() {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(applicationContext, notification)
        if (ringtone != null) {
            ringtone!!.play()
        }
    }

    private fun stopPlayingSound() {
        if (ringtone != null && ringtone!!.isPlaying) {
            ringtone!!.stop()
        }
    }
}