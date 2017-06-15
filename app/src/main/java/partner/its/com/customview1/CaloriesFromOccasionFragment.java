package partner.its.com.customview1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

/**
 * Created by roman on 15.6.17.
 */

public class CaloriesFromOccasionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.content_caloriesfrom_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final CaloriesFromOccasionsDiagramView diagramView =
                (CaloriesFromOccasionsDiagramView) view.findViewById(R.id.calories_diagram_view);
        final SeekBar seekBarThickness = (SeekBar) view.findViewById(R.id.seekBarThickness);

        SeekBar.OnSeekBarChangeListener seekBarPercentageChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                diagramView.setThicknessDiagram(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        seekBarThickness.setOnSeekBarChangeListener(seekBarPercentageChangeListener);

        SeekBar seekBarSize = (SeekBar) view.findViewById(R.id.seekBarSize);
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
        seekBarSize.setProgress(diagramView.getLayoutParams().width);
    }
}
