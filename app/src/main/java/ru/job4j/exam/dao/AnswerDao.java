package ru.job4j.exam.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.job4j.exam.entitties.Answer;

@Dao
public interface AnswerDao {

    @Query("SELECT * FROM answers WHERE question_id = :questionId")
    List<Answer> getAnswersByQuestionId(long questionId);

    @Query("DELETE FROM answers")
    void deleteAll();

    @Insert
    void add(Answer answer);
}
