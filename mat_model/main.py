import sys
import argparse

def main() -> None:
    parser = argparse.ArgumentParser(description="Modelos de matrículas")

    if len(sys.argv) < 2:
        parser.print_help()
        sys.exit(1)

    args = parser.parse_args()
