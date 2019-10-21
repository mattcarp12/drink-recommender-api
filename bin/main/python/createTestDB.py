import pandas as pd
from sklearn import datasets
import sqlalchemy as db

#import iris dataset
dataset=datasets.load_iris()
data=pd.DataFrame(dataset['data'],columns=["Petal Length","Petal Width","Sepal Length","Sepal Width"])
data['Species']=dataset['target']
data['Species']=data['Species'].apply(lambda x: dataset['target_names'][x])

#create connection
engine = db.create_engine('postgresql://user:user@localhost/iris')

#create table in postgres of iris dataset
data.to_sql(con=engine, name='iris', if_exists='replace')



    
