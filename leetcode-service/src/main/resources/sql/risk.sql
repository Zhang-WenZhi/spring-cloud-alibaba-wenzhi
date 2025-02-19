CREATE TABLE risk (
    id SERIAL PRIMARY KEY,          -- 自增主键
    name VARCHAR(255) NOT NULL,     -- 风险名称
    description TEXT                -- 风险描述
);