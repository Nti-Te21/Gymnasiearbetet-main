from base64 import *


def list_to_base64(input_list):
    # Convert the list to bytes
    byte_data = bytes(str(input_list), "utf-8")

    # Encode the bytes to base64
    base64_data = b64encode(byte_data)

    return base64_data.decode("utf-8")


def base64_to_list(base64_string):
    # Decode the base64 string to bytes
    byte_data = b64decode(base64_string)

    # Convert the bytes to a string and evaluate as a Python expression to get the original list
    decoded_list = eval(byte_data.decode("utf-8"))

    return decoded_list


# Example list
my_list = ["minecraft", "a", "a"]

# Convert the list to base64
encoded_result = list_to_base64(my_list)

# Decode the base64 back to the list
decoded_result = base64_to_list(encoded_result)

# Print the results
print("Original List:", my_list)
print("Base64 Encoding:", encoded_result)
print("Record name:", decoded_result[0])
print("User name:", decoded_result[1])
print("password:", decoded_result[2])
