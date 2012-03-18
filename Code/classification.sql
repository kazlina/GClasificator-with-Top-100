-- �� ���� ��������� id ������; ��� ������� ������������ ������, � ������� ������� �������� ����� � ���������� ��������� ���� ���� �� ��������������� ������
select GPM.Id <id �������������> from GPM, `Group`, Statistics, Black_List <�������> where <������� ��� �������������, ��������������� ����������� ������>;


-- ������� = ����� ������ � ����������������� 12
-- ����� �����-�� �����, ������ ���������� �������� ���� � ������, ����������� �� "��������� ������"
select (
--sum(Group_Link.Weight) + sum(Group_Word.Weight)
veroyatnost_na_osnove_klyuchevyh_slov_iz_lenty + 
veroyatnost_na_osnove_ssylok_iz_lenty +
veroyatnost_na_osnove_klyuchevyh_slov_iz_profilya +
veroyatnost_na_osnove_ssylok_iz_profilya +
veroyatnost_na_osnove_procentnogo_sootnosheniya_tipov_kontenta
)/5 as RATING from 
{
	-- slova_v_lente - ���������� ���� �������� ����, slovo - ������ ���� � ��������� ������� �����
	-- Keywords.number_of_occurrences * Group_Word.Weight
	select /*7*/(sum()) as veroyatnost_na_osnove_klyuchevyh_slov_iz_lenty/*,
			() as veroyatnost_na_osnove_ssylok_iz_profilya,
			() as veroyatnost_na_osnove_klyuchevyh_slov_iz_profilya,
			() as veroyatnost_na_osnove_ssylok_iz_lenty,
			() as veroyatnost_na_osnove_procentnogo_sootnosheniya_tipov_kontenta*/
		from
			Group_Link /*�� �� ���� ��� ������*/,
			Group_Word /*�� �� ���� ��� �����*/,
			/*GPM, */
			Pets, 
			Black_List /*�������, ����� �� ���������������� �����������*/,
			Keywords /*����� ��� ������������ �������������� ����� � �����*/,
			Links /*����� ��� ������������ �������������� ������ � �����*/
		where
			

}
where GPM.Id = Links.Id_GPM and 
Links.Id_Def = Group_Link.Id_Def
and Black_List.Id != GPM.Id /*�������� �� ����*/;

-- ������� ��� ���� ��� ������ GPM
-- REQUIRED_GROUP_ID ����� ������������ � �������� ���������
select GPM.Id, sum(Keywords.number_of_occurrences * Group_Word.Weight) as words_weight 
	from GPM, Keywords, Group_Word, `Group`
		where `Group`.Id = REQUIRED_GROUP_ID /*������� ������ �� ���������� ������������� ������*/ and
		`Group`.Id = Group_Word.Id_Group /*����� ����������� ������*/ and
		Keywords.Id_GPM = GPM.Id /*����� ����������� GPM'�*/
	group by GPM.Id desc;