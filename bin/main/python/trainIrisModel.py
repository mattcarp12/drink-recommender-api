
import pandas as pd
from sklearn.naive_bayes import GaussianNB
import sqlalchemy as db

#create connection
con = db.create_engine('postgresql://user:user@localhost/iris')

#read data from postgres
data = pd.read_sql("select * from iris"
                   , con = con
                   , index_col="index")

# create pipeline and fit model
from sklearn2pmml import PMMLPipeline
pipeline = PMMLPipeline([
        ("classifier", GaussianNB())
    ])
pipeline.fit(data[data.columns.difference(["Species"])], data["Species"])

# save model as pmml file
from sklearn2pmml import sklearn2pmml
sklearn2pmml(pipeline, "model.pmml")


