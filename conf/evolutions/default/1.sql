# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table pais (
  id                        bigserial not null,
  nome                      varchar(50) not null,
  ddi                       varchar(4),
  data_cadastro             timestamp,
  data_alteracao            timestamp,
  constraint uq_pais_nome unique (nome),
  constraint pk_pais primary key (id))
;




# --- !Downs

drop table if exists pais cascade;

