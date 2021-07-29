import setuptools

with open("README.md", "r") as r:
    long_description = r.read()

setuptools.setup(
    name = "mat_model",
    version = "0.0.0",
    author = "Otreblan",
    description = "Modelos de matrículas",
    long_description = long_description,
    long_description_content_type="text/markdown",
    url="https://github.com/cs2901-2021-1/project-backend-grupo3sinnombre",
    packages=setuptools.find_packages(),
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: Other/Proprietary License",
    ],
    install_requires=[
        "boto3",
        "cx-Oracle",
        "flask",
        "pandas",
        "waitress",
        # tensorflow
    ],
    entry_points={
        "console_scripts": [
            "mat-model = mat_model:main",
        ],
    },
)
