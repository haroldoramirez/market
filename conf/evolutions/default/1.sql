# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cidade (
  id                        bigserial not null,
  nome                      varchar(25) not null,
  ddd                       varchar(4),
  estado_id                 bigint not null,
  data_cadastro             timestamp,
  data_alteracao            timestamp,
  constraint uq_cidade_nome unique (nome),
  constraint uq_cidade_1 unique (nome,estado_id),
  constraint pk_cidade primary key (id))
;

create table cliente (
  id                        bigserial not null,
  nome                      varchar(35) not null,
  rg                        varchar(15),
  cpf                       varchar(15) not null,
  cnpj                      varchar(20) not null,
  ie                        varchar(15),
  email                     varchar(35),
  telefone                  varchar(12),
  celular                   varchar(12),
  contato                   varchar(35),
  logradouro                varchar(35),
  numero                    varchar(5),
  cep                       varchar(10),
  bairro                    varchar(20),
  complemento               varchar(10),
  tipo_pessoa               integer,
  observacoes               varchar(10),
  cidade_id                 bigint not null,
  data_cadastro             timestamp,
  data_alteracao            timestamp,
  constraint ck_cliente_tipo_pessoa check (tipo_pessoa in (0,1)),
  constraint uq_cliente_nome unique (nome),
  constraint uq_cliente_cpf unique (cpf),
  constraint uq_cliente_cnpj unique (cnpj),
  constraint pk_cliente primary key (id))
;

create table estado (
  id                        bigserial not null,
  nome                      varchar(25) not null,
  uf                        varchar(3),
  pais_id                   bigint not null,
  data_cadastro             timestamp,
  data_alteracao            timestamp,
  constraint uq_estado_1 unique (nome,pais_id),
  constraint pk_estado primary key (id))
;

create table pais (
  id                        bigserial not null,
  nome                      varchar(25) not null,
  ddi                       varchar(4),
  sigla                     varchar(3),
  data_cadastro             timestamp,
  data_alteracao            timestamp,
  constraint uq_pais_nome unique (nome),
  constraint pk_pais primary key (id))
;

create table usuario (
  id                        bigserial not null,
  email                     varchar(30) not null,
  senha                     varchar(60) not null,
  privilegio                integer not null,
  data_cadastro             timestamp,
  data_alteracao            timestamp,
  padrao_do_sistema         boolean not null,
  constraint uq_usuario_email unique (email),
  constraint pk_usuario primary key (id))
;

alter table cidade add constraint fk_cidade_estado_1 foreign key (estado_id) references estado (id);
create index ix_cidade_estado_1 on cidade (estado_id);
alter table cliente add constraint fk_cliente_cidade_2 foreign key (cidade_id) references cidade (id);
create index ix_cliente_cidade_2 on cliente (cidade_id);
alter table estado add constraint fk_estado_pais_3 foreign key (pais_id) references pais (id);
create index ix_estado_pais_3 on estado (pais_id);



# --- !Downs

drop table if exists cidade cascade;

drop table if exists cliente cascade;

drop table if exists estado cascade;

drop table if exists pais cascade;

drop table if exists usuario cascade;

