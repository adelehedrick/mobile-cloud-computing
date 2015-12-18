package ca.hedrick.modernartui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    static private final String URL = "http://www.moma.org";
    static private final String TAG = "ModernArtUI";

    // For use with app chooser
    static private final String CHOOSER_TEXT = "Load " + URL + " with:";

    private SeekBar seeker;
    private int seek_max = 100;

    static private int[] colours;
    private Box[] boxes;

    private class Box {
        public LinearLayout box;
        public int colour;

        public Box(LinearLayout b, int c) {
            box = b;
            colour = c;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        seeker = (SeekBar) findViewById(R.id.seekBar1);
        seeker.setMax(seek_max);
        setSupportActionBar(toolbar);

        colours = new int[5];
        colours[0] = ((ColorDrawable) findViewById(R.id.box_A).getBackground()).getColor();
        colours[1] = ((ColorDrawable) findViewById(R.id.box_B).getBackground()).getColor();
        colours[2] = ((ColorDrawable) findViewById(R.id.box_D).getBackground()).getColor();
        colours[3] = ((ColorDrawable) findViewById(R.id.box_C).getBackground()).getColor();
        colours[4] = ((ColorDrawable) findViewById(R.id.box_E).getBackground()).getColor();

        boxes = new Box[11];
        boxes[0] = new Box((LinearLayout) findViewById(R.id.box_A), colours[0]);
        boxes[1] = new Box((LinearLayout) findViewById(R.id.box_B), colours[1]);
        boxes[2] = new Box((LinearLayout) findViewById(R.id.box_C), colours[3]);
        boxes[3] = new Box((LinearLayout) findViewById(R.id.box_D), colours[2]);
        boxes[4] = new Box((LinearLayout) findViewById(R.id.box_E), colours[4]);
        boxes[5] = new Box((LinearLayout) findViewById(R.id.box_F), colours[1]);
        boxes[6] = new Box((LinearLayout) findViewById(R.id.box_G), colours[2]);
        boxes[7] = new Box((LinearLayout) findViewById(R.id.box_H), colours[3]);
        boxes[8] = new Box((LinearLayout) findViewById(R.id.box_I), colours[0]);
        boxes[9] = new Box((LinearLayout) findViewById(R.id.box_J), colours[1]);
        boxes[10] = new Box((LinearLayout) findViewById(R.id.box_K), colours[4]);


        seeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress;
                Log.d("DEBUG", "Progress is " + progressChanged);
                for (Box b: boxes) {
                    changeColor(b.box, b.colour);

                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            private void changeColor(LinearLayout box, int original_color) {
                float[] hsvColor = new float[3];
                Color.colorToHSV(original_color, hsvColor);
                hsvColor[0] = hsvColor[0] + progressChanged * 360/seek_max;
                hsvColor[0] = hsvColor[0] % 360;
                box.setBackgroundColor(Color.HSVToColor(Color.alpha(original_color), hsvColor));
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

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.more_info_msg)
                    .setPositiveButton(R.string.visit_moma, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                            Intent chooserIntent = Intent.createChooser(baseIntent, CHOOSER_TEXT);
                            Log.i(TAG, "Chooser Intent Action:" + chooserIntent.getAction());
                            startActivity(chooserIntent);
                        }
                    })
                    .setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog myAlert = builder.create();
            myAlert.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
