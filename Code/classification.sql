select GPM.id_gpm, (
veroyatnost_na_osnove_klyuchevyh_slov_iz_lenty + 
veroyatnost_na_osnove_ssylok_iz_lenty +
veroyatnost_na_osnove_klyuchevyh_slov_iz_profilya +
veroyatnost_na_osnove_ssylok_iz_profilya +
veroyatnost_na_osnove_procentnogo_sootnosheniya_tipov_kontenta
)/5 as RATING from 
(
	select GPM.id_gpm, (sum(ves_proizvedeniya_slova_lenti)) as veroyatnost_na_osnove_klyuchevyh_slov_lenti,
			(sum(ves_proizvedeniya_ssilki_lenti)) as veroyatnost_na_osnove_ssylok_iz_lenty,
      (sum(ves_proizvedeniya_ssilki_profilya)) as veroyatnost_na_osnove_ssylok_iz_profilya,
			(sum(ves_proizvedeniya_slova_profilya)) as veroyatnost_na_osnove_klyuchevyh_slov_iz_profilya,
			(100 - (abs(GroupContentRatio.group_text_ratio - rating_text_posts) + abs(GroupContentRatio.group_video_ratio - rating_text_posts) + abs(GroupContentRatio.group_pictures_ratio - rating_text_posts) + abs(GroupContentRatio.group_link_ratio - rating_text_posts))) as veroyatnost_na_osnove_procentnogo_sootnosheniya_tipov_kontenta
		from GroupContentRatio,
			(
				select GPM.id_gpm,
					(GroupWord.word_weight_in_post * PostWord.count_words_in_post) as ves_proizvedeniya_slova_lenti
					from GPM, PostWord, GroupWord, WordDict
					where GPM.id_gpm = PostWord.id_gpm and
						PostWord.id_word = WordDict.id_word and
						REQUIRED_GROUP_ID = WordDict.id_word and
						GPM.id_gpm != BlackList.id_gpm
			),
			(
				select GPM.id_gpm,
					(GroupLink.link_weight_in_post * PostLink.`Count`) as ves_proizvedeniya_ssilki_lenti
					from GPM, PostLink, GroupLink, LinkDict
					where GPM.id_gpm = PostLink.id_gpm and
						PostLink.id_link = LinkDict.id_link and
						LinkDict.id_link = REQUIRED_GROUP_ID and
						GPM.id_gpm != BlackList.id_gpm
			),
			(
				select GPM.id_gpm,
					(GroupLink.link_weight_in_profile * ProfileLink.count_links_in_profile) as ves_proizvedeniya_ssilki_profilya
					from GPM, ProfileLink, GroupLink, LinkDict
					where GPM.id_gpm = ProfileLink.id_gpm and
						ProfileLink.Id_Link = LinkDict.id_link and
						LinkDict.id_link = REQUIRED_GROUP_ID and
						GPM.id_gpm != BlackList.id_gpm
			),
			(
				select GPM.id_gpm,
					(GroupWord.word_weight_in_post * ProfileWord.count_words_in_profile) as ves_proizvedeniya_slova_profilya
					from GPM, ProfileWord, GroupWord, WordDict
					where GPM.id_gpm = PostLink.id_gpm and
						ProfileWord.id_word = WordDict.id_word and
						WordDict.id_word = REQUIRED_GROUP_ID and
						GPM.id_gpm != BlackList.id_gpm
			),
			(
				select id_gpm_with_posts_count,
					(count_text_posts/ posts_count) as rating_text_posts,
					(count_video_posts/ posts_count) as rating_video_posts,
					(count_pictures_posts/ posts_count) as rating_picture_posts,
					(count_link_posts/ posts_count) as rating_link_posts
					from GPM.id_gpm,
					(
						select GPM.id_gpm as id_gpm_with_posts_count, sum(Posts.id) as posts_count
							from GPM, Posts
							where GPM.id_gpm = Posts.id_gpm
							group by GPM.id_gpm
					),
					(
						select GPM.id_gpm as id_gpm_text_posts_count,
							sum(Posts.type_of_post) as count_text_posts
							from GPM, Posts
							where GPM.id_gpm = Posts.id_gpm and
								Posts.type_of_post = 'text'
							group by GPM.id_gpm
					),
					(
						select GPM.id_gpm as id_gpm_video_posts_count,
							sum(Posts.type_of_post) as count_video_posts
							from GPM, Posts
							where GPM.id_gpm = Posts.id_gpm and
								Posts.type_of_post = 'video'
							group by GPM.id_gpm
					),
					(
						select GPM.id_gpm as id_gpm_pictures_posts_count,
							sum(Posts.type_of_post) as count_pictures_posts
							from GPM, Posts
							where GPM.id_gpm = Posts.id_gpm and
								Posts.type_of_post = 'picture'
							group by GPM.id_gpm
					),
					(
						select GPM.id_gpm as id_gpm_link_posts_count,
							sum(Posts.type_of_post) as count_link_posts
							from GPM, Posts
							where GPM.id_gpm = Posts.id_gpm and
								Posts.type_of_post = 'link'
							group by GPM.id_gpm
					)
					where
						GPM.id_gpm = id_gpm_with_posts_count and
						GPM.id_gpm = id_gpm_text_posts_count and
						GPM.id_gpm = id_gpm_video_posts_count and
						GPM.id_gpm = id_gpm_pictures_posts_count and
						GPM.id_gpm = id_gpm_link_posts_count
			)
 )
group by GPM.id_gpm desc
order by RATING;