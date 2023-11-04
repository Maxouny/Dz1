ALTER TABLE student_data DROP CONSTRAINT student_data_family_name_class_key;
-- Копируем данные из CSV-файла в общую таблицу student_data
COPY student_data (family,
                   name,
                   age,
                   class,
                   physics,
                   mathematics,
                   rus,
                   literature,
                   geometry,
                   informatics)
    FROM '/students.csv' DELIMITER ';' CSV HEADER;

-- Заполняем таблицу students из общей таблицы
INSERT INTO students (student_id, family, name, age, class)
SELECT student_id, family, name, age, class
FROM student_data;

-- Заполняем таблицу student_class из общей таблицы
INSERT INTO student_class (student_id, class, family, name, age)
SELECT student_id, class, family, name, age
FROM student_data;

-- Заполняем таблицу curricula с учебными планами
INSERT INTO curricula (student_id, subjects)
SELECT student_id, ARRAY ['physics', 'mathematics', 'rus', 'literature', 'geometry', 'informatics']
FROM student_data;

-- Заполняем таблицу grades из общей таблицы
INSERT INTO grades (student_id, family, name, age, class, physics, mathematics, rus, literature, geometry, informatics)
SELECT student_id,
       family,
       name,
       age,
       class,
       physics,
       mathematics,
       rus,
       literature,
       geometry,
       informatics
FROM student_data;
UPDATE grades
SET average = (physics + mathematics + rus + literature + geometry + informatics) / 6;