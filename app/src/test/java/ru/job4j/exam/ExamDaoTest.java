package ru.job4j.exam;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.job4j.exam.dao.ExamDao;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.store.ExamRoomDatabase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(maxSdk = 28)
public class ExamDaoTest {

    private ExamRoomDatabase db;
    private ExamDao examDao;

    private LiveData<List<Exam>> liveData;
    private List<Exam> exams;

    @Mock
    private Observer observer;

    @Mock
    private LifecycleOwner mockOwner;

    private LifecycleRegistry lifecycle;

    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                ExamRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        examDao = db.examDao();
        liveData = examDao.getAll();
        liveData.observeForever(observer);
        lifecycle = new LifecycleRegistry(mockOwner);
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        when(mockOwner.getLifecycle()).thenReturn(lifecycle);
    }

    @After
    public void after() {
        db.close();
    }

    @Test
    public void whenAddExamThenGetTheSame() {
        Exam exam = new Exam(1, "Title", "Desc", 1, 1);
        Exam expect = exam;

        examDao.add(exam);
        liveData.observe(mockOwner, data -> exams = data);
        Exam actual = exams.get(0);
        assertThat(actual, is(expect));
    }

    @Test
    public void whenAdd3ExamsThenGetTheSame3() {
        Exam exam1 = new Exam(1, "Title1", "Desc", 1, 1);
        Exam exam2 = new Exam(2, "Title2", "Desc", 1, 1);
        Exam exam3 = new Exam(3, "Title3", "Desc", 1, 1);
        List<Exam> expect = Arrays.asList(exam1, exam2, exam3);

        examDao.add(exam1);
        examDao.add(exam2);
        examDao.add(exam3);
        liveData.observe(mockOwner, data -> exams = data);
        List<Exam> actual = exams;
        assertThat(actual, is(expect));
    }

    @Test
    public void whenAddExamAndUpdate() {
        Exam exam1 = new Exam(1, "Title1", "Desc", 1, 1);
        examDao.add(exam1);
        exam1.setDesc("Edit");
        Exam expect = exam1;
        examDao.update(exam1);

        liveData.observe(mockOwner, data -> exams = data);
        Exam actual = exams.get(0);
        assertThat(actual, is(expect));
    }

    @Test
    public void whenAdd3ExamsThenDeleteById2AndGetRemaining2() {
        Exam exam1 = new Exam(1, "Title1", "Desc", 1, 1);
        Exam exam2 = new Exam(2, "Title2", "Desc", 1, 1);
        Exam exam3 = new Exam(3, "Title3", "Desc", 1, 1);
        List<Exam> expect = Arrays.asList(exam1, exam3);

        examDao.add(exam1);
        examDao.add(exam2);
        examDao.add(exam3);
        examDao.deleteById(exam2);
        liveData.observe(mockOwner, data -> exams = data);
        List<Exam> actual = exams;
        assertThat(actual, is(expect));
    }

    @Test
    public void whenAdd3ExamsThenDeleteAllAndGetEmpty() {
        Exam exam1 = new Exam(1, "Title1", "Desc", 1, 1);
        Exam exam2 = new Exam(2, "Title2", "Desc", 1, 1);
        Exam exam3 = new Exam(3, "Title3", "Desc", 1, 1);
        List<Exam> expect = Collections.emptyList();

        examDao.add(exam1);
        examDao.add(exam2);
        examDao.add(exam3);
        examDao.deleteAll();
        liveData.observe(mockOwner, data -> exams = data);
        List<Exam> actual = exams;
        assertThat(actual, is(expect));
    }
}
