package mehta.siddharth.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView resultTextView;
    Button restartButton, exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        resultTextView = findViewById(R.id.resultTextView);
        restartButton = findViewById(R.id.restartButton);
        exitButton = findViewById(R.id.exitButton);

        Intent intent = getIntent();
        int marksScored = intent.getIntExtra(QuizActivity.MARKS_SCORED, 0);
        int totalQuestions = intent.getIntExtra(QuizActivity.TOTAL_QUESTIONS, 0);

        resultTextView.setText("Scored:\n"+ marksScored +" / "+ totalQuestions);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);

            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ResultActivity.this.startActivity(intent);
            }
        });
    }


}
