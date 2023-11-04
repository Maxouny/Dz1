-- Создаем таблицу для хранения данных из CSV с заголовками
CREATE TABLE student_data
(
    student_id  SERIAL PRIMARY KEY, -- Добавляем уникальный идентификатор
    family      VARCHAR,
    name        VARCHAR,
    age         INT,
    class       INT,
    physics     INT,
    mathematics INT,
    rus         INT,
    literature  INT,
    geometry    INT,
    informatics INT,
    UNIQUE (family, name, class)    -- Создаем уникальный индекс
);

-- Создаем таблицу для хранения списка учеников
CREATE TABLE students
(
    student_id INT PRIMARY KEY, -- Внешний ключ
    family VARCHAR,
    name   VARCHAR,
    age    INT,
    class  INT,
    FOREIGN KEY (student_id) REFERENCES student_data(student_id)
);

-- Создаем таблицу для хранения списка учебных групп
CREATE TABLE student_class
(
    student_id INT PRIMARY KEY, -- Внешний ключ
    class  INT,
    family VARCHAR,
    name   VARCHAR,
    age    INT,
    FOREIGN KEY (student_id) REFERENCES student_data(student_id)
);

-- Создаем таблицу для хранения учебных планов
CREATE TABLE curricula
(
    student_id INT PRIMARY KEY,
    subjects TEXT[],
    FOREIGN KEY (student_id) REFERENCES student_data(student_id)
);

-- Создаем таблицу для хранения успеваемости учеников
CREATE TABLE grades
(
    student_id  INT PRIMARY KEY, -- Внешний ключ
    family      VARCHAR,
    name        VARCHAR,
    age         INT,
    class       INT,
    physics     INT,
    mathematics INT,
    rus         INT,
    literature  INT,
    geometry    INT,
    informatics INT,
    average double precision,
    FOREIGN KEY (student_id) REFERENCES student_data (student_id)
);


