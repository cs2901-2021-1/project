from json import dumps, loads
from flask import Flask, Response, request
from os import getenv

import cx_Oracle

app = Flask(__name__)

def connect():
    dsn = cx_Oracle.makedsn(
        getenv("HOST"),
        getenv("PORT"),
        service_name = getenv("SID")
    )

    return cx_Oracle.connect(
        user     = getenv("USER"),
        password = getenv("PASSWORD"),
        dsn      = dsn,
        encoding = "UTF-8"
    )


@app.route("/train")
def train():
    course = request.args.get("course")
    print(course)

    return Response(course)

@app.route("/predict")
def predict():
    course = request.args.get("course")

    # Dummy
    p = [0.5, 0.5]
    response = dumps({"p": p})

    return Response(response, mimetype="application/json")

@app.route("/courses")
def courses():
    cursor = connect().cursor()

    sql ="""
SELECT JSON_OBJECT(
    'cursoId'     IS ca.CODACTIVIDAD,
    'cursoDesc'   IS ca.DESCRIPCIONCORTA,
    'cursoCod'    IS ca.IDACTIVIDAD,
    'areaFunId'   IS gaf.CODAREAFUNCIONAL,
    'areaFunDesc' IS gaf.DESCRIPCORTA
)
FROM CONFIGURACION.CON_ACTIVIDAD ca
INNER JOIN GENERAL.GEN_AREA_FUNCIONAL gaf ON gaf.CODAREAFUNCIONAL = ca.CODAREAFUNCIONAL AND gaf.ISDELETED = 'N'
WHERE ca.ISDELETED = 'N'
    """

    cursor.execute(sql)

    return Response(dumps([loads(row[0]) for row in cursor]),
        mimetype="application/json")

def main() -> None:
    app.run()
