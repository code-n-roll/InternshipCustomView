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
        mProgressView.setCaloriesBudget(500);
        SeekBar consumedSeekbar = (SeekBar) view.findViewById(R.id.seekbar_consumed);
        SeekBar activeSeekbar = (SeekBar) view.findViewById(R.id.seekbar_active);
        SeekBar widthSeekbar = (SeekBar) view.findViewById(R.id.seekbar_width);
        SeekBar heightSeekbar = (SeekBar) view.findViewById(R.id.seekbar_height);
        widthSeekbar.setOnSeekBarChangeListener(mWidthListener);
        heightSeekbar.setOnSeekBarChangeListener(mHeightListener);
        consumedSeekbar.setOnSeekBarChangeListener(mConsumedListener);
        activeSeekbar.setOnSeekBarChangeListener(mActiveListener);
        consumedSeekbar.setMax(mProgressView.getCaloriesBudget());
        activeSeekbar.setMax(mProgressView.getCaloriesBudget());
    }

    private SeekBar.OnSeekBarChangeListener mConsumedListener = new SeekBar.OnSeekBarChangeListener() {
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


    private SeekBar.OnSeekBarChangeListener mActiveListener = new SeekBar.OnSeekBarChangeListener() {
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

    private SeekBar.OnSeekBarChangeListener mWidthListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mProgressView.setLayoutParams(new LinearLayout.LayoutParams(progress, mProgressView.getHeight()));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener mHeightListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mProgressView.setLayoutParams(new LinearLayout.LayoutParams(mProgressView.getWidth(), progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


}
