package dev.ssonsallsub.randommenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LinearLayout regLayout;
    EditText input;
    TextView randomText;
    ArrayList<TextView> tvList;
    Random r;
    Integer ranNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        r = new Random();
        tvList = new ArrayList<>();
        regLayout = findViewById(R.id.regLayout);
        input = findViewById(R.id.menuName);
        randomText = findViewById(R.id.randomText);
        randomText.setText("뭐 먹지?");
    }


    public void regBtnClick(View v) {
        TextView tv = new TextView(this);
        tv.setText(input.getText());
        tvList.add(tv);
        regLayout.addView(tv);
        input.setText("");
    }

    public void startBtnClick(View v) {
        try {
            new SetRandomText().execute();
        } catch (Exception e) {
        }
    }

    public void resetClick(View v) {
        input.setText("");
        randomText.setText("");
        tvList.clear();
        regLayout.removeAllViews();
    }

    class SetRandomText extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            if (tvList.size() != 0) {
                ranNum = r.nextInt() % tvList.size();
                while (ranNum < 0) {
                    ranNum = ranNum * (-1);
                }
            }
            publishProgress();
            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
            int test = 0;
            Integer tmp;
            while (test < 100) {
                tmp = r.nextInt() % tvList.size();
                while (tmp < 0) {
                    tmp = tmp * (-1);
                }
                Log.e("test", "onProgressUpdate: "+tmp);
                randomText.setText(tvList.get(tmp).getText());
                test++;

                try {
                    //Thread.sleep(50);
                } catch (Exception e) {
                }
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Integer tmp = ranNum;
            randomText.setText(tvList.get(tmp).getText());

        }
    }
}
