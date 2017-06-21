package partner.its.com.customview1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by roman on 20.6.17.
 */

public class CaloriesConsumedFragment extends Fragment {
    private CaloriesConsumedProgressView mProgressView;
    private TextView caloriesBudget;
    private TextView caloriesConsumed;
    private TextView caloriesActive;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calories_consumed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressView = (CaloriesConsumedProgressView) view.findViewById(R.id.calories_consumed_progress);
        caloriesConsumed = (TextView) view.findViewById(R.id.calories_consumed);
        caloriesActive = (TextView) view.findViewById(R.id.calories_active);
        caloriesBudget = (TextView) view.findViewById(R.id.calories_budget);

        SeekBar consumedSeekbar = (SeekBar) view.findViewById(R.id.seekbar_consumed);
        SeekBar activeSeekbar = (SeekBar) view.findViewById(R.id.seekbar_active);
        SeekBar widthSeekbar = (SeekBar) view.findViewById(R.id.seekbar_width);
        SeekBar heightSeekbar = (SeekBar) view.findViewById(R.id.seekbar_height);
        widthSeekbar.setOnSeekBarChangeListener(mWidthListener);
        heightSeekbar.setOnSeekBarChangeListener(mHeightListener);
        consumedSeekbar.setOnSeekBarChangeListener(mConsumedListener);
        activeSeekbar.setOnSeekBarChangeListener(mActiveListener);
        consumedSeekbar.setMax(mProgressView.getCaloriesConsumed() + 2000);
        activeSeekbar.setMax(mProgressView.getCaloriesActive() + 500);
    }

    private SeekBar.OnSeekBarChangeListener mConsumedListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mProgressView.setCaloriesConsumed(progress);
            String text = mProgressView.getCaloriesConsumed()+"cal";
            caloriesConsumed.setText(text);
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
            String text = mProgressView.getCaloriesActive() + "cal";
            caloriesActive.setText(text);
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
