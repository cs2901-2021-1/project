import sys
import argparse

def import_models():
    from .models import models
    return models()

def train(args: argparse.Namespace) -> None:
    m = import_models()
    m.train(args.students, args.enrollment)

def query(args: argparse.Namespace) -> None:
    m = import_models()
    m.query(args.course, args.students)

def main() -> None:
    parser = argparse.ArgumentParser(description="Modelos de matrículas")
    subparsers = parser.add_subparsers(required=True)

    parser_train = subparsers.add_parser("train")

    parser_train.add_argument("-s", "--students", required=True)
    parser_train.add_argument("-e", "--enrollment", required=True)

    parser_train.set_defaults(func=train)

    parser_query = subparsers.add_parser("query")

    parser_query.add_argument("-c", "--course", required=True)
    parser_query.add_argument("-s", "--students", required=True)

    parser_query.set_defaults(func=query)

    if len(sys.argv) < 2:
        parser.print_help()
        sys.exit(1)

    args = parser.parse_args()

    args.func(args)
