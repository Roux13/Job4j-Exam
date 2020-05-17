package ru.job4j.exam;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ru.job4j.exam.dao.HintDao;
import ru.job4j.exam.entitties.Hint;
import ru.job4j.exam.store.ExamRoomDatabase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(RobolectricTestRunner.class)
@Config(minSdk = 19,
maxSdk = 28)
public class HintDaoTest {

    private ExamRoomDatabase db;
    private HintDao hintDao;

    @Before
    public void init() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                ExamRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        hintDao = db.hintDao();
    }

    @After
    public void after() {
        db.close();
    }

    @Test
    public void whenAddHintThenGetTheSameHint() {
        Hint hint = new Hint(1, "text", 1);
        Hint expect = hint;

        hintDao.add(hint);
        Hint actual = hintDao.getHintByQuestionId(1);

        assertThat(actual, is(expect));
    }

    @Test
    public void whenAdd3HintsThenGetByQuestionId2() {
        Hint hint1 = new Hint(1, "Text", 1);
        Hint hint2 = new Hint(2, "Text", 2);
        Hint hint3 = new Hint(3, "Text", 3);
        Hint expect = hint2;

        hintDao.add(hint1);
        hintDao.add(hint2);
        hintDao.add(hint3);
        Hint actual = hintDao.getHintByQuestionId(2);

        assertThat(actual, is(expect));
    }

    @Test
    public void whenAdd3HintsThenDeleteAndGetNull() {
        Hint hint1 = new Hint(1, "Text", 1);
        Hint hint2 = new Hint(2, "Text", 2);
        Hint hint3 = new Hint(3, "Text", 3);
        Hint expect = null;

        hintDao.add(hint1);
        hintDao.add(hint2);
        hintDao.add(hint3);
        hintDao.deleteAll();
        Hint actual = hintDao.getHintByQuestionId(2);

        assertThat(actual, is(expect));
    }
}
