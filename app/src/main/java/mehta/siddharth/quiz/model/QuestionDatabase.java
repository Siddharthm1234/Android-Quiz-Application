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
            questionDao.insert(new Question("What is Kaleyra, Inc.'s stock symbol?", "KLR", "KAL", "KAY", "KYA", 1));
            questionDao.insert(new Question("When was Kaleyra, Inc. incorporated?", "2020", "1997", "1999", "2005", 3));
            questionDao.insert(new Question("When does Kaleyra, Inc.'s fiscal year end?", "31st December", "1st May", "15th April", "2nd July ", 1));
            questionDao.insert(new Question("Where is Kaleyra corporate headquarters?", "Washington DC", "Bangalore, India", "Moscow, Russia", "Milan, Italy", 4));
            questionDao.insert(new Question("Who is CEO of Kaleyra?", "Julia Pulzone", "Dario Calogero", "Ashish Agarwal", "Emilio Hirsch", 2));
            return null;
        }
    }
}
