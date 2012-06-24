SELECT 
    GPM.id as gpm_id
FROM
    GPM,
    (
        SELECT gpm as gpm_id, 
            Profile.id as profile_id, 
            Max(dateUpdated) as dateUpdated,
            Profile.nFollowers as nFollowers
        FROM profile
        group by profile.gpm
    ) gpm_and_profile
where
    gpm.id = gpm_and_profile.gpm_id
order by nFollowers desc
limit 250;