from faker import Faker
import csv
from os import path, makedirs

Faker.seed(0)
fake = Faker()

student_folder = path.join(path.dirname(__file__), "student")
point_folder = path.join(path.dirname(__file__), "points")

makedirs(student_folder, exist_ok=True)
makedirs(point_folder, exist_ok=True)

fake_classes = ["17HCB1", "18HCB1", "19CTT1", "16CTN"]
exist_courses_for_16 = ["CTT031", "CTT032"]
exist_courses_for_17 = ["CTT011", "CTT012"]

field_names = ["stt", "mssv", "name", "gender", "cmnd"]
field_names_point = ["stt", "mssv", "name", "gk", "ck", "other", "average"]

def getWriter(filepath: str, fieldnames: list):
    file = open(filepath, "w", newline="")
    writer = csv.DictWriter(file, fieldnames=fieldnames, delimiter=",", quotechar="\"")
    writer.writeheader()
    return writer

for fake_class in fake_classes:
    writer = getWriter(f"{student_folder}/{fake_class}.csv", field_names)

    mssv_prefix = fake_class[:2]

    point_files = []
    if mssv_prefix == "16":
        point_files = list(map(
            lambda name: getWriter(f"{point_folder}/{fake_class}_{name}.csv", field_names_point),
            exist_courses_for_16
        ))
    elif mssv_prefix == "17":
        point_files = list(map(
            lambda name: getWriter(f"{point_folder}/{fake_class}_{name}.csv", field_names_point),
            exist_courses_for_16
        ))

    for stt in range(100):
        student = {
            "stt": stt,
            "mssv": f"{mssv_prefix}{stt:04}",
            "name": fake.name(),
            "gender": "nam" if fake.pybool() else "nữ",
            "cmnd": fake.msisdn()
        }
        writer.writerow(student)
        print (f"Created student {student['mssv']}")
        print (f"{len(point_files)}")

        for point_file in point_files:
            gk = fake.pyint(max_value=10, min_value=2)
            ck = fake.pyint(max_value=10, min_value=2)
            other = fake.pyint(max_value=10, min_value=2)
            average = round((gk + ck + other) / 3, 2)

            point = {
                "stt": stt,
                "mssv": student["mssv"],
                "name": student["name"],
                "gk": gk,
                "ck": ck,
                "other": other,
                "average": average
            }
            point_file.writerow(point)
            print (f"\tpoint {average}")
