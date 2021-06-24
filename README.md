# Currency_Converter

A web service that allows you to calculate the amount after currency conversion.

Functional requirements
1. The system allows you to convert any positive amount from one currency to another;
2. The following currencies are supported: PLN, EUR, USD, GBP;
3. The base currency of the system is PLN. The sale and purchase of a foreign currency are made in relation to the base currency. 
  Converting an amount in a foreign currency to another foreign currency is done as the sale of one currency and the purchase of another;
4. The system uses the table for buying and selling foreign currencies provided by the National Bank of Poland (Table C). 
  Documentation is available at: http://api.nbp.pl/; 
5. The system calculates a commission for each sale and purchase operation in the amount of 2%.

Non-functional requirements
1. REST style web service (no frontend);
2. The application uses the Spring Boot framework;
3. Application built with Maven for JAR artifact;
