select GPM.id_gpm, (
veroyatnost_na_osnove_klyuchevyh_slov_iz_lenty + 
veroyatnost_na_osnove_ssylok_iz_lenty +
veroyatnost_na_osnove_klyuchevyh_slov_iz_profilya +
veroyatnost_na_osnove_ssylok_iz_profilya +
veroyatnost_na_osnove_procentnogo_sootnosheniya_tipov_kontenta
)/5 as RATING from 
(
	select GPM.id_gpm, (sum(TotalWeightOfOneWord)) as veroyatnost_na_osnove_klyuchevyh_slov_lenti,
			(sum(TotalWeightOfOneLinkFromFeed)) as veroyatnost_na_osnove_ssylok_iz_lenty,
      (sum(TotalWeightOfOneLinkFromProfile)) as veroyatnost_na_osnove_ssylok_iz_profilya,
			(sum(TotalWeightOfOneWordFromProfile)) as veroyatnost_na_osnove_klyuchevyh_slov_iz_profilya,
			(100 - (abs(GroupContentRatio.group_text_ratio - rating_text_posts) + abs(GroupContentRatio.group_video_ratio - rating_video_posts) + abs(GroupContentRatio.group_pictures_ratio - rating_pictures_posts) + abs(GroupContentRatio.group_link_ratio - rating_link_posts))) as veroyatnost_na_osnove_procentnogo_sootnosheniya_tipov_kontenta
		from GroupContentRatio,
			( -- counting of summ weight for one word from feed
				select GPM.id, (GroupWord.postWeight * PostWord.amount) as TotalWeightOfOneWordFromFeed
					from GPM, PostWord, GroupWord, Word, Post
					where GPM.id = Post.gpm and
                Post.id = PostWord.post and
                PostWord.word = Word.id and
                Word.id = GroupWord.word and
                GroupWord.groupdescr = 5/*REQUIRED_GROUP_ID.id*/ and
                GPM.id != BlackList.id
			),
			( -- counting of summ weight for one link from feed
				select GPM.id,
					(GroupLink.postWeight * PostLink.amount) as TotalWeightOfOneLinkFromFeed
					from GPM, PostLink, GroupLink, Link, Post
					where GPM.id = Post.gpm and
                Post.id = PostLink.post and
                PostLink.link = Link.id and
                Link.id = GroupLink.link and
                GroupLink.groupDescr = REQUIRED_GROUP_ID and
                GPM.id != BlackList.id
			),
			( -- counting of summ weight for one link from profile
				select GPM.id,
					(GroupLink.profileWeight * ProfileLink.amount) as TotalWeightOfOneLinkFromProfile
					from GPM, ProfileLink, GroupLink, Link, Profile
					where GPM.id = Profile.gpm and
						Profile.id = ProfileLink.profile and
						ProfileLink.link = Link.id and
            Link.id = GroupLink.link and
            GroupLink.groupDescr = REQUIRED_GROUP_ID and
						GPM.id != BlackList.id
			),
			( -- counting of summ weight for one word from profile
				select GPM.id,
					(GroupWord.profileWeight * ProfileWord.amount) as TotalWeightOfOneWordFromProfile
					from GPM, ProfileWord, GroupWord, Word, Profile
					where GPM.id = Profile.gpm and
						Profile.id = ProfileWord.profile and
						ProfileWord.word = Word.id and
            Word.id = GroupWord.word and
            GroupWord.groupDescr = REQUIRED_GROUP_ID and
						GPM.id != BlackList.id
			),
			(
				select GPM.id as idGPM,
					(count_text_posts/ posts_count) as rating_text_posts,
					(count_video_posts/ posts_count) as rating_video_posts,
					(count_photo_posts/ posts_count) as rating_photo_posts,
					(count_link_posts/ posts_count) as rating_link_posts,
          (count_audio_posts/ posts_count) as rating_audio_posts
					from GPM,
					(
						select GPM.id as id_gpm_with_posts_count, count(Post.id) as posts_count
							from GPM, Post
							where GPM.id = Post.gpm
							group by GPM.id
					) as PostsCount,
					(
						select GPM.id as id_gpm_text_posts_count, ifnull(count(PostTextTable.gpm_id),0) as count_text_posts
							from GPM
                left join
                (
                    select Post.gpm as gpm_id
                    from Post, Content
                    where
                        Post.kindContent = Content.id and
                        Content.kind = 'text'
                ) PostTextTable
                on GPM.id = PostTextTable.gpm_id
            group by GPM.id
					) as TextPostsCount,
					(
						select GPM.id as id_gpm_video_posts_count, ifnull(count(PostVideoTable.gpm_id),0) as count_video_posts
							from GPM
                left join
                (
                    select Post.gpm as gpm_id
                    from Post, Content
                    where 
                        Post.kindContent = Content.id and
                        Content.kind = 'video'
                ) PostVideoTable
                on GPM.id = PostVideoTable.gpm_id
							group by GPM.id
					) as VideoPostsCount,
					(
						select GPM.id as id_gpm_photo_posts_count, ifnull(count(PostPhotoTable.gpm_id),0) as count_photo_posts
							from GPM
                left join
                (
                    select Post.gpm as gpm_id
                    from Post, Content
                    where
                        Post.kindContent = Content.id and
                        Content.kind = 'photo'
                ) PostPhotoTable
                on GPM.id = PostPhotoTable.gpm_id
							group by GPM.id
					) as PhotoPostsCount,
					(
						select GPM.id as id_gpm_link_posts_count, ifnull(count(PostLinkTable.gpm_id),0) as count_link_posts
							from GPM
                left join
                (
                    select Post.gpm as gpm_id
                    from Post, Content
                    where
                        Post.kindContent = Content.id and
                        Content.kind = 'link'
                ) PostLinkTable
                on GPM.id = PostLinkTable.gpm_id
							group by GPM.id
					) as LinkPostsCount,
          (
						select GPM.id as id_gpm_audio_posts_count, ifnull(count(PostAudioTable.gpm_id),0) as count_audio_posts
							from GPM
                left join
                (
                    select Post.gpm as gpm_id
                    from Post, Content
                    where
                        Post.kindContent = Content.id and
                        Content.kind = 'audio'
                ) PostAudioTable
                on GPM.id = PostAudioTable.gpm_id
							group by GPM.id
					) as AudioPostsCount
					where
          	GPM.id = id_gpm_with_posts_count and
						GPM.id = id_gpm_text_posts_count and
						GPM.id = id_gpm_video_posts_count and
						GPM.id = id_gpm_photo_posts_count and
						GPM.id = id_gpm_link_posts_count and
						GPM.id = id_gpm_audio_posts_count;
			)
 )
group by GPM.id desc
order by RATING;