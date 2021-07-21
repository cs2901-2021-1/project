from json import dumps
from flask import Flask, Response, request

app = Flask(__name__)

@app.route("/train")
def train():
    course = request.args.get("course")
    print(course)

    return Response(course)

@app.route("/predict")
def predict():
    course = request.args.get("course")

    # Dummy
    p = [0.5, 0.5]
    response = dumps({"p": p})

    return Response(response, mimetype="application/json")

def main() -> None:
    app.run()
