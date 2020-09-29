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
            questionDao.insert(new Question("Which protocol is used to send E-mail?", "HTTP", "FTP", "POP3", "SMTP", 4));
            questionDao.insert(new Question("What is the full form of HTTP?", "Hyper Transfer Text Protocol", "Hyper Text Transfer Protocol", "Hexagonal Text Transfer Protocol", "Hyper Text Transfer Prototype", 2));
            questionDao.insert(new Question("Random Access Memory (RAM) is which storage of device?", "Primary", "Secondary", "Tertiary", "None of the above", 1));
            questionDao.insert(new Question("What is the size of IP v-4 address?", "16-bits", "8-bits", "32-bits", "24-bits", 3));
            questionDao.insert(new Question("In computer, what is the smallest and basic unit of information storage?", "Bit", "Byte", "Mega byte", "Newton", 1));
            return null;
        }
    }
}
