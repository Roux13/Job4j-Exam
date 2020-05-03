package ru.job4j.exam.dao;

import androidx.room.Dao;
import androidx.room.Query;

import ru.job4j.exam.entitties.Hint;

@Dao
public interface HintDao {

    @Query("SELECT * FROM hints WHERE _id = :questionId")
    Hint getByQuestionId(int questionId);

}
