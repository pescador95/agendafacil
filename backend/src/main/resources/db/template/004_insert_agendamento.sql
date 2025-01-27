-- agendamento
INSERT INTO
        template.AGENDAMENTO (
                ID,
                DATAAGENDAMENTO,
                HORARIOAGENDAMENTO,
                ATIVO,
                COMPREFERENCIA,
                ORGANIZACAOID,
                PESSOAID,
                PROFISSIONALID,
                STATUSAGENDAMENTOID,
                TIPOAGENDAMENTOID
        )
VALUES
        (
                nextval('template.agendamento_id_seq' :: regclass),
                CURRENT_DATE,
                '11:00:00',
                TRUE,
                FALSE,
                1,
                3,
                2,
                1,
                1
        ),
        (
                nextval('template.agendamento_id_seq' :: regclass),
                CURRENT_DATE,
                '10:00:00',
                TRUE,
                FALSE,
                2,
                7,
                3,
                1,
                1
        );

INSERT INTO
        template.AGENDAMENTO (
                ID,
                DATAAGENDAMENTO,
                HORARIOAGENDAMENTO,
                ATIVO,
                COMPREFERENCIA,
                ORGANIZACAOID,
                PESSOAID,
                PROFISSIONALID,
                STATUSAGENDAMENTOID,
                TIPOAGENDAMENTOID
        )
VALUES
        (
                nextval('template.agendamento_id_seq' :: regclass),
                CURRENT_DATE,
                '13:00:00',
                TRUE,
                FALSE,
                1,
                3,
                2,
                1,
                1
        ),
        (
                nextval('template.agendamento_id_seq' :: regclass),
                CURRENT_DATE,
                '14:00:00',
                TRUE,
                FALSE,
                2,
                7,
                3,
                1,
                1
        );

-- tipoagendamentousuarios
INSERT INTO
        template.TIPOAGENDAMENTOUSUARIOS (
                PROFISSIONALID,
                TIPOAGENDAMENTOID
        )
VALUES
        (2, 1);

INSERT INTO
        template.TIPOAGENDAMENTOUSUARIOS (
                PROFISSIONALID,
                TIPOAGENDAMENTOID
        )
VALUES
        (1, 1);

INSERT INTO
        template.TIPOAGENDAMENTOUSUARIOS (
                PROFISSIONALID,
                TIPOAGENDAMENTOID
        )
VALUES
        (1, 2);

INSERT INTO
        template.TIPOAGENDAMENTOUSUARIOS (
                PROFISSIONALID,
                TIPOAGENDAMENTOID
        )
VALUES
        (7, 1);

INSERT INTO
        template.TIPOAGENDAMENTOUSUARIOS (
                PROFISSIONALID,
                TIPOAGENDAMENTOID
        )
VALUES
        (7, 2);

INSERT INTO
        template.TIPOAGENDAMENTOUSUARIOS (
                PROFISSIONALID,
                TIPOAGENDAMENTOID
        )
VALUES
        (7, 3);

INSERT INTO
        template.TIPOAGENDAMENTOUSUARIOS (
                PROFISSIONALID,
                TIPOAGENDAMENTOID
        )
VALUES
        (7, 4);

INSERT INTO
        template.TIPOAGENDAMENTOUSUARIOS (
                PROFISSIONALID,
                TIPOAGENDAMENTOID
        )
VALUES
        (7, 5);