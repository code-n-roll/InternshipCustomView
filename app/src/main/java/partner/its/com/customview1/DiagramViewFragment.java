package partner.its.com.customview1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.util.Random;

/**
 * Created by roman on 15.6.17.
 */

public class DiagramViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_diagram_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final DiagramView diagramView = (DiagramView) view.findViewById(R.id.diagram_view);

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
        final SeekBar seekBarPercentage = (SeekBar) view.findViewById(R.id.seekBarPercentage);
        seekBarPercentage.setProgress(diagramView.getPercentageCount());
        seekBarPercentage.setOnSeekBarChangeListener(seekBarPercentageChangeListener);

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
        SeekBar seekBarSize = (SeekBar) view.findViewById(R.id.seekBarSize);
        seekBarSize.setOnSeekBarChangeListener(seekBarSizeChangeListener);
        seekBarSize.setProgress(diagramView.getLayoutParams().width);

        final Random randomColor = new Random();

        SeekBar.OnSeekBarChangeListener seekBarBackgroundChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                diagramView.setBackgroundColor(Color.rgb(randomColor.nextInt(progress),
                        randomColor.nextInt(progress),
                        randomColor.nextInt(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        SeekBar seekBarBackground = (SeekBar) view.findViewById(R.id.seekBarBackgroundColor);
        seekBarBackground.setOnSeekBarChangeListener(seekBarBackgroundChangeListener);

        SeekBar.OnSeekBarChangeListener seekBarForegroundChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                diagramView.setForegroundColor(Color.rgb(randomColor.nextInt(progress),
                        randomColor.nextInt(progress),
                        randomColor.nextInt(progress)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        SeekBar seekBarForeground = (SeekBar) view.findViewById(R.id.seekBarForegroundColor);
        seekBarForeground.setOnSeekBarChangeListener(seekBarForegroundChangeListener);
    }


}
