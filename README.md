## Base URL

The base URL for all API requests is:

`https://localhost:8083/api`

Endpoints
### `POST /client/create`
 endpoint is used to create a new client. The request should include the client's name, password, details, and account information.
 
### Parameters (json)
- `name` (required):  The name of the client.
- `password` (required): The password for the client's account.
- `details` (optional): Additional details about the client.
- `account` (required): Account information for the client.  ---->  "Silver" , "Gold", "Platinum"
  
### Response

Returns a JSON object with the following properties:
  - `id`: The unique identifier of the client.
  - `name`: 
  - `password`:
  - `details`: 
  - `account`: 

### `GET /client/login`
The endpoint performs an HTTP GET request to log in a client with the provided name and password.

Query Params
- `name` (required):  The name of the client.
- `password` (required): The password for the client's account.
- 
### Response
Returns a JSON object with the following properties:
  - `id`: The unique identifier of the client.
  - `name`: 
  - `password`:
  - `details`: 
  - `account`: 


### `POST /link/create`
This endpoint allows the creation of a new link.

### Parameters (json)
- `name` (string, required):  The name of the link.
- `status` (boolean, required): The status of the link.
### Body (json)
{
    "name" : "Chatmate",
    "status" : true
}

### `GET /client/links`
The endpoint retrieves client links based on the provided client ID.

Path Variables
- `clientId` (required):  id of the client.


### `POST /client/activateLink`

This endpoint triggers the activation of a client link.

Query Params
- `clientId` (required):  
- `linkId` (required):

### `POST /client/deactivateLink`

This endpoint triggers the deactivation of a client link.

Query Params
- `clientId` (required):  
- `linkId` (required):

### `DELETE /client/delete`

This endpoint is used to delete a client.

Query Params
- `name` (required):  The name of the client.
- `password` (required): The password for the client's account.
