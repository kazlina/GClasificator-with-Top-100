CREATE DATABASE  IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `test`;
-- MySQL dump 10.13  Distrib 5.5.24, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: test
-- ------------------------------------------------------
-- Server version	5.5.24-0ubuntu0.12.04.1

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
-- Dumping routines for database 'test'
--
/*!50003 DROP PROCEDURE IF EXISTS `classification` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `classification`(IN group_id INT)
BEGIN
    -- table of all good GPMs 
    -- that have more than 30 posts 
    -- and that not in the Black List           (i'll add it later)
    -- and that not in the "Added by admin"     (i'll add it later)
    -- and more than 50 +1's on post, 30 comments on post and 20 shared posts
    DROP TEMPORARY TABLE IF EXISTS good_GPMs;
    CREATE TEMPORARY TABLE good_GPMs (
        gpm_id INT UNSIGNED NOT NULL,
        count_posts INT NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO good_GPMs (gpm_id, count_posts)
    SELECT gpm, count(*)
    FROM post 
    GROUP BY gpm
    HAVING count(*)>30 
        AND (sum(nPlusOne)/count(*))>50 
        AND (sum(nComment)/count(*))>30
        AND (sum(nResharers)/count(*))>10;

    -- 1.CLASSIFICATION ON WORDS
    -- table of all words for group (все ключевые слова для группы "фотографы")
    DROP TEMPORARY TABLE IF EXISTS group_words;
    CREATE TEMPORARY TABLE group_words (
        word_id INT UNSIGNED NOT NULL,
        post_weight DECIMAL(3,2) NOT NULL,
        profile_weight DECIMAL(3,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO group_words (word_id, post_weight, profile_weight)
    SELECT groupword.word, groupword.postWeight, groupword.profileWeight 
    FROM groupword
    WHERE groupword.groupDescr = group_id;

    -- 1.1.POSTS
    -- table of posts of good GPMs
    DROP TEMPORARY TABLE IF EXISTS post_GPMs;
    CREATE TEMPORARY TABLE post_GPMs (
        gpm_id INT UNSIGNED NOT NULL,
        post_id INT UNSIGNED NOT NULL,
        content_type INT UNSIGNED NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO post_GPMs (gpm_id,post_id, content_type)
    SELECT g.gpm_id, p.id, p.kindContent
    FROM post p JOIN good_GPMs g ON p.gpm = g.gpm_id;

    CALL classification_from_post_on_word();

    -- 1.2.PROFILE
    -- table of profiles of good GPMs (the last profile)
    DROP TEMPORARY TABLE IF EXISTS profile_GPMs;
    CREATE TEMPORARY TABLE profile_GPMs (
        gpm_id INT UNSIGNED NOT NULL,
        profile_id INT UNSIGNED NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO profile_GPMs (gpm_id,profile_id)
    SELECT g.gpm_id, MAX(p.id)
    FROM profile p JOIN good_GPMs g ON p.gpm = g.gpm_id
    GROUP BY g.gpm_id;

    CALL classification_from_profile_on_word();

    -- 2.CLASSIFICATION ON LINKS
    -- table of all links for group (все ключевые ссылки для группы "фотографы")
    DROP TEMPORARY TABLE IF EXISTS group_links;
    CREATE TEMPORARY TABLE group_links (
        link_id INT UNSIGNED NOT NULL,
        post_weight DECIMAL(3,2) NOT NULL,
        profile_weight DECIMAL(3,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO group_links (link_id, post_weight, profile_weight)
    SELECT grouplink.link, grouplink.postWeight, grouplink.profileWeight 
    FROM grouplink
    WHERE grouplink.groupDescr = group_id;

    -- 2.1.POSTS
    CALL classification_from_post_on_link();

    -- 2.2.PROFILE
    CALL classification_from_profile_on_link();

    -- 3.CLASSIFICATION ON CONTENT TYPE
    CALL classification_on_content_type(group_id);

    -- 4.COMMON POSSIBILITY 
    DROP TEMPORARY TABLE IF EXISTS common_possibility;
    CREATE TEMPORARY TABLE common_possibility (
        gpm_id INT UNSIGNED NOT NULL,
        possibility DECIMAL(6,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO common_possibility (gpm_id, possibility)
    SELECT good_GPMs.gpm_id, 
        IFNULL(post_w.possibility, 0) + 
        IFNULL(profile_w.possibility, 0) +
        IFNULL(content.possibility, 0) +
        IFNULL(post_l.possibility, 0) + 
        IFNULL(profile_l.possibility, 0)
    FROM good_GPMs 
        LEFT JOIN possibility_for_GPM_on_post_words post_w 
            ON good_GPMs.gpm_id = post_w.gpm_id
        LEFT JOIN possibility_for_GPM_on_profile_words profile_w 
            ON good_GPMs.gpm_id = profile_w.gpm_id
        LEFT JOIN possibility_on_gpms_content content 
            ON good_GPMs.gpm_id = content.gpm_id
        LEFT JOIN possibility_for_GPM_on_post_links post_l 
            ON good_GPMs.gpm_id = post_l.gpm_id
        LEFT JOIN possibility_for_GPM_on_profile_links profile_l 
            ON good_GPMs.gpm_id = profile_l.gpm_id;

    DROP TEMPORARY TABLE IF EXISTS possibility_for_GPM_on_profile_words;
    DROP TEMPORARY TABLE IF EXISTS possibility_for_GPM_on_post_words;
    DROP TEMPORARY TABLE IF EXISTS possibility_on_gpms_content;
    DROP TEMPORARY TABLE IF EXISTS possibility_for_GPM_on_post_links;
    DROP TEMPORARY TABLE IF EXISTS possibility_for_GPM_on_profile_links;


    DROP TEMPORARY TABLE IF EXISTS rate_gpms;
    CREATE TEMPORARY TABLE rate_gpms (
        gpm_id INT UNSIGNED NOT NULL,
        rate DECIMAL (6,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO rate_gpms (gpm_id, rate)
    SELECT * FROM common_possibility
    ORDER BY possibility DESC
    LIMIT 0, 250;

    SELECT gpm_id AS gpm, rate
    FROM rate_gpms;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `classification_from_post_on_link` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `classification_from_post_on_link`()
BEGIN
-- select from GPM's posts links (links for group), and group them, and count them
    -- table for this staff
    DROP TEMPORARY TABLE IF EXISTS all_post_links_of_good_GPMs;
    CREATE TEMPORARY TABLE all_post_links_of_good_GPMs (
        gpm_id INT UNSIGNED NOT NULL,
        link_id INT UNSIGNED NOT NULL,
        count_link INT UNSIGNED NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO all_post_links_of_good_GPMs (gpm_id, link_id, count_link)
    SELECT post_GPMs.gpm_id, postlink.link, sum(postlink.amount)
    FROM post_GPMs JOIN postlink
        ON post_GPMs.post_id = postlink.post 
    GROUP BY post_GPMs.gpm_id, postlink.link;

    -- group's links from activity good GPMs
    DROP TEMPORARY TABLE IF EXISTS group_post_links_of_good_GPMs;
    CREATE TEMPORARY TABLE group_post_links_of_good_GPMs (
        gpm_id INT UNSIGNED NOT NULL,
        link_id INT UNSIGNED NOT NULL,
        count_link INT UNSIGNED NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO group_post_links_of_good_GPMs (gpm_id, link_id, count_link)
    SELECT a.gpm_id, a.link_id, a.count_link
    FROM all_post_links_of_good_GPMs a JOIN group_links g
        ON a.link_id = g.link_id;

    DROP TEMPORARY TABLE IF EXISTS all_post_links_of_good_GPMs;

    -- possibility for links from posts
    DROP TEMPORARY TABLE IF EXISTS possibility_for_GPM_on_post_links;
    CREATE TEMPORARY TABLE possibility_for_GPM_on_post_links (
        gpm_id INT UNSIGNED NOT NULL,
        possibility DECIMAL(6,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO possibility_for_GPM_on_post_links (gpm_id, possibility)
    SELECT g.gpm_id, SUM(g.count_link * group_links.post_weight) AS possibility_post_link
    FROM group_post_links_of_good_GPMs g JOIN group_links ON g.link_id = group_links.link_id
    GROUP BY g.gpm_id;

    DROP TEMPORARY TABLE IF EXISTS group_post_links_of_good_GPMs;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `classification_from_post_on_word` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `classification_from_post_on_word`()
BEGIN
-- select from GPM's posts words (words for group), and group them, and count them
    -- table for this staff
    DROP TEMPORARY TABLE IF EXISTS all_post_words_of_good_GPMs;
    CREATE TEMPORARY TABLE all_post_words_of_good_GPMs (
        gpm_id INT UNSIGNED NOT NULL,
        word_id INT UNSIGNED NOT NULL,
        count_word INT UNSIGNED NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO all_post_words_of_good_GPMs (gpm_id, word_id, count_word)
    SELECT post_GPMs.gpm_id, postword.word, sum(postword.amount)
    FROM post_GPMs JOIN postword 
        ON post_GPMs.post_id = postword.post 
    GROUP BY post_GPMs.gpm_id, postword.word;

    -- group's words from activity good GPMs
    DROP TEMPORARY TABLE IF EXISTS group_post_words_of_good_GPMs;
    CREATE TEMPORARY TABLE group_post_words_of_good_GPMs (
        gpm_id INT UNSIGNED NOT NULL,
        word_id INT UNSIGNED NOT NULL,
        count_word INT UNSIGNED NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO group_post_words_of_good_GPMs (gpm_id, word_id, count_word)
    SELECT a.gpm_id, a.word_id, a.count_word
    FROM all_post_words_of_good_GPMs a JOIN group_words g
        ON a.word_id = g.word_id;

    DROP TEMPORARY TABLE IF EXISTS all_post_words_of_good_GPMs;

    -- possibility for words from posts
    DROP TEMPORARY TABLE IF EXISTS possibility_for_GPM_on_post_words;
    CREATE TEMPORARY TABLE possibility_for_GPM_on_post_words (
        gpm_id INT UNSIGNED NOT NULL,
        possibility DECIMAL(6,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO possibility_for_GPM_on_post_words (gpm_id, possibility)
    SELECT g.gpm_id, SUM(g.count_word * group_words.post_weight) AS possibility_post_word
    FROM group_post_words_of_good_GPMs g JOIN group_words ON g.word_id = group_words.word_id
    GROUP BY g.gpm_id;

    DROP TEMPORARY TABLE IF EXISTS group_post_words_of_good_GPMs;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `classification_from_profile_on_link` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `classification_from_profile_on_link`()
BEGIN
-- table of links from profiles
    DROP TEMPORARY TABLE IF EXISTS all_profile_links_of_good_GPMs;
    CREATE TEMPORARY TABLE all_profile_links_of_good_GPMs (
        gpm_id INT UNSIGNED NOT NULL,
        link_id INT UNSIGNED NOT NULL,
        count_link INT UNSIGNED NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO all_profile_links_of_good_GPMs (gpm_id, link_id, count_link)
    SELECT profile_GPMs.gpm_id, profilelink.link, sum(profilelink.amount)
    FROM profile_GPMs JOIN profilelink 
        ON profile_GPMs.profile_id = profilelink.profile 
    GROUP BY profile_GPMs.gpm_id, profilelink.link;

    -- group's links from activity good GPMs
    DROP TEMPORARY TABLE IF EXISTS group_profile_links_of_good_GPMs;
    CREATE TEMPORARY TABLE group_profile_links_of_good_GPMs (
        gpm_id INT UNSIGNED NOT NULL,
        link_id INT UNSIGNED NOT NULL,
        count_link INT UNSIGNED NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO group_profile_links_of_good_GPMs (gpm_id, link_id, count_link)
    SELECT a.gpm_id, a.link_id, a.count_link
    FROM all_profile_links_of_good_GPMs a JOIN group_links g
        ON a.link_id = g.link_id;

    DROP TEMPORARY TABLE IF EXISTS all_profile_links_of_good_GPMs;

    -- possibility for links from profoles
    DROP TEMPORARY TABLE IF EXISTS possibility_for_GPM_on_profile_links;
    CREATE TEMPORARY TABLE possibility_for_GPM_on_profile_links (
        gpm_id INT UNSIGNED NOT NULL,
        possibility DECIMAL(6,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO possibility_for_GPM_on_profile_links (gpm_id, possibility)
    SELECT g.gpm_id, SUM(g.count_link * group_links.profile_weight) AS possibility_post_link
    FROM group_profile_links_of_good_GPMs g JOIN group_links ON g.link_id = group_links.link_id
    GROUP BY g.gpm_id;

    DROP TEMPORARY TABLE IF EXISTS group_profile_links_of_good_GPMs;
    DROP TEMPORARY TABLE IF EXISTS group_links;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `classification_from_profile_on_word` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `classification_from_profile_on_word`()
BEGIN
    -- table of words from profiles
    DROP TEMPORARY TABLE IF EXISTS all_profile_words_of_good_GPMs;
    CREATE TEMPORARY TABLE all_profile_words_of_good_GPMs (
        gpm_id INT UNSIGNED NOT NULL,
        word_id INT UNSIGNED NOT NULL,
        count_word INT UNSIGNED NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO all_profile_words_of_good_GPMs (gpm_id, word_id, count_word)
    SELECT profile_GPMs.gpm_id, profileword.word, sum(profileword.amount)
    FROM profile_GPMs JOIN profileword 
        ON profile_GPMs.profile_id = profileword.profile 
    GROUP BY profile_GPMs.gpm_id, profileword.word;

    -- group's words from activity good GPMs
    DROP TEMPORARY TABLE IF EXISTS group_profile_words_of_good_GPMs;
    CREATE TEMPORARY TABLE group_profile_words_of_good_GPMs (
        gpm_id INT UNSIGNED NOT NULL,
        word_id INT UNSIGNED NOT NULL,
        count_word INT UNSIGNED NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO group_profile_words_of_good_GPMs (gpm_id, word_id, count_word)
    SELECT a.gpm_id, a.word_id, a.count_word
    FROM all_profile_words_of_good_GPMs a JOIN group_words g
        ON a.word_id = g.word_id;

    DROP TEMPORARY TABLE IF EXISTS all_profile_words_of_good_GPMs;

    -- possibility for words from profoles
    DROP TEMPORARY TABLE IF EXISTS possibility_for_GPM_on_profile_words;
    CREATE TEMPORARY TABLE possibility_for_GPM_on_profile_words (
        gpm_id INT UNSIGNED NOT NULL,
        possibility DECIMAL(6,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO possibility_for_GPM_on_profile_words (gpm_id, possibility)
    SELECT g.gpm_id, SUM(g.count_word * group_words.profile_weight) AS possibility_post_word
    FROM group_profile_words_of_good_GPMs g JOIN group_words ON g.word_id = group_words.word_id
    GROUP BY g.gpm_id;

    DROP TEMPORARY TABLE IF EXISTS group_profile_words_of_good_GPMs;
    DROP TEMPORARY TABLE IF EXISTS group_words;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `classification_on_content_type` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `classification_on_content_type`(IN group_id INT)
BEGIN
-- possibility for content
    -- percent on posts of each content for each GPM
    DROP TEMPORARY TABLE IF EXISTS gpms_content_type;
    CREATE TEMPORARY TABLE gpms_content_type (
        gpm_id INT UNSIGNED NOT NULL,
        content_type INT UNSIGNED NOT NULL,
        amount DECIMAL (6,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO gpms_content_type (gpm_id,content_type,amount)
    SELECT post_GPMs.gpm_id, post_GPMs.content_type, count(post_GPMs.content_type)/good_GPMs.count_posts
    FROM post_GPMs RIGHT JOIN good_GPMs ON post_GPMs.gpm_id = good_GPMs.gpm_id
    GROUP BY gpm_id, content_type;


    -- 3.1.TEXT
    SET @amount_text_for_group = 0;
    SELECT textPercent/100
        INTO @amount_text_for_group
        FROM groupdescr 
        WHERE id = group_id;
    DROP TEMPORARY TABLE IF EXISTS gpms_text;
    CREATE TEMPORARY TABLE gpms_text (
        gpm_id INT UNSIGNED NOT NULL,
        amount DECIMAL (6,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO gpms_text (gpm_id, amount)
    SELECT gpm_id, abs(amount - @amount_text_for_group)
    FROM gpms_content_type 
    WHERE content_type = 5;

    -- 3.2.IMAGE
    SET @amount_image_for_group = 0;
    SELECT imagePercent/100
        INTO @amount_image_for_group
        FROM groupdescr 
        WHERE id = group_id;
    DROP TEMPORARY TABLE IF EXISTS gpms_image;
    CREATE TEMPORARY TABLE gpms_image (
        gpm_id INT UNSIGNED NOT NULL,
        amount DECIMAL (6,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO gpms_image (gpm_id, amount)
    SELECT gpm_id, abs(amount - @amount_image_for_group)
    FROM gpms_content_type 
    WHERE content_type = 2;

    -- 3.3.VIDEO
    SET @amount_video_for_group = 0;
    SELECT videoPercent/100
        INTO @amount_video_for_group
        FROM groupdescr 
        WHERE id = group_id;
    DROP TEMPORARY TABLE IF EXISTS gpms_video;
    CREATE TEMPORARY TABLE gpms_video (
        gpm_id INT UNSIGNED NOT NULL,
        amount DECIMAL (6,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO gpms_video (gpm_id, amount)
    SELECT gpm_id, abs(amount - @amount_video_for_group)
    FROM gpms_content_type 
    WHERE content_type = 1;

    -- 3.4.LINK
    SET @amount_link_for_group = 0;
    SELECT linkPercent/100
        INTO @amount_link_for_group
        FROM groupdescr 
        WHERE id = group_id;
    DROP TEMPORARY TABLE IF EXISTS gpms_link;
    CREATE TEMPORARY TABLE gpms_link (
        gpm_id INT UNSIGNED NOT NULL,
        amount DECIMAL (6,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO gpms_link (gpm_id, amount)
    SELECT gpm_id, abs(amount - @amount_link_for_group)
    FROM gpms_content_type 
    WHERE content_type = 3;

    -- 3.5.AUDIO
    SET @amount_audio_for_group = 0;
    SELECT audioPercent/100
        INTO @amount_audio_for_group
        FROM groupdescr 
        WHERE id = group_id;
    DROP TEMPORARY TABLE IF EXISTS gpms_audio;
    CREATE TEMPORARY TABLE gpms_audio (
        gpm_id INT UNSIGNED NOT NULL,
        amount DECIMAL (6,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO gpms_audio (gpm_id, amount)
    SELECT gpm_id, abs(amount - @amount_audio_for_group)
    FROM gpms_content_type 
    WHERE content_type = 4;

    DROP TEMPORARY TABLE IF EXISTS gpms_content_type;

    -- 3.6.COMMON
    DROP TEMPORARY TABLE IF EXISTS possibility_on_gpms_content;
    CREATE TEMPORARY TABLE possibility_on_gpms_content (
        gpm_id INT UNSIGNED NOT NULL,
        possibility DECIMAL (6,2) NOT NULL
    ) ENGINE=MEMORY;
    INSERT INTO possibility_on_gpms_content (gpm_id, possibility)
    SELECT good_GPMs.gpm_id, 
            1-(IFNULL(gpms_text.amount,@amount_text_for_group) 
            + IFNULL(gpms_image.amount,@amount_image_for_group)
            + IFNULL(gpms_video.amount,@amount_video_for_group)
            + IFNULL(gpms_link.amount,@amount_link_for_group)
            + IFNULL(gpms_audio.amount,@amount_audio_for_group))/5 AS possibility
    FROM good_GPMs 
        LEFT JOIN gpms_text ON good_GPMs.gpm_id = gpms_text.gpm_id
        LEFT JOIN gpms_image ON good_GPMs.gpm_id = gpms_image.gpm_id
        LEFT JOIN gpms_video ON good_GPMs.gpm_id = gpms_video.gpm_id
        LEFT JOIN gpms_link ON good_GPMs.gpm_id = gpms_link.gpm_id
        LEFT JOIN gpms_audio ON good_GPMs.gpm_id = gpms_audio.gpm_id;

    DROP TEMPORARY TABLE IF EXISTS gpms_text;
    DROP TEMPORARY TABLE IF EXISTS gpms_image;
    DROP TEMPORARY TABLE IF EXISTS gpms_video;
    DROP TEMPORARY TABLE IF EXISTS gpms_link;
    DROP TEMPORARY TABLE IF EXISTS gpms_audio;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-10-24 11:48:38
