import csv


class Accounts:
    def __init__(self, username, password) -> None:
        self.username = username
        self.password = password
        self.logged_in = False

    def __str__(self) -> str:
        return f"Username: {self.username} Password: {self.password}"


def main():
    global done
    print("Welcome to my password database program!")
    done = True
    account_list = []
    with open("accounts.csv", "r", encoding="utf-8") as file:
        for line in file:
            line = line.strip("\n")
            line_list = line.split(",")
            account_list.append(Accounts(line_list[0], line_list[1]))
    while done == True:
        choice = input("\n1. Create account \n2. Log in \n3. Exit program\n")
        if choice == "1":
            while True:
                username = input("Username:")
                password = input("Password:")
                account = Accounts(username, password)

                name_taken = False

                for existing_account in account_list:
                    if existing_account.username == username:
                        print("Username is alredy taken try a diffrent one")
                        name_taken = True
                        break
                if name_taken == False:
                    account_list.append(account)
                    add_to_file = [account.username, account.password]
                    with open("accounts.csv", "a", newline="", encoding="utf8") as file:
                        writer = csv.writer(file)
                        writer.writerow(add_to_file)
                    break
        elif choice == "2":
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
        elif choice == "3":
            break


def password_managment(account):
    global done
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
            break


main()
