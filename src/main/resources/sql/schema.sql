
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` INT AUTO_INCREMENT NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) UNIQUE NOT NULL,
  `password` BINARY(60) NOT NULL,
  `role` ENUM('DEVELOPER', 'MANAGER') NOT NULL,
  `enabled` BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (`user_id`)
);

CREATE TABLE IF NOT EXISTS `project` (
  `project_id` INT AUTO_INCREMENT NOT NULL,
  `name` VARCHAR(45) UNIQUE NOT NULL,
  PRIMARY KEY (`project_id`)
);

CREATE TABLE IF NOT EXISTS `user_project` (
  `user_id` INT NOT NULL,
  `project_id` INT NOT NULL,
  INDEX `user_id_project_id_idx` (`user_id`,`project_id`),
  FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE ,
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `task` (
  `task_id` INT AUTO_INCREMENT NOT NULL,
  `name` VARCHAR(45),
  `description` text,
  `status` ENUM('WAITING', 'IMPLEMENTATION', 'VERIFYING', 'RELEASING') NOT NULL,
  `project_id` INT NOT NULL,
  `developer_id` INT DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`) ON DELETE CASCADE ,
  FOREIGN KEY (`developer_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE IF NOT EXISTS `comment` (
  `comment_id` INT AUTO_INCREMENT NOT NULL,
  `content` text NOT NULL,
  `task_id` INT NOT NULL,
  PRIMARY KEY (`comment_id`),
  FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`) ON DELETE CASCADE
);


