import csv
import sys
from base64 import *
from Crypto.Util.Padding import pad, unpad
from Crypto.Cipher import AES
import hashlib

current_account_records = []
local_record_data = {}


class Accounts:
    def __init__(self, username, password, account_id) -> None:
        self.username = username
        self.password = password
        self.account_id = account_id

    def __str__(self) -> str:
        return f"Username: {self.username} Password: {self.password}"


def password_managment(account):
    print(f"Welcome {account.password}!")
    done = True
    global current_account_records
    local_record_data.clear()
    current_account_records.clear()
    import_records_from_file()
    current_account_records = local_record_data.get(account.account_id)

    if current_account_records == False:
        current_account_records = []

    while done == True:
        choice = input(
            "\n1. Create stored password \n2. View passwords\n3. Log out \n4. Exit program and log out\n"
        )
        if choice == "1":
            create_record(account)
            local_record_data[account.account_id] = current_account_records
        elif choice == "2":
            print("\nViewing passwords coming soon")
            print(current_account_records)
            decrypt_record(
                current_account_records[1],
                generate_key(account.password, account.account_id),
            )
        elif choice == "3":
            print("\nLogged out!")
            account.logged_in = False
            save_records_to_file()
            break
        elif choice == "4":
            print("\nLogged out! See you next time!\n")
            account.logged_in = False
            done = False
            sys.exit()


def create_account(account_list):
    while True:
        username = input("Username:")
        password = input("Password:")
        account_id = len(account_list) + 1
        account = Accounts(username, password, account_id)

        name_taken = False

        for existing_account in account_list:
            if existing_account.username == username:
                print("Username is alredy taken try a diffrent one")
                name_taken = True
                break
        if name_taken == False:
            account_list.append(account)
            add_to_file = [account.username, account.password, account.account_id]
            with open("accounts.csv", "a", newline="", encoding="utf8") as file:
                writer = csv.writer(file)
                writer.writerow(add_to_file)
            break


def login(account_list):
    username = input("Username:")
    password = input("Password:")
    logged_in = False
    for account in account_list:
        if account.username == username and account.password == password:
            account.logged_in = True
            logged_in_account = account
            logged_in = True
            break
        else:
            account.logged_in = False

    if logged_in == True:
        password_managment(account)
    else:
        print("Log in failed!")


def create_record(logged_in_account):
    global current_account_records
    print(current_account_records)
    record_name = input("Record name:")
    record_username = input("Username:")
    record_password = input("Password:")
    record = f"{record_name}\n{record_username}\n{record_password}"
    encrypted_record = encrypt_record(
        record,
        key=generate_key(logged_in_account.password, logged_in_account.account_id),
    )
    print(encrypted_record, "encrypted record is it one or 2?")

    print(current_account_records)
    if current_account_records:
        current_account_records.append(encrypted_record)
    else:
        current_account_records = [int(logged_in_account.account_id), encrypted_record]


def save_records_to_file():
    global current_account_records
    try:
        test_id = str(current_account_records[0])
        if test_id not in local_record_data:
            local_record_data[current_account_records[0]] = current_account_records
            print(local_record_data)
            print(current_account_records)
        else:
            pass
    except Exception as e:
        print("error nothing works kill me now")
    if current_account_records:
        with open("records.csv", "w", newline="", encoding="utf8") as writer:
            if local_record_data:
                first_account = True
                for account_id, records in local_record_data.items():
                    if first_account == False:
                        writer.write("\n")
                    else:
                        first_account = False
                    if records:
                        for record in records:
                            # cant have duplicate records in the same account as .index will only return the first index of a matching record
                            print(record)
                            if records.index(record) == len(records) - 1:
                                writer.write(record)
                            else:
                                writer.write(str(record) + ",")


def import_records_from_file():
    with open("records.csv", "r", encoding="utf8") as file:
        csv_reader = csv.reader(file)
        for line in csv_reader:
            print(line, "the line")
            local_record_data[line[0]] = line


def encrypt_record(record, key):
    record = bytes(record, "utf-8")
    ciper = AES.new(key, AES.MODE_ECB)
    ciper_text = ciper.encrypt(pad(record, AES.block_size))
    encoded = b64encode(ciper_text).decode("utf-8")
    return f"{encoded}"


def generate_key(password, salt):
    byt_password = bytes(password, "utf-8")
    byt_salt = bytes(salt, "utf-8")
    key = pad(byt_password + byt_salt, AES.block_size)
    return key


def decrypt_record(record, key):
    print(record)
    encoded = record
    ciper = AES.new(key, AES.MODE_ECB)
    encoded = b64decode(encoded)
    decrypted = unpad(ciper.decrypt(encoded), AES.block_size).decode("utf-8")
    print(decrypted)
    return decrypted
