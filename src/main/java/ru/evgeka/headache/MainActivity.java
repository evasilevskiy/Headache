package ru.evgeka.headache;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private boolean handler_get, handler_put;
    private boolean nochange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nochange = true;

        SwitchCompat switchHeadache = findViewById(R.id.switchHeadache);
        TextView textViewStatus = findViewById(R.id.textViewStatus);

        switchHeadache.setChecked(false);
        switchHeadache.setClickable(false);

        Controller controller = new Controller();
        controller.getState();


        handler_get = new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {

                int result = controller.getResult();

                switch (result) {
                    case 1:
                        textViewStatus.setText(R.string.status_success);
                        switchHeadache.setEnabled(true);
                        switchHeadache.setClickable(true);
                        switchHeadache.setChecked(true);
                        nochange = false;
                        break;
                    case 0:
                        textViewStatus.setText(R.string.status_success);
                        switchHeadache.setEnabled(true);
                        switchHeadache.setClickable(true);
                        switchHeadache.setChecked(false);
                        nochange = false;
                        break;
                    default:
                        textViewStatus.setText(R.string.status_fail);
                        switchHeadache.setEnabled(false);
                        switchHeadache.setClickable(false);
                        break;
                }
            }
        }, 1000);

        switchHeadache.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                boolean isChecked_tmp = isChecked;

                if (!nochange) {

                    textViewStatus.setText(R.string.status_sending);
                    switchHeadache.setEnabled(false);
                    switchHeadache.setClickable(false);

                    controller.putState(isChecked);

                    handler_put = new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            int result = controller.getResult();

                            switchHeadache.setEnabled(true);
                            switchHeadache.setClickable(true);

                            switch (result) {
                                case 1:
                                    textViewStatus.setText(R.string.status_success);
                                    break;
                                default:
                                    textViewStatus.setText(R.string.status_fail);
                                    nochange = true;
                                    switchHeadache.setChecked(!isChecked_tmp);
                                    break;
                            }
                        }
                    }, 1000);
                }
            }
        });
    }

    public void goGrafana (View view) {

        Intent browserIntent = new
        Intent(Intent.ACTION_VIEW, Uri.parse("http://37.46.131.252:15306/d/phVd2EwVz/davlenie-i-golova?orgId=1&from=now-24h&to=now"));
        startActivity(browserIntent);
    }
}