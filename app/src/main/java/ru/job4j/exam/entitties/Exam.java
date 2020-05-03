package ru.job4j.exam.entitties;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "exams")
public class Exam implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String desc;

    @ColumnInfo(name = "result",
    defaultValue = "0")
    private int result;

    @ColumnInfo(name = "date")
    private long time;

    @ColumnInfo(name = "is_selected",
    defaultValue = "0")
    private boolean isSelected;

    public Exam(int id, String title, String desc, int result, long time, boolean isSelected) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.result = result;
        this.isSelected = isSelected;
    }

    @Ignore
    public Exam(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public long getTime() {
        return time;
    }

    public int getResult() {
        return result;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return id == exam.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", time=" + time +
                ", result=" + result +
                '}';
    }
}
