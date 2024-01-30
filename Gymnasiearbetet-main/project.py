from functions import *


def main():
    global done
    print("Welcome to my password database program!")
    done = True
    account_list = []
    with open("accounts.csv", "r", encoding="utf-8") as file:
        for line in file:
            line = line.strip("\n")
            line_list = line.split(",")
            account_list.append(Accounts(line_list[0], line_list[1], line_list[2]))
    while done == True:
        choice = input("1. Create account \n2. Log in \n3. Exit program\n")
        if choice == "1":
            create_account(account_list)
        elif choice == "2":
            login(account_list)
        elif choice == "3":
            break


main()
