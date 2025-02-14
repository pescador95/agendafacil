-- tipoagendamento
INSERT INTO
    test.TIPOAGENDAMENTO (ID, TIPOAGENDAMENTO)
VALUES
    (
        nextval('test.tipoagendamento_id_seq' :: regclass),
        'Fisioterapia'
    );

INSERT INTO
    test.TIPOAGENDAMENTO (ID, TIPOAGENDAMENTO)
VALUES
    (
        nextval('test.tipoagendamento_id_seq' :: regclass),
        'Ozonioterapia'
    );

INSERT INTO
    test.TIPOAGENDAMENTO (ID, TIPOAGENDAMENTO)
VALUES
    (
        nextval('test.tipoagendamento_id_seq' :: regclass),
        'Pilates'
    );

INSERT INTO
    test.TIPOAGENDAMENTO (ID, TIPOAGENDAMENTO)
VALUES
    (
        nextval('test.tipoagendamento_id_seq' :: regclass),
        'Osteopatia'
    );

INSERT INTO
    test.TIPOAGENDAMENTO (ID, TIPOAGENDAMENTO)
VALUES
    (
        nextval('test.tipoagendamento_id_seq' :: regclass),
        'Microfisioterapia'
    );

-- statusagendamento
INSERT INTO
    test.STATUSAGENDAMENTO (ID, STATUS)
VALUES
    (
        nextval('test.statusagendamento_id_seq' :: regclass),
        'Agendado'
    );

INSERT INTO
    test.STATUSAGENDAMENTO (ID, STATUS)
VALUES
    (
        nextval('test.statusagendamento_id_seq' :: regclass),
        'Remarcado'
    );

INSERT INTO
    test.STATUSAGENDAMENTO (ID, STATUS)
VALUES
    (
        nextval('test.statusagendamento_id_seq' :: regclass),
        'Cancelado'
    );

INSERT INTO
    test.STATUSAGENDAMENTO (ID, STATUS)
VALUES
    (
        nextval('test.statusagendamento_id_seq' :: regclass),
        'Em Aberto'
    );

INSERT INTO
    test.STATUSAGENDAMENTO (ID, STATUS)
VALUES
    (
        nextval('test.statusagendamento_id_seq' :: regclass),
        'Atendido'
    );

-- genero
INSERT INTO
    test.GENERO (ID, GENERO)
VALUES
    (
        nextval('test.genero_id_seq' :: regclass),
        'Masculino'
    );

INSERT INTO
    test.GENERO (ID, GENERO)
VALUES
    (
        nextval('test.genero_id_seq' :: regclass),
        'Feminino'
    );

-- role
INSERT INTO
    test.ROLE (
        ID,
        PRIVILEGIO,
        ADMIN
    )
VALUES
    (
        nextval('test.role_id_seq' :: regclass),
        'usuario',
        FALSE
    );

INSERT INTO
    test.ROLE (
        ID,
        PRIVILEGIO,
        ADMIN
    )
VALUES
    (
        nextval('test.role_id_seq' :: regclass),
        'bot',
        FALSE
    );

INSERT INTO
    test.ROLE (
        ID,
        PRIVILEGIO,
        ADMIN
    )
VALUES
    (
        nextval('test.role_id_seq' :: regclass),
        'admin',
        TRUE
    );

-- endereco
INSERT INTO
    test.ENDERECO (
        ID,
        CEP,
        LOGRADOURO,
        NUMERO,
        COMPLEMENTO,
        CIDADE,
        ESTADO,
        ATIVO
    )
VALUES
    (
        nextval('test.endereco_id_seq' :: regclass),
        85485000,
        'Avenida Brasil',
        500,
        'Prédio de dois andares',
        'Três Barras do Paraná',
        'Paraná',
        TRUE
    );

INSERT INTO
    test.ENDERECO (
        ID,
        CEP,
        LOGRADOURO,
        NUMERO,
        COMPLEMENTO,
        CIDADE,
        ESTADO,
        ATIVO
    )
VALUES
    (
        nextval('test.endereco_id_seq' :: regclass),
        85810 - 011,
        'Rua Paraná',
        500,
        'Centro Comercial',
        'Cascavel',
        'Paraná',
        TRUE
    );

-- pessoa
INSERT INTO
    test.pessoa (
        ativo,
        datanascimento,
        createdat,
        createdby,
        deletedat,
        deletedby,
        enderecoid,
        generoid,
        id,
        telegramid,
        updatedat,
        updatedby,
        whatsappid,
        celular,
        cpf,
        email,
        nome,
        telefone
    )
VALUES
    (
        TRUE,
        NULL,
        '2024-02-18 09:21:11.476',
        0,
        NULL,
        0,
        NULL,
        NULL,
        nextval('test.pessoa_id_seq' :: regclass),
        NULL,
        NULL,
        0,
        NULL,
        '45991000000',
        '12345678911',
        'desafiozoo2022@gmail.com',
        'Agendabot',
        '4533110000'
    ),
    (
        TRUE,
        NULL,
        '2024-02-18 09:21:11.477',
        0,
        NULL,
        0,
        NULL,
        NULL,
        nextval('test.pessoa_id_seq' :: regclass),
        1376688519,
        NULL,
        0,
        NULL,
        '45991330277',
        '12345678912',
        'iedio_junior@hotmail.com',
        'iédio Júnior',
        '1376688519'
    ),
    (
        TRUE,
        NULL,
        '2024-02-18 09:21:11.478',
        0,
        NULL,
        0,
        NULL,
        NULL,
        nextval('test.pessoa_id_seq' :: regclass),
        NULL,
        NULL,
        0,
        NULL,
        '45998189415',
        '12345678913',
        'adrissonvinicius02@gmail.com',
        'Adrisson Vinicius Araujo',
        '45998189415'
    ),
    (
        TRUE,
        NULL,
        '2024-02-18 09:21:11.479',
        0,
        NULL,
        0,
        NULL,
        NULL,
        nextval('test.pessoa_id_seq' :: regclass),
        NULL,
        NULL,
        0,
        NULL,
        '45998132931',
        '12345678914',
        'felicecatto@hotmail.com',
        'Felipe Cecatto',
        '45998132931'
    ),
    (
        TRUE,
        NULL,
        '2024-02-18 09:21:11.480',
        0,
        NULL,
        0,
        NULL,
        NULL,
        nextval('test.pessoa_id_seq' :: regclass),
        NULL,
        NULL,
        0,
        NULL,
        '45997850013',
        '12345678915',
        'fagner@exemplo.com',
        'Fagner Menegasso',
        '45997850013'
    ),
    (
        TRUE,
        NULL,
        '2024-02-18 09:21:11.481',
        0,
        NULL,
        0,
        NULL,
        NULL,
        nextval('test.pessoa_id_seq' :: regclass),
        NULL,
        NULL,
        0,
        NULL,
        '45998017886',
        '12345678916',
        'vitoriamoreschi@hotmail.com',
        'Vitória Moreschi',
        '45998017886'
    ),
    (
        TRUE,
        NULL,
        '2024-02-18 09:21:11.482',
        0,
        NULL,
        0,
        NULL,
        NULL,
        nextval('test.pessoa_id_seq' :: regclass),
        NULL,
        NULL,
        0,
        NULL,
        '45998579877',
        '12345678917',
        NULL,
        'Fernando Schindler',
        '4566541651'
    );

-- organizacao
INSERT INTO
    test.organizacao (
        ativo,
        createdat,
        createdby,
        deletedat,
        deletedby,
        enderecoid,
        id,
        telegramid,
        timezoneid,
        updatedat,
        updatedby,
        whatsappid,
        celular,
        cnpj,
        email,
        nome,
        telefone,
        tenant
    )
VALUES
    (
        TRUE,
        '2024-02-18 09:21:11.483',
        0,
        NULL,
        0,
        1,
        nextval('test.organizacao_id_seq' :: regclass),
        NULL,
        200,
        NULL,
        0,
        NULL,
        '45991000001',
        '426723380001300',
        'organizacao1@gmail.com',
        'Organização test - Três Barras',
        '4533091010',
        'test'
    );

INSERT INTO
    test.organizacao (
        ativo,
        createdat,
        createdby,
        deletedat,
        deletedby,
        enderecoid,
        id,
        telegramid,
        timezoneid,
        updatedat,
        updatedby,
        whatsappid,
        celular,
        cnpj,
        email,
        nome,
        telefone,
        tenant
    )
VALUES
    (
        TRUE,
        '2024-02-18 09:21:11.484',
        0,
        NULL,
        0,
        2,
        nextval('test.organizacao_id_seq' :: regclass),
        NULL,
        200,
        NULL,
        0,
        NULL,
        '45991000002',
        '426723380001200',
        'organizacao2@gmail.com',
        'Organização test - Cascavel',
        '4532351010',
        'test'
    );

-- usuario
INSERT INTO
    test.USUARIO (
        ALTERARSENHA,
        ATIVO,
        BOT,
        CREATEDAT,
        CREATEDBY,
        DATATOKEN,
        DELETEDAT,
        DELETEDBY,
        ID,
        ORGANIZACAODEFAULTID,
        PESSOAID,
        UPDATEDAT,
        UPDATEDBY,
        LOGIN,
        "password",
        "token"
    )
VALUES
    (
        FALSE,
        TRUE,
        TRUE,
        '2024-02-18 09:21:11.476',
        1,
        NULL,
        NULL,
        NULL,
        nextval('test.usuario_id_seq' :: regclass),
        1,
        1,
        NULL,
        NULL,
        'Agendabot',
        '$2a$10$K18REHituULGcB2AeDqMBuKuvvlEe8FCbQGPrav.ZB6uqgbJOO47u',
        NULL
    );

INSERT INTO
    test.USUARIO (
        ALTERARSENHA,
        ATIVO,
        BOT,
        CREATEDAT,
        CREATEDBY,
        DATATOKEN,
        DELETEDAT,
        DELETEDBY,
        ID,
        ORGANIZACAODEFAULTID,
        PESSOAID,
        UPDATEDAT,
        UPDATEDBY,
        LOGIN,
        "password",
        "token"
    )
VALUES
    (
        FALSE,
        TRUE,
        FALSE,
        '2024-02-18 09:21:11.477',
        1,
        NULL,
        NULL,
        NULL,
        nextval('test.usuario_id_seq' :: regclass),
        2,
        2,
        NULL,
        NULL,
        'iedio',
        '$2a$10$raAPyQb127dlJXRYaRDFjeRGnzEW02z/qgZZ7nyBhSvtc6NHrWsGS',
        NULL
    );

INSERT INTO
    test.USUARIO (
        ALTERARSENHA,
        ATIVO,
        BOT,
        CREATEDAT,
        CREATEDBY,
        DATATOKEN,
        DELETEDAT,
        DELETEDBY,
        ID,
        ORGANIZACAODEFAULTID,
        PESSOAID,
        UPDATEDAT,
        UPDATEDBY,
        LOGIN,
        "password",
        "token"
    )
VALUES
    (
        FALSE,
        TRUE,
        FALSE,
        '2024-02-18 09:21:11.478',
        1,
        NULL,
        NULL,
        NULL,
        nextval('test.usuario_id_seq' :: regclass),
        1,
        3,
        NULL,
        NULL,
        'adrisson',
        '$2a$10$xlOKRSROsTN8xSsOhPiRZuVFg/dmmardKfXv..lwYbWaVFvfptUjO',
        NULL
    );

INSERT INTO
    test.USUARIO (
        ALTERARSENHA,
        ATIVO,
        BOT,
        CREATEDAT,
        CREATEDBY,
        DATATOKEN,
        DELETEDAT,
        DELETEDBY,
        ID,
        ORGANIZACAODEFAULTID,
        PESSOAID,
        UPDATEDAT,
        UPDATEDBY,
        LOGIN,
        "password",
        "token"
    )
VALUES
    (
        FALSE,
        TRUE,
        FALSE,
        '2024-02-18 09:21:11.479',
        1,
        NULL,
        NULL,
        NULL,
        nextval('test.usuario_id_seq' :: regclass),
        2,
        4,
        NULL,
        NULL,
        'felipe',
        '$2a$10$OLXknxqJlHd5GM1v8Me5P.9sUutjktboH0KNNWuDc1DQRTFNaFOtS',
        NULL
    );

INSERT INTO
    test.USUARIO (
        ALTERARSENHA,
        ATIVO,
        BOT,
        CREATEDAT,
        CREATEDBY,
        DATATOKEN,
        DELETEDAT,
        DELETEDBY,
        ID,
        ORGANIZACAODEFAULTID,
        PESSOAID,
        UPDATEDAT,
        UPDATEDBY,
        LOGIN,
        "password",
        "token"
    )
VALUES
    (
        FALSE,
        TRUE,
        FALSE,
        '2024-02-18 09:21:11.480',
        1,
        NULL,
        NULL,
        NULL,
        nextval('test.usuario_id_seq' :: regclass),
        1,
        5,
        NULL,
        NULL,
        'admin',
        '$2a$10$K18REHituULGcB2AeDqMBuKuvvlEe8FCbQGPrav.ZB6uqgbJOO47u$2a$10$K18REHituULGcB2AeDqMBuKuvvlEe8FCbQGPrav.ZB6uqgbJOO47u',
        NULL
    );

INSERT INTO
    test.USUARIO (
        ALTERARSENHA,
        ATIVO,
        BOT,
        CREATEDAT,
        CREATEDBY,
        DATATOKEN,
        DELETEDAT,
        DELETEDBY,
        ID,
        ORGANIZACAODEFAULTID,
        PESSOAID,
        UPDATEDAT,
        UPDATEDBY,
        LOGIN,
        "password",
        "token"
    )
VALUES
    (
        FALSE,
        TRUE,
        FALSE,
        '2024-02-18 09:21:11.481',
        1,
        NULL,
        NULL,
        NULL,
        nextval('test.usuario_id_seq' :: regclass),
        2,
        6,
        NULL,
        NULL,
        'vitoria',
        '$2a$10$KY8WsyooozYNubdTiNd.eew17rcay2TFsyIyKmItSpAiFie7WevfG',
        NULL
    );

INSERT INTO
    test.USUARIO (
        ALTERARSENHA,
        ATIVO,
        BOT,
        CREATEDAT,
        CREATEDBY,
        DATATOKEN,
        DELETEDAT,
        DELETEDBY,
        ID,
        ORGANIZACAODEFAULTID,
        PESSOAID,
        UPDATEDAT,
        UPDATEDBY,
        LOGIN,
        "password",
        "token"
    )
VALUES
    (
        FALSE,
        TRUE,
        FALSE,
        '2024-02-18 09:21:11.482',
        1,
        NULL,
        NULL,
        NULL,
        nextval('test.usuario_id_seq' :: regclass),
        1,
        7,
        NULL,
        NULL,
        'fernando',
        '$2a$10$5daMMULbYdM3h6cKm27.WuTBpr0SRrZqhZfgMYRcrTjNoPaIcNPyq',
        NULL
    );

INSERT INTO
    test.USUARIOROLES (USUARIOID, ROLEID)
VALUES
    (1, 1);

INSERT INTO
    test.USUARIOROLES (USUARIOID, ROLEID)
VALUES
    (1, 3);

INSERT INTO
    test.USUARIOROLES (USUARIOID, ROLEID)
VALUES
    (2, 1);

INSERT INTO
    test.USUARIOROLES (USUARIOID, ROLEID)
VALUES
    (3, 1);

INSERT INTO
    test.USUARIOROLES (USUARIOID, ROLEID)
VALUES
    (4, 1);

INSERT INTO
    test.USUARIOROLES (USUARIOID, ROLEID)
VALUES
    (4, 3);

INSERT INTO
    test.USUARIOROLES (USUARIOID, ROLEID)
VALUES
    (5, 1);

INSERT INTO
    test.USUARIOROLES (USUARIOID, ROLEID)
VALUES
    (5, 1);

INSERT INTO
    test.USUARIOROLES (USUARIOID, ROLEID)
VALUES
    (6, 2);

INSERT INTO
    test.USUARIOROLES (USUARIOID, ROLEID)
VALUES
    (7, 1);

INSERT INTO
    test.USUARIOROLES (USUARIOID, ROLEID)
VALUES
    (7, 3);

INSERT INTO
    test.USUARIOORGANIZACAO (USUARIOID, ORGANIZACAOID)
VALUES
    (1, 1);

INSERT INTO
    test.USUARIOORGANIZACAO (USUARIOID, ORGANIZACAOID)
VALUES
    (2, 2);

INSERT INTO
    test.USUARIOORGANIZACAO (USUARIOID, ORGANIZACAOID)
VALUES
    (3, 1);

INSERT INTO
    test.USUARIOORGANIZACAO (USUARIOID, ORGANIZACAOID)
VALUES
    (4, 2);

INSERT INTO
    test.USUARIOORGANIZACAO (USUARIOID, ORGANIZACAOID)
VALUES
    (5, 1);

INSERT INTO
    test.USUARIOORGANIZACAO (USUARIOID, ORGANIZACAOID)
VALUES
    (7, 2);

INSERT INTO
    test.USUARIOORGANIZACAO (USUARIOID, ORGANIZACAOID)
VALUES
    (7, 1);

-- tipoagendamentoorganizacoes
INSERT INTO
    test.TIPOAGENDAMENTOORGANIZACOES (
        TIPOAGENDAMENTOID,
        ORGANIZACAOID
    )
VALUES
    (1, 1);

INSERT INTO
    test.TIPOAGENDAMENTOORGANIZACOES (
        TIPOAGENDAMENTOID,
        ORGANIZACAOID
    )
VALUES
    (1, 2);

INSERT INTO
    test.TIPOAGENDAMENTOORGANIZACOES (
        TIPOAGENDAMENTOID,
        ORGANIZACAOID
    )
VALUES
    (2, 1);

INSERT INTO
    test.TIPOAGENDAMENTOORGANIZACOES (
        TIPOAGENDAMENTOID,
        ORGANIZACAOID
    )
VALUES
    (2, 2);

INSERT INTO
    test.TIPOAGENDAMENTOORGANIZACOES (
        TIPOAGENDAMENTOID,
        ORGANIZACAOID
    )
VALUES
    (3, 1);

INSERT INTO
    test.TIPOAGENDAMENTOORGANIZACOES (
        TIPOAGENDAMENTOID,
        ORGANIZACAOID
    )
VALUES
    (3, 2);

INSERT INTO
    test.TIPOAGENDAMENTOORGANIZACOES (
        TIPOAGENDAMENTOID,
        ORGANIZACAOID
    )
VALUES
    (4, 1);

INSERT INTO
    test.TIPOAGENDAMENTOORGANIZACOES (
        TIPOAGENDAMENTOID,
        ORGANIZACAOID
    )
VALUES
    (4, 2);

INSERT INTO
    test.TIPOAGENDAMENTOORGANIZACOES (
        TIPOAGENDAMENTOID,
        ORGANIZACAOID
    )
VALUES
    (5, 1);