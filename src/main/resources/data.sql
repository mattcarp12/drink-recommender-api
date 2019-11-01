insert into questions values ('hotdog', 'Is a hotdog a sandwich or taco?');
insert into questions values ('gender', 'What is your gender?');

insert into question_choices(choice, question_name) values ('Sandwich', 'hotdog');
insert into question_choices(choice, question_name) values ('Taco', 'hotdog');
insert into question_choices(choice, question_name) values ('Male', 'gender');
insert into question_choices(choice, question_name) values ('Feale', 'gender');
insert into question_choices(choice, question_name) values ('Other', 'gender');
