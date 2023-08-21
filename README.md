# Authentication-FinanceApp
Learning how to safely store passwords in a database. 

Any application that requires the use of passwords for authentication needs to
be able to securely store these passwords. As such it is important to know which methods are best.

## Notes
1. Never store passwords in plaintext.
2. Don's use encryption methods that can be decrypted.
3. Use a recommended hashing algorithm.
4. Never store the actual password, store the hashed password instead.
5. Add salt to passwords (salts - random strings added to a users password before hashing to increase security).Store the salt with the hashed password.
6. Use "slow" hashing functions/algorithms. They increase the time taken to make a password but, more importantly, increase the time taken to break a password when using attacks like brute force.
7. Use the same hashing algorithm for password storing and for password validation. So you can compare hashed passwords for authentication.

## Goal

Creating a simple login and signup page that makes use of hashing algorithms to create 
a password, storing this password in a database during signup and eventually retrieving it for login authentication.
