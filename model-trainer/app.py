import os
from threading import Thread

import pandas as pd
import redis
from sklearn.naive_bayes import GaussianNB
from sklearn.preprocessing import LabelBinarizer
from sklearn_pandas import DataFrameMapper
import sqlalchemy as db
import logging


def pub(myredis):
    logging.info("Publishing on transferModel")
    myredis.publish('transferModel', 'transferModel')

def sub(myredis, name):
    pubsub = myredis.pubsub()
    pubsub.subscribe(['trainModel'])
    for item in pubsub.listen():
        train_model('LIVE')

def get_sample_data(con):
    data = pd.read_sql("select * from sample_training_data"
                       , con = con
                       , index_col="id")
    
    pipeline = PMMLPipeline([
            ("transformation", DataFrameMapper([
                (["hotdog"], [CategoricalDomain(), LabelBinarizer()]),
                (["tp"], [CategoricalDomain(), LabelBinarizer()])
            ])),
            ("classifier", GaussianNB())
        ])
    return data, pipeline
    
def get_training_data(con):
    data = pd.read_sql("select * from user_responses"
                       , con = con
                       , index_col="id")
    
    data.pivot(index = 'session_id', colums = 'question_name', values = 'question_choice')
    
    logging.info(data)
    
    pipeline = PMMLPipeline([
            ("transformation", DataFrameMapper([
                (["hotdog"], [CategoricalDomain(), LabelBinarizer()]),
                (["tp"], [CategoricalDomain(), LabelBinarizer()]),
                (["personality"], [CategoricalDomain(), LabelBinarizer()]),
                (["dayofweek"], [CategoricalDomain(), LabelBinarizer()])
            ])),
            ("classifier", GaussianNB())
        ])
    return data, pipeline
    
    

def train_model(dataType):
    
    from sklearn2pmml import PMMLPipeline
    from sklearn2pmml.decoration import CategoricalDomain
    
    try:
        myredis
    except NameError:
        myredis = redis.from_url(os.environ.get("REDIS_URL")) if os.environ.get("REDIS_URL") else redis.Redis()
    
    #create connection
    DATABASE_URL = os.environ.get('DATABASE_URL') or "postgresql://postgres:postgres@localhost:5432/postgres"
    con = db.create_engine(DATABASE_URL)

    # Get data and pipeline
    data, pipeline = get_training_data(con) if dataType == 'LIVE' else get_sample_data(con)

    X = data[data.columns.difference(["drink"])]
    y = data["drink"]

    logging.info("Training model...")
    pipeline.fit(X, y)

    # save model as pmml file then put to redis
    from sklearn2pmml import sklearn2pmml
    sklearn2pmml(pipeline, "model.pmml")
    logging.info("Saving trained model to redis under key: 'MODEL'")
    myredis.set(name = "MODEL", value = open("model.pmml", "r").read())
    
    # notify spring boot app the model is ready
    pub(myredis)
    
    
    


if __name__ == '__main__':
    myredis = redis.from_url(os.environ.get("REDIS_URL")) if os.environ.get("REDIS_URL") else redis.Redis()
    Thread(target=sub, args=(myredis,'reader1')).start()

