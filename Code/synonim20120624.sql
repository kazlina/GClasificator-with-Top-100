USE `test`;

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
) ENGINE=InnoDB AUTO_INCREMENT=395 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `synonym`
--

LOCK TABLES `synonym` WRITE;
/*!40000 ALTER TABLE `synonym` DISABLE KEYS */;
INSERT INTO `synonym` VALUES (317,1,'роза'),(387,2,'тюльпан'),(182,3,'одуванчик'),(219,4,'изображение'),(220,4,'картинка'),(221,4,'фото'),(228,13,'Юлия'),(229,13,'Юля'),(234,15,'Екатерина'),(233,15,'Катерина'),(232,15,'Катя'),(1,16,'photoes'),(2,16,'photography'),(291,16,'фото'),(292,16,'фотография'),(3,17,'photographers'),(293,17,'фотограф'),(340,18,'кэнон'),(342,19,'никон'),(4,21,'objective'),(245,21,'одалживать'),(244,21,'ссуда'),(360,22,'сони'),(335,24,'кадр'),(334,24,'снимать'),(295,25,'фотошоп'),(198,26,'раскрытие'),(199,26,'экспозиция'),(178,27,'контраст'),(179,27,'контрастировать'),(149,28,'блеск'),(148,28,'яркость'),(181,29,'стричь'),(167,30,'окрашивать'),(166,30,'цвет'),(168,30,'цветной'),(303,32,'портрет'),(247,33,'освещение'),(246,33,'свет'),(363,34,'оператор'),(362,34,'утверждение'),(265,35,'модель'),(315,36,'ретушировать'),(327,37,'студия'),(294,38,'фотомонтаж'),(211,40,'вспышка'),(210,40,'высвечивать'),(205,41,'фильтр'),(206,41,'фильтровать'),(263,42,'flashcard'),(262,42,'флешка'),(54,43,'аккумулятор'),(311,44,'рефлектор'),(201,45,'изготавливать'),(202,45,'способ'),(212,46,'фокусироваться'),(5,47,'tape'),(204,47,'лента'),(203,47,'фильм'),(192,48,'редакционный'),(193,48,'статья'),(248,49,'белье'),(382,50,'топлис'),(273,51,'обнаженный'),(197,52,'эротика'),(196,52,'эротический'),(142,53,'увиличение'),(341,54,'электронвольт'),(324,55,'башмак'),(326,55,'обувной'),(325,55,'туфля'),(86,56,'апертура'),(138,57,'заставлять'),(137,57,'принуждать'),(136,57,'штык'),(257,58,'большой'),(256,58,'макро'),(147,59,'брэкетинг'),(169,60,'композиция'),(280,62,'акрашивать'),(282,62,'изображать'),(281,62,'краска'),(6,63,'image'),(7,63,'painting'),(300,63,'изображение'),(299,63,'картина'),(73,64,'animalism'),(76,64,'анимализм'),(75,64,'похотливость'),(74,64,'чувственность'),(283,65,'художник'),(209,66,'вспышка'),(379,67,'значение'),(151,68,'кисточка'),(150,68,'кисть'),(152,68,'писать'),(153,68,'рисовать'),(289,69,'карандаш'),(8,70,'dash'),(380,70,'штрихование'),(157,71,'картина'),(156,71,'полотно'),(155,71,'холст'),(191,72,'мольберт'),(9,73,'perspective'),(307,73,'перспектива'),(355,74,'экспертиза'),(266,75,'монохроматический'),(213,76,'футуризм'),(235,77,'пейзаж'),(322,78,'затенять'),(321,78,'тень'),(287,79,'панель'),(288,79,'панельный'),(286,80,'палитра'),(356,81,'полутон'),(370,82,'тональность'),(135,83,'окружение'),(133,83,'подоплека'),(132,83,'предпосылка'),(134,83,'происхождение'),(131,83,'условие'),(130,83,'фон'),(344,84,'набросок'),(343,84,'эскиз'),(388,85,'сюриализм'),(391,86,'акварель'),(214,87,'здоровье'),(11,88,'doping'),(190,88,'допинг'),(386,89,'тренер'),(177,90,'кандидат'),(309,92,'белок'),(308,92,'протеин'),(50,93,'акклиматизация'),(361,94,'стадион'),(310,95,'судья'),(226,96,'судить'),(227,96,'судья'),(119,97,'атака'),(115,97,'атаковать'),(118,97,'критиковать'),(116,97,'нападать'),(121,97,'нападки'),(120,97,'наступление'),(123,97,'поражать'),(122,97,'разрушение'),(124,97,'разъедать'),(117,97,'штурмовать'),(200,98,'фанат'),(143,99,'борьба'),(243,100,'нога'),(12,101,'winner'),(374,101,'победа'),(375,101,'победитель'),(159,102,'вызывать'),(160,102,'оспаривать'),(161,102,'спрашивать'),(158,102,'требовать'),(195,103,'выносливость'),(187,104,'диета'),(10,105,'disqualify'),(188,105,'дисквалификация'),(189,105,'удаление'),(349,106,'группа'),(350,106,'команда'),(236,107,'вести'),(237,107,'лидировать'),(56,108,'быстрота'),(57,108,'живость'),(60,108,'ловкость'),(61,108,'проворство'),(58,108,'резвость'),(59,108,'сообразительность'),(268,109,'мускл'),(269,109,'мышца'),(126,110,'назначать'),(127,110,'назначение'),(125,110,'присуждать'),(128,110,'присуждение'),(129,110,'решение'),(96,111,'аппелировать'),(95,111,'аппеляция'),(100,111,'взывать'),(89,111,'воззвание'),(105,111,'жаловаться'),(92,111,'мольба'),(104,111,'обжаловать'),(97,111,'обращаться'),(88,111,'обращение'),(98,111,'прибегать'),(93,111,'привлекательность'),(87,111,'призыв'),(94,111,'притягательность'),(101,111,'просить'),(91,111,'просьба'),(102,111,'умолять'),(103,111,'упрашивать'),(383,112,'забирать'),(251,113,'неудачник'),(253,113,'проигравший'),(14,114,'action'),(13,114,'grip'),(217,114,'действие'),(215,114,'держать'),(218,114,'захватывать'),(216,114,'проводить'),(357,115,'лауреат'),(358,115,'призер'),(314,116,'результат'),(364,117,'strap'),(365,117,'полоса'),(366,117,'связывать'),(275,118,'оппонент'),(276,118,'противник'),(284,119,'спаривание'),(285,119,'спаривать'),(71,120,'дилетант'),(65,120,'любитель'),(69,120,'любительский'),(67,120,'непрофессионал'),(66,120,'поклонник'),(70,120,'самодеятельный'),(230,121,'жюри'),(231,121,'присяжные'),(351,122,'арбитр'),(352,122,'рецензент'),(77,123,'диктор'),(78,123,'комментатор'),(367,124,'tactic'),(368,124,'тактика'),(278,125,'темп'),(279,125,'шаг'),(173,126,'тренер'),(172,126,'тренировать'),(208,127,'конец'),(207,127,'финал'),(270,128,'музыка'),(51,129,'аккомпанемент'),(52,130,'аккомпанист'),(53,131,'созвучный'),(68,132,'АЦП'),(55,133,'ретуш'),(62,134,'воздух'),(15,135,'allegro'),(63,135,'аллегретто'),(64,135,'аллегро'),(72,136,'анданте'),(109,137,'arch'),(110,137,'арка'),(112,137,'дуга'),(111,137,'свод'),(113,138,'arpeggio'),(114,138,'арпеджио'),(331,139,'песня'),(377,140,'вокал'),(378,140,'голос'),(139,141,'блюз'),(154,142,'сопрано'),(16,143,'chorus'),(336,143,'ансамбль'),(337,143,'группа'),(339,143,'коллектив'),(338,143,'хор'),(17,144,'chordal'),(18,144,'chordophone'),(162,144,'аккорд'),(163,144,'аккордный'),(171,145,'согласие'),(19,146,'conducting'),(20,146,'conductor'),(175,146,'поведение'),(174,146,'проводить'),(176,146,'проводник'),(180,147,'шнур'),(183,148,'децибел'),(21,149,'dholi'),(274,150,'октава'),(385,152,'низкочастотный'),(140,153,'hoofer'),(141,153,'танцор'),(394,155,'клуб'),(392,155,'объем'),(329,156,'симфония'),(297,157,'фортепьяно'),(369,158,'токкато'),(390,159,'tunefully'),(389,159,'гармонично'),(381,160,'мелодичный'),(316,163,'ритм'),(302,164,'кусок'),(301,164,'часть'),(277,165,'увертюра'),(261,166,'мелодия'),(22,167,'motive'),(267,167,'мотив'),(258,168,'майор'),(264,169,'незначительный'),(318,170,'наука'),(25,171,'institute'),(372,171,'институт'),(371,171,'университет'),(23,172,'doctor'),(24,172,'reseacher'),(312,172,'доктор'),(313,172,'исследователь'),(296,173,'лекарство'),(290,174,'философия'),(170,175,'компьютер'),(330,176,'технология'),(306,177,'преподаватель'),(304,177,'профессор'),(305,177,'учитель'),(359,178,'софт'),(373,179,'работа'),(224,180,'заинтересованный'),(223,180,'интересовать'),(144,181,'мир'),(145,181,'свет'),(146,181,'царство'),(320,182,'ученый'),(348,183,'встреча'),(345,183,'общественный'),(347,183,'собрание'),(346,183,'социальный'),(260,184,'math'),(259,184,'математика'),(49,185,'академик'),(222,186,'информация'),(238,187,'изучать'),(242,187,'изучающий'),(240,187,'обучать'),(241,187,'обучающий'),(239,187,'узнавать'),(194,188,'образование'),(319,189,'научный'),(29,191,'coder'),(28,191,'developers'),(27,191,'development'),(26,191,'programmist'),(30,191,'webmaster'),(186,191,'кодер'),(185,191,'программист'),(184,191,'разработчик'),(376,192,'веб'),(41,193,'ios'),(43,193,'linux'),(44,193,'ubuntu'),(42,193,'windows'),(108,193,'андроид'),(106,193,'ОП'),(34,194,'c'),(37,194,'eclipse'),(47,194,'html'),(36,194,'javascript'),(46,194,'jquery'),(31,194,'lisp'),(45,194,'mysql'),(33,194,'pascal'),(35,194,'php'),(32,194,'python'),(38,194,'ruby'),(48,194,'sql'),(225,194,'язык'),(164,195,'код'),(165,195,'кодировать'),(249,196,'линукс'),(250,196,'ОП'),(39,197,'app'),(40,197,'application'),(107,197,'приложение'),(254,198,'мак'),(255,198,'ОП');
/*!40000 ALTER TABLE `synonym` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

-- Dump completed on 2012-06-24 17:55:39
