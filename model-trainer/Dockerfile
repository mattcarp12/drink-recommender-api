FROM python:3.6-slim
RUN apt-get install -y openjdk-8-jre
COPY requirements.txt /
RUN pip install -r /requirements.txt
COPY . /app
ENV DATABASE_URL="postgresql://postgres:postgres@localhost:5432/postgres"
ENV PORT = 5000
CMD [ "python", "/app/app.py" ]