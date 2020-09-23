package mehta.siddharth.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mehta.siddharth.quiz.model.Question;
import mehta.siddharth.quiz.viewmodel.QuestionViewModel;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView,questionNumberTextView;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4;
    private Button nextButton;
    private int markScored = 0;

    List<Question> questionList;
    Question currQuestion;

    boolean answered;
    private int questionNumber = 0, totalQuestions;

    private QuestionViewModel questionViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        questionTextView = findViewById(R.id.questionTextView);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        nextButton = findViewById(R.id.nextButton);


        questionViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(QuestionViewModel.class);

        questionViewModel.getAllQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                Toast.makeText(QuizActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
                fetchQuestion(questions);
            }
        });

    }
    //Fetch all questions from Database along with the options and answers
    private void fetchQuestion(List<Question> questions){
        questionList = questions;
        startQuiz();
    }

    private void setQuestionOnUI(){
        radioGroup.clearCheck();
        totalQuestions = questionList.size();

        if(questionNumber < totalQuestions){
            currQuestion = questionList.get(questionNumber);
            questionNumberTextView.setText("QUESTION: "+questionNumber+1);
            questionTextView.setText(currQuestion.getQuestion());
            rb1.setText(currQuestion.getOptionA());
            rb2.setText(currQuestion.getOptionB());
            rb3.setText(currQuestion.getOptionC());
            rb4.setText(currQuestion.getOptionD());
            questionNumber++;
            answered = false;
        }
        else{
            //TODO: leave page
        }
    }

    private void startQuiz() {
        setQuestionOnUI();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answered){
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        quizOperation();
                    }else{
                        Toast.makeText(QuizActivity.this, "Please select your answer!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void quizOperation(){
        answered = true;
        int rbSelected = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = radioGroup.findViewById(rbSelected);
        int idx = radioGroup.indexOfChild(radioButton);

        if(idx == currQuestion.getAnswer()){
            markScored+=1;
        }
        setQuestionOnUI();
    }

}
