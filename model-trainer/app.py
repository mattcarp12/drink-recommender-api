from flask import Flask
from flask import Response
import pandas as pd
from sklearn.naive_bayes import GaussianNB
from sklearn.preprocessing import LabelBinarizer
from sklearn_pandas import DataFrameMapper
import sqlalchemy as db
import os
import redis
from threading import Thread

app = Flask(__name__)

@app.route('/hello')
def hello_world():
    return 'Hello, World!'

@app.route('/test')
def database_test():
    #create connection
    DATABASE_URL = os.environ['DATABASE_URL']
    con = db.create_engine(DATABASE_URL)
    return Response(response = tuple(pd.read_sql("select * from test_table"
                        , con = con
                        , index_col = "id").iloc[0]), status = 200)
    

def pub(myredis):
    myredis.publish('transferModel', 'transferModel')


# Subscribe to messages that are sent from Spring Boot app 
# When receive a message, invoke train_model()
def sub(myredis, name):
    pubsub = myredis.pubsub()
    pubsub.subscribe(['trainModel'])
    for item in pubsub.listen():
        train_model()

def train_model():

    #create connection
    DATABASE_URL = os.environ['DATABASE_URL']
    con = db.create_engine(DATABASE_URL)

    #read data from postgres
    data = pd.read_sql("select * from sample_model_data"
                       , con = con
                       , index_col="id")

    # create pipeline and fit model
    from sklearn2pmml import PMMLPipeline
    from sklearn2pmml.decoration import CategoricalDomain
    X = data[data.columns.difference(["drink"])]
    y = data["drink"]

    pipeline = PMMLPipeline([
            ("transformation", DataFrameMapper([
                (["gender"], [CategoricalDomain(), LabelBinarizer()]),
                (["pets"], [CategoricalDomain(), LabelBinarizer()]),
                (["dayofweek"], [CategoricalDomain(), LabelBinarizer()])
            ])),
            ("classifier", GaussianNB())
        ])
    pipeline.fit(X, y)

    # save model as pmml file then put to redis
    from sklearn2pmml import sklearn2pmml
    sklearn2pmml(pipeline, "model.pmml")
    myredis.set(name = "MODEL", value = open("model.pmml", "r").read())
    
    # notify spring boot app the model is ready
    pub(myredis)
    
    
    


if __name__ == '__main__':
    myredis = redis.from_url(os.environ.get("REDIS_URL")) if os.environ.get("REDIS_URL") else redis.Redis()
    #Thread(target=pub, args=(myredis,)).start()
    Thread(target=sub, args=(myredis,'reader1')).start()

