package mehta.siddharth.quiz.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Question.class, version = 1)
public abstract class QuestionDatabase extends RoomDatabase {
    
    private static QuestionDatabase instance;

    public abstract QuestionDao questionDao();
    public static synchronized QuestionDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    QuestionDatabase.class, "question_database")
                    .addCallback(roomCallBack)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private QuestionDao questionDao;

        private PopulateDbAsyncTask(QuestionDatabase questionDatabase){
            questionDao = questionDatabase.questionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            questionDao.insert(new Question("What is Question1?", "option1", "option2", "option3", "option4", 1));
            questionDao.insert(new Question("What is Question2?", "optionA", "optionB", "optionC", "optionD", 4));
            return null;
        }
    }
}
