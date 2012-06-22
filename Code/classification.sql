select all_rating.gpm, ifnull(all_rating.RATING,0) from 
(
select GPM.id as gpm, (
    veroyatnost_na_osnove_klyuchevyh_slov_lenty + 
    veroyatnost_na_osnove_ssylok_iz_lenty +
    veroyatnost_na_osnove_klyuchevyh_slov_iz_profilya +
    veroyatnost_na_osnove_ssylok_iz_profilya +
    veroyatnost_na_osnove_procentnogo_sootnosheniya_tipov_kontenta
    )/5 as RATING from
    GPM
    left join
    (
        select GPM.id as gpm_id, (postWordWeight.wordPercent) as veroyatnost_na_osnove_klyuchevyh_slov_lenty,
                (postLinkWeight.linkPercent) as veroyatnost_na_osnove_ssylok_iz_lenty,
          (profileWordWeight.wordPercent) as veroyatnost_na_osnove_klyuchevyh_slov_iz_profilya,
                (profileLinkWeight.linkPercent) as veroyatnost_na_osnove_ssylok_iz_profilya,
                (100 - (abs(ifnull(GroupDescr.textPercent,0) - gpmContentRatio.rating_text_posts*100) +
                abs(ifnull(GroupDescr.videoPercent,0) - rating_video_posts*100)
                + abs(ifnull(GroupDescr.imagePercent,0) - rating_image_posts*100)
                + abs(ifnull(GroupDescr.linkPercent,0) - rating_link_posts*100)
                + abs(ifnull(GroupDescr.audioPercent,0) - rating_audio_posts*100))) as 
                veroyatnost_na_osnove_procentnogo_sootnosheniya_tipov_kontenta
            from GroupDescr, GPM,
          ( -- counting of post word percent for group
            select GPM.id as gpm_id,
    ifnull(weight_and_amount_all_words.wordWeight,0)/ifnull(weight_and_amount_all_words.amount,1)*100 as wordPercent
from
    GPM
    left join
        (
            select sumEachWord.gpm_id as gpm_id,
                ifnull(wordWeight,0) as wordWeight, 
                amountAllWord.amount as amount
                    from
                    ( -- gpm i ves ego ssilok
                          select GPM.id as gpm_id, sum(post_word_weight.wordWeight) as wordWeight
                          from GPM
                          ,
                          (-- gpm i ves ego slov dlya kaghdogo posta
                              select post.gpm as gpm_id, posts_word_weight.word_weight as wordWeight
                              from
                              post,
                              (
                                  select postWord.post as post_id, 
                                        sum(postWord.amount * group_word.postWeight) as word_weight
                                        from
                                  postWord,
                                  (
                                      select groupWord.word as word,
                                          groupWord.postWeight as postWeight
                                      from
                                      groupWord
                                      where groupDescr = 2
                                  ) group_word
                                  where postWord.word = group_word.word
                                  group by postWord.post
                              ) posts_word_weight
                              where posts_word_weight.post_id = post.id
                          ) post_word_weight
                          where GPM.id = post_word_weight.gpm_id
                          group by post_word_weight.gpm_id
                    ) sumEachWord
                    ,
                    (
                        SELECT gpm.id as gpm_id,
                            sum(PostWord.amount) as amount
                        FROM
                            GPM, Post, PostWord, Word
                        where
                            GPM.id = Post.gpm and
                            Post.id = PostWord.post and
                            PostWord.Word = Word.id
                            group by gpm.id
                    ) amountAllWord
                    where sumEachWord.gpm_id = amountAllWord.gpm_id
                ) weight_and_amount_all_words
                on GPM.id = weight_and_amount_all_words.gpm_id
          ) postWordWeight,
          ( -- counting of post link percent for group
            select GPM.id as gpm_id,
    ifnull(weight_and_amount_all_links.linkWeight,0)/ifnull(weight_and_amount_all_links.amount,1)*100 as linkPercent
from
    GPM
    left join
        (
            select sumEachLink.gpm_id as gpm_id,
                ifnull(linkWeight,0) as linkWeight, 
                amountAllLink.amount as amount
                    from
                    ( -- gpm i ves ego ssilok
                          select GPM.id as gpm_id, /*ifnull(*/sum(post_link_weight.linkWeight)/*,0)*/ as linkWeight
                          from GPM
                          ,
                          (-- gpm i ves ego slov dlya kaghdogo posta
                              select post.gpm as gpm_id, posts_link_weight.link_weight as linkWeight
                              from
                              post,
                              (
                                  select postLink.post as post_id, 
                                        sum(postLink.amount * group_link.postWeight) as link_weight
                                        from
                                  postLink,
                                  (
                                      select groupLink.link as link,
                                          groupLink.postWeight as postWeight
                                      from
                                      groupLink
                                      where groupDescr = 2
                                  ) group_link
                                  where postLink.link = group_link.link
                                  group by postLink.post
                              ) posts_link_weight
                              where posts_link_weight.post_id = post.id
                          ) post_link_weight
                          where GPM.id = post_link_weight.gpm_id
                          group by post_link_weight.gpm_id
                    ) sumEachLink
                    ,
                    (
                        SELECT gpm.id as gpm_id,
                            sum(PostLink.amount) as amount
                        FROM
                            GPM, Post, PostLink, Link
                        where
                            GPM.id = Post.gpm and
                            Post.id = Postlink.post and
                            PostLink.Link = Link.id
                            group by gpm.id
                    ) amountAllLink
                    where sumEachLink.gpm_id = amountAllLink.gpm_id
                ) weight_and_amount_all_links
                on GPM.id = weight_and_amount_all_links.gpm_id
          ) postLinkWeight,
          ( -- counting of profile word percent for group
           select gpm.id as gpm_id, ifnull(wordWeight,0)/ifnull(amount,1)*100 as wordPercent
from
    gpm
    left join
        (
            select
                sumEachword.gpm_id as gpm_id,
                sumEachword.wordWeight,
                amountAllword.amount
            from
                (
                    select GPM.id as gpm_id, ifnull(profile_word_weight.wordWeight,0) as wordWeight
                    from GPM
                        left join
                            (
                                select last_profiles.gpm_id as gpm_id, 
                                    profiles_word_weight.word_weight as wordWeight, 
                                    profiles_word_weight.amount  as amount  
                                from
                                    (
                                        SELECT gpm as gpm_id, 
                                            profile.id as profile_id, 
                                            Max(dateUpdated) as dateUpdated
                                        FROM test.profile 
                                        group by profile.gpm
                                    ) last_profiles
                                    left join
                                        (
                                            select profileword.profile as profile_id,
                                                sum(profileword.amount * group_word.profileWeight) as word_weight,
                                                profileword.amount as amount 
                                            from
                                                profileword,
                                                (
                                                    select groupword.word as word_id,
                                                        groupword.profileWeight as profileWeight,
                                                        groupword.word as word
                                                    from
                                                        groupword
                                                    where groupDescr = 2
                                                ) group_word
                                            where profileword.word = group_word.word
                                            group by profileword.profile
                                        ) profiles_word_weight
                                    on profiles_word_weight.profile_id = last_profiles.profile_id
                            ) profile_word_weight
                        on GPM.id = profile_word_weight.gpm_id
                ) sumEachword,
                (
                    SELECT gpm.id as gpm_id, 
                        sum(Profileword.amount) as amount
                    FROM
                        gpm, Profile, Profileword, word
                    where
                        GPM.id = Profile.gpm and
                        Profile.id = Profileword.profile and
                        Profileword.word = word.id
                    group by gpm.id
                ) amountAllword
                where
                    sumEachword.gpm_id = amountAllword.gpm_id
        ) WeightAndAmountProfilewords
    ON gpm.id = WeightAndAmountProfilewords.gpm_id
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
                    select GPM.id as gpm_id, ifnull(profile_link_weight.linkWeight,0) as linkWeight
                    from GPM
                        left join
                            (
                                select last_profiles.gpm_id as gpm_id, 
                                    profiles_link_weight.link_weight as linkWeight, 
                                    profiles_link_weight.amount  as amount  
                                from
                                    (
                                        SELECT gpm as gpm_id, 
                                            profile.id as profile_id, 
                                            Max(dateUpdated) as dateUpdated
                                        FROM test.profile 
                                        group by profile.gpm
                                    ) last_profiles
                                    left join
                                        (
                                            select profilelink.profile as profile_id,
                                                sum(profilelink.amount * group_link.profileWeight) as link_weight,
                                                profilelink.amount as amount 
                                            from
                                                profilelink,
                                                (
                                                    select grouplink.link as link_id,
                                                        grouplink.profileWeight as profileWeight,
                                                        grouplink.link as link 
                                                    from
                                                        grouplink
                                                    where groupDescr = 2
                                                ) group_link
                                            where profilelink.link = group_link.link
                                            group by profilelink.profile
                                        ) profiles_link_weight
                                    on profiles_link_weight.profile_id = last_profiles.profile_id
                            ) profile_link_weight
                        on GPM.id = profile_link_weight.gpm_id
                ) sumEachLink,
                (
                    SELECT gpm.id as gpm_id, 
                        sum(ProfileLink.amount) as amount
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
                            -- Content.kind = 'text'
                            Content.id = '1'
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
                            -- Content.kind = 'video'
                            Content.id = '4'
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
                            -- Content.kind = 'photo'
                            Content.id = '2'
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
                            -- Content.kind = 'link'
                            Content.id = '5'
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
                            -- Content.kind = 'audio'
                            Content.id = '3'
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
     
     on gpm.id = Veroyatnost.gpm_id
    group by GPM.id desc
    order by RATING desc
) all_rating
left join
(
    select 
        blacklist.id as gpm_id
    from
        blacklist
    union
    select 
        addedbyadmin.gpm as id
    from
        addedbyadmin
    where groupdescr = 2
) notadd
on all_rating.gpm = notadd.gpm_id
where notadd.gpm_id is null
limit 0, 250;