package mehta.siddharth.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import mehta.siddharth.quiz.model.Question;
import mehta.siddharth.quiz.viewmodel.QuestionViewModel;

public class QuizActivity extends AppCompatActivity {

    private QuestionViewModel questionViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        questionViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(QuestionViewModel.class);

        questionViewModel.getAllQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                Toast.makeText(QuizActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
