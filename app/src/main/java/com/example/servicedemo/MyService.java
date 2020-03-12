package com.example.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyService extends Service {

    boolean generateRandom;
    int myRandomNumber;
    public  MyBinder myBinder=new MyBinder();

    class  MyBinder extends Binder{

        public MyService getService(){

            return MyService.this;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("servicestarted","started");
        generateRandom=true;
        startRandomNumberGenerator();
        new Thread(new Runnable() {
            @Override
            public void run() {


                generateRandom=true;
                startRandomNumberGenerator();
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        stopRandom();
    }

    public  void startRandomNumberGenerator(){

        if(generateRandom){

            try {
                Thread.sleep(1000);
                myRandomNumber = new Random().nextInt(50) + 13;
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void stopRandom(){
        generateRandom=false;
    }

    public  int getMyRandomNumber(){

        return myRandomNumber;
    }
}
