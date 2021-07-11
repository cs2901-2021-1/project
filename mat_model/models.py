from typing import List, Optional, Any
from tensorflow.keras.layers.experimental.preprocessing import Normalization

import tensorflow as tf
import pandas as pd

class models(object):
    """Model training and usage"""
    def __init__(self, course_id: str, students_path: str):
        self.course_id     = course_id
        self.students_path = students_path

    def __model_path(self) -> str:
        return f"model-{self.course_id}"

    def __get_df(self) -> Optional[pd.DataFrame]:
        df = pd.read_csv(self.students_path)

        if isinstance(df, pd.DataFrame):
            return df
        else:
            return None

    def __df2ds(self, df: pd.DataFrame, target: str = "target", batch_size = 32) -> tf.data.Dataset:
        df = df.copy()
        labels = df.pop(target)
        ds = tf.data.Dataset.from_tensor_slices((dict(df), labels))
        return ds.shuffle(buffer_size=len(df)).batch(batch_size)

    def __encode(self, feature , name: str, ds: tf.data.Dataset):
        layer = Normalization()

        feature_ds = ds.map(lambda x, _: x[name])
        feature_ds = feature_ds.map(lambda x: tf.expand_dims(x, -1))

        layer.adapt(feature_ds)

        return layer(feature)

    def __make_model(self, inputs: List[str], ds: tf.data.Dataset) -> tf.keras.Model:
        all_inputs = []
        all_features = []

        for input in inputs:
            col = tf.keras.Input(shape=(1,), name=input)
            encoded = self.__encode(col, input, ds)
            all_inputs.append(col)
            all_features.append(encoded)

        x = tf.keras.layers.Dense(32, activation="relu")(tf.keras.layers.concatenate(all_features))
        x = tf.keras.layers.Dropout(0.5)(x)
        output = tf.keras.layers.Dense(1)(x)
        return tf.keras.Model(all_inputs, output)

    def __save_model(self, model: tf.keras.Model):
        model.save(self.__model_path())

    def train(self):
        df = self.__get_df()

        if df is not None:
            self.__train_df(df)

    def __train_df(self, df: pd.DataFrame):
        df = df.rename(columns={"matricula": "target"})

        df_val = df.sample(frac=0.2)
        df_train:Any = df.drop(df_val.index)

        ds_train = self.__df2ds(df_train)
        ds_val   = self.__df2ds(df_val)

        model = self.__make_model(["test_val"], ds_train)

        model.compile(
            optimizer='adam',
            loss=tf.keras.losses.BinaryCrossentropy(from_logits=True),
            metrics=["accuracy"]
        )
        model.fit(ds_train, epochs=10, validation_data=ds_val)

        self.__save_model(model)

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

