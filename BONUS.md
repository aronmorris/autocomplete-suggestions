#API KEYS & BILLING
Microservices should follow the principle of single responsibility. So if this microservice for autocompletion 
suggestions does this, it should also be determining authentication for the purposes of logging API uses.

A possible solution to resolve this is to create an API gateway. A user is able to access the microservice they want
by first providing authentication to the gateway. The gateway can then forward the token to an authentication service,
and if OK'd, log the user and the API service they're requesting to a billing service that can register the 
transaction.


##Scaling
The above approach appears to scale well. The concept of the load balancer is mature, and it can be augmented with API
gateway behavior. The API gateway is stateless and its operations are not resource-heavy.
The number of requests is temporarily increased within the network (authentication, billing, resource)
but each microservice can be on wholly different servers, adding resiliency.

##Model
The timeline of use can be as follows:

1. User requests authorization for a resource.
2. The API gateway redirects them to the authorization service.
3. The authorization server passes the user a JWT directly.
4. The user passes the JWT to the API gateway as part of the resource request.
5. The API gateway checks authentication with the authentication service.
6. If accepted, the API gateway forwards the user's resource request.
7. The API gateway logs the user and resource request to the billing service.
   1. As a note, the billing service can now bill differently based on what the user is requesting.
8. The resource microservice directly passes the resource to the user.