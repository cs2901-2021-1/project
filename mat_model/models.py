from typing import List

import tensorflow as tf
import pandas as pd

class models(object):
    """Model training and usage"""
    def __init__(self, course_id: str, students_path: str):
        self.course_id     = course_id
        self.students_path = students_path

    def train(self):
        print(f"TODO train")

    def predict(self) -> List[float]:
        """Get a vector p of probabilities."""
        df = pd.read_csv(self.students_path)

        if isinstance(df, pd.DataFrame):
            return self.__predict_df(df)
        else:
            return []

    def __predict_df(self, df: pd.DataFrame) -> List[float]:
        # TODO
        return [-1.0]*df.size

