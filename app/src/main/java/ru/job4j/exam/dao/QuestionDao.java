package ru.job4j.exam.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.job4j.exam.entitties.Question;

@Dao
public interface QuestionDao {

    @Query("SELECT * FROM questions WHERE position = :position ORDER BY position")
    Question getByPosition(int position);

    @Query("SELECT * FROM questions WHERE exam_id = :examId")
    List<Question> getQuestionsByExamId(long examId);

    @Query("SELECT * FROM questions")
    List<Question> getAllQuestion();

    @Query("DELETE FROM questions")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(Question question);

    @Query("SELECT * FROM questions WHERE id = :id")
    Question getById(long id);
}
