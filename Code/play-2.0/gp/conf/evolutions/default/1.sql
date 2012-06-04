# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table AddedByAdmin (
  id                        bigint auto_increment not null,
  gpm                       bigint,
  groupDescr                bigint,
  position                  integer not null,
  dateOfAddition            datetime not null,
  dateOfRemoval             datetime,
  commentField              varchar(255),
  constraint uq_AddedByAdmin_1 unique (gpm,groupDescr),
  constraint pk_AddedByAdmin primary key (id))
;

create table BlackList (
  id                        bigint auto_increment not null,
  dateOfAddition            datetime not null,
  reasonOfAddition          varchar(255),
  constraint pk_BlackList primary key (id))
;

create table Content (
  id                        bigint auto_increment not null,
  kind                      varchar(5) not null,
  constraint uq_Content_kind unique (kind),
  constraint pk_Content primary key (id))
;

create table GPM (
  id                        bigint auto_increment not null,
  idGpm                     varchar(21) not null,
  constraint uq_GPM_idGpm unique (idGpm),
  constraint pk_GPM primary key (id))
;

create table Gender (
  id                        bigint auto_increment not null,
  value                     varchar(10) not null,
  constraint uq_Gender_value unique (value),
  constraint pk_Gender primary key (id))
;

create table GroupDescr (
  id                        bigint auto_increment not null,
  name                      varchar(50) not null,
  activeImage               varchar(255) not null,
  passiveImage              varchar(255) not null,
  description               varchar(255),
  textPercent               integer,
  imagePercent              integer,
  linkPercent               integer,
  videoPercent              integer,
  audioPercent              integer,
  constraint uq_GroupDescr_name unique (name),
  constraint pk_GroupDescr primary key (id))
;

create table GroupLink (
  id                        bigint auto_increment not null,
  groupDescr                bigint,
  link                      bigint,
  postWeight                float not null,
  profileWeight             float not null,
  constraint uq_GroupLink_1 unique (groupDescr,link),
  constraint pk_GroupLink primary key (id))
;

create table GroupWord (
  id                        bigint auto_increment not null,
  groupDescr                bigint,
  word                      bigint,
  postWeight                float not null,
  profileWeight             float not null,
  constraint uq_GroupWord_1 unique (groupDescr,word),
  constraint pk_GroupWord primary key (id))
;

create table Link (
  id                        bigint auto_increment not null,
  link                      varchar(255) not null,
  constraint uq_Link_link unique (link),
  constraint pk_Link primary key (id))
;

create table NewGPM (
  id                        bigint auto_increment not null,
  idGpm                     varchar(21) not null,
  nMentiens                 integer not null,
  constraint uq_NewGPM_idGpm unique (idGpm),
  constraint pk_NewGPM primary key (id))
;

create table Post (
  id                        bigint auto_increment not null,
  postId                    varchar(40) not null,
  gpm                       bigint,
  dateCreate                datetime not null,
  kindContent               bigint,
  nComment                  integer,
  nPlusOne                  integer,
  nResharers                integer,
  isRepost                  tinyint(1) default 0 not null,
  constraint uq_Post_postId unique (postId),
  constraint pk_Post primary key (id))
;

create table PostLink (
  id                        bigint auto_increment not null,
  post                      bigint,
  link                      bigint,
  amount                    integer not null,
  constraint uq_PostLink_1 unique (post,link),
  constraint pk_PostLink primary key (id))
;

create table PostWord (
  id                        bigint auto_increment not null,
  post                      bigint,
  word                      bigint,
  amount                    integer not null,
  constraint uq_PostWord_1 unique (post,word),
  constraint pk_PostWord primary key (id))
;

create table Profile (
  id                        bigint auto_increment not null,
  gpm                       bigint,
  dateUpdated               datetime not null,
  name                      varchar(100),
  image                     varchar(255),
  gender                    bigint,
  tagline                   varchar(255),
  relationshipStatus        bigint,
  nFollowers                integer,
  constraint uq_Profile_1 unique (gpm,dateUpdated),
  constraint pk_Profile primary key (id))
;

create table ProfileLink (
  id                        bigint auto_increment not null,
  profile                   bigint,
  link                      bigint,
  amount                    integer not null,
  constraint uq_ProfileLink_1 unique (profile,link),
  constraint pk_ProfileLink primary key (id))
;

create table ProfileWord (
  id                        bigint auto_increment not null,
  profile                   bigint,
  word                      bigint,
  amount                    integer not null,
  constraint uq_ProfileWord_1 unique (profile,word),
  constraint pk_ProfileWord primary key (id))
;

create table Relationship (
  id                        bigint auto_increment not null,
  status                    varchar(30) not null,
  constraint uq_Relationship_status unique (status),
  constraint pk_Relationship primary key (id))
;

create table Synonym (
  id                        bigint auto_increment not null,
  word                      bigint,
  synonym                   varchar(30) not null,
  constraint uq_Synonym_1 unique (word,synonym),
  constraint pk_Synonym primary key (id))
;

create table Word (
  id                        bigint auto_increment not null,
  word                      varchar(30) not null,
  constraint uq_Word_word unique (word),
  constraint pk_Word primary key (id))
;

alter table AddedByAdmin add constraint fk_AddedByAdmin_gpm_1 foreign key (gpm) references GPM (id) on delete restrict on update restrict;
create index ix_AddedByAdmin_gpm_1 on AddedByAdmin (gpm);
alter table AddedByAdmin add constraint fk_AddedByAdmin_group_2 foreign key (groupDescr) references GroupDescr (id) on delete restrict on update restrict;
create index ix_AddedByAdmin_group_2 on AddedByAdmin (groupDescr);
alter table BlackList add constraint fk_BlackList_gpm_3 foreign key (Id) references GPM (id) on delete restrict on update restrict;
create index ix_BlackList_gpm_3 on BlackList (Id);
alter table GroupLink add constraint fk_GroupLink_group_4 foreign key (groupDescr) references GroupDescr (id) on delete restrict on update restrict;
create index ix_GroupLink_group_4 on GroupLink (groupDescr);
alter table GroupLink add constraint fk_GroupLink_link_5 foreign key (link) references Link (id) on delete restrict on update restrict;
create index ix_GroupLink_link_5 on GroupLink (link);
alter table GroupWord add constraint fk_GroupWord_group_6 foreign key (groupDescr) references GroupDescr (id) on delete restrict on update restrict;
create index ix_GroupWord_group_6 on GroupWord (groupDescr);
alter table GroupWord add constraint fk_GroupWord_word_7 foreign key (word) references Word (id) on delete restrict on update restrict;
create index ix_GroupWord_word_7 on GroupWord (word);
alter table Post add constraint fk_Post_gpm_8 foreign key (gpm) references GPM (id) on delete restrict on update restrict;
create index ix_Post_gpm_8 on Post (gpm);
alter table Post add constraint fk_Post_kindContent_9 foreign key (kindContent) references Content (id) on delete restrict on update restrict;
create index ix_Post_kindContent_9 on Post (kindContent);
alter table PostLink add constraint fk_PostLink_post_10 foreign key (post) references Post (id) on delete restrict on update restrict;
create index ix_PostLink_post_10 on PostLink (post);
alter table PostLink add constraint fk_PostLink_link_11 foreign key (link) references Link (id) on delete restrict on update restrict;
create index ix_PostLink_link_11 on PostLink (link);
alter table PostWord add constraint fk_PostWord_post_12 foreign key (post) references Post (id) on delete restrict on update restrict;
create index ix_PostWord_post_12 on PostWord (post);
alter table PostWord add constraint fk_PostWord_word_13 foreign key (word) references Word (id) on delete restrict on update restrict;
create index ix_PostWord_word_13 on PostWord (word);
alter table Profile add constraint fk_Profile_gpm_14 foreign key (gpm) references GPM (id) on delete restrict on update restrict;
create index ix_Profile_gpm_14 on Profile (gpm);
alter table Profile add constraint fk_Profile_gender_15 foreign key (gender) references Gender (id) on delete restrict on update restrict;
create index ix_Profile_gender_15 on Profile (gender);
alter table Profile add constraint fk_Profile_relationshipStatus_16 foreign key (relationshipStatus) references Relationship (id) on delete restrict on update restrict;
create index ix_Profile_relationshipStatus_16 on Profile (relationshipStatus);
alter table ProfileLink add constraint fk_ProfileLink_profile_17 foreign key (profile) references Profile (id) on delete restrict on update restrict;
create index ix_ProfileLink_profile_17 on ProfileLink (profile);
alter table ProfileLink add constraint fk_ProfileLink_link_18 foreign key (link) references Link (id) on delete restrict on update restrict;
create index ix_ProfileLink_link_18 on ProfileLink (link);
alter table ProfileWord add constraint fk_ProfileWord_profile_19 foreign key (profile) references Profile (id) on delete restrict on update restrict;
create index ix_ProfileWord_profile_19 on ProfileWord (profile);
alter table ProfileWord add constraint fk_ProfileWord_word_20 foreign key (word) references Word (id) on delete restrict on update restrict;
create index ix_ProfileWord_word_20 on ProfileWord (word);
alter table Synonym add constraint fk_Synonym_word_21 foreign key (word) references Word (id) on delete restrict on update restrict;
create index ix_Synonym_word_21 on Synonym (word);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table AddedByAdmin;

drop table BlackList;

drop table Content;

drop table GPM;

drop table Gender;

drop table GroupDescr;

drop table GroupLink;

drop table GroupWord;

drop table Link;

drop table NewGPM;

drop table Post;

drop table PostLink;

drop table PostWord;

drop table Profile;

drop table ProfileLink;

drop table ProfileWord;

drop table Relationship;

drop table Synonym;

drop table Word;

SET FOREIGN_KEY_CHECKS=1;

