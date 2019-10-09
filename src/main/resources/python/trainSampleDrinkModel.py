
import pandas as pd
from sklearn.naive_bayes import GaussianNB
from sklearn.preprocessing import LabelBinarizer
from sklearn_pandas import DataFrameMapper
import sqlalchemy as db

#create connection
con = db.create_engine('postgresql://user:user@localhost/drink-recommender')

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
            #([["gender"], ["pets"], ["dayofweek"]], [CategoricalDomain(), LabelBinarizer()])
        ])),
        ("classifier", GaussianNB())
    ])
pipeline.fit(X, y)

# save model as pmml file
from sklearn2pmml import sklearn2pmml
sklearn2pmml(pipeline, "drinkModel.pmml")


