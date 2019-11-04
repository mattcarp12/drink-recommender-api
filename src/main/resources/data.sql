insert into questions(question_name, question_text) values ('personality', 'Pick one word to describe your personality:');
insert into questions(question_name, question_text) values ('hotdog', 'Is a hotdog a sandwich or taco?');
insert into questions(question_name, question_text) values ('tp', 'Toilet paper: over or under?');
insert into questions(question_name, question_text) values ('dayofweek', '');
insert into questions(question_name, question_text) values ('drink', '');

insert into question_choices(choice, question_id) values ('Effulgent', 1);
insert into question_choices(choice, question_id) values ('Equanimous', 1);
insert into question_choices(choice, question_id) values ('Exerable', 1);
insert into question_choices(choice, question_id) values ('Sandwich', 2);
insert into question_choices(choice, question_id) values ('Taco', 2);
insert into question_choices(choice, question_id) values ('Over', 3);
insert into question_choices(choice, question_id) values ('Under', 3);
insert into question_choices(choice, question_id) values ('Sunday', 4);
insert into question_choices(choice, question_id) values ('Monday', 4);
insert into question_choices(choice, question_id) values ('Tuesday', 4);
insert into question_choices(choice, question_id) values ('Wednesday', 4);
insert into question_choices(choice, question_id) values ('Thursday', 4);
insert into question_choices(choice, question_id) values ('Friday', 4);
insert into question_choices(choice, question_id) values ('Saturday', 4);
insert into question_choices(choice, question_id) values ('Water', 5);
insert into question_choices(choice, question_id) values ('Soda', 5);
insert into question_choices(choice, question_id) values ('Beer', 5);
insert into question_choices(choice, question_id) values ('Wine', 5);



drop table if exists sample_training_data;

create table sample_training_data (
	id serial primary key,
	drink varchar (50) not null,
	hotdog varchar (50) not null,
	tp varchar (50) not null
);

insert into sample_training_data (drink, hotdog, tp) values ('Beer', 'Sandwich', 'Over');
insert into sample_training_data (drink, hotdog, tp) values ('Beer', 'Taco', 'Over');
insert into sample_training_data (drink, hotdog, tp) values ('Beer', 'Sandwich', 'Under');
insert into sample_training_data (drink, hotdog, tp) values ('Beer', 'Sandwich', 'Over');
insert into sample_training_data (drink, hotdog, tp) values ('Water', 'Sandwich', 'Under');
insert into sample_training_data (drink, hotdog, tp) values ('Water', 'Taco', 'Under');
insert into sample_training_data (drink, hotdog, tp) values ('Water', 'Taco', 'Under');
insert into sample_training_data (drink, hotdog, tp) values ('Water', 'Sandwich', 'Over');
insert into sample_training_data (drink, hotdog, tp) values ('Soda', 'Taco', 'Over');
insert into sample_training_data (drink, hotdog, tp) values ('Soda', 'Taco', 'Over');
insert into sample_training_data (drink, hotdog, tp) values ('Soda', 'Taco', 'Under');
insert into sample_training_data (drink, hotdog, tp) values ('Soda', 'Sandwich', 'Under');
insert into sample_training_data (drink, hotdog, tp) values ('Wine', 'Taco', 'Under');
insert into sample_training_data (drink, hotdog, tp) values ('Wine', 'Taco', 'Under');
insert into sample_training_data (drink, hotdog, tp) values ('Wine', 'Taco', 'Over');
insert into sample_training_data (drink, hotdog, tp) values ('Wine', 'Sandwich', 'Over');




