-- на вход поступает id группы; для каждого пользователя массив, в котором указаны ключевые слова и количество вхождений этих слов за рассматриваемый период
select GPM.Id <id пользователей> from GPM, `Group`, Statistics, Black_List <таблицы> where <условия для пользователей, соответствующие запрошенной группе>;


-- рейтинг = связь знаний с действительностью 12
-- будет какие-то числа, равные количеству ключевых слов и ссылок, появившихся за "последний период"
select (
--sum(Group_Link.Weight) + sum(Group_Word.Weight)
veroyatnost_na_osnove_klyuchevyh_slov_iz_lenty + 
veroyatnost_na_osnove_ssylok_iz_lenty +
veroyatnost_na_osnove_klyuchevyh_slov_iz_profilya +
veroyatnost_na_osnove_ssylok_iz_profilya +
veroyatnost_na_osnove_procentnogo_sootnosheniya_tipov_kontenta
)/5 as RATING from 
{
	-- slova_v_lente - количество всех ключевых слов, slovo - массив слов и вхождений каждого слова
	-- Keywords.number_of_occurrences * Group_Word.Weight
	select /*7*/(sum()) as veroyatnost_na_osnove_klyuchevyh_slov_iz_lenty/*,
			() as veroyatnost_na_osnove_ssylok_iz_profilya,
			() as veroyatnost_na_osnove_klyuchevyh_slov_iz_profilya,
			() as veroyatnost_na_osnove_ssylok_iz_lenty,
			() as veroyatnost_na_osnove_procentnogo_sootnosheniya_tipov_kontenta*/
		from
			Group_Link /*из неё берём вес ссылки*/,
			Group_Word /*из неё берём вес слова*/,
			/*GPM, */
			Pets, 
			Black_List /*смотрим, чтобы не классифицировать отверженных*/,
			Keywords /*нужна для установления принадлежности слова к юзеру*/,
			Links /*нужна для установления принадлежности ссылки к юзеру*/
		where
			

}
where GPM.Id = Links.Id_GPM and 
Links.Id_Def = Group_Link.Id_Def
and Black_List.Id != GPM.Id /*чернявых не берём*/;

-- находим вес слов для одного GPM
-- REQUIRED_GROUP_ID будет передаваться в качестве параметра
select GPM.Id, sum(Keywords.number_of_occurrences * Group_Word.Weight) as words_weight 
	from GPM, Keywords, Group_Word, `Group`
		where `Group`.Id = REQUIRED_GROUP_ID /*смотрим только на конкретную запрашиваемую группу*/ and
		`Group`.Id = Group_Word.Id_Group /*слово принадлежит группе*/ and
		Keywords.Id_GPM = GPM.Id /*слово принадлежит GPM'у*/
	group by GPM.Id desc;