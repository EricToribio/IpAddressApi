To run this api you will need MySQL workbench. Go into the application.properties and change it to your mySQL username and password.
I used postman to test all the routes. If you go to the body tab select raw and change the dropdrown to JSON.
ie {
"ip" : "10.0.0.1/24"
}
for the CIDR block
and 
{
"ip" : "10.0.0.2"
}
for acquiring and releasing Ip addresses.
