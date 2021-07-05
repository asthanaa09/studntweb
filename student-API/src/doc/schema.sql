-------------------------------
-- MYSQL v8.0
-------------------------------

--------------------------------
-- Table Structure: Role
--------------------------------

CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` int NOT NULL,
  `creation_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--------------------------------
-- Table Structure: Student
--------------------------------

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `student_id` bigint DEFAULT NULL,
  `access_time` datetime DEFAULT NULL,
  `active` int NOT NULL DEFAULT '0',
  `creation_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--------------------------------
-- Table Structure: User
--------------------------------

CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `student` bigint NOT NULL,
  `access_time` datetime DEFAULT NULL,
  `creation_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--------------------------------
-- Table Structure: User_roles
--------------------------------

CREATE TABLE `user_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `role_id` int NOT NULL,
  `creation_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_ref_idx` (`user_id`),
  KEY `role_ref_idx` (`role_id`),
  CONSTRAINT `role_ref` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `user_ref` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--------------------------------
-- Table Structure: Project
--------------------------------

CREATE TABLE `project` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `photo_id` bigint DEFAULT NULL,
  `duration` int NOT NULL,
  `student_id` bigint NOT NULL,
  `creation_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `student_ref_idx` (`student_id`),
  CONSTRAINT `student_ref` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--------------------------------
-- Table Structure: files
--------------------------------

CREATE TABLE `files` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `content` varchar(45) NOT NULL,
  `file_type` int NOT NULL,
  `endpoint` text NOT NULL,
  `creation_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


