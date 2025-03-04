--
---- 任务主表
--CREATE TABLE job_task (
--  id              BIGINT AUTO_INCREMENT PRIMARY KEY,
--  task_name       VARCHAR(100)  NOT NULL,
--  task_class      VARCHAR(255)  NOT NULL,
--  method_name     VARCHAR(100)  NOT NULL,
--  trigger_type    VARCHAR(20)   NOT NULL DEFAULT 'CRON',
--  cron_expression VARCHAR(50)            DEFAULT NULL,
--  interval_millis BIGINT                 DEFAULT NULL,
--  enabled         TINYINT(1)    NOT NULL DEFAULT 1,
--  version         INT           NOT NULL DEFAULT 0,
--  description     VARCHAR(500),
--  create_time     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
--  update_time     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
--  UNIQUE KEY uniq_job (task_class, method_name),
--  CHECK (
--    (trigger_type = 'CRON' AND cron_expression IS NOT NULL) OR
--    (trigger_type IN ('FIXED_RATE', 'FIXED_DELAY') AND interval_millis > 0)
--  )
--);
--
---- 任务执行日志表
--CREATE TABLE job_task_log (
--  id           BIGINT AUTO_INCREMENT PRIMARY KEY,
--  task_id      BIGINT       NOT NULL,
--  status       VARCHAR(20)  NOT NULL,
--  start_time   DATETIME     NOT NULL,
--  end_time     DATETIME              DEFAULT NULL,
--  error_info   TEXT                  DEFAULT NULL,
--  duration     BIGINT                DEFAULT NULL,
--  trigger_type VARCHAR(20)  NOT NULL,
--  trigger_conf VARCHAR(100) NOT NULL
--);
--
---- 通知配置表（可选）
--CREATE TABLE notify_config (
--  id           BIGINT AUTO_INCREMENT PRIMARY KEY,
--  task_id      BIGINT      NOT NULL,
--  notify_type  VARCHAR(20) NOT NULL,
--  receiver     VARCHAR(500) NOT NULL,
--  template     TEXT        NOT NULL
--);




-- 任务主表
CREATE TABLE job_task (
  id              BIGSERIAL PRIMARY KEY,
  task_name       VARCHAR(100)  NOT NULL,
  task_class      VARCHAR(255)  NOT NULL,
  method_name     VARCHAR(100)  NOT NULL,
  trigger_type    VARCHAR(20)   NOT NULL DEFAULT 'CRON',
  cron_expression VARCHAR(50)            DEFAULT NULL,
  interval_millis BIGINT                 DEFAULT NULL,
  enabled         BOOLEAN       NOT NULL DEFAULT TRUE,
  version         INT           NOT NULL DEFAULT 0,
  description     VARCHAR(500),
  create_time     TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time     TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (task_class, method_name),
  CHECK (
    (trigger_type = 'CRON' AND cron_expression IS NOT NULL) OR
    (trigger_type IN ('FIXED_RATE', 'FIXED_DELAY') AND interval_millis > 0)
  )
);

-- 创建一个触发器函数，用于在更新时自动更新 update_time 列
CREATE OR REPLACE FUNCTION update_job_task_update_time()
RETURNS TRIGGER AS $$
BEGIN
    NEW.update_time = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 创建触发器，当 job_task 表有更新操作时触发上述函数
CREATE TRIGGER update_job_task_trigger
BEFORE UPDATE ON job_task
FOR EACH ROW
EXECUTE FUNCTION update_job_task_update_time();

-- 任务执行日志表
CREATE TABLE job_task_log (
  id           BIGSERIAL PRIMARY KEY,
  task_id      BIGINT       NOT NULL,
  status       VARCHAR(20)  NOT NULL,
  start_time   TIMESTAMP    NOT NULL,
  end_time     TIMESTAMP              DEFAULT NULL,
  error_info   TEXT                  DEFAULT NULL,
  duration     BIGINT                DEFAULT NULL,
  trigger_type VARCHAR(20)  NOT NULL,
  trigger_conf VARCHAR(100) NOT NULL
);

-- 通知配置表（可选）
CREATE TABLE notify_config (
  id           BIGSERIAL PRIMARY KEY,
  task_id      BIGINT      NOT NULL,
  notify_type  VARCHAR(20) NOT NULL,
  receiver     VARCHAR(500) NOT NULL,
  template     TEXT        NOT NULL
);


INSERT INTO public.job_task
(id, task_name, task_class, method_name, trigger_type, cron_expression, interval_millis, enabled, "version", description, create_time, update_time)
VALUES(1, '每日报表', 'com.wenzhi.leetcode_service.task.execute.ReportJob', 'generateDailyReport', 'CRON', '0 0 3 * * ?', NULL, true, 0, NULL, '2025-03-04 23:34:00.263', '2025-03-05 02:25:40.464');
INSERT INTO public.job_task
(id, task_name, task_class, method_name, trigger_type, cron_expression, interval_millis, enabled, "version", description, create_time, update_time)
VALUES(2, '链表打印', 'com.wenzhi.leetcode_service.task.execute.PrettyPrintLinkedListExecute', 'execute', 'CRON', '0 0/5 * * * ?', NULL, true, 0, 'This is a test task 1', '2025-03-05 00:28:03.501', '2025-03-05 02:30:50.526');
INSERT INTO public.job_task
(id, task_name, task_class, method_name, trigger_type, cron_expression, interval_millis, enabled, "version", description, create_time, update_time)
VALUES(3, '邮件发送', 'com.wenzhi.leetcode_service.task.execute.EmailJob', 'run', 'FIXED_RATE', '', 60000, true, 0, 'This is a test task 2', '2025-03-05 00:28:03.501', '2025-03-05 02:30:19.066'); Task 2', 'com.example.tasks.TestTask2', 'run', 'FIXED_RATE', NULL, 60000, true, 0, 'This is a test task 2');






