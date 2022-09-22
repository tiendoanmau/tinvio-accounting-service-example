--Create SQL Finance Business Accounts -
create table IF NOT EXISTS finance_business_accounts (
id serial PRIMARY KEY,
pan VARCHAR(100) NOT NULL UNIQUE,
account_type VARCHAR(100) NOT NULL,
name VARCHAR(255) NOT NULL DEFAULT 'Business Account',
parent_id integer,
status varchar(100) default 'INACTIVE' check(status in ('ACTIVE', 'INACTIVE', 'CREATED')),
currency varchar(10),
balance decimal,
hold_balance decimal,
remarks text,
flags json,
reference_id varchar(100) unique,   
created_by json NOT NULL,
updated_by json NOT NULL,
created_at TIMESTAMP NOT NULL,
updated_at TIMESTAMP NOT NULL,
constraint fk_finance_account_types foreign key(account_type) references finance_account_types(code)
on delete cascade);


-- SQL Sequence for Finance Business Accounts -
-- finance_business_accounts_id_seq


--SQL Sequence for Generating PAN
create sequence IF NOT EXISTS finance_business_accounts_pan_seq
START 1
INCREMENT 1
MINVALUE 1
MAXVALUE 9999999999
OWNED BY finance_business_accounts.pan
NO cycle;

-- Index for PAN
CREATE unique INDEX if not exists idx_finance_business_accounts_pan ON finance_business_accounts USING btree (pan);
