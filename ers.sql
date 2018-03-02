--clear tables
DROP TABLE ers_reimbursement;
DROP TABLE ers_users;
DROP TABLE ers_user_roles;
DROP TABLE ers_reimbursement_status;
DROP TABLE ers_reimbursement_type;


--create tables

create table ers_reimbursement
(
    reimb_id number primary key,
    reimb_amount number,
    reimb_submitted timestamp,
    reimb_resolved timestamp,
    reimb_description varchar2(250),
    reimb_receipt blob,
    reimb_author number,
    reimb_resolver number,
    reimb_status_id number,
    reimb_type_id number
);
create table ers_users
(
    ers_users_id number primary key,
    ers_username varchar2(50),
    ers_password varchar2(50),
    user_first_name varchar2(100),
    user_last_name varchar2(100),
    user_email varchar2(150) unique,
    user_role_id number
);
create table ers_reimbursement_status
(
   reimb_status_id number primary key,
   reimb_status varchar2(10)
);
create table ers_reimbursement_type
(
   reimb_type_id number primary key,
   reimb_type varchar2(10)
);
create table ers_user_roles
(
   ers_user_role_id number primary key,
   user_role varchar2(10)
);

-- add foreign keys

alter table ers_reimbursement add constraint fk_auth 
foreign key (reimb_author) references ers_users(ers_users_id);

alter table ers_reimbursement add constraint fk_reslvr 
foreign key (reimb_resolver) references ers_users(ers_users_id);

alter table ers_reimbursement add constraint status_fk
foreign key (reimb_status_id) references ers_reimbursement_status(reimb_status_id);

alter table ers_reimbursement add constraint type_fk
foreign key (reimb_type_id) references ers_reimbursement_type(reimb_type_id);

alter table ers_users add constraint user_roles_fk 
foreign key (user_role_id) references ers_user_roles(ers_user_role_id);

--sequences and triggers for primary keys

CREATE SEQUENCE reimb_id_seq;
CREATE SEQUENCE ers_users_id_seq;

CREATE OR REPLACE TRIGGER reimb_id_trigger
BEFORE INSERT OR UPDATE ON ers_reimbursement
FOR EACH ROW 
BEGIN
    IF inserting THEN
        SELECT reimb_id_seq.NEXTVAL INTO :NEW.reimb_id FROM dual;
    ELSIF updating THEN 
        SELECT :OLD.reimb_id INTO :NEW.reimb_id FROM dual;
    END IF;
END;
/

CREATE OR REPLACE TRIGGER ers_users_id_trigger
BEFORE INSERT OR UPDATE ON ers_users
FOR EACH ROW 
BEGIN
    IF inserting THEN
        SELECT ers_users_id_seq.NEXTVAL INTO :NEW.ers_users_id FROM dual;
    ELSIF updating THEN 
        SELECT :OLD.ers_users_id INTO :NEW.ers_users_id FROM dual;
    END IF;
END;
/
 
 --initialize values
INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type) VALUES (1, 'Lodging');
INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type) VALUES (2, 'Travel');
INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type) VALUES (3, 'Food');
INSERT INTO ers_reimbursement_type (reimb_type_id, reimb_type) VALUES (4, 'Other');

INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status) VALUES (1, 'Pending');
INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status) VALUES (2, 'Approved');
INSERT INTO ers_reimbursement_status (reimb_status_id, reimb_status) VALUES (3, 'Denied');

INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (1, 'Employee');
INSERT INTO ers_user_roles (ers_user_role_id, user_role) VALUES (2, 'Manager');

commit;

--testing
SELECT * FROM ers_users WHERE ers_username = 'blake' AND ers_password = 'pass';

SELECT * FROM ers_reimbursement 
RIGHT OUTER JOIN ers_users author
ON ers_reimbursement.reimb_author = author.ers_users_id
FULL OUTER JOIN ers_users resolv
ON ers_reimbursement.reimb_resolver = resolv.ers_users_id
WHERE author.ers_users_id = 41;

SELECT * FROM ers_reimbursement_status;

SELECT * FROM ers_reimbursement 
INNER JOIN ers_reimbursement_status
USING(reimb_status_id)
WHERE reimb_status = 'Pending';

SELECT * FROM ers_reimbursement INNER JOIN ers_users ON ers_users_id = reimb_author WHERE reimb_status_id = 1;

SELECT * FROM ers_reimbursement;