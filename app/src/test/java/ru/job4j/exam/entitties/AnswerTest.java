package ru.job4j.exam.entitties;

import org.junit.Test;

import static org.junit.Assert.*;

public class AnswerTest {

    @Test
    public void equalsWhenTwoAnswersWithTheSameArgs() {
        Answer answer1 = new Answer(0, "", 0);
        Answer answer2 = new Answer(0, "", 0);

        assertEquals(answer1, answer2);
    }

}