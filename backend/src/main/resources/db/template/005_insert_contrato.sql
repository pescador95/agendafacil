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
    'valuedatacontrato',
    'valuedataterminocontrato',
    valuenumeromaximosessoes,
    valuetipocontrato,
    'template'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            config.contrato
        WHERE
            tenant = 'template'
    );

INSERT INTO
    config.tenant (id, tenant, contratoid)
SELECT
    nextval('config.tenant_id_seq' :: regclass),
    'template',
    (
        select
            id
        from
            config.contrato
        where
            tenant = 'template'
    )
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            config.tenant
        WHERE
            tenant = 'template'
    );