
-- package ddl

    CREATE SCHEMA bank AUTHORIZATION postgres;
    COMMENT ON SCHEMA bank IS 'Bank - Module DB';

    --Person 

        -- Person - Sequence
        CREATE SEQUENCE bank.s_person START WITH 1 INCREMENT BY 1;
        COMMENT ON SEQUENCE bank.s_person IS 'Person sequence';

        -- Person - Table
        CREATE TABLE bank.t_person (
            id BIGINT DEFAULT nextval('bank.s_person'),
            name VARCHAR(128) NOT NULL,
            ci VARCHAR(13) NOT NULL UNIQUE,
            gender VARCHAR(32) NOT NULL,
            age INTEGER NOT NULL,
            address VARCHAR(128) NOT NULL,
            phone VARCHAR(32) NOT NULL,
            
            CONSTRAINT bank_pk_person PRIMARY KEY (id)
        );

        COMMENT ON TABLE bank.t_person IS 'Person table';
        COMMENT ON COLUMN bank.t_person.id IS 'Person id';
        COMMENT ON COLUMN bank.t_person.name IS 'Name';
        COMMENT ON COLUMN bank.t_person.ci IS 'Ci of Person';
        COMMENT ON COLUMN bank.t_person.gender IS 'Gender of Person';
        COMMENT ON COLUMN bank.t_person.age IS 'Age of Person';
        COMMENT ON COLUMN bank.t_person.address IS 'Address of Person';
        COMMENT ON COLUMN bank.t_person.phone IS 'Phone of Person';

        COMMENT ON CONSTRAINT bank_pk_person ON bank.t_person  IS 'Restriction PK workorder';

    --Client

        -- Client - Table
        CREATE TABLE bank.t_client (
            id BIGINT NOT NULL,
            password VARCHAR(128) NOT NULL,
            
            status VARCHAR(1) NOT NULL, -- ('1', '0')
            last_modified_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
            CONSTRAINT bank_pk_client PRIMARY KEY (id)
        );

        COMMENT ON TABLE bank.t_client IS 'Client table';
        COMMENT ON COLUMN bank.t_client.id IS 'Client_Person id';
        COMMENT ON COLUMN bank.t_client.password IS 'Password';

        COMMENT ON COLUMN bank.t_client.status IS 'Record status';
        COMMENT ON COLUMN bank.t_client.last_modified_date IS 'Date of the last modification of the record';
        COMMENT ON CONSTRAINT bank_pk_client ON bank.t_client  IS 'Restriction PK workorder';

    --Account 

        -- Account - Sequence
        CREATE SEQUENCE bank.s_account START WITH 1000 INCREMENT BY 1;
        COMMENT ON SEQUENCE bank.s_account IS 'Account sequence';

        -- Account - Table
        CREATE TABLE bank.t_account (
            account_id BIGINT DEFAULT nextval('bank.s_account'),
            client_id BIGINT NOT NULL, --FK
            type_account VARCHAR(32) NOT NULL,
            balance NUMERIC(12,2) NOT NULL,
            
            status VARCHAR(1) NOT NULL, -- ('1', '0')
            last_modified_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
            CONSTRAINT bank_pk_account PRIMARY KEY (account_id)
        );

        COMMENT ON TABLE bank.t_account IS 'Account table';
        COMMENT ON COLUMN bank.t_account.account_id IS 'Account id';
        COMMENT ON COLUMN bank.t_account.client_id IS 'Client id FK';
        COMMENT ON COLUMN bank.t_account.type_account IS 'Type of account - Ahorros o Corriente';
        COMMENT ON COLUMN bank.t_account.balance IS 'Balance initial of account';

        COMMENT ON COLUMN bank.t_account.status IS 'Record status';
        COMMENT ON COLUMN bank.t_account.last_modified_date IS 'Date of the last modification of the record';
        COMMENT ON CONSTRAINT bank_pk_account ON bank.t_account  IS 'Restriction PK workorder';

    --Transactions 

        -- Transactions - Sequence
        CREATE SEQUENCE bank.s_transactions START WITH 1 INCREMENT BY 1;
        COMMENT ON SEQUENCE bank.s_transactions IS 'Transactions sequence';

        -- Transactions - Table
        CREATE TABLE bank.t_transactions (
            transaction_id BIGINT DEFAULT nextval('bank.s_transactions'),
            account_id BIGINT NOT NULL, --FK
            date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
            amount NUMERIC(12,2) NOT NULL,
            type VARCHAR(32) NOT NULL,
            balance_previous NUMERIC(12,2) NOT NULL,
            balance_final NUMERIC(12,2) NOT NULL, 
            
            status VARCHAR(1) NOT NULL, -- ('1', '0')
            last_modified_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
            CONSTRAINT bank_pk_transactions PRIMARY KEY (transaction_id)
        );

        COMMENT ON TABLE bank.t_transactions IS 'Transactions table';
        COMMENT ON COLUMN bank.t_transactions.transaction_id IS 'Transactions id';
        COMMENT ON COLUMN bank.t_transactions.account_id IS 'Account id FK';
        COMMENT ON COLUMN bank.t_transactions.date IS 'Date of transactions';
        COMMENT ON COLUMN bank.t_transactions.amount IS 'Amount money for transactions';
        COMMENT ON COLUMN bank.t_transactions.type IS 'Type of transactions - DEBITO O CREDITO';
        COMMENT ON COLUMN bank.t_transactions.balance_previous IS 'Balance previous transactions';
        COMMENT ON COLUMN bank.t_transactions.balance_final IS 'Balance final after transactions';

        COMMENT ON COLUMN bank.t_transactions.status IS 'Record status';
        COMMENT ON COLUMN bank.t_transactions.last_modified_date IS 'Date of the last modification of the record';
        COMMENT ON CONSTRAINT bank_pk_transactions ON bank.t_transactions  IS 'Restriction PK workorder';

    --FOREIGN KEYS

        /* Client */
        -- Person
        ALTER TABLE bank.t_client ADD CONSTRAINT person_client_fk
        FOREIGN KEY (id) REFERENCES bank.t_person (id)
        ON DELETE RESTRICT ON UPDATE RESTRICT;
        COMMENT ON CONSTRAINT person_client_fk ON bank.t_client IS 'Restriction FK Position PK user for created record';

        /* Account */
        -- Client
        ALTER TABLE bank.t_account ADD CONSTRAINT client_account_fk
        FOREIGN KEY (client_id) REFERENCES bank.t_client (id)
        ON DELETE RESTRICT ON UPDATE RESTRICT;
        COMMENT ON CONSTRAINT person_client_fk ON bank.t_client IS 'Restriction FK Position PK user for created record';

        /* Transactions */
        -- Account 
        ALTER TABLE bank.t_transactions ADD CONSTRAINT account_transactions_fk
        FOREIGN KEY (account_id) REFERENCES bank.t_account (account_id)
        ON DELETE RESTRICT ON UPDATE RESTRICT;
        COMMENT ON CONSTRAINT account_transactions_fk ON bank.t_transactions IS 'Restriction FK Position PK user for created record';

-- package dml 

    --Person
        INSERT INTO bank.t_person (name, ci, gender, age, address, phone) VALUES ( 'JOSE LEMA', '1727384892', 'Masculino', 40, 'Otavalo sn y principal', '0982547853');

    --Client
        INSERT INTO bank.t_client (id , password, status) VALUES ((select p.id from bank.t_person p where p.name = 'JOSE LEMA'),'cHJ1ZWJhcw==', '1');

    --Account
        INSERT INTO bank.t_account (client_id, type_account, balance, status) VALUES ((select p.id from bank.t_person p where p.name = 'JOSE LEMA'),'Ahorros', 0, 1);

    --Transactions 
        INSERT INTO bank.t_transactions (account_id, amount, type, balance_previous, balance_final, status) VALUES ((select a.account_id from bank.t_account a where a.type_account = 'Ahorros'),200, 'CREDITO', 0, 200, 1);


