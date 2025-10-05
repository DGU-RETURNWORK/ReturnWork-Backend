-- V1__create_tables.sql

CREATE TABLE IF NOT EXISTS regions (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       dong_li VARCHAR(50) NULL,
    sido VARCHAR(50) NULL,
    sido_normalized VARCHAR(50) NULL,
    sigungu VARCHAR(50) NULL,
    full_address VARCHAR(200) NOT NULL,
    search_keywords VARCHAR(500) NULL
    );

CREATE TABLE IF NOT EXISTS user (
                                    user_id BINARY(16) NOT NULL PRIMARY KEY,
    birthday DATE NOT NULL,
    created_at DATETIME(6) NULL,
    deleted_at DATETIME(6) NULL,
    region_id BIGINT NOT NULL,
    updated_at DATETIME(6) NULL,
    name VARCHAR(15) NULL,
    phone_number VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NULL,
    provider_id VARCHAR(100) NULL,
    career TEXT NULL,
    image_key VARCHAR(255) NULL,
    provider ENUM('GOOGLE', 'NORMAL') NOT NULL,
    role ENUM('ADMIN', 'USER') NOT NULL,
    status ENUM('ACTIVE', 'DELETED', 'PENDING') NOT NULL,
    CONSTRAINT UKob8kqyqqgmefl0aco34akdtpe UNIQUE (email),
    CONSTRAINT FKne7659jgj43guudxjysfdrotw FOREIGN KEY (region_id) REFERENCES regions (id)
    );

CREATE TABLE IF NOT EXISTS job (
                                   job_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   code BIGINT NOT NULL,
                                   created_at DATETIME(6) NULL,
    updated_at DATETIME(6) NULL,
    name VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS accident (
                                        accident_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        created_at DATETIME(6) NULL,
    updated_at DATETIME(6) NULL,
    user_id BINARY(16) NULL,
    detail VARCHAR(200) NOT NULL,
    accident_type VARCHAR(50) NOT NULL,
    industry_type VARCHAR(50) NOT NULL,
    injury_area VARCHAR(50) NOT NULL,
    injury_severity VARCHAR(50) NOT NULL,
    CONSTRAINT FKbfexqgyxsevhqaksc3e1f4xbo FOREIGN KEY (user_id) REFERENCES user (user_id)
    );

CREATE TABLE IF NOT EXISTS survey (
                                      survey_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      a1 INT NULL,
                                      a2 INT NULL,
                                      a3 INT NULL,
                                      a4 INT NULL,
                                      a5 INT NULL,
                                      a6 INT NULL,
                                      a7 INT NULL,
                                      a8 INT NULL,
                                      a9 INT NULL,
                                      a10 INT NULL,
                                      survey_date DATE NULL,
                                      created_at DATETIME(6) NULL,
    updated_at DATETIME(6) NULL,
    user_id BINARY(16) NULL,
    status ENUM('PENDING', 'SUCCESS') NULL,
    CONSTRAINT FK51x6iogwvw5n6pa7sl339ltju FOREIGN KEY (user_id) REFERENCES user (user_id)
    );

CREATE TABLE IF NOT EXISTS profile (
                                       profile_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       accident_id BIGINT NULL,
                                       created_at DATETIME(6) NULL,
    survey_id BIGINT NULL,
    updated_at DATETIME(6) NULL,
    user_id BINARY(16) NULL,
    CONSTRAINT FK9kpiyr7t1iesitxor3t3wn0he FOREIGN KEY (survey_id) REFERENCES survey (survey_id),
    CONSTRAINT FKa68ddxoy100hlh4md6qcl2m47 FOREIGN KEY (accident_id) REFERENCES accident (accident_id),
    CONSTRAINT FKawh070wpue34wqvytjqr4hj5e FOREIGN KEY (user_id) REFERENCES user (user_id)
    );

CREATE TABLE IF NOT EXISTS profile_job (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           created_at DATETIME(6) NULL,
    job_id BIGINT NULL,
    profile_id BIGINT NULL,
    updated_at DATETIME(6) NULL,
    CONSTRAINT FKbt5p2chuhomvlre0eoikhpj6r FOREIGN KEY (profile_id) REFERENCES profile (profile_id),
    CONSTRAINT FKl26fcujjsmtftrwkriq19h5eo FOREIGN KEY (job_id) REFERENCES job (job_id)
    );

CREATE TABLE IF NOT EXISTS resume (
                                      resume_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      question_count INT NOT NULL,
                                      created_at DATETIME(6) NULL,
    job_id BIGINT NULL,
    updated_at DATETIME(6) NULL,
    user_id BINARY(16) NULL,
    capability VARCHAR(50) NOT NULL,
    career TEXT NULL,
    title VARCHAR(255) NOT NULL,
    resume_status ENUM('COMPLETED', 'DRAFT') NOT NULL,
    CONSTRAINT FKh60knx17xjxxo5ox298xcu5ai FOREIGN KEY (job_id) REFERENCES job (job_id),
    CONSTRAINT FKiqntisdlc7ta7sjr6d8rj5ae2 FOREIGN KEY (user_id) REFERENCES user (user_id)
    );

CREATE TABLE IF NOT EXISTS resume_question (
                                               resume_question_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                               question_order INT NULL,
                                               word_limit INT NULL,
                                               created_at DATETIME(6) NULL,
    resume_id BIGINT NULL,
    updated_at DATETIME(6) NULL,
    resume_answer TEXT NULL,
    resume_question VARCHAR(255) NULL,
    question_status ENUM('ANSWERED', 'ANSWERING', 'GENERATED') NULL,
    CONSTRAINT FK2e9regavqu7bklh0770ng3ddp FOREIGN KEY (resume_id) REFERENCES resume (resume_id)
    );

CREATE TABLE IF NOT EXISTS training (
                                        training_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        course_fee INT NULL,
                                        end_date DATE NULL,
                                        start_date DATE NULL,
                                        created_at DATETIME(6) NULL,
    inst_code BIGINT NOT NULL,
    region_id BIGINT NULL,
    training_area_code BIGINT NULL,
    updated_at DATETIME(6) NULL,
    address VARCHAR(255) NULL,
    title VARCHAR(255) NOT NULL,
    CONSTRAINT FK7t0qlyrlnhn8xjj1atdwtevad FOREIGN KEY (region_id) REFERENCES regions (id)
    );