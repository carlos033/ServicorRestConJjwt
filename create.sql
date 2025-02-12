-- 🔹 Crear tabla HOSPITAL con ID autoincremental
CREATE TABLE hospital (
    id BIGINT NOT NULL AUTO_INCREMENT, 
    nombre_hos VARCHAR(255) NOT NULL UNIQUE, 
    poblacion VARCHAR(255) NOT NULL, 
    numero_consultas INT NOT NULL,  
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- 🔹 Crear tabla PACIENTE
CREATE TABLE paciente (
    nss VARCHAR(255) NOT NULL, 
    f_nacimiento DATE NOT NULL, 
    nombre VARCHAR(255) NOT NULL, 
    password VARCHAR(255), 
    PRIMARY KEY (nss)
) ENGINE=InnoDB;

-- 🔹 Crear tabla MEDICO con referencia correcta a HOSPITAL (usando ID)
CREATE TABLE medico (
    n_licencia VARCHAR(255) NOT NULL, 
    consulta INT NOT NULL, 
    especialidad VARCHAR(255) NOT NULL, 
    nombre VARCHAR(255) NOT NULL, 
    password VARCHAR(255) NOT NULL, 
    hospital_id BIGINT,
    PRIMARY KEY (n_licencia),
    CONSTRAINT FK_medico_hospital FOREIGN KEY (hospital_id) REFERENCES hospital (id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- 🔹 Crear tabla CITA
CREATE TABLE cita (
    id INT NOT NULL AUTO_INCREMENT, 
    f_hora_cita DATETIME(6) NOT NULL, 
    n_licencia VARCHAR(255) NOT NULL, 
    nss VARCHAR(255) NOT NULL, 
    PRIMARY KEY (id), 
    CONSTRAINT UK_medico_cita UNIQUE (n_licencia, f_hora_cita), 
    CONSTRAINT UK_paciente_cita UNIQUE (nss, f_hora_cita),
    CONSTRAINT FK_cita_medico FOREIGN KEY (n_licencia) REFERENCES medico (n_licencia) ON DELETE CASCADE,
    CONSTRAINT FK_cita_paciente FOREIGN KEY (nss) REFERENCES paciente (nss) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 🔹 Crear tabla INFORMES con ID autoincremental
CREATE TABLE informes (
    id BIGINT NOT NULL AUTO_INCREMENT, 
    nombre_inf VARCHAR(255) NOT NULL, 
    url VARCHAR(255) NOT NULL UNIQUE, 
    n_licencia VARCHAR(255) NOT NULL, 
    nss VARCHAR(255) NOT NULL, 
    PRIMARY KEY (id), 
    CONSTRAINT FK_informe_medico FOREIGN KEY (n_licencia) REFERENCES medico (n_licencia) ON DELETE CASCADE, 
    CONSTRAINT FK_informe_paciente FOREIGN KEY (nss) REFERENCES paciente (nss) ON DELETE CASCADE
) ENGINE=InnoDB;

alter table cita add constraint UK_medico_cita unique (n_licencia, f_hora_cita)
alter table cita add constraint UK_paciente_cita unique (nss, f_hora_cita)
alter table informes add constraint UK_qoa74294agc7t9wfmaohci4l5 unique (url)
alter table cita add constraint FKrhrsrlsokgxqgycc1aatdpd7e foreign key (n_licencia) references medico (n_licencia)
alter table cita add constraint FKrrdww5idcfdiv6tl1pgwxv0cr foreign key (nss) references paciente (nss)
alter table informes add constraint FK1918c8iebtxluljo6l1cjh5f3 foreign key (n_licencia) references medico (n_licencia)
alter table informes add constraint FKkw0a0r6fqdn3stbn5oayn2f89 foreign key (nss) references paciente (nss)
alter table medico add constraint FKco547bra1b3oqv5sts6o6wecu foreign key (nombre_hos) references hospital (nombre_hos)
