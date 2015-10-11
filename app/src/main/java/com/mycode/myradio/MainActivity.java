package com.mycode.myradio;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnTouchListener, OnPreparedListener,
        OnCompletionListener
{
    private ViewFlipper flipper = null;
    private float fromPosition;

    final String LOG_TAG = "myLogs";
    String RADIO_STATION [] = {"http://online-radioroks.tavrmedia.ua/RadioROKS", "http://195.95.206.17/HitFM",
            "http://cast.radiogroup.com.ua:8000/europaplus", "http://online.radiorecord.ru:8102/dub_128"};
    MediaPlayer mediaPlayer;
    AudioManager am;

    private class LoadImagesTask extends AsyncTask<Void, Void, List<Bitmap>>
    {

        @Override
        protected List<Bitmap> doInBackground(Void... params)
        {
            List<Bitmap> bitmaps = new ArrayList<Bitmap>();
            bitmaps.add(BitmapUtils.getCircleMaskedBitmapUsingPorterDuff(BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.first), 100));
            bitmaps.add(BitmapUtils.getCircleMaskedBitmapUsingPorterDuff(BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.second), 100));
            bitmaps.add(BitmapUtils.getCircleMaskedBitmapUsingPorterDuff(BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.third), 100));
            bitmaps.add(BitmapUtils.getCircleMaskedBitmapUsingPorterDuff(BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.fourth), 100));
            return bitmaps;
        }

        @Override
        protected void onPostExecute(List<Bitmap> result)
        {
            if (isCancelled() || result == null)
            {
                return;
            }
            image1.setImageBitmap(result.get(0));
            image2.setImageBitmap(result.get(1));
            image3.setImageBitmap(result.get(2));
            image4.setImageBitmap(result.get(3));
        }

    }

    private LoadImagesTask imagesTask = null;
    private ImageView image1 = null;
    private ImageView image2 = null;
    private ImageView image3 = null;
    private ImageView image4 = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mainLayout.setOnTouchListener(this);

        flipper = (ViewFlipper) findViewById(R.id.flipper);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int layouts[] = new int[]{ R.layout.first, R.layout.second, R.layout.third, R.layout.fourth };
        for (int layout : layouts)
            flipper.addView(inflater.inflate(layout, null));

        image1 = (ImageView) findViewById(R.id.ibFirst);
        image2 = (ImageView) findViewById(R.id.ibSecond);
        image3 = (ImageView) findViewById(R.id.ibThird);
        image4 = (ImageView) findViewById(R.id.ibFourth);

        am = (AudioManager) getSystemService(AUDIO_SERVICE);


    }



    public void onClickStart(View view) {
        releaseMP();

        try {
            switch (view.getId()) {
              /*  case R.id.btnStartHttp:
                    Log.d(LOG_TAG, "start HTTP");
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(DATA_HTTP);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    Log.d(LOG_TAG, "prepareAsync");
                    mediaPlayer.setOnPreparedListener(this);
                    mediaPlayer.prepareAsync();
                    break;*/
                case R.id.ibFirst:
                    Log.d(LOG_TAG, "start Stream");
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(RADIO_STATION[0]);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    Log.d(LOG_TAG, "prepareAsync");
                    mediaPlayer.setOnPreparedListener(this);
                    mediaPlayer.prepareAsync();
                    break;
                case R.id.ibSecond:
                    Log.d(LOG_TAG, "start Stream");
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(RADIO_STATION[1]);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    Log.d(LOG_TAG, "prepareAsync");
                    mediaPlayer.setOnPreparedListener(this);
                    mediaPlayer.prepareAsync();
                    break;
                case R.id.ibThird:
                    Log.d(LOG_TAG, "start Stream");
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(RADIO_STATION[2]);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    Log.d(LOG_TAG, "prepareAsync");
                    mediaPlayer.setOnPreparedListener(this);
                    mediaPlayer.prepareAsync();
                    break;
                case R.id.ibFourth:
                    Log.d(LOG_TAG, "start Stream");
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(RADIO_STATION[3]);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    Log.d(LOG_TAG, "prepareAsync");
                    mediaPlayer.setOnPreparedListener(this);
                    mediaPlayer.prepareAsync();
                    break;
                case R.id.btnStopStream:
                    mediaPlayer = null;
                    break;
            /*    case R.id.btnStartSD:
                    Log.d(LOG_TAG, "start SD");
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(DATA_SD);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    break;
                case R.id.btnStartUri:
                    Log.d(LOG_TAG, "start Uri");
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(this, DATA_URI);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    break;
                case R.id.btnStartRaw:
                    Log.d(LOG_TAG, "start Raw");
                    mediaPlayer = MediaPlayer.create(this, R.raw.main_music);
                    mediaPlayer.start();
                    break;*/

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mediaPlayer == null)
            return;

        mediaPlayer.setOnCompletionListener(this);
    }

    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(LOG_TAG, "onPrepared");
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(LOG_TAG, "onCompletion");
    }


    public boolean onTouch(View view, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                fromPosition = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float toPosition = event.getX();
                if (fromPosition > toPosition)
                {


                    flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.go_next_in));
                    flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.go_next_out));
                    flipper.showNext();

                }
                else if (fromPosition < toPosition)
                {
                    flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.go_prev_in));
                    flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.go_prev_out));
                    flipper.showPrevious();

                }
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        imagesTask = new LoadImagesTask();
        imagesTask.execute();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        imagesTask.cancel(true);
    }
}