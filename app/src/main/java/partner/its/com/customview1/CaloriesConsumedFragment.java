package partner.its.com.customview1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

/**
 * Created by roman on 20.6.17.
 */

public class CaloriesConsumedFragment extends Fragment {
    private CaloriesConsumedProgressView mProgressView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calories_consumed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressView = (CaloriesConsumedProgressView) view.findViewById(R.id.calories_consumed_progress);
        SeekBar consumedSeekbar = (SeekBar) view.findViewById(R.id.seekbar_consumed);
        SeekBar activeSeekbar = (SeekBar) view.findViewById(R.id.seekbar_active);
        consumedSeekbar.setOnSeekBarChangeListener(consumedListener);
        activeSeekbar.setOnSeekBarChangeListener(activeListener);
    }

    private SeekBar.OnSeekBarChangeListener consumedListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mProgressView.setCaloriesConsumed(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    private SeekBar.OnSeekBarChangeListener activeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mProgressView.setCaloriesActive(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
