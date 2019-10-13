
CREATE TABLE public.sample_model_data
(
	id int NOT NULL,
    drink varchar(20) NOT NULL,
    gender varchar(20),
    pets varchar(20), 
	dayOfWeek varchar(20),
    CONSTRAINT sample_model_data_pkey PRIMARY KEY (id)
)