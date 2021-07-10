from typing import List, Optional

import tensorflow as tf
import pandas as pd

class models(object):
    """Model training and usage"""
    def __init__(self, course_id: str, students_path: str):
        self.course_id     = course_id
        self.students_path = students_path

    def __get_df(self) -> Optional[pd.DataFrame]:
        df = pd.read_csv(self.students_path)

        if isinstance(df, pd.DataFrame):
            return df
        else:
            return None

    def train(self):
        df = self.__get_df()

        if df:
            self.__train_df(df)

    def __train_df(self, df: pd.DataFrame):
        # TODO
        pass

    def predict(self) -> List[float]:
        """Get a vector p of probabilities."""
        df = self.__get_df()

        if df:
            return self.__predict_df(df)
        else:
            return []

    def __predict_df(self, df: pd.DataFrame) -> List[float]:
        # TODO
        return [-1.0]*df.size

