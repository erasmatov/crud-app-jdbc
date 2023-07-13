CREATE TABLE developers_skills
(
    developer_id INT references developers (id) NOT NULL,
    skill_id     INT references skills (id)     NOT NULL
);