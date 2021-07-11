from typing import List, Optional, Any

import tensorflow as tf
import pandas as pd

def df2ds( df: pd.DataFrame, target: str = "target", batch_size = 32) -> tf.data.Dataset:
    df = df.copy()
    labels = df.pop(target)
    ds = tf.data.Dataset.from_tensor_slices((dict(df), labels))
    return ds.shuffle(buffer_size=len(df)).batch(batch_size)

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

        df_val = df.sample(frac=0.2)
        df_train:Any = df.drop(df_val.index)

        ds_train = df2ds(df_train)
        ds_val   = df2ds(df_val)
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

