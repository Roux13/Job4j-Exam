package ru.job4j.exam.store;

import android.provider.BaseColumns;

public final class ExamDbSchema {

    private ExamDbSchema() {
    }

    public final static class ExamTable implements BaseColumns {

        public static final String TABLE_NAME = "exams";

        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESC = "description";
        public static final String COLUMN_NAME_RESULT = "result";
        public static final String COLUMN_NAME_DATE = "date";

    }

    public static final class QuestionTable implements BaseColumns {

        public static final String TABLE_NAME = "questions";

        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESC = "description";
        public static final String COLUMN_NAME_EXAM_ID = "exam_id";
        public static final String COLUMN_NAME_ANSWER_ID = "answer_id";
        public static final String COLUMN_NAME_POSITION = "position";

    }

    public static final class AnswersTable implements BaseColumns {

        public static final String TABLE_NAME = "answers";

        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_QUESTION_ID = "question_id";
//        public static final String COLUMN_NAME_CORRECT = "correct";

    }

    public static final class HintTable implements BaseColumns {

        public static final String TABLE_NAME = "hints";

        public static final String COLUMN_NAME_HINT = "hint";
        public static final String COLUMN_NAME_QUESTION_ID = "question_id";

    }

}
