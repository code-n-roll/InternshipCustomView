package partner.its.com.customview1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final DiagramView diagramView = (DiagramView) findViewById(R.id.diagram_view);
        final SeekBar seekBarPercentage = (SeekBar) findViewById(R.id.seekBarPercentage);
        seekBarPercentage.setProgress(diagramView.getPercentageCount());

        SeekBar.OnSeekBarChangeListener seekBarPercentageChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                diagramView.setPercentageCount(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        seekBarPercentage.setOnSeekBarChangeListener(seekBarPercentageChangeListener);

        SeekBar seekBarSize = (SeekBar) findViewById(R.id.seekBarSize);
        SeekBar.OnSeekBarChangeListener seekBarSizeChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                diagramView.setLayoutParams(new LinearLayout.LayoutParams(progress, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        seekBarSize.setOnSeekBarChangeListener(seekBarSizeChangeListener);

        SeekBar seekBarBackground = (SeekBar) findViewById(R.id.seekBarBackgroundColor);
        SeekBar.OnSeekBarChangeListener seekBarBackgroundChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                diagramView.setBackgroundColor(Color.rgb(progress, progress, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        seekBarBackground.setOnSeekBarChangeListener(seekBarBackgroundChangeListener);


    }
}
