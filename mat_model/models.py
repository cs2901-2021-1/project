from subprocess import run
from tensorflow.keras.layers.experimental.preprocessing import Normalization
from typing import List, Optional, Any, Dict

import pandas as pd
import tensorflow as tf

class models(object):
    """Model training and usage"""
    def __init__(self, course_id: str, inputs_cols: List[str], target_col: str):
        self.course_id     = course_id
        self.inputs_cols   = inputs_cols
        self.target_col    = target_col

    def __model_path(self) -> str:
        return f"model-{self.course_id}"

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

    def __make_model(self, ds: tf.data.Dataset) -> tf.keras.Model:
        all_inputs = []
        all_features = []

        for input in self.inputs_cols:
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
        run(["tar", "acf", f"{self.__model_path()}.tar.zst", self.__model_path()])

    def train(self, df: pd.DataFrame):
        df = df.rename(columns={self.target_col: "target"})

        df_val = df.sample(frac=0.2)
        df_train:Any = df.drop(df_val.index)

        ds_train = self.__df2ds(df_train)
        ds_val   = self.__df2ds(df_val)

        model = self.__make_model(ds_train)

        model.compile(
            optimizer='adam',
            loss=tf.keras.losses.BinaryCrossentropy(from_logits=True),
            metrics=["accuracy"]
        )
        model.fit(ds_train, epochs=10, validation_data=ds_val)

        self.__save_model(model)

    def __load_model(self) -> Optional[tf.keras.Model]:
        run(["tar", "xf", f"{self.__model_path()}.tar.zst"])
        model: Any =  tf.keras.models.load_model(self.__model_path())

        if isinstance(model, tf.keras.Model):
            return model
        else:
            return None

    def predict(self, df: pd.DataFrame) -> List[float]:
        """Get a vector p of probabilities."""
        model = self.__load_model()

        if model is None or df.empty:
            return []

        input_dict = self.__df2tfdict(df)
        predictions = model.predict(input_dict)
        return [float(x[0]) for x in tf.nn.sigmoid(predictions).numpy()]

    def __df2tfdict(self, df: pd.DataFrame) -> Dict:
        df_dict: Any = df.to_dict()
        return {
            name: tf.convert_to_tensor(list(value.values()))
            for name, value in df_dict.items()
        }
