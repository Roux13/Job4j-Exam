package ru.job4j.exam.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ru.job4j.exam.entitties.Question;

@Dao
public interface QuestionDao {

    @Query("SELECT * FROM questions WHERE position = :position")
    Question getByPosition(int position);

    @Query("SELECT * FROM questions WHERE exam_id = :examId")
    List<Question> getQuestionsByExamId(int examId);
}
