import sys
import argparse

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
    print(course)

    return Response(course)

def main() -> None:
    app.run()
