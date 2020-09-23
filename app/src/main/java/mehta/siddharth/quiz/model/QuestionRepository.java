package mehta.siddharth.quiz.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionRepository {
    private QuestionDao questionDao;
    private LiveData<List<Question>> allQuestions;

    public QuestionRepository(Application application){
        QuestionDatabase database = QuestionDatabase.getInstance(application);
        questionDao = database.questionDao();
        questionDao.getAllQuestions();
    }

    public LiveData<List<Question>> getAllQuestions(){
        return allQuestions;
    }
}
