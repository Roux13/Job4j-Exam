package ru.job4j.exam.entitties;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "questions", foreignKeys =
        {@ForeignKey(entity = Exam.class,
                    parentColumns = "_id",
                    childColumns = "exam_id"),
        @ForeignKey(entity = Answer.class,
                    parentColumns = "_id",
                    childColumns = "correct_answer_id")
        })
public class Question {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "exam_id")
    private int examId;

    @ColumnInfo(name = "correct_answer_id")
    private int correctAnswerId;

    @ColumnInfo(name = "position")
    private int position;

    @Ignore
    private List<Answer> answers;

    public Question(int id, String text, int examId, int correctAnswerId, int position) {
        this.id = id;
        this.text = text;
        this.examId = examId;
        this.correctAnswerId = correctAnswerId;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public int getExamId() {
        return examId;
    }

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }

    public int getPosition() {
        return position;
    }

    public Question(int id, String text, List<Answer> answers, int correctAnswerId) {
        this.id = id;
        this.text = text;
        this.answers = answers;
        this.correctAnswerId = correctAnswerId;
    }

    public String getText() {
        return text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public int getAnswer() {
        return correctAnswerId;
    }
}
