--Part 1
-- column name: id | data type: int
-- column name: employer |  data type: varchar(255)
-- column name: name |  data type: varchar(255)
-- column name: skills |  data type: varchar(255)

--Part 2
SELECT name
FROM employer
WHERE location = "St. Louis City";

--Part 3
DROP TABLE job;

--Part 4
SELECT *
FROM skill
LEFT JOIN job_skills ON skill.id = job_skills.skills_id
WHERE job_skills.jobs_id IS NOT NULL
ORDER BY name ASC;