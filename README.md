# splitwise
Just another expense sharing service

### To Build the project
> mvn clean install
### To Run the project
> java -jar target/splitwise-0.0.1-SNAPSHOT.jar

## Notes from the dev
**Rushed up to this point from start last 5 hours. Since all stable cases are working, I'm submitting the code now. I'll still work on code clean up and good to have features.** 
1. It runs with an in-memory H2 DB. So with the basic maven configurations, the project should be working fine. 
2. I'll work on dockerising it in sometime. But hey, that's why it's in mem db now. 
3. The sample APIs are all mentioned below. 
4. I started to play with groups but I'm focussing more on what needs to be delivered. 
5. I'll also add a swagger doc over the time
6. As for unit tests, I started writing for get all users, but time was not on my side. Maybe I'll add them later on.
## Accessing the db
After the service is up, go to
>http://localhost:8080/h2-console/ 
I have'nt changed anything else so a direct login with the defaut creds(uname: sa) should work fine.

Maybe I'll add screenshots as well. That may be helpful I guess. But it's fairly straight forward.

## Test cases
Considering that the service is running in your local at 8080, here are the curl commands for testing.
### Get all users 
>curl --location --request GET 'http://localhost:8080/user/all'
### Add an equal expense
1. The user given in the header will be used and not the addedBy field. I'll remove it in the next release. 
2. The users are not validated. So if you give an invalid user, the id gets stored but upon retrieval, it will show null.
3. The splitTypes are EQUAL, PERCENT, MANUAL with validations along with it. I'll add examples for all 3.
>curl --location --request POST 'http://localhost:8080/expense' \
--header 'user: U1' \
--header 'Content-Type: application/json' \
--data-raw '{
"addedBy": "vamm",
"label": "pizza",
"description": "yummy",
"paidBySplitType": "MANUAL",
"paidByUserInfo": "2",
"paidAmounts": "1000",
"owedBySplitType": "EQUAL",
"owedByUserInfo": "1 2 3 4 5",
"owedAmounts": "",
"totalAmount": 1000
}'

>curl --location --request POST 'http://localhost:8080/expense' \
--header 'user: U1' \
--header 'Content-Type: application/json' \
--data-raw '{
"addedBy": "vamm",
"label": "asdf",
"description": "asdf",
"paidBySplitType": "MANUAL",
"paidByUserInfo": "2 3",
"paidAmounts": "750 250",
"owedBySplitType": "PERCENT",
"owedByUserInfo": "1 2 3 4 57",
"owedAmounts": "30 30 30 5 5",
"totalAmount": 1000
}'

>curl --location --request POST 'http://localhost:8080/expense' \
--header 'user: U1' \
--header 'Content-Type: application/json' \
--data-raw '{
"addedBy": "vamm",
"label": "asdf",
"description": "asdf",
"paidBySplitType": "MANUAL",
"paidByUserInfo": "1 3",
"paidAmounts": "750 250",
"owedBySplitType": "MANUAL",
"owedByUserInfo": "1 2 3 4 5",
"owedAmounts": "300 300 200 100 100",
"totalAmount": 1000
}'

### Expense per user
>curl --location --request GET 'http://localhost:8080/expense/all' \
--header 'user: 2'

### Balance per user
>curl --location --request GET 'http://localhost:8080/balance/user' \
--header 'user: 2'

### Overall balance for a user
>curl --location --request GET 'http://localhost:8080/balance/all' \
--header 'user: 1'