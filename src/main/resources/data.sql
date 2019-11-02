insert into questions values ('hotdog', 'Is a hotdog a sandwich or taco?');
insert into questions values ('tp', 'Toilet paper: over or under?');
insert into questions values ('personality', 'Pick one word to describe your personality:');

insert into question_choices(choice, question_name) values ('Sandwich', 'hotdog');
insert into question_choices(choice, question_name) values ('Taco', 'hotdog');
insert into question_choices(choice, question_name) values ('Over', 'tp');
insert into question_choices(choice, question_name) values ('Under', 'tp');
insert into question_choices(choice, question_name) values ('Effulgent', 'personality');
insert into question_choices(choice, question_name) values ('Equanimous', 'personality');
insert into question_choices(choice, question_name) values ('Exerable', 'personality');

insert into drinks values ('water', 'www.images.com/water.jpg');
insert into drinks values ('beer', 'www.images.com/beer.jpg');
insert into drinks values ('soda', 'www.images.com/soda.jpg');
insert into drinks values ('wine', 'www.images.com/wine.jpg');


drop table if exists model_training_data;

create table model_training_data (
	id serial primary key,
	drink varchar (50) not null,
	hotdog varchar (50) not null,
	tp varchar (50) not null
);

insert into model_training_data (drink, hotdog, tp) values ('beer', 'Sandwich', 'Over');
insert into model_training_data (drink, hotdog, tp) values ('beer', 'Taco', 'Over');
insert into model_training_data (drink, hotdog, tp) values ('beer', 'Sandwich', 'Under');
insert into model_training_data (drink, hotdog, tp) values ('beer', 'Sandwich', 'Over');
insert into model_training_data (drink, hotdog, tp) values ('water', 'Sandwich', 'Under');
insert into model_training_data (drink, hotdog, tp) values ('water', 'Taco', 'Under');
insert into model_training_data (drink, hotdog, tp) values ('water', 'Taco', 'Under');
insert into model_training_data (drink, hotdog, tp) values ('water', 'Sandwich', 'Over');
insert into model_training_data (drink, hotdog, tp) values ('soda', 'Taco', 'Over');
insert into model_training_data (drink, hotdog, tp) values ('soda', 'Taco', 'Over');
insert into model_training_data (drink, hotdog, tp) values ('soda', 'Taco', 'Under');
insert into model_training_data (drink, hotdog, tp) values ('soda', 'Sandwich', 'Under');
insert into model_training_data (drink, hotdog, tp) values ('wine', 'Taco', 'Under');
insert into model_training_data (drink, hotdog, tp) values ('wine', 'Taco', 'Under');
insert into model_training_data (drink, hotdog, tp) values ('wine', 'Taco', 'Over');
insert into model_training_data (drink, hotdog, tp) values ('wine', 'Sandwich', 'Over');




