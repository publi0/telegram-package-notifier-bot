
# Package Notifier Bot

Receve information about your packages with this telegram bot!

## Demo

1. Starting the bot

![image](https://user-images.githubusercontent.com/14155185/144717118-93643824-ebe9-4c06-be09-478018a114f5.png)

2. Choose an option
 
 ![image](https://user-images.githubusercontent.com/14155185/144717142-425fd5f1-8a91-42e4-8044-bbe9aef71b1b.png)

3. Add an package

![image](https://user-images.githubusercontent.com/14155185/144717171-eb70858a-d790-4933-b9e9-12c6c089e72d.png)
![image](https://user-images.githubusercontent.com/14155185/144717186-911529e5-dca9-4c6c-bfbd-b5c1b3d392a9.png)

4. My Packages
![image](https://user-images.githubusercontent.com/14155185/144717219-cd5e51ca-53b4-44af-b51c-8134d8a6f951.png)

5. Package Info
![image](https://user-images.githubusercontent.com/14155185/144717229-6e60b2f1-a287-4d97-9860-eaacaa14298a.png)

## Stack 

- Java 17
- Spring Boot
- Spring Security
- Auth0
- MongoDB
- RabbitMQ
- TelegramBot

## Shippiment Companies

### How is structure?

![image](https://user-images.githubusercontent.com/14155185/144717344-3632b98b-83d1-4fe3-92f2-9f41d7ca26c0.png)

- Companies are structured in a Strategy model

![image](https://user-images.githubusercontent.com/14155185/144717409-1c6f3c2c-7b8b-462d-946d-53363eeee3b6.png)
Shipping Company interface 

![image](https://user-images.githubusercontent.com/14155185/144717432-f0c58b91-67cf-474c-afc2-3f67e5abd8de.png)
Factory for finding correct shippiment company

![image](https://user-images.githubusercontent.com/14155185/144717455-1f9d0d8a-7caa-460f-a9cd-a70b320aad49.png)
Example of usage

### How to add a new company?

1. Implement this interface 
 ![image](https://user-images.githubusercontent.com/14155185/144717475-b0664a18-89a2-4470-9b71-f34c8ba1de16.png)
2. Create a enum for the company
 ![image](https://user-images.githubusercontent.com/14155185/144717488-f06c4888-18ae-42ec-beef-81a8a2dc3694.png)
3. If you need to add some variables this is the place
![image](https://user-images.githubusercontent.com/14155185/144717512-1cbb0836-74d2-4094-b426-cfb45a36b172.png)

Its done! You have a new company!

### How I get the updastes?

1. A scheduler will trigger a method that find all actives packages in database(mongoDB) and for each is search if has new updates
2. If there is a new update they will be save in database and a message will be send to the user in telegram

## Endpoints for direct updates - API Reference

#### Get all tracking events

```http
  GET /api/tracking/{company}/{trackingNumber}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `Path: company` | `string` | **Required**. Shippiment company |
| `Path: trackingNumber` | `string` | **Required**. Track id |
| `Header: Authorization` | `string` | **Required**. Authorization |

#### Get latest tracking event

```http
  GET /api/tracking/{company}/{trackingNumber}/latest
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `Path: company` | `string` | **Required**. Shippiment company |
| `Path: trackingNumber` | `string` | **Required**. Track id |
| `Header: Authorization` | `string` | **Required**. Authorization |
