# Intercom code test

A code challenge sent by Intercom for the hiring process.

## Specification

We have some customer records in a text file (customers.json) -- one customer per line, JSON-encoded. We want to invite any customer within 100km of our Dublin office for some food and drinks on us. Write a program that will read the full list of customers and output the names and user ids of matching customers (within 100km), sorted by User ID (ascending).

* You can use the first formula from [this Wikipedia article](https://en.wikipedia.org/wiki/Great-circle_distance) to calculate distance. Don't forget, you'll need to convert degrees to radians.
* The GPS coordinates for our Dublin office are 53.339428, -6.257664.
* You can find the Customer list [here](https://gist.github.com/brianw/19896c50afa89ad4dec3).