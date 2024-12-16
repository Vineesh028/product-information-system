

## Product Information System


![PIMnew](https://github.com/user-attachments/assets/86767ee3-735b-4fd6-b2a7-3f20f80515b8)

Services
1. Product source service
Provides the APIs to get the product details from various sources.
Receives the unstructured data and store it into MongoDB. This service also pushes the
data into Kafka which will be then handled by product data service.
Adapting outbox pattern for eventual consistency.
2. Data segregation Service
Receives product data from Kafka and converts it into structured data and saves it into
PostgresDB.
3. Product Information Service – with dashboard
Provides the APIs for modifying products, promotions and publishing changes to respective
Kafka topics.
Adapting outbox pattern for eventual consistency.
4. User portal service
Provides APIs to get products details to user.
It receives product information from product information service via Kafka and stores it in
PostgresDB. User portal receives product information through API call and messages from
service through websocket.
5. Marketing service
Provides APIs to get promotion data. It receives promotion information from product
information service via Kafka and stores it in PostgresDB.

Dashboard and User interfaces
1. Product information dashboard
User interface to change and publish product and promotion information
2. User portal App
For customer to access product information
3. Promotion portal
User interface to access promotion information.
