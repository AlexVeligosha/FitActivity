package com.example.hatsune.fitactivity.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import com.example.hatsune.fitactivity.R;
import com.example.hatsune.fitactivity.dto.ActivityDTO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment {

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    TimerStatus timeStatus = TimerStatus.STOPPED;

    private Button button;
    private ProgressBar progressBarCircle;
    private EditText editTextMinute;
    private TextView textViewTime;
    private ImageView imageViewReset;
    private ImageView imageViewStartStop;
    private CountDownTimer countDownTimer;

    private long remainTime;
    private long timeCountInMilliSeconds = 1 * 60000;





    private OnFragmentInteractionListener mListener;

    public TimerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment TimerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimerFragment newInstance() {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(R.string.activity);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        progressBarCircle = (ProgressBar) view.findViewById(R.id.progressBarCircle);
        editTextMinute = (EditText) view.findViewById(R.id.editTextMinute);
        textViewTime = (TextView) view.findViewById(R.id.textViewTime);
        imageViewReset = (ImageView) view.findViewById(R.id.imageViewReset);
        imageViewReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopCountDownTimer();
                startCountDownTimer();
            }
        });
        imageViewStartStop = (ImageView) view.findViewById(R.id.imageViewStartStop);
        imageViewStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });
        return view;
    }

    private void startStop() {
        if(timeStatus == TimerStatus.STOPPED){
            setTimeValue();
            setProgressBarValue();
            imageViewReset.setVisibility(View.VISIBLE);
            imageViewStartStop.setImageResource(R.drawable.ic_stop);
            editTextMinute.setEnabled(false);
            timeStatus = timeStatus.STARTED;
            startCountDownTimer();
        }
        else {
            imageViewReset.setVisibility(View.GONE);
            imageViewStartStop.setImageResource(R.drawable.ic_start);
            editTextMinute.setEnabled(true);
            timeStatus = timeStatus.STOPPED;
            stopCountDownTimer();
        }
    }

    private void setTimeValue() {
        int time = 0;
        if (!editTextMinute.getText().toString().isEmpty()){
            time = Integer.parseInt(editTextMinute.getText().toString().trim());
        }
        else {
            Toast.makeText(getActivity(), getString(R.string.message_minutes), Toast.LENGTH_LONG).show();
        }
        timeCountInMilliSeconds = time * 60 * 1000;
    }

    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisecondUntilFinished) {
                textViewTime.setText(hmsTimeFormatter(millisecondUntilFinished));
                progressBarCircle.setProgress((int) (millisecondUntilFinished / 1000));
                remainTime = millisecondUntilFinished / (1000 * 60);
            }

            @Override
            public void onFinish() {
                textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));
                setProgressBarValue();
                imageViewReset.setVisibility(View.GONE);
                imageViewStartStop.setImageResource(R.drawable.ic_start);
                editTextMinute.setEnabled(true);
                timeStatus = TimerStatus.STOPPED;
                ActivityDTO activityDTO = new ActivityDTO(String.valueOf(remainTime));

            }
        }.start();
        countDownTimer.start();
    }

    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    private void setProgressBarValue(){
        progressBarCircle.setMax((int) (timeCountInMilliSeconds / 1000));
        progressBarCircle.setProgress((int) (timeCountInMilliSeconds / 1000));
    }

    private String hmsTimeFormatter(long milliSeconds){
        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MICROSECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnFragmentInteractionListener {

        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




}
