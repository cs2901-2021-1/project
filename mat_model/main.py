import sys
import argparse

def import_models():
    from .models import models
    return models()

def train(args: argparse.Namespace) -> None:
    m = import_models()
    m.train(args.course, args.students)

def predict(args: argparse.Namespace) -> None:
    m = import_models()
    m.predict(args.course, args.students)

def main() -> None:
    parser = argparse.ArgumentParser(description="Modelos de matrículas")

    parser.add_argument("-c", "--course", required=True)
    parser.add_argument("-s", "--students", required=True)

    subparsers = parser.add_subparsers(required=True)

    parser_train = subparsers.add_parser("train")
    parser_train.set_defaults(func=train)

    parser_predict = subparsers.add_parser("predict")
    parser_predict.set_defaults(func=predict)

    if len(sys.argv) < 2:
        parser.print_help()
        sys.exit(1)

    args = parser.parse_args()

    args.func(args)
