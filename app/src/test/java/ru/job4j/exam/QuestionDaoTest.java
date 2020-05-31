package ru.job4j.exam;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.job4j.exam.dao.QuestionDao;
import ru.job4j.exam.entitties.Question;
import ru.job4j.exam.store.ExamRoomDatabase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(RobolectricTestRunner.class)
@Config(minSdk = 21,
maxSdk = 28)
public class QuestionDaoTest {

    private ExamRoomDatabase db;
    private QuestionDao questionDao;

    @Before
    public void init() {
        db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getContext(),
                ExamRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        questionDao = db.questionDao();
    }

    @After
    public void after() {
        db.close();
    }

    @Test
    public void whenAddQuestionThenWeGetTheSame() {
        Question question = new Question(1, "text", 1, 1, 1);

        questionDao.add(question);
        Question actual = questionDao.getQuestionsByExamId(1).get(0);

        assertThat(actual, is(question));
    }

    @Test
    public void whenAdd3QuestionsThenWeGetTheSame3() {
        Question question1 = new Question(1, "text", 1, 1, 1);
        Question question2 = new Question(2, "text", 1, 1, 1);
        Question question3 = new Question(3, "text", 1, 1, 1);
        List<Question> expect = Arrays.asList(question1, question2, question3);

        questionDao.add(question1);
        questionDao.add(question2);
        questionDao.add(question3);
        List<Question> actual = questionDao.getQuestionsByExamId(1);

        assertThat(actual, is(expect));
    }

    @Test
    public void whenAdd3QuestionsThenDeleteAllAndGetEmpty() {
        Question question1 = new Question(1, "text", 1, 1, 1);
        Question question2 = new Question(2, "text", 1, 1, 1);
        Question question3 = new Question(3, "text", 1, 1, 1);
        List<Question> expect = Collections.emptyList();

        questionDao.add(question1);
        questionDao.add(question2);
        questionDao.add(question3);
        questionDao.deleteAll();
        List<Question> actual = questionDao.getQuestionsByExamId(1);

        assertThat(actual, is(expect));
    }

    @Test
    public void whenAdd3QuestionsWithDifferentExamIdThenDeleteAllAndGetEmpty() {
        Question question1 = new Question(1, "text", 1, 1, 1);
        Question question2 = new Question(2, "text", 2, 1, 1);
        Question question3 = new Question(3, "text", 3, 1, 1);
        List<Question> expect = Arrays.asList(question1, question2, question3);

        questionDao.add(question1);
        questionDao.add(question2);
        questionDao.add(question3);
        List<Question> actual = questionDao.getAllQuestion();

        assertThat(actual, is(expect));
    }

    @Test
    public void whenAdd3QuestionsThenGetOneWithId2() {
        Question question1 = new Question(1, "text", 1, 1, 1);
        Question question2 = new Question(2, "text", 2, 1, 1);
        Question question3 = new Question(3, "text", 3, 1, 1);

        questionDao.add(question1);
        questionDao.add(question2);
        questionDao.add(question3);
        Question actual = questionDao.getById(2);

        assertThat(actual, is(question2));
    }

}
