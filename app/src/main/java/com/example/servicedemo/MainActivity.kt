package com.example.servicedemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.servicedemo.MyService.MyBinder
import kotlinx.android.synthetic.main.activity_main.*
import java.awt.font.NumericShaper

class MainActivity : AppCompatActivity() {

    lateinit var  serviceConnection: ServiceConnection
    private  var isServiceBound=false

    private lateinit var  serviceIntent: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startServiceButton.setOnClickListener {


            serviceIntent=Intent(applicationContext,MyService::class.java)

            startService(serviceIntent)
            bindService()




        }

    }

    private fun bindService(){


        serviceConnection=
            object : ServiceConnection {
                override fun onServiceDisconnected(name: ComponentName?) {

                    isServiceBound=false

                }

                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

                    isServiceBound=true
                    var myserviceBinder: MyBinder= service as MyBinder
                   val myService=myserviceBinder.service

                    Log.d("Ran",""+myService.getMyRandomNumber())
                }
            }

        bindService(serviceIntent,serviceConnection,Context.BIND_AUTO_CREATE)
    }
}
