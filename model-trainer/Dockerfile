FROM python:3.6-slim
COPY requirements.txt /
RUN pip install -r /requirements.txt
COPY . /app
ENV DATABASE_URL="postgresql://postgres:postgres@localhost:5432/postgres"
CMD [ "python", "/app/app.py" ]