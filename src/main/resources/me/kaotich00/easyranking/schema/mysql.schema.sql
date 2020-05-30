CREATE TABLE IF NOT EXISTS `easyranking_board` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(500) NOT NULL,
  `max_players` int(11) NOT NULL,
  `is_visible` int(11) NOT NULL,
  `is_deleted` int(11) NOT NULL,
  `user_score_name` varchar(100) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `easyranking_user` (
  `uuid` varchar(36) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `easyranking_user_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` varchar(36) NOT NULL,
  `id_board` int(11) NOT NULL,
  `amount` float NOT NULL DEFAULT '0',
  `created_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_score_fk_board` (`id_board`),
  KEY `user_score_fk_user` (`id_user`),
  CONSTRAINT `user_score_fk_board` FOREIGN KEY (`id_board`) REFERENCES `easyranking_board` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_score_fk_user` FOREIGN KEY (`id_user`) REFERENCES `easyranking_user` (`uuid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `easyranking_score_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user_score` int(11) NOT NULL,
  `amount` float NOT NULL DEFAULT '0',
  `board_reset_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `created_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `score_history_fk_user_score` (`id_user_score`),
  CONSTRAINT `score_history_fk_user_score` FOREIGN KEY (`id_user_score`) REFERENCES `easyranking_user_score` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;