from typing import List, Optional
from sklearn.model_selection import train_test_split

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

        if df is not None:
            self.__train_df(df)

    def __train_df(self, df: pd.DataFrame):
        df = df.rename(columns={"matricula": "target"})

        df_train, df_val = train_test_split(df, test_size=0.2)

        # TODO

    def predict(self) -> List[float]:
        """Get a vector p of probabilities."""
        df = self.__get_df()

        if df is not None:
            return self.__predict_df(df)
        else:
            return []

    def __predict_df(self, df: pd.DataFrame) -> List[float]:
        # TODO
        return [-1.0]*df.size

