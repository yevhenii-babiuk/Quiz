CREATE TABLE quiz
(
    quiz_id         INT PRIMARY KEY,
    name            VARCHAR(50),
    question_number INT,
    image           TEXT

);

CREATE TABLE question
(
    question_id   INT PRIMARY KEY,
    quiz_id       INT,
    question_type ENUM ('select_option', 'select_sequence', 'true_false', 'enter_answer'),
    content       VARCHAR(255),
    score         INT,
    image         TEXT,
    FOREIGN KEY (quiz_id)
        REFERENCES quiz (quiz_id)
        ON DELETE CASCADE
);

CREATE TABLE question_option
(
    option_id      INT PRIMARY KEY,
    question_id    INT,
    content        VARCHAR(255),
    is_correct     BOOLEAN,
    sequence_order INT,
    image          TEXT,
    FOREIGN KEY (question_id)
        REFERENCES question (question_id)
        ON DELETE CASCADE
);

CREATE TABLE game
(
    game_id INT PRIMARY KEY,
    quiz_id INT,
    FOREIGN KEY (quiz_id)
        REFERENCES quiz (quiz_id)
        ON DELETE CASCADE
);

CREATE TABLE user
(
    user_id       INT,
    game_id       INT,
    login         VARCHAR(25),
    registered_id INT,
    score         INT,
    is_correct    BOOLEAN,
    PRIMARY KEY (user_id, game_id, login),
    FOREIGN KEY (game_id)
        REFERENCES game (game_id)
        ON DELETE CASCADE
);

CREATE TABLE game_questions
(
    game_question_id INT PRIMARY KEY,
    game_id          INT,
    question_id      INT,
    is_current       BOOLEAN DEFAULT FALSE,
    finish_time      TIMESTAMP,
    FOREIGN KEY (game_id)
        REFERENCES game (game_id)
        ON DELETE CASCADE,
    FOREIGN KEY (question_id)
        REFERENCES question (question_id)
        ON DELETE CASCADE
);

CREATE TABLE settings
(
    game_id                  INT,
    time                     INT,
    question_answer_sequence BOOLEAN,
    quick_answer_bonus       BOOLEAN,
    combo                    BOOLEAN,
    intermediate_result      BOOLEAN,
    FOREIGN KEY (game_id)
        REFERENCES game (game_id)
        ON DELETE CASCADE
);

CREATE TABLE answer
(
    user_id           INT,
    current_answer    VARCHAR(255),
    question_id       INT,
    is_correct_answer BOOLEAN,
    time              INT,
    FOREIGN KEY (user_id)
        REFERENCES user (user_id)
        ON DELETE CASCADE
);