-- mysql
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    email VARCHAR(100)
);

INSERT INTO users (username, password, nickname, email)
VALUES ('admin', '$2a$10$E7Z.6X5UcX7v1h6k8YQzOeY4oWq1fVjJ1GtLW7mKZ4dLb3rJ5S7QW', '管理员', 'admin@example.com');




-- postgresql
-- 创建 users 表（PostgreSQL 语法）
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,      -- 使用 BIGSERIAL 自增主键
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50),
    email VARCHAR(100)
);

-- 插入测试数据（使用 DEFAULT 让主键自增）
INSERT INTO users (username, password, nickname, email)
VALUES (
    'admin',
    '$2a$10$E7Z.6X5UcX7v1h6k8YQzOeY4oWq1fVjJ1GtLW7mKZ4dLb3rJ5S7QW', -- BCrypt 加密后的密码
    '管理员',
    'admin@example.com'
);


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	id bigserial NOT NULL,
	username varchar(50) NOT NULL,
	"password" varchar(100) NOT NULL,
	nickname varchar(50) NULL,
	email varchar(100) NULL,
	phone varchar(100) NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id),
	CONSTRAINT users_username_key UNIQUE (username)
);