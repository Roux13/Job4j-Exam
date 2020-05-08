package ru.job4j.exam.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import ru.job4j.exam.entitties.Hint;

@Dao
public interface HintDao {

    @Insert
    void add(Hint hint);

    @Query("SELECT * FROM hints WHERE id = :questionId")
    Hint getHintByQuestionId(long questionId);

    @Query("DELETE FROM hints")
    void deleteAll();
}
