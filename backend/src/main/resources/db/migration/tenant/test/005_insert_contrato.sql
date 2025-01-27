-- contrato
INSERT INTO
    config.contrato (
        id,
        ativo,
        datacontrato,
        dataterminocontrato,
        numeromaximosessoes,
        tipocontratoid,
        tenant
    )
SELECT
    nextval('config.contrato_id_seq' :: regclass),
    true,
    '2024-04-24',
    '2030-08-24',
    10,
    1,
    'test'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            config.contrato
        WHERE
            tenant = 'test'
    );

INSERT INTO
    config.tenant (id, tenant, contratoid)
SELECT
    nextval('config.tenant_id_seq' :: regclass),
    'test',
    (
        select
            id
        from
            config.contrato
        where
            tenant = 'test'
    )
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            config.tenant
        WHERE
            tenant = 'test'
    );