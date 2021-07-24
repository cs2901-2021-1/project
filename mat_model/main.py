from json import dumps, loads
from flask import Flask, Response, request
from os import getenv
from .models import models

import cx_Oracle
import pandas as pd

app = Flask(__name__)
columns = ["numveces", "tiempo"]
target = "matricula"

def connect():
    dsn = cx_Oracle.makedsn(
        getenv("ORACLE_HOST"),
        getenv("ORACLE_PORT"),
        service_name = getenv("ORACLE_SID")
    )

    return cx_Oracle.connect(
        user     = getenv("ORACLE_USER"),
        password = getenv("ORACLE_PASSWORD"),
        dsn      = dsn,
        encoding = "UTF-8"
    )


@app.route("/train")
def train():
    course = request.args.get("course")

    if course is None:
        return Response() # TODO

    cursor = connect().cursor()

    sql="""
SELECT aamd.NUMVECES, pp.FECHAINICIO - ga.FECHAINGRESO,
CASE
    WHEN aam.CODALUMNOMATRICULA IN (
        SELECT CODALUMNOMATRICULA
        FROM ACADEMICO.ACA_ALUMNO_MATRICULA_CURSO
        WHERE ISDELETED = 'N'
        ) THEN 1
    ELSE 0
END AS MATRICULADO
FROM ACADEMICO.ACA_ALUMNO_MATRICULA_DEMANDA aamd
INNER JOIN ACADEMICO.ACA_ALUMNO_MATRICULA aam
    ON aam.CODALUMNOMATRICULA = aamd.CODALUMNOMATRICULA
    AND aam.ISDELETED         = 'N'
INNER JOIN ACADEMICO.ACA_ALUMNO_MALLA aam2
    ON aam2.CODALUMNOMALLA = aam.CODALUMNOMALLA
    AND aam2.ISDELETED     = 'N'
INNER JOIN GENERAL.GEN_ALUMNO ga
    ON ga.CODALUMNO  = aam2.CODALUMNO
    AND ga.ISDELETED = 'N'
INNER JOIN PROGRAMACION.PRO_PERIODORANGO pp
    ON pp.CODPERIODORANGO = aam.CODPERIODORANGO
    AND pp.ISDELETED      = 'N'
WHERE aamd.ISDELETED = 'N'
    AND aamd.CODCURSO = :course
    """

    cursor.execute(sql, course=course)

    df = pd.DataFrame(
        [_ for _ in cursor],
        columns=columns + [target]
    )

    m = models(course, columns, target)

    m.train(df)

    return Response()

@app.route("/predict")
def predict():
    course = request.args.get("course")
    period = request.args.get("period")

    if course is None or period is None:
        return Response() # TODO

    cursor = connect().cursor()

    sql ="""
SELECT aamd.NUMVECES, pp.FECHAINICIO - ga.FECHAINGRESO
FROM ACADEMICO.ACA_ALUMNO_MATRICULA_DEMANDA aamd
INNER JOIN ACADEMICO.ACA_ALUMNO_MATRICULA aam
    ON aam.CODALUMNOMATRICULA = aamd.CODALUMNOMATRICULA
    AND aam.ISDELETED         = 'N'
    AND aam.CODPERIODORANGO   = :period
INNER JOIN ACADEMICO.ACA_ALUMNO_MALLA aam2
    ON aam2.CODALUMNOMALLA = aam.CODALUMNOMALLA
    AND aam2.ISDELETED     = 'N'
INNER JOIN GENERAL.GEN_ALUMNO ga
    ON ga.CODALUMNO  = aam2.CODALUMNO
    AND ga.ISDELETED = 'N'
INNER JOIN PROGRAMACION.PRO_PERIODORANGO pp
    ON pp.CODPERIODORANGO = aam.CODPERIODORANGO
    AND pp.ISDELETED      = 'N'
WHERE aamd.ISDELETED = 'N'
    AND aamd.CODCURSO = :course
    """

    cursor.execute(sql, course=course, period=period)

    df = pd.DataFrame([_ for _ in cursor], columns=columns)

    m = models(course, columns, target)

    response = dumps({"p": m.predict(df)})

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
INNER JOIN GENERAL.GEN_AREA_FUNCIONAL gaf
    ON gaf.CODAREAFUNCIONAL = ca.CODAREAFUNCIONAL
    AND gaf.ISDELETED       = 'N'
WHERE ca.ISDELETED = 'N'
    """

    cursor.execute(sql)

    return Response(dumps([loads(row[0]) for row in cursor]),
        mimetype="application/json")

@app.route("/periods")
def periods():
    cursor = connect().cursor()

    sql ="""
SELECT JSON_OBJECT(
    'periodoCod'   IS pp.CODPERIODO,
    'periodoAlias' IS pp.NOMALIAS,
    'fechaInicio'  IS pp2.FECHAINICIO,
    'fechaFin'     IS pp2.FECHAFIN
)
FROM PROGRAMACION.PRO_PERIODO pp
INNER JOIN PROGRAMACION.PRO_PERIODORANGO pp2
    ON pp2.CODPERIODO = pp.CODPERIODO
    AND pp2.ISDELETED = 'N'
WHERE pp.ISDELETED = 'N'
    """

    cursor.execute(sql)

    return Response(dumps([loads(row[0]) for row in cursor]),
        mimetype="application/json")

def main() -> None:
    app.run(port=int(getenv("PORT", 5000)), host=("0.0.0.0"))
