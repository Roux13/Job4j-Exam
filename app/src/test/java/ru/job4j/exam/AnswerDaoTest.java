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

import ru.job4j.exam.dao.AnswerDao;
import ru.job4j.exam.entitties.Answer;
import ru.job4j.exam.store.ExamRoomDatabase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(RobolectricTestRunner.class)
@Config(minSdk = 21,
        maxSdk = 28)
public class AnswerDaoTest {

    private ExamRoomDatabase db;
    private AnswerDao answerDao;

    @Before
    public void initDB() {
        db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getContext(),
                ExamRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        answerDao = db.answerDao();
    }

    @After
    public void after() {
        db.close();
    }

    @Test
    public void whenAddAnswerThenGetTheSame() {
        Answer answer = new Answer(1, "Text", 1);

        answerDao.add(answer);
        Answer actual = answerDao.getAnswersByQuestionId(1).get(0);

        assertThat(actual, is(answer));
    }

    @Test
    public void whenAdd3AnswerThenGetTheSame3() {
        Answer answer1 = new Answer(1, "Text", 1);
        Answer answer2 = new Answer(2, "Text", 1);
        Answer answer3 = new Answer(3, "Text", 1);
        List<Answer> expect = Arrays.asList(answer1, answer2, answer3);

        answerDao.add(answer1);
        answerDao.add(answer2);
        answerDao.add(answer3);
        List<Answer> actual = answerDao.getAnswersByQuestionId(1);

        assertThat(actual, is(expect));
    }

    @Test
    public void whenThenDeleteAllThenGetEmpty() {
        Answer answer1 = new Answer(1, "Text", 1);
        Answer answer2 = new Answer(2, "Text", 1);
        Answer answer3 = new Answer(3, "Text", 1);

        List<Answer> expect = Collections.emptyList();

        answerDao.add(answer1);
        answerDao.add(answer2);
        answerDao.add(answer3);
        answerDao.deleteAll();
        List<Answer> actual = answerDao.getAnswersByQuestionId(1);

        assertThat(actual, is(expect));
    }

}