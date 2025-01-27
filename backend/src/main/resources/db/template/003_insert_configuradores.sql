-- configuradoragendamento
INSERT INTO
    template.CONFIGURADORAGENDAMENTO (
        ID,
        AGENDADOMINGOMANHA,
        AGENDADOMINGONOITE,
        AGENDADOMINGOTARDE,
        AGENDAMANHA,
        AGENDANOITE,
        AGENDASABADOMANHA,
        AGENDASABADONOITE,
        AGENDASABADOTARDE,
        AGENDATARDE,
        ATENDEDOMINGO,
        ATENDESABADO,
        CONFIGURADORORGANIZACAO,
        DATAACAO,
        HORAMINUTOINTERVALO,
        HORAMINUTOTOLERANCIA,
        HORARIOFIMMANHA,
        HORARIOFIMNOITE,
        HORARIOFIMTARDE,
        HORARIOINICIOMANHA,
        HORARIOINICIONOITE,
        HORARIOINICIOTARDE,
        NOME,
        ORGANIZACAOID,
        PROFISSIONALID,
        USUARIOID
    )
VALUES
    (
        nextval(
            'template.configuradoragendamento_id_seq' :: regclass
        ),
        FALSE,
        FALSE,
        FALSE,
        TRUE,
        FALSE,
        TRUE,
        TRUE,
        TRUE,
        TRUE,
        FALSE,
        TRUE,
        FALSE,
        '2023-04-08 12:31:16.398',
        '01:00:00',
        '00:30:00',
        '12:00:00',
        '23:00:00',
        '18:00:00',
        '08:00:00',
        '18:00:00',
        '13:00:00',
        'Configurador Empresa 1',
        1,
        1,
        NULL
    );

INSERT INTO
    template.CONFIGURADORAGENDAMENTO (
        ID,
        AGENDADOMINGOMANHA,
        AGENDADOMINGONOITE,
        AGENDADOMINGOTARDE,
        AGENDAMANHA,
        AGENDANOITE,
        AGENDASABADOMANHA,
        AGENDASABADONOITE,
        AGENDASABADOTARDE,
        AGENDATARDE,
        ATENDEDOMINGO,
        ATENDESABADO,
        CONFIGURADORORGANIZACAO,
        DATAACAO,
        HORAMINUTOINTERVALO,
        HORAMINUTOTOLERANCIA,
        HORARIOFIMMANHA,
        HORARIOFIMNOITE,
        HORARIOFIMTARDE,
        HORARIOINICIOMANHA,
        HORARIOINICIONOITE,
        HORARIOINICIOTARDE,
        NOME,
        ORGANIZACAOID,
        PROFISSIONALID,
        USUARIOID
    )
VALUES
    (
        nextval(
            'template.configuradoragendamento_id_seq' :: regclass
        ),
        FALSE,
        FALSE,
        FALSE,
        TRUE,
        FALSE,
        FALSE,
        FALSE,
        FALSE,
        TRUE,
        FALSE,
        FALSE,
        FALSE,
        '2023-04-08 12:31:17.811',
        '01:00:00',
        '00:30:00',
        '12:00:00',
        '23:00:00',
        '18:00:00',
        '08:00:00',
        '18:00:00',
        '13:00:00',
        'Configurador Empresa 2',
        2,
        2,
        NULL
    );

INSERT INTO
    template.CONFIGURADORAGENDAMENTO (
        ID,
        AGENDADOMINGOMANHA,
        AGENDADOMINGONOITE,
        AGENDADOMINGOTARDE,
        AGENDAMANHA,
        AGENDANOITE,
        AGENDASABADOMANHA,
        AGENDASABADONOITE,
        AGENDASABADOTARDE,
        AGENDATARDE,
        ATENDEDOMINGO,
        ATENDESABADO,
        CONFIGURADORORGANIZACAO,
        DATAACAO,
        HORAMINUTOINTERVALO,
        HORAMINUTOTOLERANCIA,
        HORARIOFIMMANHA,
        HORARIOFIMNOITE,
        HORARIOFIMTARDE,
        HORARIOINICIOMANHA,
        HORARIOINICIONOITE,
        HORARIOINICIOTARDE,
        NOME,
        ORGANIZACAOID,
        PROFISSIONALID,
        USUARIOID
    )
VALUES
    (
        nextval(
            'template.configuradoragendamento_id_seq' :: regclass
        ),
        FALSE,
        FALSE,
        FALSE,
        TRUE,
        FALSE,
        FALSE,
        FALSE,
        FALSE,
        TRUE,
        FALSE,
        FALSE,
        FALSE,
        '2023-04-08 12:31:17.811',
        '01:00:00',
        '00:30:00',
        '12:00:00',
        '23:00:00',
        '18:00:00',
        '08:00:00',
        '18:00:00',
        '13:00:00',
        'Configurador Empresa 1',
        1,
        2,
        NULL
    );

INSERT INTO
    template.CONFIGURADORAGENDAMENTO (
        ID,
        AGENDADOMINGOMANHA,
        AGENDADOMINGONOITE,
        AGENDADOMINGOTARDE,
        AGENDAMANHA,
        AGENDANOITE,
        AGENDASABADOMANHA,
        AGENDASABADONOITE,
        AGENDASABADOTARDE,
        AGENDATARDE,
        ATENDEDOMINGO,
        ATENDESABADO,
        CONFIGURADORORGANIZACAO,
        DATAACAO,
        HORAMINUTOINTERVALO,
        HORAMINUTOTOLERANCIA,
        HORARIOFIMMANHA,
        HORARIOFIMNOITE,
        HORARIOFIMTARDE,
        HORARIOINICIOMANHA,
        HORARIOINICIONOITE,
        HORARIOINICIOTARDE,
        NOME,
        ORGANIZACAOID,
        PROFISSIONALID,
        USUARIOID
    )
VALUES
    (
        nextval(
            'template.configuradoragendamento_id_seq' :: regclass
        ),
        FALSE,
        FALSE,
        FALSE,
        TRUE,
        FALSE,
        TRUE,
        TRUE,
        TRUE,
        TRUE,
        FALSE,
        TRUE,
        FALSE,
        '2023-04-08 12:31:16.398',
        '01:00:00',
        '00:30:00',
        '12:00:00',
        '23:00:00',
        '18:00:00',
        '08:00:00',
        '18:00:00',
        '13:00:00',
        'Configurador Empresa 1',
        2,
        1,
        NULL
    );

-- configuradoragendamentoespecial    
INSERT INTO
    template.CONFIGURADORAGENDAMENTOESPECIAL (
        ID,
        NOME,
        PROFISSIONALID,
        DATAINICIO,
        DATAFIM,
        ORGANIZACAOID,
        DATAACAO
    )
VALUES
    (
        nextval(
            'template.configuradoragendamentoespecial_id_seq' :: regclass
        ),
        'Semana de Ozonoterapia',
        1,
        '2023-04-13',
        '2023-04-14',
        2,
        NOW()
    );

INSERT INTO
    template.CONFIGURADORAGENDAMENTOESPECIAL (
        ID,
        NOME,
        PROFISSIONALID,
        DATAINICIO,
        DATAFIM,
        ORGANIZACAOID,
        DATAACAO
    )
VALUES
    (
        nextval(
            'template.configuradoragendamentoespecial_id_seq' :: regclass
        ),
        'Semana de Osteopatia',
        2,
        '2023-04-13',
        '2023-04-14',
        1,
        NOW()
    );

-- configuradorausencia
INSERT INTO
    template.CONFIGURADORAUSENCIA (
        ID,
        DATAACAO,
        DATAFIMAUSENCIA,
        DATAINICIOAUSENCIA,
        HORAFIMAUSENCIA,
        HORAINICIOAUSENCIA,
        NOMEAUSENCIA,
        OBSERVACAO,
        USUARIOACAOID
    )
VALUES
    (
        nextval(
            'template.configuradorausencia_id_seq' :: regclass
        ),
        '2023-04-21 09:17:31.021716',
        '2023-04-21',
        '2023-04-21',
        '00:00:00',
        '23:59:59',
        'Configurador Feriado',
        'Aniversário do Junior',
        NULL
    );

INSERT INTO
    template.CONFIGURADORAUSENCIAUSUARIO (CONFIGURADORAUSENCIAID, USUARIOID)
VALUES
    (1, 1);

INSERT INTO
    template.CONFIGURADORAUSENCIAUSUARIO (CONFIGURADORAUSENCIAID, USUARIOID)
VALUES
    (1, 3);

-- configuradornotificacao
INSERT INTO
    template.CONFIGURADORNOTIFICACAO (
        ID,
        DATAACAO,
        DATAINTERVALO,
        HORAMINUTOINTERVALO,
        MENSAGEM
    )
VALUES
    (
        nextval(
            'template.configuradornotificacao_id_seq' :: regclass
        ),
        NOW(),
        1,
        '01:00',
        'Olá, NOME!\n\nVocê tem um agendamento de TIPOAGENDAMENTO marcado para DIA SEMANA às HORARIO na EMPRESA.\n \n Endereço: ENDERECO\n Contato: CONTATO \n Profissional: PROFISSIONAL \n Data do Agendamento: DATA \n Horário do Agendamento: HORARIO\n \n Atensiosamente, \n EMPRESA.'
    );

INSERT INTO
    template.CONFIGURADORNOTIFICACAO (
        ID,
        DATAACAO,
        DATAINTERVALO,
        HORAMINUTOINTERVALO,
        MENSAGEM
    )
VALUES
    (
        nextval(
            'template.configuradornotificacao_id_seq' :: regclass
        ),
        NOW(),
        0,
        '01:00',
        'Olá, NOME!\n\nVocê tem um agendamento de TIPOAGENDAMENTO marcado para DIA SEMANA às HORARIO na EMPRESA.\n \n Endereço: ENDERECO\n Contato: CONTATO \n Profissional: PROFISSIONAL \n Data do Agendamento: DATA \n Horário do Agendamento: HORARIO\n \n Atensiosamente, \n EMPRESA.'
    );