package net.ddns.ilyapetrovm.mult;


import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;


public class MainActivity extends ActionBarActivity {

    private AnimationDrawable animation;
    private ImageView jpgView;
    private TextView subpath;
    private File file[];
    private ToggleButton tgLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jpgView = (ImageView) findViewById(R.id.imageViewAnim);
        animation = new AnimationDrawable();
        final Button animbtn = (Button) findViewById(R.id.buttonAnim);
        final Button loadbtn = (Button) findViewById(R.id.buttonLoad);
        tgLoop = (ToggleButton) findViewById(R.id.toggleButton);
        tgLoop.setChecked(false);

        animbtn.setVisibility(View.INVISIBLE);
        animbtn.setClickable(false);

        if(animation.isRunning() ) {
            animation.stop();
            animbtn.setText("Play");

        }

        animbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Clicks","Animate");
                if(animation.isRunning() ) {
                    animation.stop();
                    animbtn.setText("Play");
                }
                else
                {
                    if(animation.getNumberOfFrames() > 0) {
                        animation.start();
                        animbtn.setText("Stop");
                    }
                }
            }
        });

        loadbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                subpath = (TextView) findViewById(R.id.pathEdit);
                String path = Environment.getExternalStorageDirectory().toString()+(subpath.getText());

                Log.d("Files", "Path+subpath: " + path);
                File f = new File(path);
                Log.d("Files", "file creation " + path);
                Arrays.sort(f.listFiles());
                file = f.listFiles();
                for (int i=0; i<file.length; i++) {
                    animation.addFrame(Drawable.createFromPath(file[i].getAbsolutePath()), 100);
                }
                jpgView.setBackgroundDrawable(animation);
                animbtn.setVisibility(View.VISIBLE);
                animbtn.setClickable(true);
                loadbtn.setText("Update");
            }
        });
        tgLoop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(animation.isOneShot())
                    animation.setOneShot(false);
                else
                    animation.setOneShot(true);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
