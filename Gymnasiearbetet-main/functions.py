import csv
import sys


class Accounts:
    def __init__(self, username, password, account_id) -> None:
        self.username = username
        self.password = password
        self.account_id = account_id

    def __str__(self) -> str:
        return f"Username: {self.username} Password: {self.password}"


def password_managment(account):
    done = True
    while done == True:
        choice = input(
            "\n1. Create stored password \n2. View passwords\n3. Log out \n4. Exit program and log out\n"
        )
        if choice == "1":
            print("\nCreating password coming soon")
        elif choice == "2":
            print("\nViewing passwords coming soon")
        elif choice == "3":
            print("\nLogged out!")
            account.logged_in = False
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
            logged_in = True
            break
        else:
            account.logged_in = False

    if logged_in == True:
        password_managment(account)
    else:
        print("Log in failed!")
