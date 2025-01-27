
INSERT INTO
    config.tipocontrato (id, descricao, tipocontrato)
VALUES
(
        1,
        'Permite com que você utilize apenas 1 sessão por Usuário, derrubando a sessão anterior.',
        'Sessão única'
    );

INSERT INTO
    config.tipocontrato (id, descricao, tipocontrato)
VALUES
(
        2,
        'Permite que você utilize o mesmo Usuário para várias sessões simultâneas, porém a cada sessão ativa será contabilizada.',
        'Sessão Compartilhada'
    );

INSERT INTO config.contrato (
    id,
    ativo,
    consideracoes,
    datacontrato,
    numeromaximosessoes,
    systemdatedeleted,
    tipocontratoid,
    tenant
)
SELECT
    nextval('config.contrato_id_seq'::regclass),
    true,
    'Contrato por usuário.',
    '2023-08-24',
    10,
    NULL,
    1,
    'test'
WHERE NOT EXISTS (
    SELECT 1
    FROM config.contrato
    WHERE
        tenant = 'test'
);

INSERT INTO
    config.tenant (id, tenant, contratoid)
SELECT
    nextval('tenant_id_seq' :: regclass),
    'test',
    1
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            config.tenant
        WHERE
            tenant = 'test'
    );

INSERT INTO
    config.applicationconfig (
        id,
        agendafacilurl,
        emailpassword,
        emailuser,
        quarkusbaseurl,
        quarkusport,
        quarkusurl,
        redisbaseurl,
        scheduleenabled,
        telegrambaseurl,
        whatsappbaseurl,
        profile
    )
SELECT
    nextval('applicationconfig_id_seq' :: regclass),
    'https://agendafacil.app/',
    'tsysepulluubzkkm',
    'desafiozoo2022@gmail.com',
    'http://agendafacil-backend:3000',
    3000,
    'agendafacil-backend',
    'redis://redis-service:6379',
    true,
    'http://telegram-service:5000',
    'http://whatsapp-service:4000',
    'dev'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            config.applicationconfig
        WHERE
            profile = 'dev'
    );

INSERT INTO
    config.applicationconfig (
        id,
        agendafacilurl,
        emailpassword,
        emailuser,
        quarkusbaseurl,
        quarkusport,
        quarkusurl,
        redisbaseurl,
        scheduleenabled,
        telegrambaseurl,
        whatsappbaseurl,
        profile
    )
SELECT
        nextval('applicationconfig_id_seq' :: regclass),
        'https://agendafacil.app/',
        'tsysepulluubzkkm',
        'desafiozoo2022@gmail.com',
        'http://agendafacil-backend:3000',
        3000,
        'agendafacil-backend',
        'redis://redis-service:6379',
        true,
        'http://telegram-service:5000',
        'http://whatsapp-service:4000',
        'prod'
        WHERE
            NOT EXISTS (
                SELECT
                    1
                FROM
                    config.applicationconfig
                WHERE
                    profile = 'prod'
            );


