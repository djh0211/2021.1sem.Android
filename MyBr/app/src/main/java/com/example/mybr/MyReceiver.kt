package com.example.mybr

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MyReceiver : BroadcastReceiver() {
    val usagePattern = Regex("""^[가-힣a-zA-Z0-9]+$]""")
    val paymentPattern = Regex("""^*\d{3}원$""")
    val dayPattern = Regex("""^*d{2}/\d{2}""")
    val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        scope.launch {
            if (intent.action.equals("android.provider.Telephony.SMS_RECEIVED")){
                val bundle = intent.extras
                val objects = bundle?.get("pdus") as Array<Any>
                val sms = objects[0] as ByteArray
                val format = bundle.getString("format")
                val message = SmsMessage.createFromPdu(sms, format)
                val msg = message.messageBody
                if (msg.contains("건국카드")){
                    val tempstr = msg.split("\n")
                    var resultstr=""
                    for(str in tempstr.subList(1, tempstr.size)){
                        if (usagePattern.containsMatchIn(str)){
                            resultstr += "$str : "
                        }
                        if (paymentPattern.containsMatchIn(str)){
                            resultstr += "$str : "
                        }
                        if (dayPattern.containsMatchIn(str)){
                            val temp = str.split(" ")
                            val daytemp = temp[0].split("/")
                            val month = daytemp[0]
                            val day = daytemp[1]
                            resultstr += "${month}월  ${day}일 카드 승인됨"
                        }
                    }
                    if (!resultstr.isEmpty()){
                        val newIntent = Intent(context, MainActivity::class.java)
                        newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        newIntent.putExtra("msg", resultstr)
                        context.startActivity(newIntent)
                    }
                    pendingResult.finish()


                }

            }
        }

    }
}