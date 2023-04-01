create table cita (id integer not null auto_increment, f_hora_cita datetime(6) not null, n_licencia varchar(255) not null, nss varchar(255) not null, primary key (id)) engine=InnoDB
create table hospital (nombre_hos varchar(255) not null, poblacion varchar(255) not null, primary key (nombre_hos)) engine=InnoDB
create table informes (nombre_inf varchar(255) not null, url varchar(255) not null, n_licencia varchar(255) not null, nss varchar(255) not null, primary key (nombre_inf)) engine=InnoDB
create table medico (n_licencia varchar(255) not null, consulta integer not null, especialidad varchar(255) not null, nombre varchar(255) not null, password varchar(255) not null, nombre_hos varchar(255), primary key (n_licencia)) engine=InnoDB
create table paciente (nss varchar(255) not null, f_nacimiento date not null, nombre varchar(255) not null, password varchar(255), primary key (nss)) engine=InnoDB
alter table cita add constraint UK_medico_cita unique (n_licencia, f_hora_cita)
alter table cita add constraint UK_paciente_cita unique (nss, f_hora_cita)
alter table informes add constraint UK_qoa74294agc7t9wfmaohci4l5 unique (url)
alter table cita add constraint FKrhrsrlsokgxqgycc1aatdpd7e foreign key (n_licencia) references medico (n_licencia)
alter table cita add constraint FKrrdww5idcfdiv6tl1pgwxv0cr foreign key (nss) references paciente (nss)
alter table informes add constraint FK1918c8iebtxluljo6l1cjh5f3 foreign key (n_licencia) references medico (n_licencia)
alter table informes add constraint FKkw0a0r6fqdn3stbn5oayn2f89 foreign key (nss) references paciente (nss)
alter table medico add constraint FKco547bra1b3oqv5sts6o6wecu foreign key (nombre_hos) references hospital (nombre_hos)
