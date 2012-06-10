CREATE DATABASE  IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `test`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.1.53-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `play_evolutions`
--

DROP TABLE IF EXISTS `play_evolutions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `play_evolutions` (
  `id` int(11) NOT NULL,
  `hash` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `applied_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `apply_script` text COLLATE utf8_unicode_ci,
  `revert_script` text COLLATE utf8_unicode_ci,
  `state` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_problem` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `play_evolutions`
--

LOCK TABLES `play_evolutions` WRITE;
/*!40000 ALTER TABLE `play_evolutions` DISABLE KEYS */;
INSERT INTO `play_evolutions` VALUES (1,'750c21cfb3e56f8515a095ba19d60b61bfdf287b','2012-04-29 12:14:41','create table AddedByAdmin (\nid                        bigint auto_increment not null,\ngpm                       bigint,\ngroupDescr                bigint,\nposition                  integer not null,\ndateOfAddition            datetime not null,\ndateOfRemoval             datetime,\ncommentField              varchar(255),\nconstraint uq_AddedByAdmin_1 unique (gpm,groupDescr),\nconstraint pk_AddedByAdmin primary key (id))\n;\n\ncreate table BlackList (\nid                        bigint auto_increment not null,\ndateOfAddition            datetime not null,\nreasonOfAddition          varchar(255),\nconstraint pk_BlackList primary key (id))\n;\n\ncreate table Content (\nid                        bigint auto_increment not null,\nkind                      varchar(5) not null,\nconstraint uq_Content_kind unique (kind),\nconstraint pk_Content primary key (id))\n;\n\ncreate table GPM (\nid                        bigint auto_increment not null,\nidGpm                     varchar(21) not null,\nconstraint uq_GPM_idGpm unique (idGpm),\nconstraint pk_GPM primary key (id))\n;\n\ncreate table Gender (\nid                        bigint auto_increment not null,\nvalue                     varchar(10) not null,\nconstraint uq_Gender_value unique (value),\nconstraint pk_Gender primary key (id))\n;\n\ncreate table GroupDescr (\nid                        bigint auto_increment not null,\nname                      varchar(50) not null,\nactiveImage               varchar(255) not null,\npassiveImage              varchar(255) not null,\ndescription               varchar(255),\ntextPercent               integer,\nimagePercent              integer,\nlinkPercent               integer,\nvideoPercent              integer,\naudioPercent              integer,\nconstraint uq_GroupDescr_name unique (name),\nconstraint pk_GroupDescr primary key (id))\n;\n\ncreate table GroupLink (\nid                        bigint auto_increment not null,\ngroupDescr                bigint,\nlink                      bigint,\npostWeight                float not null,\nprofileWeight             float not null,\nconstraint uq_GroupLink_1 unique (groupDescr,link),\nconstraint pk_GroupLink primary key (id))\n;\n\ncreate table GroupWord (\nid                        bigint auto_increment not null,\ngroupDescr                bigint,\nword                      bigint,\npostWeight                float not null,\nprofileWeight             float not null,\nconstraint uq_GroupWord_1 unique (groupDescr,word),\nconstraint pk_GroupWord primary key (id))\n;\n\ncreate table Link (\nid                        bigint auto_increment not null,\nlink                      varchar(255) not null,\nconstraint uq_Link_link unique (link),\nconstraint pk_Link primary key (id))\n;\n\ncreate table NewGPM (\nid                        bigint auto_increment not null,\nidGpm                     varchar(21) not null,\nnMentiens                 integer not null,\nconstraint uq_NewGPM_idGpm unique (idGpm),\nconstraint pk_NewGPM primary key (id))\n;\n\ncreate table Post (\nid                        bigint auto_increment not null,\npostId                    varchar(40) not null,\ngpm                       bigint,\ndateCreate                datetime not null,\nkindContent               bigint,\nnComment                  integer,\nnPlusOne                  integer,\nnResharers                integer,\nisRepost                  tinyint(1) default 0 not null,\nconstraint uq_Post_postId unique (postId),\nconstraint pk_Post primary key (id))\n;\n\ncreate table PostLink (\nid                        bigint auto_increment not null,\npost                      bigint,\nlink                      bigint,\namount                    integer not null,\nconstraint uq_PostLink_1 unique (post,link),\nconstraint pk_PostLink primary key (id))\n;\n\ncreate table PostWord (\nid                        bigint auto_increment not null,\npost                      bigint,\nword                      bigint,\namount                    integer not null,\nconstraint uq_PostWord_1 unique (post,word),\nconstraint pk_PostWord primary key (id))\n;\n\ncreate table Profile (\nid                        bigint auto_increment not null,\ngpm                       bigint,\ndateUpdated               datetime not null,\nname                      varchar(100),\nimage                     varchar(255),\ngender                    bigint,\ntagline                   varchar(255),\nrelationshipStatus        bigint,\nnFollowers                integer,\nconstraint uq_Profile_1 unique (gpm,dateUpdated),\nconstraint pk_Profile primary key (id))\n;\n\ncreate table ProfileLink (\nid                        bigint auto_increment not null,\nprofile                   bigint,\nlink                      bigint,\namount                    integer not null,\nconstraint uq_ProfileLink_1 unique (profile,link),\nconstraint pk_ProfileLink primary key (id))\n;\n\ncreate table ProfileWord (\nid                        bigint auto_increment not null,\nprofile                   bigint,\nword                      bigint,\namount                    integer not null,\nconstraint uq_ProfileWord_1 unique (profile,word),\nconstraint pk_ProfileWord primary key (id))\n;\n\ncreate table Relationship (\nid                        bigint auto_increment not null,\nstatus                    varchar(30) not null,\nconstraint uq_Relationship_status unique (status),\nconstraint pk_Relationship primary key (id))\n;\n\ncreate table Synonym (\nid                        bigint auto_increment not null,\nword                      bigint,\nsynonym                   varchar(30) not null,\nconstraint uq_Synonym_1 unique (word,synonym),\nconstraint pk_Synonym primary key (id))\n;\n\ncreate table Word (\nid                        bigint auto_increment not null,\nword                      varchar(30) not null,\nconstraint uq_Word_word unique (word),\nconstraint pk_Word primary key (id))\n;\n\nalter table AddedByAdmin add constraint fk_AddedByAdmin_gpm_1 foreign key (gpm) references GPM (id) on delete restrict on update restrict;\ncreate index ix_AddedByAdmin_gpm_1 on AddedByAdmin (gpm);\nalter table AddedByAdmin add constraint fk_AddedByAdmin_group_2 foreign key (groupDescr) references GroupDescr (id) on delete restrict on update restrict;\ncreate index ix_AddedByAdmin_group_2 on AddedByAdmin (groupDescr);\nalter table BlackList add constraint fk_BlackList_gpm_3 foreign key (Id) references GPM (id) on delete restrict on update restrict;\ncreate index ix_BlackList_gpm_3 on BlackList (Id);\nalter table GroupLink add constraint fk_GroupLink_group_4 foreign key (groupDescr) references GroupDescr (id) on delete restrict on update restrict;\ncreate index ix_GroupLink_group_4 on GroupLink (groupDescr);\nalter table GroupLink add constraint fk_GroupLink_link_5 foreign key (link) references Link (id) on delete restrict on update restrict;\ncreate index ix_GroupLink_link_5 on GroupLink (link);\nalter table GroupWord add constraint fk_GroupWord_group_6 foreign key (groupDescr) references GroupDescr (id) on delete restrict on update restrict;\ncreate index ix_GroupWord_group_6 on GroupWord (groupDescr);\nalter table GroupWord add constraint fk_GroupWord_word_7 foreign key (word) references Word (id) on delete restrict on update restrict;\ncreate index ix_GroupWord_word_7 on GroupWord (word);\nalter table Post add constraint fk_Post_gpm_8 foreign key (gpm) references GPM (id) on delete restrict on update restrict;\ncreate index ix_Post_gpm_8 on Post (gpm);\nalter table Post add constraint fk_Post_kindContent_9 foreign key (kindContent) references Content (id) on delete restrict on update restrict;\ncreate index ix_Post_kindContent_9 on Post (kindContent);\nalter table PostLink add constraint fk_PostLink_post_10 foreign key (post) references Post (id) on delete restrict on update restrict;\ncreate index ix_PostLink_post_10 on PostLink (post);\nalter table PostLink add constraint fk_PostLink_link_11 foreign key (link) references Link (id) on delete restrict on update restrict;\ncreate index ix_PostLink_link_11 on PostLink (link);\nalter table PostWord add constraint fk_PostWord_post_12 foreign key (post) references Post (id) on delete restrict on update restrict;\ncreate index ix_PostWord_post_12 on PostWord (post);\nalter table PostWord add constraint fk_PostWord_word_13 foreign key (word) references Word (id) on delete restrict on update restrict;\ncreate index ix_PostWord_word_13 on PostWord (word);\nalter table Profile add constraint fk_Profile_gpm_14 foreign key (gpm) references GPM (id) on delete restrict on update restrict;\ncreate index ix_Profile_gpm_14 on Profile (gpm);\nalter table Profile add constraint fk_Profile_gender_15 foreign key (gender) references Gender (id) on delete restrict on update restrict;\ncreate index ix_Profile_gender_15 on Profile (gender);\nalter table Profile add constraint fk_Profile_relationshipStatus_16 foreign key (relationshipStatus) references Relationship (id) on delete restrict on update restrict;\ncreate index ix_Profile_relationshipStatus_16 on Profile (relationshipStatus);\nalter table ProfileLink add constraint fk_ProfileLink_profile_17 foreign key (profile) references Profile (id) on delete restrict on update restrict;\ncreate index ix_ProfileLink_profile_17 on ProfileLink (profile);\nalter table ProfileLink add constraint fk_ProfileLink_link_18 foreign key (link) references Link (id) on delete restrict on update restrict;\ncreate index ix_ProfileLink_link_18 on ProfileLink (link);\nalter table ProfileWord add constraint fk_ProfileWord_profile_19 foreign key (profile) references Profile (id) on delete restrict on update restrict;\ncreate index ix_ProfileWord_profile_19 on ProfileWord (profile);\nalter table ProfileWord add constraint fk_ProfileWord_word_20 foreign key (word) references Word (id) on delete restrict on update restrict;\ncreate index ix_ProfileWord_word_20 on ProfileWord (word);\nalter table Synonym add constraint fk_Synonym_word_21 foreign key (word) references Word (id) on delete restrict on update restrict;\ncreate index ix_Synonym_word_21 on Synonym (word);','SET FOREIGN_KEY_CHECKS=0;\n\ndrop table AddedByAdmin;\n\ndrop table BlackList;\n\ndrop table Content;\n\ndrop table GPM;\n\ndrop table Gender;\n\ndrop table GroupDescr;\n\ndrop table GroupLink;\n\ndrop table GroupWord;\n\ndrop table Link;\n\ndrop table NewGPM;\n\ndrop table Post;\n\ndrop table PostLink;\n\ndrop table PostWord;\n\ndrop table Profile;\n\ndrop table ProfileLink;\n\ndrop table ProfileWord;\n\ndrop table Relationship;\n\ndrop table Synonym;\n\ndrop table Word;\n\nSET FOREIGN_KEY_CHECKS=1;','applied','');
/*!40000 ALTER TABLE `play_evolutions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gpm` bigint(20) DEFAULT NULL,
  `dateUpdated` datetime NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gender` bigint(20) DEFAULT NULL,
  `tagline` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `relationshipStatus` bigint(20) DEFAULT NULL,
  `nFollowers` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_Profile_1` (`gpm`,`dateUpdated`),
  KEY `ix_Profile_gpm_14` (`gpm`),
  KEY `ix_Profile_gender_15` (`gender`),
  KEY `ix_Profile_relationshipStatus_16` (`relationshipStatus`),
  CONSTRAINT `fk_Profile_gender_15` FOREIGN KEY (`gender`) REFERENCES `gender` (`id`),
  CONSTRAINT `fk_Profile_gpm_14` FOREIGN KEY (`gpm`) REFERENCES `gpm` (`id`),
  CONSTRAINT `fk_Profile_relationshipStatus_16` FOREIGN KEY (`relationshipStatus`) REFERENCES `relationship` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES (9,112,'2012-05-27 17:04:18','Alan Shapiro','https://lh5.googleusercontent.com/-L4dAo8ap9vg/AAAAAAAAAAI/AAAAAAAAAAA/WTxfIRcQ7MY/photo.jpg?sz=50',1,'Passionate, award-winning photographer. Advertising Chief Creative Officer, too.',NULL,100);
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grouplink`
--

DROP TABLE IF EXISTS `grouplink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grouplink` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `groupDescr` bigint(20) DEFAULT NULL,
  `link` bigint(20) DEFAULT NULL,
  `postWeight` float NOT NULL,
  `profileWeight` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_GroupLink_1` (`groupDescr`,`link`),
  KEY `ix_GroupLink_group_4` (`groupDescr`),
  KEY `ix_GroupLink_link_5` (`link`),
  CONSTRAINT `fk_GroupLink_group_4` FOREIGN KEY (`groupDescr`) REFERENCES `groupdescr` (`id`),
  CONSTRAINT `fk_GroupLink_link_5` FOREIGN KEY (`link`) REFERENCES `link` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grouplink`
--

LOCK TABLES `grouplink` WRITE;
/*!40000 ALTER TABLE `grouplink` DISABLE KEYS */;
INSERT INTO `grouplink` VALUES (1,3,3,0,0),(2,3,4,0,0),(3,3,5,0,0),(4,3,7,0,0),(5,3,8,0,0),(6,4,1,0,0),(7,4,2,0,0),(8,4,10,0,0),(9,5,6,0,0),(10,5,9,0,0);
/*!40000 ALTER TABLE `grouplink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relationship`
--

DROP TABLE IF EXISTS `relationship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relationship` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_Relationship_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relationship`
--

LOCK TABLES `relationship` WRITE;
/*!40000 ALTER TABLE `relationship` DISABLE KEYS */;
INSERT INTO `relationship` VALUES (3,'engaged'),(2,'in_a_relationship'),(9,'in_civil_union'),(8,'in_domestic_partnership'),(5,'its_complicated'),(4,'married'),(6,'open_relationship'),(1,'single'),(7,'widowed');
/*!40000 ALTER TABLE `relationship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupword`
--

DROP TABLE IF EXISTS `groupword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groupword` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `groupDescr` bigint(20) DEFAULT NULL,
  `word` bigint(20) DEFAULT NULL,
  `postWeight` float NOT NULL,
  `profileWeight` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_GroupWord_1` (`groupDescr`,`word`),
  KEY `ix_GroupWord_group_6` (`groupDescr`),
  KEY `ix_GroupWord_word_7` (`word`),
  CONSTRAINT `fk_GroupWord_group_6` FOREIGN KEY (`groupDescr`) REFERENCES `groupdescr` (`id`),
  CONSTRAINT `fk_GroupWord_word_7` FOREIGN KEY (`word`) REFERENCES `word` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=310 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupword`
--

LOCK TABLES `groupword` WRITE;
/*!40000 ALTER TABLE `groupword` DISABLE KEYS */;
INSERT INTO `groupword` VALUES (1,1,1,0,0),(2,1,2,0,0),(3,1,3,0,0),(4,1,4,0,0),(5,1,5,0,0),(6,1,6,0,0),(7,1,7,0,0),(8,1,8,0,0),(9,1,9,0,0),(10,1,10,0,0),(11,1,11,0,0),(12,1,12,0,0),(13,1,13,0,0),(14,1,14,0,0),(15,1,17,0,0),(16,1,18,0,0),(17,1,19,0,0),(18,1,20,0,0),(19,1,21,0,0),(20,1,22,0,0),(21,1,23,0,0),(22,1,24,0,0),(23,1,25,0,0),(24,1,26,0,0),(25,1,27,0,0),(26,1,28,0,0),(27,1,29,0,0),(28,1,30,0,0),(29,1,31,0,0),(30,1,32,0,0),(31,1,33,0,0),(32,1,34,0,0),(33,2,35,0,0),(34,2,36,0,0),(35,2,37,0,0),(36,2,38,0,0),(37,2,39,0,0),(38,2,40,0,0),(39,2,41,0,0),(40,2,42,0,0),(41,2,43,0,0),(42,2,44,0,0),(43,2,45,0,0),(44,2,46,0,0),(45,2,47,0,0),(46,2,48,0,0),(47,2,49,0,0),(48,2,50,0,0),(49,2,51,0,0),(50,2,52,0,0),(51,2,53,0,0),(52,2,54,0,0),(53,2,55,0,0),(54,2,56,0,0),(55,2,57,0,0),(56,2,58,0,0),(57,2,59,0,0),(58,2,60,0,0),(59,2,61,0,0),(60,2,62,0,0),(61,2,63,0,0),(62,2,64,0,0),(63,2,65,0,0),(64,2,66,0,0),(65,2,67,0,0),(66,2,68,0,0),(67,2,69,0,0),(68,2,70,0,0),(69,2,71,0,0),(70,2,72,0,0),(71,2,73,0,0),(72,2,74,0,0),(73,2,75,0,0),(74,2,76,0,0),(75,2,77,0,0),(76,2,78,0,0),(77,2,79,0,0),(78,2,80,0,0),(79,2,81,0,0),(80,2,82,0,0),(81,2,83,0,0),(82,2,84,0,0),(83,2,85,0,0),(84,2,86,0,0),(85,2,87,0,0),(86,2,88,0,0),(87,2,89,0,0),(88,2,90,0,0),(89,2,92,0,0),(90,2,93,0,0),(91,2,94,0,0),(92,2,95,0,0),(93,2,96,0,0),(94,2,97,0,0),(95,2,98,0,0),(96,2,99,0,0),(97,2,100,0,0),(98,2,101,0,0),(99,2,102,0,0),(100,2,103,0,0),(101,2,104,0,0),(102,2,105,0,0),(103,2,106,0,0),(104,2,108,0,0),(105,2,109,0,0),(106,2,110,0,0),(107,2,111,0,0),(108,2,112,0,0),(109,2,113,0,0),(110,2,114,0,0),(111,2,115,0,0),(112,3,116,0,0),(113,3,117,0,0),(114,3,118,0,0),(115,3,119,0,0),(116,3,120,0,0),(117,3,121,0,0),(118,3,122,0,0),(119,3,123,0,0),(120,3,124,0,0),(121,3,125,0,0),(122,3,126,0,0),(123,3,127,0,0),(124,3,128,0,0),(125,3,129,0,0),(126,3,130,0,0),(127,3,131,0,0),(128,3,132,0,0),(129,3,133,0,0),(130,3,134,0,0),(131,3,135,0,0),(132,3,136,0,0),(133,3,137,0,0),(134,3,138,0,0),(135,3,139,0,0),(136,3,140,0,0),(137,3,141,0,0),(138,3,142,0,0),(139,3,143,0,0),(140,4,1,0,0),(141,4,6,0,0),(142,4,7,0,0),(143,4,10,0,0),(144,4,17,0,0),(145,4,18,0,0),(146,4,28,0,0),(147,4,29,0,0),(148,4,36,0,0),(149,4,43,0,0),(150,4,59,0,0),(151,4,78,0,0),(152,4,145,0,0),(153,4,146,0,0),(154,4,148,0,0),(155,4,149,0,0),(156,4,150,0,0),(157,4,151,0,0),(158,4,152,0,0),(159,4,153,0,0),(160,4,154,0,0),(161,4,155,0,0),(162,4,157,0,0),(163,4,159,0,0),(164,4,160,0,0),(165,4,161,0,0),(166,4,162,0,0),(167,4,163,0,0),(168,4,164,0,0),(169,4,165,0,0),(170,4,166,0,0),(171,4,168,0,0),(172,4,169,0,0),(173,4,170,0,0),(174,4,171,0,0),(175,4,172,0,0),(176,4,175,0,0),(177,4,176,0,0),(178,4,177,0,0),(179,4,178,0,0),(180,4,180,0,0),(181,4,183,0,0),(182,4,184,0,0),(183,4,185,0,0),(184,4,187,0,0),(185,4,189,0,0),(186,4,190,0,0),(187,4,191,0,0),(188,4,192,0,0),(189,4,193,0,0),(190,4,194,0,0),(191,4,195,0,0),(192,4,196,0,0),(193,4,197,0,0),(194,4,198,0,0),(195,4,199,0,0),(196,4,200,0,0),(197,4,201,0,0),(198,4,202,0,0),(199,4,203,0,0),(200,4,204,0,0),(201,4,205,0,0),(202,4,206,0,0),(203,4,207,0,0),(204,5,6,0,0),(205,5,7,0,0),(206,5,128,0,0),(207,5,131,0,0),(208,5,203,0,0),(209,5,208,0,0),(210,5,209,0,0),(211,5,210,0,0),(212,5,211,0,0),(213,5,213,0,0),(214,5,214,0,0),(215,5,215,0,0),(216,5,216,0,0),(217,5,217,0,0),(218,5,218,0,0),(219,5,219,0,0),(220,5,220,0,0),(221,5,221,0,0),(222,5,223,0,0),(223,5,224,0,0),(224,5,226,0,0),(225,5,227,0,0),(226,5,228,0,0),(227,5,229,0,0),(228,5,230,0,0),(229,5,231,0,0),(230,5,232,0,0),(231,5,233,0,0),(232,5,234,0,0),(233,5,235,0,0),(234,5,236,0,0),(235,5,237,0,0),(236,5,238,0,0),(237,5,239,0,0),(238,5,241,0,0),(239,5,242,0,0),(240,5,243,0,0),(241,5,244,0,0),(242,5,245,0,0),(243,5,246,0,0),(244,5,247,0,0),(245,5,248,0,0),(246,5,250,0,0),(247,5,251,0,0),(248,5,253,0,0),(249,5,254,0,0),(250,5,255,0,0),(251,5,256,0,0),(252,5,257,0,0),(253,5,258,0,0),(254,5,259,0,0),(255,5,260,0,0),(256,5,261,0,0),(257,5,262,0,0),(258,5,263,0,0),(259,5,264,0,0),(260,5,265,0,0),(261,5,266,0,0),(262,5,267,0,0),(263,5,268,0,0),(264,5,269,0,0),(265,5,270,0,0),(266,5,271,0,0),(267,5,272,0,0),(268,5,273,0,0),(269,5,274,0,0),(270,5,275,0,0),(271,5,276,0,0),(272,5,277,0,0),(273,5,278,0,0),(274,5,279,0,0),(275,5,280,0,0),(276,5,281,0,0),(277,5,282,0,0),(278,5,283,0,0),(279,5,284,0,0),(280,5,285,0,0),(281,5,286,0,0),(282,5,287,0,0),(283,5,288,0,0),(284,5,289,0,0),(285,5,290,0,0),(286,5,291,0,0),(287,5,292,0,0),(288,5,293,0,0),(289,5,294,0,0),(290,5,295,0,0),(291,5,296,0,0),(292,5,297,0,0),(293,5,298,0,0),(294,5,299,0,0),(295,5,300,0,0),(296,5,301,0,0),(297,5,302,0,0),(298,5,303,0,0),(299,5,304,0,0),(300,5,305,0,0),(301,5,306,0,0),(302,5,307,0,0),(303,5,308,0,0),(304,5,309,0,0),(305,5,310,0,0),(306,5,311,0,0),(307,5,312,0,0),(308,5,313,0,0),(309,5,314,0,0);
/*!40000 ALTER TABLE `groupword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postlink`
--

DROP TABLE IF EXISTS `postlink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `postlink` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `post` bigint(20) DEFAULT NULL,
  `link` bigint(20) DEFAULT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_PostLink_1` (`post`,`link`),
  KEY `ix_PostLink_post_10` (`post`),
  KEY `ix_PostLink_link_11` (`link`),
  CONSTRAINT `fk_PostLink_link_11` FOREIGN KEY (`link`) REFERENCES `link` (`id`),
  CONSTRAINT `fk_PostLink_post_10` FOREIGN KEY (`post`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postlink`
--

LOCK TABLES `postlink` WRITE;
/*!40000 ALTER TABLE `postlink` DISABLE KEYS */;
/*!40000 ALTER TABLE `postlink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blacklist`
--

DROP TABLE IF EXISTS `blacklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blacklist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dateOfAddition` datetime NOT NULL,
  `reasonOfAddition` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_BlackList_gpm_3` (`id`),
  CONSTRAINT `fk_BlackList_gpm_3` FOREIGN KEY (`id`) REFERENCES `gpm` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blacklist`
--

LOCK TABLES `blacklist` WRITE;
/*!40000 ALTER TABLE `blacklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `blacklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `addedbyadmin`
--

DROP TABLE IF EXISTS `addedbyadmin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `addedbyadmin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gpm` bigint(20) DEFAULT NULL,
  `groupDescr` bigint(20) DEFAULT NULL,
  `position` int(11) NOT NULL,
  `dateOfAddition` datetime NOT NULL,
  `dateOfRemoval` datetime DEFAULT NULL,
  `commentField` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_AddedByAdmin_1` (`gpm`,`groupDescr`),
  KEY `ix_AddedByAdmin_gpm_1` (`gpm`),
  KEY `ix_AddedByAdmin_group_2` (`groupDescr`),
  CONSTRAINT `fk_AddedByAdmin_gpm_1` FOREIGN KEY (`gpm`) REFERENCES `gpm` (`id`),
  CONSTRAINT `fk_AddedByAdmin_group_2` FOREIGN KEY (`groupDescr`) REFERENCES `groupdescr` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addedbyadmin`
--

LOCK TABLES `addedbyadmin` WRITE;
/*!40000 ALTER TABLE `addedbyadmin` DISABLE KEYS */;
/*!40000 ALTER TABLE `addedbyadmin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `link`
--

DROP TABLE IF EXISTS `link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `link` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `link` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_Link_link` (`link`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `link`
--

LOCK TABLES `link` WRITE;
/*!40000 ALTER TABLE `link` DISABLE KEYS */;
INSERT INTO `link` VALUES (1,'http://www.500px.com'),(7,'http://www.blogger.com'),(3,'http://www.blogspot.com'),(2,'http://www.flickr.com'),(6,'http://www.github.com'),(8,'http://www.livejournal.com'),(10,'http://www.panoramio.com'),(9,'http://www.stackoverflow.com'),(4,'http://www.wordpress.com'),(5,'http://www.youtube.com');
/*!40000 ALTER TABLE `link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profilelink`
--

DROP TABLE IF EXISTS `profilelink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profilelink` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `profile` bigint(20) DEFAULT NULL,
  `link` bigint(20) DEFAULT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_ProfileLink_1` (`profile`,`link`),
  KEY `ix_ProfileLink_profile_17` (`profile`),
  KEY `ix_ProfileLink_link_18` (`link`),
  CONSTRAINT `fk_ProfileLink_link_18` FOREIGN KEY (`link`) REFERENCES `link` (`id`),
  CONSTRAINT `fk_ProfileLink_profile_17` FOREIGN KEY (`profile`) REFERENCES `profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profilelink`
--

LOCK TABLES `profilelink` WRITE;
/*!40000 ALTER TABLE `profilelink` DISABLE KEYS */;
/*!40000 ALTER TABLE `profilelink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `synonym`
--

DROP TABLE IF EXISTS `synonym`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `synonym` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `word` bigint(20) DEFAULT NULL,
  `synonym` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_Synonym_1` (`word`,`synonym`),
  KEY `ix_Synonym_word_21` (`word`),
  CONSTRAINT `fk_Synonym_word_21` FOREIGN KEY (`word`) REFERENCES `word` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synonym`
--

LOCK TABLES `synonym` WRITE;
/*!40000 ALTER TABLE `synonym` DISABLE KEYS */;
INSERT INTO `synonym` VALUES (5,1,'художник'),(4,2,'искусство'),(2,24,'акриловый'),(3,25,'архитектор'),(6,26,'художественный'),(1,31,'абстрактный'),(7,35,'футбол'),(8,36,'тайм'),(9,37,'спортивный'),(11,39,'спорт'),(12,40,'баскетбол'),(13,41,'игра'),(14,42,'круг'),(15,43,'круги'),(16,44,'лыжи'),(17,45,'гонка'),(18,46,'соревнования'),(19,47,'плавание'),(20,48,'игральный'),(21,49,'команда'),(22,50,'бег'),(23,51,'велогонка'),(24,52,'попытка'),(25,53,'партия'),(26,54,'хоккей'),(27,55,'тренировка'),(28,56,'спортсмен'),(29,57,'волейбол'),(30,58,'гоночный'),(31,59,'место'),(32,60,'велосипед'),(33,61,'фитнес'),(34,62,'бассейн'),(35,63,'бейсбол'),(36,64,'играл'),(37,65,'старт'),(38,66,'теннис'),(39,67,'команды'),(40,68,'игровой'),(41,69,'лига'),(42,70,'регби'),(43,71,'лыжник'),(44,72,'пловец'),(45,73,'активность'),(46,74,'сноуборд'),(47,75,'бокс'),(48,76,'лыжа'),(49,77,'нфл'),(50,78,'стрельба'),(51,79,'гольф'),(52,80,'игрок'),(53,81,'победа'),(54,82,'стратегия'),(55,83,'фигура'),(57,85,'тренер'),(58,86,'скачки'),(59,87,'восхождение'),(60,88,'нба'),(61,89,'йога'),(63,92,'гонщик'),(64,93,'вызов'),(66,95,'марафон'),(67,96,'крикет'),(68,97,'выстрел'),(69,98,'велосипедист'),(70,99,'шахматы'),(71,100,'бегун'),(72,101,'олимпийский'),(73,102,'велосипеды'),(74,103,'гимнастика'),(75,104,'борьба'),(76,105,'спуск'),(77,106,'гол'),(78,108,'конькобежец'),(79,109,'игроки'),(80,110,'тур'),(81,111,'коньки'),(82,112,'инструктор'),(83,113,'альпинист'),(84,114,'боец'),(85,115,'чемпионат'),(86,116,'блог'),(87,117,'блоггер'),(88,118,'писатель'),(89,119,'писать'),(90,120,'блоггерство'),(91,121,'youtube'),(92,122,'пишу'),(93,123,'блоги'),(94,124,'автор'),(95,125,'контент'),(96,126,'посты'),(97,127,'пост'),(98,128,'профиль'),(99,129,'blogspot'),(100,130,'поделиться'),(101,131,'wordpress'),(102,132,'блоггеры'),(103,133,'публикую'),(104,134,'статья'),(106,136,'подкастер'),(108,138,'опубликованный'),(109,140,'ревью'),(110,141,'канал'),(111,143,'видеоблог');
/*!40000 ALTER TABLE `synonym` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `postId` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `gpm` bigint(20) DEFAULT NULL,
  `dateCreate` datetime NOT NULL,
  `kindContent` bigint(20) DEFAULT NULL,
  `nComment` int(11) DEFAULT NULL,
  `nPlusOne` int(11) DEFAULT NULL,
  `nResharers` int(11) DEFAULT NULL,
  `isRepost` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_Post_postId` (`postId`),
  KEY `ix_Post_gpm_8` (`gpm`),
  KEY `ix_Post_kindContent_9` (`kindContent`),
  CONSTRAINT `fk_Post_gpm_8` FOREIGN KEY (`gpm`) REFERENCES `gpm` (`id`),
  CONSTRAINT `fk_Post_kindContent_9` FOREIGN KEY (`kindContent`) REFERENCES `content` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (101,'z13scrbowxybibnn1231jrlwdkqjct0es',112,'2012-05-27 06:19:06',2,175,695,61,0),(102,'z12tx5orjwufctsbn04cg5yybla2tx341f0',112,'2012-05-26 09:24:54',2,261,885,125,0),(103,'z125yh5j0ov1chl2f231jrlwdkqjct0es',112,'2012-05-26 03:26:11',2,50,262,13,0),(104,'z13pxbhqdo3nedlay04cg5yybla2tx341f0',112,'2012-05-25 02:10:27',2,199,624,77,0),(105,'z13hhfdrqt2zirysd231jrlwdkqjct0es',112,'2012-05-24 21:30:23',4,39,114,24,0),(106,'z13bhj0ppxmju1tir04cg5yybla2tx341f0',112,'2012-05-24 11:05:34',2,54,309,15,0),(107,'z13hjnfpwqyazn4k4231jrlwdkqjct0es',112,'2012-05-24 09:16:42',1,58,38,5,0),(108,'z13dexgjrpmiwrzwv04cg5yybla2tx341f0',112,'2012-05-24 07:47:17',4,35,26,3,0),(109,'z12mtntjoviufdfr404cg5yybla2tx341f0',112,'2012-05-24 05:31:42',5,26,71,49,0),(110,'z12bhvsaytiiexv2k231jrlwdkqjct0es',112,'2012-05-23 22:53:05',2,140,785,115,0),(111,'z13wjbohyoz3s50lq231jrlwdkqjct0es',112,'2012-05-23 10:05:06',5,24,109,53,0),(112,'z12tzpopgomhup13u231jrlwdkqjct0es',112,'2012-05-23 06:56:40',2,33,186,13,0),(113,'z12bzvv5np2lyt3po04cg5yybla2tx341f0',112,'2012-05-23 04:34:37',1,29,24,1,0),(114,'z12cebipitqugth2j231jrlwdkqjct0es',112,'2012-05-23 01:45:09',2,30,192,16,0),(115,'z12vgfsybxf5sxy22231jrlwdkqjct0es',112,'2012-05-22 05:36:12',2,216,784,149,0),(116,'z13dx1dzfq21i35ac04cg5yybla2tx341f0',112,'2012-05-22 02:29:24',2,15,124,9,0),(117,'z12nvrawmzexits5j04cg5yybla2tx341f0',112,'2012-05-21 10:52:28',2,177,856,123,0),(118,'z12edbviiqqvd1qqv04cg5yybla2tx341f0',112,'2012-05-21 02:12:07',2,32,228,18,0),(119,'z12wjdjodrqgxvt0j231jrlwdkqjct0es',112,'2012-05-20 03:51:42',2,115,434,37,0),(120,'z13jghmqfx2wfbeou04cg5yybla2tx341f0',112,'2012-05-19 07:37:55',2,81,349,38,0),(121,'z13ig3g5vv3odp1d1231jrlwdkqjct0es',112,'2012-05-19 03:48:13',2,67,366,35,0),(122,'z12udbjxrkbqxrwus231jrlwdkqjct0es',112,'2012-05-19 01:34:47',2,49,133,6,0),(123,'z12zz5rb2rbjhzb2v231jrlwdkqjct0es',112,'2012-05-18 09:11:54',2,56,336,15,0),(124,'z121efriukfdh3pp004cg5yybla2tx341f0',112,'2012-05-17 23:58:22',2,33,248,14,0),(125,'z12xdp4w2nfkf3kak04cg5yybla2tx341f0',112,'2012-05-17 15:35:22',2,169,755,89,0),(126,'z13xur3x5rv3hxxuf231jrlwdkqjct0es',112,'2012-05-17 07:56:25',2,49,278,21,0),(127,'z12fexupglmjuzssp231jrlwdkqjct0es',112,'2012-05-14 23:21:41',2,206,830,140,0),(128,'z13zeroh4nvmenkra04cg5yybla2tx341f0',112,'2012-05-14 04:39:13',2,46,314,25,0),(129,'z13ajjhzdszoihz2o231jrlwdkqjct0es',112,'2012-05-13 09:04:40',2,58,230,23,0),(130,'z13ntb3iktz0y5dir04cg5yybla2tx341f0',112,'2012-05-13 03:13:39',5,4,57,0,0),(131,'z13evn5ykkm0xr03r231jrlwdkqjct0es',112,'2012-05-13 00:37:36',2,149,598,98,0),(132,'z13sjre4on20zlnhh04cg5yybla2tx341f0',112,'2012-05-12 07:49:20',2,50,170,11,0),(133,'z124zdbzqo2afnql4231jrlwdkqjct0es',112,'2012-05-11 09:30:30',2,23,180,10,0),(134,'z13pgfwjayqvultgp04cg5yybla2tx341f0',112,'2012-05-11 02:05:08',1,10,9,0,1),(135,'z131hhayvrn3ut5og04cg5yybla2tx341f0',112,'2012-05-10 12:48:25',2,46,225,16,0),(136,'z12uu3xgskebjxdjq04cg5yybla2tx341f0',112,'2012-05-09 12:16:09',2,91,350,44,0),(137,'z123sneynti5invdm231jrlwdkqjct0es',112,'2012-05-08 13:50:49',2,49,267,28,0),(138,'z12vw3zgwwzpvt34n04cg5yybla2tx341f0',112,'2012-05-06 23:28:46',2,90,476,38,0),(139,'z13psvl5xtifs3s3u231jrlwdkqjct0es',112,'2012-05-06 08:27:43',2,41,224,15,0),(140,'z13itnxzezygsfxik04cg5yybla2tx341f0',112,'2012-05-06 00:37:52',2,267,940,174,0),(141,'z12dez2yfum2jnk01231jrlwdkqjct0es',112,'2012-05-05 04:22:40',2,21,139,9,0),(142,'z12pu1tgpvnktbnji04cg5yybla2tx341f0',112,'2012-05-05 04:07:44',1,7,14,7,0),(143,'z133y1mwdyf3dbdrx04cg5yybla2tx341f0',112,'2012-05-04 22:23:05',2,74,374,34,0),(144,'z13etzn4fwnkyxeud231jrlwdkqjct0es',112,'2012-05-04 11:47:46',2,46,289,26,0),(145,'z12vchoqnkmay3jfe231jrlwdkqjct0es',112,'2012-05-04 01:47:31',5,2,15,0,0),(146,'z13tyv4wpyvewpb0i231jrlwdkqjct0es',112,'2012-05-03 21:44:43',2,177,413,39,0),(147,'z120xjlwdlbru30fg231jrlwdkqjct0es',112,'2012-05-02 22:23:29',2,125,413,51,0),(148,'z12rxpzwfojycbafq231jrlwdkqjct0es',112,'2012-05-02 05:06:55',2,60,143,10,0),(149,'z13oux5orpy5uv3j504cg5yybla2tx341f0',112,'2012-05-01 23:24:33',2,67,222,13,0),(150,'z124y1kh5temyj2my231jrlwdkqjct0es',112,'2012-05-01 08:17:37',2,67,284,33,0),(151,'z12hyfvz0wavfvngc04cg5yybla2tx341f0',112,'2012-04-30 22:18:00',2,61,332,32,0),(152,'z12lh3exjwj4ytpmo231jrlwdkqjct0es',112,'2012-04-30 04:37:19',2,162,358,45,0),(153,'z132uhdy1tqvvvk4j04cg5yybla2tx341f0',112,'2012-04-29 11:46:45',2,124,408,52,0),(154,'z13bervihle5x1o0m231jrlwdkqjct0es',112,'2012-04-29 04:36:25',2,35,253,23,0),(155,'z13dxbljrmbexp5ow04cg5yybla2tx341f0',112,'2012-04-29 04:32:27',5,8,27,4,1),(156,'z13hwjsy3k2wtj4u0231jrlwdkqjct0es',112,'2012-04-29 01:42:16',2,17,174,13,0),(157,'z13nulqijkfjcps3u231jrlwdkqjct0es',112,'2012-04-28 03:52:33',2,67,303,70,0),(158,'z12rjnvbcnvcsrbcr231jrlwdkqjct0es',112,'2012-04-27 23:34:29',2,44,281,24,0),(159,'z13pgrszpoqgfhayr04cg5yybla2tx341f0',112,'2012-04-27 08:42:54',2,24,171,13,0),(160,'z122fzxozrrlilygd04cg5yybla2tx341f0',112,'2012-04-27 08:25:18',2,7,34,5,0),(161,'z12vztnjaynazlnhr04cg5yybla2tx341f0',112,'2012-04-27 08:24:55',2,13,53,4,0),(162,'z13awvbh0u31tbqsd231jrlwdkqjct0es',112,'2012-04-27 04:13:12',2,27,192,13,0),(163,'z12vgvrqfqvyuxsdg231jrlwdkqjct0es',112,'2012-04-26 22:05:15',2,45,184,9,0),(164,'z13fwpczyybxi5s4304cg5yybla2tx341f0',112,'2012-04-26 13:26:38',2,70,278,15,0),(165,'z12xsnqxftuksrs1v231jrlwdkqjct0es',112,'2012-04-26 10:32:33',2,38,234,15,0),(166,'z133ellr5z3yzbep404cg5yybla2tx341f0',112,'2012-04-26 05:30:51',1,39,17,0,1),(167,'z125zz1gxzzivz0gw04cg5yybla2tx341f0',112,'2012-04-25 21:53:14',2,29,160,9,0),(168,'z132vpbrivrpgjtgq04cg5yybla2tx341f0',112,'2012-04-25 02:11:37',2,54,248,17,0),(169,'z12lgpfgywrwelh2f231jrlwdkqjct0es',112,'2012-04-24 21:59:39',2,88,445,75,0),(170,'z12jxbnwntfwx33pd04cg5yybla2tx341f0',112,'2012-04-24 21:47:51',2,29,139,19,0),(171,'z13wtvzrvxrfy5kfm231jrlwdkqjct0es',112,'2012-04-24 21:47:43',2,18,119,6,0),(172,'z131g3ugtmaxdnkbj04cg5yybla2tx341f0',112,'2012-04-24 21:47:36',2,16,102,10,0),(173,'z12hilwb3yfcynpvj231jrlwdkqjct0es',112,'2012-04-24 21:47:27',2,16,89,7,0),(174,'z13ndbcywvbwiheer231jrlwdkqjct0es',112,'2012-04-24 21:47:16',2,10,68,10,0),(175,'z12mz5u45veaupt4504cg5yybla2tx341f0',112,'2012-04-24 09:05:57',2,59,315,43,0),(176,'z135x3vbcqa2gpxcw231jrlwdkqjct0es',112,'2012-04-23 21:44:11',2,151,499,40,0),(177,'z124uh3hbnf0vzaxm04cg5yybla2tx341f0',112,'2012-04-23 13:06:16',1,114,69,3,0),(178,'z13bsdrjpu3oz1bzg04cg5yybla2tx341f0',112,'2012-04-23 12:00:33',1,221,62,8,0),(179,'z13bux5rnzeft34kj231jrlwdkqjct0es',112,'2012-04-23 07:09:34',2,83,525,90,0),(180,'z13ygxp4lxuqchv3c231jrlwdkqjct0es',112,'2012-04-23 05:12:12',2,48,308,22,0),(181,'z12yc1ijxxrqj141v231jrlwdkqjct0es',112,'2012-04-22 23:13:52',2,42,136,21,0),(182,'z13ehru5skimuxc4004cg5yybla2tx341f0',112,'2012-04-22 23:10:54',2,10,68,6,0),(183,'z12eeprpbxa2x4xsksfxwj2g5nuac2q0',112,'2012-04-22 23:10:42',2,15,91,10,0),(184,'z121uvagiobzi3izs04cg5yybla2tx341f0',112,'2012-04-22 07:36:04',2,53,318,47,0),(185,'z12aibqyyoqrfb1kd231jrlwdkqjct0es',112,'2012-04-22 06:26:02',2,50,127,7,0),(186,'z13vgdqyrwj0df0k5231jrlwdkqjct0es',112,'2012-04-22 04:52:20',1,18,9,0,0),(187,'z13ixjlwyry2cxcep231jrlwdkqjct0es',112,'2012-04-21 23:34:34',2,70,328,50,0),(188,'z123flgp5nmoedhrp04cg5yybla2tx341f0',112,'2012-04-21 05:44:51',2,30,220,21,0),(189,'z12hwtujxxieflnq204cg5yybla2tx341f0',112,'2012-04-21 00:45:24',2,58,154,10,0),(190,'z135wlp4mq25x3zi104cg5yybla2tx341f0',112,'2012-04-20 22:40:02',2,86,169,17,0),(191,'z12jzlrgop2oghpyg04cg5yybla2tx341f0',112,'2012-04-20 09:52:37',2,44,148,12,0),(192,'z12kudxiws2li3euv231jrlwdkqjct0es',112,'2012-04-20 04:34:33',2,118,611,103,0),(193,'z12qgpo4wsrhwdbnh231jrlwdkqjct0es',112,'2012-04-19 08:38:00',2,22,171,27,0),(194,'z13dfl2aiq34d52mp231jrlwdkqjct0es',112,'2012-04-19 08:34:19',2,6,66,3,0),(195,'z13ghfqxsouwxnruj231jrlwdkqjct0es',112,'2012-04-19 08:34:13',2,7,56,4,0),(196,'z12wwbtbyrrjfvznp231jrlwdkqjct0es',112,'2012-04-19 08:34:11',2,4,40,1,0),(197,'z13dyjrqpkief3ttx231jrlwdkqjct0es',112,'2012-04-19 08:33:58',2,7,51,1,0),(198,'z13zxp3zptn2zlyj504cg5yybla2tx341f0',112,'2012-04-19 00:48:13',2,52,271,24,0),(199,'z123hnihymudgri3l231jrlwdkqjct0es',112,'2012-04-19 00:26:12',5,19,51,19,0),(200,'z12mvrcgnmmfshieq231jrlwdkqjct0es',112,'2012-04-18 11:25:29',2,35,0,16,0);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gpm`
--

DROP TABLE IF EXISTS `gpm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gpm` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idGpm` varchar(21) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_GPM_idGpm` (`idGpm`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gpm`
--

LOCK TABLES `gpm` WRITE;
/*!40000 ALTER TABLE `gpm` DISABLE KEYS */;
INSERT INTO `gpm` VALUES (1,'100000329940413161996'),(2,'100000356136760184279'),(3,'100000772955143706751'),(4,'100001460601553769984'),(5,'100001803773152492451'),(6,'100002037172819753939'),(7,'100002429498291810665'),(8,'100002886266231272351'),(9,'100002947862537076711'),(10,'100003317583351942945'),(11,'100003628603413742554'),(12,'100003850557805960555'),(13,'100004016748161538154'),(14,'100004516056282859774'),(15,'100004583185844922211'),(16,'100005239178056249351'),(17,'100005710824892014286'),(18,'100005711559155088683'),(19,'100005809427652429580'),(20,'100006297432662329995'),(21,'100006477772655287322'),(22,'100021025784352405813'),(23,'100035762233109552669'),(24,'100038567648880602892'),(25,'100059725356222329475'),(26,'100062540864597103249'),(27,'100081010984768669358'),(28,'100084528885853378500'),(29,'100095245926948995335'),(30,'100113758181245830335'),(31,'100121771572038665942'),(32,'100124389520659251191'),(33,'100125012078853567494'),(34,'100129275726588145876'),(35,'100185707560478368490'),(36,'100193529331792590881'),(37,'100200160956657706975'),(38,'100201277843142907471'),(39,'100219691404074384850'),(40,'100233345614313192787'),(41,'100238746026920057941'),(42,'100238778462210489846'),(43,'100238824487132161176'),(44,'100251974409620806821'),(45,'100255479358995396743'),(46,'100262595546646927505'),(47,'100269980559891536109'),(48,'100272572056305744950'),(49,'100275307499530023476'),(50,'100279438294886290330'),(51,'100299055611081620740'),(52,'100300281975626912157'),(53,'100304352681897837766'),(54,'100316447977798313854'),(55,'100324436248497823664'),(56,'100331338428465688037'),(57,'100346965843536809009'),(58,'100349743543849719885'),(59,'100362719753000939534'),(60,'100365530928998392590'),(61,'100366041156340390615'),(62,'100372650346667646089'),(63,'100377493270775536948'),(64,'100382758901355515850'),(65,'100397143564111754723'),(66,'100401683392479420272'),(67,'100412096384209660073'),(68,'100425739170055690781'),(69,'100429564727464307274'),(70,'100502770020470411205'),(71,'100518419853963396365'),(72,'100715328241636136414'),(73,'101269616518480136760'),(74,'101892269165398528426'),(75,'101960720994009339267'),(76,'102150693225130002912'),(77,'102871830398264157687'),(78,'103172158448932010502'),(79,'103533326117556337218'),(80,'106189723444098348646'),(81,'106425036468998163064'),(82,'106472137404451336877'),(83,'107117483540235115863'),(84,'107276867598285658079'),(85,'107284813721446648524'),(86,'107765155354887834405'),(87,'108030124760702808713'),(88,'108176814619778619437'),(89,'108551811075711499995'),(90,'108809238871290369247'),(91,'108998673146368660257'),(92,'109813896768294978296'),(93,'110950950882502330382'),(94,'110981030061712822816'),(95,'111000835266298743697'),(96,'111588569124648292310'),(97,'112063946124358686266'),(98,'113359777710817483258'),(99,'113455290791279442483'),(112,'114056084994793924628'),(108,'114536133164105123829'),(100,'114862790439773763162'),(101,'115360471097759949621'),(102,'115956363612512643809'),(103,'116247667398036716276'),(104,'116872576248355504859'),(105,'117420368760274729369'),(106,'117570997424193022129'),(107,'117673062930419636633');
/*!40000 ALTER TABLE `gpm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `word`
--

DROP TABLE IF EXISTS `word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `word` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `word` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_Word_word` (`word`)
) ENGINE=InnoDB AUTO_INCREMENT=315 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `word`
--

LOCK TABLES `word` WRITE;
/*!40000 ALTER TABLE `word` DISABLE KEYS */;
INSERT INTO `word` VALUES (31,'abstract'),(24,'acrylic'),(73,'activities'),(294,'admin'),(266,'administrator'),(265,'ajax'),(180,'amateur'),(210,'android'),(299,'apache'),(280,'api'),(234,'app'),(236,'application'),(233,'applications'),(229,'apps'),(25,'architect'),(197,'architecture'),(187,'area'),(2,'art'),(134,'articles'),(1,'artist'),(26,'artistic'),(8,'artwork'),(124,'author'),(63,'baseball'),(270,'basic'),(40,'basketball'),(175,'beautiful'),(171,'beauty'),(60,'bike'),(102,'bikes'),(254,'bit'),(307,'bits'),(116,'blog'),(117,'blogger'),(132,'bloggers'),(120,'blogging'),(123,'blogs'),(129,'blogspot'),(75,'boxing'),(297,'browser'),(231,'c++'),(157,'camera'),(190,'canon'),(184,'capture'),(93,'challenge'),(141,'channel'),(115,'chempionships'),(99,'chess'),(42,'circle'),(43,'circles'),(193,'clients'),(113,'climber'),(87,'climbing'),(85,'coach'),(90,'coaching'),(230,'code'),(278,'coder'),(277,'coding'),(32,'color'),(14,'comics'),(221,'computer'),(257,'computers'),(275,'computing'),(22,'concept'),(125,'content'),(272,'core'),(189,'create'),(169,'creative'),(96,'cricket'),(178,'critique'),(245,'css'),(288,'css3'),(51,'cycling'),(98,'cyclist'),(301,'databases'),(269,'debian'),(296,'delphi'),(7,'design'),(6,'designer'),(264,'develop'),(276,'developed'),(208,'developer'),(246,'developers'),(243,'developing'),(215,'development'),(10,'digital'),(267,'django'),(11,'drawing'),(263,'drupal'),(291,'eclipse'),(162,'enjoy'),(27,'exhibition'),(172,'fashion'),(114,'fighter'),(83,'figure'),(61,'fitness'),(35,'football'),(262,'framework'),(305,'frameworks'),(248,'freelance'),(12,'gallery'),(53,'game'),(80,'gamer'),(46,'games'),(68,'gaming'),(203,'geek'),(304,'gentoo'),(298,'github'),(206,'glamour'),(106,'goal'),(79,'golf'),(214,'google'),(9,'graphic'),(160,'great'),(103,'gym'),(283,'hacker'),(289,'hacking'),(282,'hardware'),(205,'hobby'),(54,'hockey'),(199,'home'),(242,'html'),(238,'html5'),(5,'illustration'),(3,'illustrator'),(154,'images'),(112,'instructor'),(232,'internet'),(235,'ios'),(213,'java'),(226,'javascript'),(241,'jquery'),(107,'kayaking'),(151,'landscape'),(170,'landscapes'),(69,'league'),(204,'lightroom'),(217,'linux'),(295,'lisp'),(165,'live'),(176,'look'),(258,'mac'),(192,'macro'),(17,'magazine'),(95,'marathon'),(18,'media'),(251,'microsoft'),(91,'mma'),(33,'modern'),(200,'moment'),(313,'mssql'),(309,'mvc'),(239,'mysql'),(314,'mysql5'),(183,'national'),(148,'nature'),(88,'nba'),(224,'net'),(273,'network'),(287,'networking'),(292,'networks'),(77,'nfl'),(185,'nikon'),(290,'objective-c'),(101,'olympic'),(293,'open-source'),(312,'opensource'),(286,'oracle'),(268,'page'),(4,'paint'),(13,'painter'),(19,'painting'),(159,'passion'),(196,'passionate'),(153,'people'),(149,'photo'),(145,'photographer'),(28,'photography'),(191,'photoshop'),(219,'php'),(168,'picture'),(59,'place'),(255,'platform'),(279,'platforms'),(41,'play'),(64,'played'),(56,'player'),(109,'players'),(48,'playing'),(136,'podcaster'),(62,'pool'),(201,'portfolio'),(29,'portrait'),(127,'post'),(300,'postgresql'),(126,'posts'),(207,'product'),(161,'professional'),(128,'profile'),(260,'program'),(216,'programmer'),(302,'programmers'),(220,'programming'),(274,'programs'),(164,'project'),(138,'published'),(142,'publisher'),(133,'publishing'),(218,'python'),(45,'race'),(92,'racer'),(86,'races'),(58,'racing'),(105,'rafting'),(237,'rails'),(140,'reviews'),(227,'ruby'),(70,'rugby'),(100,'runner'),(50,'running'),(308,'scripting'),(23,'sculptor'),(30,'sculpture'),(256,'server'),(130,'share'),(139,'sharing'),(78,'shoot'),(84,'shooting'),(97,'shot'),(195,'show'),(253,'site'),(261,'sites'),(108,'skater'),(111,'skates'),(76,'ski'),(71,'skier'),(44,'skiing'),(74,'snowboarding'),(38,'soccer'),(211,'software'),(223,'source'),(20,'speedpaint'),(39,'sport'),(37,'sports'),(259,'sql'),(306,'stack'),(65,'start'),(82,'strategy'),(163,'street'),(194,'studio'),(198,'style'),(271,'support'),(72,'swimmer'),(47,'swimming'),(49,'team'),(67,'teams'),(66,'tennis'),(310,'test'),(303,'tester'),(36,'time'),(135,'topics'),(110,'tour'),(94,'trainer'),(55,'training'),(155,'travel'),(52,'try'),(250,'ubuntu'),(285,'unix'),(228,'user'),(202,'various'),(143,'videoblog'),(284,'virtual'),(34,'visionary'),(21,'visual'),(57,'volleyball'),(209,'web'),(311,'webmaster'),(244,'website'),(247,'websites'),(166,'wildlife'),(81,'win'),(131,'wordpress'),(146,'work'),(177,'workshop'),(150,'world'),(104,'wrestling'),(119,'write'),(118,'writer'),(137,'writes'),(122,'writing'),(281,'xml'),(152,'years'),(89,'yoga'),(121,'youtube');
/*!40000 ALTER TABLE `word` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupdescr`
--

DROP TABLE IF EXISTS `groupdescr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groupdescr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `activeImage` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `passiveImage` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `textPercent` int(11) DEFAULT NULL,
  `imagePercent` int(11) DEFAULT NULL,
  `linkPercent` int(11) DEFAULT NULL,
  `videoPercent` int(11) DEFAULT NULL,
  `audioPercent` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_GroupDescr_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupdescr`
--

LOCK TABLES `groupdescr` WRITE;
/*!40000 ALTER TABLE `groupdescr` DISABLE KEYS */;
INSERT INTO `groupdescr` VALUES (1,'Художники','','','',NULL,NULL,NULL,NULL,NULL),(2,'Спортсмены','','',NULL,NULL,NULL,NULL,NULL,NULL),(3,'Блоггеры','','',NULL,NULL,NULL,NULL,NULL,NULL),(4,'Фотографы','','','',NULL,NULL,NULL,NULL,NULL),(5,'Разработчики','','',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `groupdescr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gender`
--

DROP TABLE IF EXISTS `gender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gender` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_Gender_value` (`value`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gender`
--

LOCK TABLES `gender` WRITE;
/*!40000 ALTER TABLE `gender` DISABLE KEYS */;
INSERT INTO `gender` VALUES (2,'female'),(1,'male'),(3,'other');
/*!40000 ALTER TABLE `gender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newgpm`
--

DROP TABLE IF EXISTS `newgpm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `newgpm` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idGpm` varchar(21) COLLATE utf8_unicode_ci NOT NULL,
  `nMentiens` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_NewGPM_idGpm` (`idGpm`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newgpm`
--

LOCK TABLES `newgpm` WRITE;
/*!40000 ALTER TABLE `newgpm` DISABLE KEYS */;
INSERT INTO `newgpm` VALUES (1,'103274398541354707265',2),(2,'113977984500465768287',2);
/*!40000 ALTER TABLE `newgpm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `content`
--

DROP TABLE IF EXISTS `content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `kind` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_Content_kind` (`kind`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content`
--

LOCK TABLES `content` WRITE;
/*!40000 ALTER TABLE `content` DISABLE KEYS */;
INSERT INTO `content` VALUES (3,'audio'),(5,'link'),(2,'photo'),(1,'text'),(4,'video');
/*!40000 ALTER TABLE `content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postword`
--

DROP TABLE IF EXISTS `postword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `postword` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `post` bigint(20) DEFAULT NULL,
  `word` bigint(20) DEFAULT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_PostWord_1` (`post`,`word`),
  KEY `ix_PostWord_post_12` (`post`),
  KEY `ix_PostWord_word_13` (`word`),
  CONSTRAINT `fk_PostWord_post_12` FOREIGN KEY (`post`) REFERENCES `post` (`id`),
  CONSTRAINT `fk_PostWord_word_13` FOREIGN KEY (`word`) REFERENCES `word` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=341 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postword`
--

LOCK TABLES `postword` WRITE;
/*!40000 ALTER TABLE `postword` DISABLE KEYS */;
INSERT INTO `postword` VALUES (171,102,175,1),(172,103,1,1),(173,105,36,1),(174,105,160,1),(175,105,28,1),(176,105,146,1),(177,105,2,1),(178,105,83,1),(179,105,254,1),(180,105,153,1),(181,105,162,1),(182,107,165,1),(183,107,254,1),(184,107,29,1),(185,107,154,1),(186,107,195,1),(187,108,165,1),(188,109,175,1),(189,109,28,1),(190,109,253,2),(191,109,254,1),(192,109,139,1),(193,111,162,1),(194,113,153,1),(195,115,176,2),(196,115,65,1),(197,115,175,1),(198,116,96,1),(199,116,130,1),(200,118,150,1),(201,119,271,1),(202,119,65,1),(203,119,175,1),(204,120,49,1),(205,122,48,1),(206,122,234,1),(207,122,150,1),(208,124,169,1),(209,126,284,1),(210,126,176,1),(211,126,65,1),(212,126,50,2),(213,126,139,2),(214,126,130,2),(215,126,169,1),(216,126,67,1),(217,126,193,1),(218,126,150,1),(219,126,83,1),(220,126,153,2),(221,126,171,1),(222,126,271,1),(223,126,160,1),(224,126,43,1),(225,127,41,1),(226,128,146,1),(227,129,29,1),(228,129,159,1),(229,129,271,1),(230,129,153,1),(231,129,36,1),(232,129,214,1),(233,130,146,2),(234,130,139,1),(235,130,176,1),(236,133,264,1),(237,133,168,1),(238,133,176,1),(239,133,84,1),(240,133,93,1),(241,133,104,1),(242,134,180,1),(243,134,32,3),(244,134,28,1),(245,134,207,1),(246,134,130,1),(247,134,187,2),(248,134,36,1),(249,137,139,1),(250,139,139,1),(251,142,180,1),(252,142,32,3),(253,142,28,1),(254,142,207,1),(255,142,130,1),(256,142,187,1),(257,143,152,1),(258,143,65,1),(259,145,130,1),(260,145,28,1),(261,145,177,2),(262,145,146,1),(263,145,198,1),(264,145,191,1),(265,145,53,1),(266,146,254,1),(267,150,84,1),(268,152,175,1),(269,152,146,1),(270,153,200,2),(271,155,150,1),(272,155,214,3),(273,155,138,1),(274,155,154,2),(275,155,160,1),(276,155,164,2),(277,155,28,1),(278,155,271,2),(279,155,176,1),(280,157,154,1),(281,157,28,1),(282,157,48,1),(283,157,191,1),(284,157,149,1),(285,159,154,2),(286,159,176,1),(287,159,84,1),(288,159,146,2),(289,163,59,1),(290,166,28,2),(291,166,127,1),(292,166,153,2),(293,167,39,1),(294,169,254,1),(295,169,65,1),(296,169,176,1),(297,175,150,1),(298,177,153,1),(299,178,153,1),(300,181,110,1),(301,181,204,1),(302,181,191,1),(303,181,270,1),(304,181,165,5),(305,181,194,1),(306,181,195,2),(307,181,121,1),(308,181,150,1),(309,181,130,1),(310,185,52,1),(311,185,160,1),(312,186,273,1),(313,186,165,1),(314,186,272,1),(315,188,32,1),(316,190,32,1),(317,191,29,2),(318,191,84,3),(319,191,78,1),(320,191,150,1),(321,191,153,3),(322,191,148,1),(323,191,159,1),(324,191,145,1),(325,191,65,1),(326,191,157,2),(327,191,168,2),(328,191,130,1),(329,191,139,1),(330,191,271,1),(331,191,52,1),(332,192,36,1),(333,193,149,1),(334,193,178,1),(335,193,195,1),(336,198,254,1),(337,199,29,1),(338,200,191,2),(339,200,195,2),(340,200,36,1);
/*!40000 ALTER TABLE `postword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profileword`
--

DROP TABLE IF EXISTS `profileword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profileword` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `profile` bigint(20) DEFAULT NULL,
  `word` bigint(20) DEFAULT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_ProfileWord_1` (`profile`,`word`),
  KEY `ix_ProfileWord_profile_19` (`profile`),
  KEY `ix_ProfileWord_word_20` (`word`),
  CONSTRAINT `fk_ProfileWord_profile_19` FOREIGN KEY (`profile`) REFERENCES `profile` (`id`),
  CONSTRAINT `fk_ProfileWord_word_20` FOREIGN KEY (`word`) REFERENCES `word` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=213 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profileword`
--

LOCK TABLES `profileword` WRITE;
/*!40000 ALTER TABLE `profileword` DISABLE KEYS */;
INSERT INTO `profileword` VALUES (183,9,169,5),(184,9,150,3),(185,9,193,1),(186,9,2,1),(187,9,273,1),(188,9,157,2),(189,9,205,1),(190,9,159,1),(191,9,153,2),(192,9,130,1),(193,9,139,3),(194,9,175,1),(195,9,162,1),(196,9,5,1),(197,9,19,1),(198,9,138,1),(199,9,165,1),(200,9,38,1),(201,9,85,1),(202,9,196,2),(203,9,145,2),(204,9,43,2),(205,9,164,2),(206,9,36,3),(207,9,166,1),(208,9,146,2),(209,9,214,5),(210,9,110,1),(211,9,28,1),(212,9,17,1);
/*!40000 ALTER TABLE `profileword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'test'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-06-10 20:09:56
