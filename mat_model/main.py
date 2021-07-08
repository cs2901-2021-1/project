import sys
import argparse

def main() -> None:
    parser = argparse.ArgumentParser(description="Modelos de matrículas")
    parser.add_argument("-q", "--query")
    parser.add_argument("-t", "--train")

    if len(sys.argv) < 2:
        parser.print_help()
        sys.exit(1)

    args = parser.parse_args()

    from .models import models
    m = models()
