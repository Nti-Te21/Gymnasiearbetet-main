import csv
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad, unpad
import base64
import os


def encrypt_and_encode(record, key):
    cipher = AES.new(key, AES.MODE_CBC)
    encrypted = cipher.encrypt(pad(record.encode("utf-8"), AES.block_size))
    print(AES.block_size)
    encoded = base64.b64encode(encrypted).decode("utf-8")
    iv = base64.b64encode(cipher.iv).decode("utf-8")
    return f"{encoded},{iv}"


def decode_and_decrypt(encoded_and_iv, key):
    encoded, iv = encoded_and_iv.split(",")
    iv = base64.b64decode(iv)
    cipher = AES.new(key, AES.MODE_CBC, iv)
    decoded = base64.b64decode(encoded)
    decrypted = unpad(cipher.decrypt(decoded), AES.block_size).decode("utf-8")
    return decrypted


def read_records_from_csv(file_path, key):
    records = []
    if os.path.exists(file_path):
        with open(file_path, "r") as csv_file:
            csv_reader = csv.reader(csv_file)
            for line in csv_reader:
                for encoded_and_iv in line:
                    decoded_record = decode_and_decrypt(encoded_and_iv, key)
                    records.append(decoded_record)
    return records


def write_record_to_csv(file_path, record, key):
    records = read_records_from_csv(file_path, key)
    records.append(record)

    with open(file_path, "a", newline="") as csv_file:
        csv_writer = csv.writer(csv_file)
        for record_line in records:
            encoded_and_iv = encrypt_and_encode(record_line, key)
            csv_writer.writerow([encoded_and_iv])


def main():
    # Example key (for demonstration purposes, use a secure key in a real scenario)
    part_key = b"ThisIsASecretKey"
    salt = b"ThisIsASalt"
    key = pad(part_key + salt, AES.block_size)

    print(key)
    print(type(key))
    print(key.decode("utf-8"))
    print(len(key))

    # Read existing records from CSV
    records = read_records_from_csv("test.csv", key)

    # Example record
    record_name = input("Enter record name: ")
    record_user_name = input("Enter record username: ")
    record_password = input("Enter record password: ")
    new_record = f"{record_name}\n{record_user_name}\n{record_password}"

    # Write new record to CSV
    write_record_to_csv("test.csv", new_record, key)

    # Output all records
    print("\nAll Records:")
    for i, record in enumerate(records, start=1):
        print(f"{i}. {record}")


main()
