/*
SQLyog Community Edition- MySQL GUI v5.21
Host - 5.0.81-community-nt : Database - tescert
*********************************************************************
Server version : 5.0.81-community-nt
*/


SET NAMES utf8;

SET SQL_MODE='';

SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';

/*Table structure for table `cert_role` */

CREATE TABLE `cert_role` (
  `role_id` int(2) NOT NULL,
  `role_name` varchar(20) NOT NULL,
  PRIMARY KEY  (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `cert_user` */

CREATE TABLE `cert_user` (
  `das_id` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `das_id_mgr` varchar(10) NOT NULL,
  PRIMARY KEY  (`das_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Users ';


/*Table structure for table `role_map` */

CREATE TABLE `role_map` (
  `das_id` varchar(10) NOT NULL,
  `role_id` int(2) NOT NULL,
  PRIMARY KEY  (`das_id`,`role_id`),
  KEY `FK_role_id_role_map` (`role_id`),
  CONSTRAINT `FK_das_id_role_map` FOREIGN KEY (`das_id`) REFERENCES `cert_user` (`das_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_role_id_role_map` FOREIGN KEY (`role_id`) REFERENCES `cert_role` (`role_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `technology` */

CREATE TABLE `technology` (
  `tech_id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(250) NOT NULL,
  PRIMARY KEY  (`tech_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `level_exam` */

CREATE TABLE `level_exam` (
  `exam_id` int unsigned NOT NULL auto_increment,
  `tech_id` int NOT NULL,
  `level` int unsigned NOT NULL default '1',
  `question_count` int default NULL,
  `descriptive_question_count` int default NULL,
  `passing_count` int NOT NULL,
  `timer_level` int NOT NULL default '0',
  `time_sec` int default NULL,  
  /*`descriptive_passing_count` int NOT NULL,*/
  PRIMARY KEY  (`exam_id`),
  KEY `FK_tech_id_level_exam` (`tech_id`),
  CONSTRAINT `FK_tech_id_level_exam` FOREIGN KEY (`tech_id`) REFERENCES `technology` (`tech_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `questions` */

CREATE TABLE `questions` (
  `question_id` int unsigned NOT NULL auto_increment,
  `text` text NOT NULL,
  `option1` varchar(500) default NULL,
  `option2` varchar(500) default NULL,
  `option3` varchar(500) default NULL,
  `option4` varchar(500) default NULL,
  `option5` varchar(500) default NULL,
  `correct_option` int default NULL,
  `exam_id` int unsigned NOT NULL,
  `question_type` int NOT NULL,
  PRIMARY KEY  (`question_id`),
  KEY `FK_exam_id_questions` (`exam_id`),
  CONSTRAINT `FK_exam_id_questions` FOREIGN KEY (`exam_id`) REFERENCES `level_exam` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `results` */

CREATE TABLE `results` (
  `result_id` int(10) unsigned NOT NULL auto_increment,
  `exam_id` int unsigned NOT NULL,
  `das_id` varchar(10) NOT NULL,
  `percentile` double unsigned NOT NULL,
  `result` int(1) unsigned NOT NULL COMMENT '0 = fail, 1 = pass',
  `enrolled_on` timestamp NOT NULL default CURRENT_TIMESTAMP COMMENT '0 = fail, 1 = pass',
  `submitted_on` timestamp NULL default NULL,
  PRIMARY KEY  (`result_id`),
  KEY `FK_exam_id_results` (`exam_id`),
  KEY `FK_das_id_results` (`das_id`),
  CONSTRAINT `FK_das_id_results` FOREIGN KEY (`das_id`) REFERENCES `cert_user` (`das_id`),
  CONSTRAINT `FK_exam_id_results` FOREIGN KEY (`exam_id`) REFERENCES `level_exam` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `result_details` */

CREATE TABLE `result_details` (
  `result_id` int(10) unsigned NOT NULL,
  `question_id` int unsigned NOT NULL,
  `answer_option` varchar(500) default NULL,
  `correct_option` int(1) default NULL,
  KEY `FK_result_id_result_details` (`result_id`),
  KEY `FK_question_id_questions` (`question_id`),
  CONSTRAINT `FK_question_id_questions` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`),
  CONSTRAINT `FK_result_id_result_details` FOREIGN KEY (`result_id`) REFERENCES `results` (`result_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
