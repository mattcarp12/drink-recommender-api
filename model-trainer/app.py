from flask import Flask
from flask import Response

app = Flask(__name__)

@app.route('/test')
def hello_world():
    print("Hello, World!")

@app.route('/trainer')
def run_app():
    train_model()
    model = open("model.pmml").read()
    return Response(response = model, status = 200, mimetype="application/xml")
    


import pandas as pd
from sklearn.naive_bayes import GaussianNB
from sklearn.preprocessing import LabelBinarizer
from sklearn_pandas import DataFrameMapper
import sqlalchemy as db

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

    # save model as pmml file
    from sklearn2pmml import sklearn2pmml
    sklearn2pmml(pipeline, "model.pmml")

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')

