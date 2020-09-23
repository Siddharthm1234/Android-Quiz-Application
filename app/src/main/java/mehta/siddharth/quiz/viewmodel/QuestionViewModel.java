package mehta.siddharth.quiz.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import mehta.siddharth.quiz.model.Question;
import mehta.siddharth.quiz.model.QuestionRepository;

public class QuestionViewModel extends AndroidViewModel {
    private QuestionRepository questionRepository;
    private LiveData<List<Question>> allQuestions;
    public QuestionViewModel(@NonNull Application application) {
        super(application);
        allQuestions = questionRepository.getAllQuestions();
    }

    public LiveData<List<Question>> getAllQuestions(){
        return allQuestions;
    }
}
