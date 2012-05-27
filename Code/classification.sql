select GPM.id, (
veroyatnost_na_osnove_klyuchevyh_slov_lenty + 
veroyatnost_na_osnove_ssylok_iz_lenty +
veroyatnost_na_osnove_klyuchevyh_slov_iz_profilya +
veroyatnost_na_osnove_ssylok_iz_profilya +
veroyatnost_na_osnove_procentnogo_sootnosheniya_tipov_kontenta
)/5 as RATING from
GPM,
(
	select GPM.id as gpm_id, (postWordWeight.wordPercent) as veroyatnost_na_osnove_klyuchevyh_slov_lenty,
			(postLinkWeight.linkPercent) as veroyatnost_na_osnove_ssylok_iz_lenty,
      (profileWordWeight.wordPercent) as veroyatnost_na_osnove_klyuchevyh_slov_iz_profilya,
			(profileLinkWeight.linkPercent) as veroyatnost_na_osnove_ssylok_iz_profilya,
			(100 - (abs(ifnull(GroupDescr.textPercent,0) - gpmContentRatio.rating_text_posts) +
            abs(ifnull(GroupDescr.videoPercent,0) - rating_video_posts)
            + abs(ifnull(GroupDescr.imagePercent,0) - rating_image_posts)
            + abs(ifnull(GroupDescr.linkPercent,0) - rating_link_posts)
            + abs(ifnull(GroupDescr.audioPercent,0) - rating_audio_posts))) as 
            veroyatnost_na_osnove_procentnogo_sootnosheniya_tipov_kontenta
		from GroupDescr, GPM,
      ( -- counting of post word percent for group
        select gpm.id as gpm_id, ifnull (wordWeight,0) / ifnull(amount,1) *100 as wordPercent
            from
            gpm
        left join
        (
            select 
                sumEachWord.gpm_id as gpm_id,
                    sumEachWord.wordWeight,
                    amountAllWord.amount
            from
                (
                    select 
                    GPM.id as gpm_id,
                        sum(GroupWord.postWeight * PostWord.amount) as wordWeight
                        from
                            GPM, Post, PostWord, Word, GroupWord, BlackList
                        where
                            GPM.id = Post.gpm and
                            Post.id = PostWord.post and 
                            PostWord.word = Word.id and 
                            Word.id = GroupWord.word and 
                            GroupWord.groupDescr = 5 and 
                            GPM.id != BlackList.id
                        group by GPM.id
                ) sumEachWord, 
                (   
                    SELECT 
                        gpm.id as gpm_id, sum(postword.amount) as amount
                    FROM
                        gpm, post, postword, word
                    where
                        gpm.id = post.gpm and post.id = postword.post and postword.word = word.id
                    group by gpm.id
                ) amountAllWord
            where
                sumEachWord.gpm_id = amountAllWord.gpm_id
        ) WeightAndAmountWords 
        ON gpm.id = WeightAndAmountWords.gpm_id
      ) postWordWeight,
      ( -- counting of post link percent for group
        select gpm.id as gpm_id, ifnull (linkWeight,0)/ifnull(amount,1)*100 as linkPercent
            from
            gpm
                left join
            (
                select 
                sumEachLink.gpm_id as gpm_id,
                    sumEachLink.linkWeight,
                    amountAllLink.amount
                from
                (
                    select GPM.id as gpm_id, sum(GroupLink.postWeight * PostLink.amount) as linkWeight
                            from GPM, Post, PostLink, Link, GroupLink, BlackList
                            where GPM.id = Post.gpm and
                        Post.id = PostLink.post and
                        PostLink.Link = Link.id and
                        Link.id = GroupLink.link and
                        GroupLink.groupDescr = 5 and
                        GPM.id != BlackList.id
                        group by GPM.id
                ) sumEachLink,
                (
                    SELECT gpm.id as gpm_id, sum(postlink.amount) as amount
                        FROM
                        gpm, post, postlink, link
                            where
                                gpm.id = post.gpm and
                                post.id = postlink.post and
                                postlink.link = link.id
                                group by gpm.id
                ) amountAllLink
            where 
                sumEachLink.gpm_id = amountAllLink.gpm_id
            ) WeightAndAmountLinks
            on gpm.id = WeightAndAmountLinks.gpm_id
      ) postLinkWeight,
      ( -- counting of profile word percent for group
        select gpm.id as gpm_id, ifnull(wordWeight,0)/ifnull(amount,1)*100 as wordPercent
            from
            gpm
                left join
            (
                select 
                sumEachWord.gpm_id as gpm_id,
                    sumEachWord.wordWeight,
                    amountAllWord.amount
                from
                    (
                        select GPM.id as gpm_id, sum(GroupWord.profileWeight * ProfileWord.amount) as wordWeight
                                from GPM, Profile, ProfileWord, Word, GroupWord, BlackList
                                where GPM.id = Profile.gpm and
                            Profile.id = ProfileWord.profile and
                            ProfileWord.word = Word.id and
                            Word.id = GroupWord.word and
                            GroupWord.groupDescr = 5 and
                            GPM.id != BlackList.id
                            group by GPM.id
                    ) sumEachWord,
                    (
                        SELECT gpm.id as gpm_id, sum(ProfileWord.amount) as amount
                            FROM
                            gpm, Profile, ProfileWord, word
                                where
                                    gpm.id = Profile.gpm and
                                    Profile.id = ProfileWord.profile and
                                    ProfileWord.word = word.id
                                    group by gpm.id
                    ) amountAllWord
                where
                    sumEachWord.gpm_id = amountAllWord.gpm_id
            ) WeightAndAmountProfileWords
            on gpm.id = WeightAndAmountProfileWords.gpm_id
        ) profileWordWeight,
        ( -- counting of profile link percent for group
        
        select gpm.id as gpm_id, ifnull(linkWeight,0)/ifnull(amount,1)*100 as linkPercent
            from
            gpm
                left join
            (
                select
                    sumEachLink.gpm_id as gpm_id,
                    sumEachLink.linkWeight,
                    amountAllLink.amount
                from
                    (
                        select GPM.id as gpm_id, sum(GroupLink.ProfileWeight * ProfileLink.amount) as linkWeight
                                from GPM, Profile, ProfileLink, Link, GroupLink, BlackList
                                where GPM.id = Profile.gpm and
                            Profile.id = ProfileLink.profile and
                            ProfileLink.Link = Link.id and
                            Link.id = GroupLink.link and
                            GroupLink.groupDescr = 5 and
                            GPM.id != BlackList.id
                            group by GPM.id
                    ) sumEachLink,
                    (
                        SELECT gpm.id as gpm_id, sum(ProfileLink.amount) as amount
                            FROM
                            gpm, Profile, ProfileLink, link
                                where
                                    GPM.id = Profile.gpm and
                                    Profile.id = Profilelink.profile and
                                    ProfileLink.Link = Link.id
                                    group by gpm.id
                    ) amountAllLink
            where
                sumEachLink.gpm_id = amountAllLink.gpm_id
        ) WeightAndAmountProfileLinks
        ON gpm.id = WeightAndAmountProfileLinks.gpm_id
      ) profileLinkWeight,
      -- ---------------------------------------------------------
      
			( -- counting content ratio
				select GPM.id as idGPM,
					(count_text_posts/ posts_count) as rating_text_posts,
					(count_video_posts/ posts_count) as rating_video_posts,
					(count_image_posts/ posts_count) as rating_image_posts,
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
						select GPM.id as id_gpm_image_posts_count, ifnull(count(PostImageTable.gpm_id),0) as count_image_posts
							from GPM
                left join
                (
                    select Post.gpm as gpm_id
                    from Post, Content
                    where
                        Post.kindContent = Content.id and
                        Content.kind = 'photo'
                ) PostImageTable
                on GPM.id = PostImageTable.gpm_id
							group by GPM.id
					) as ImagePostsCount,
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
						GPM.id = id_gpm_image_posts_count and
						GPM.id = id_gpm_link_posts_count and
						GPM.id = id_gpm_audio_posts_count
			) gpmContentRatio
    where
          	GPM.id = gpmContentRatio.idGPM and
						GPM.id = postWordWeight.gpm_id and
						GPM.id = postLinkWeight.gpm_id and
						GPM.id = profileWordWeight.gpm_id and
						GPM.id = profileLinkWeight.gpm_id
 ) Veroyatnost
 where gpm.id = Veroyatnost.gpm_id
group by GPM.id desc
order by RATING;