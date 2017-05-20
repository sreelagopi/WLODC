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

/*Data for the table `cert_role` */

insert  into `cert_role`(`role_id`,`role_name`) values (1,'manager'),(2,'admin'),(3,'resource'),(4,'reviewer');

/*Data for the table `technology` */

insert  into `technology`(`tech_id`,`name`,`description`) values (1,'Practice Exam','Core java practice questions'),(2,'Tapestry','Tapestry questions');

/*Data for the table `level_exam` */

insert  into `level_exam`(`tech_id`,`level`,`question_count`,`descriptive_question_count`,`passing_count`,`timer_level`,`time_sec`) 
values (1,1,10,5,13,10,600),(2,1,14,1,13,10,600),(2,2,12,3,13,10,600),(2,3,13,2,13,10,600),(2,4,10,5,13,10,600),(2,5,10,1,13,10,600);

/*Data for the table `role_map` */
insert  into `role_map`(`das_id`,`role_id`) values ('a134648',2);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
