insert into user_details (id, date, name)
values(10001, current_date(), 'Karan');

insert into user_details (id, date, name)
values(10002, current_date(), 'Ravi');

insert into user_details (id, date, name)
values(10003, current_date(), 'Satish');

insert into post (id, description, user_id)
values (20001, 'I want to learn DevOps', 10002);
insert into post (id, description, user_id)
values (20002, 'I want to get AWS Certified', 10003);
insert into post (id, description, user_id)
values (20003, 'I want to learn AWS', 10001);