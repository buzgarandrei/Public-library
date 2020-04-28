A back-end that implements a Public Library system, where clients can Search for books, Make Appointments, asks for some books to be seen in the library, return books and get an amount to Pay Back if they return late.
#
Bellow is a json copy that, open Postman, look for Import option, and there Paste Row Text. It will generate the http requests of this backend.
#
{
	"info": {
		"_postman_id": "3694d44e-557d-4101-9f59-ffd920f97007",
		"name": "Library-Backend",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
	},
	"item": [
		{
			"name": "Populate DB",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/populate",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"populate"
					]
				}
			},
			"response": []
		},
		{
			"name": "Search Book By a Keyword",
			"protocolProfileBehavior": {
				"disableBodyPruning": true
			},
			"request": {
				"method": "GET",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{ \"keyword\" : \"d\" }"
				},
				"url": {
					"raw": "http://localhost:8080/search",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"search"
					]
				}
			},
			"response": []
		},
		{
			"name": "Loan Book",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{ \n\"idUser\" : \"1\",\n\"idBook\" : \"3\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/loanBook",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"loanBook"
					]
				}
			},
			"response": []
		},
		{
			"name": "Return Book",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{ \n\"idUser\" : \"1\",\n\"idBook\" : \"3\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/loanBook",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"loanBook"
					]
				}
			},
			"response": []
		},
		{
			"name": "Login",
			"protocolProfileBehavior": {
				"disableBodyPruning": true
			},
			"request": {
				"method": "GET",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n\t\"email\" : \"buzgarandrei@gmail.com\",\n\t\"password\" : \"123\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/login",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"login"
					]
				}
			},
			"response": []
		},
		{
			"name": "Logout",
			"request": {
				"method": "GET",
				"header": [
					{
						"key": "Content-Type",
						"type": "text",
						"value": "application/json",
						"disabled": true
					},
					{
						"key": "TOKEN",
						"value": "099510ae1ac24f54a059fc57c7248e13",
						"type": "text"
					}
				],
				"url": {
					"raw": "http://localhost:8080/logout",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"logout"
					]
				}
			},
			"response": []
		},
		{
			"name": "Get All Users",
			"request": {
				"method": "GET",
				"header": [
					{
						"key": "TOKEN",
						"value": "1e8abfd359c142adbae8a058786b9841",
						"type": "text"
					}
				],
				"url": {
					"raw": "http://localhost:8080/users",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"users"
					]
				}
			},
			"response": []
		},
		{
			"name": "Get All Books Read By A User",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": ""
				}
			},
			"response": []
		},
		{
			"name": "Get Active Loans Of A User",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": ""
				}
			},
			"response": []
		},
		{
			"name": "History  Issue Requests Of A User",
			"protocolProfileBehavior": {
				"disableBodyPruning": true,
				"disabledSystemHeaders": {
					"connection": true,
					"accept-encoding": true,
					"accept": true,
					"host": true,
					"user-agent": true
				}
			},
			"request": {
				"method": "GET",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\t\n\t\"id\" : \"1\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/getIssueHistory",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"getIssueHistory"
					]
				}
			},
			"response": []
		},
		{
			"name": "Add A User",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n\t\"firstName\" : \"Vasile\",\n\n    \"lastName\" : \"Coman\",\n\n    \"phoneNumber\" : \"0845937983\",\n\n    \"email\" : \"coman.vasile@gmail.com\",\n\n    \"password\" : \"125\",\n\n    \"address\" : \"Zorilor, nr.98, ap.22, bloc V3\",\n\n    \"roleEnum\" : \"LIBRARIAN\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/addUser",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"addUser"
					]
				}
			},
			"response": []
		},
		{
			"name": "Update User",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n\t\"id\" : \"4\",\n\t\n\t\"firstName\" : \"Vasileee\",\n\n    \"lastName\" : \"Coman\",\n\n    \"phoneNumber\" : \"0845937983\",\n\n    \"email\" : \"coman.vasileee@gmail.com\",\n\n    \"password\" : \"125\",\n\n    \"address\" : \"Zorilor, nr.98, ap.22, bloc V3\",\n\n    \"roleEnum\" : \"LIBRARIAN\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/updateUser",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"updateUser"
					]
				}
			},
			"response": []
		},
		{
			"name": "Delete User",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": ""
				}
			},
			"response": []
		},
		{
			"name": "Get List Of All Books",
			"request": {
				"method": "GET",
				"header": [
					{
						"key": "TOKEN",
						"value": "1edfe241e30343c29ed201cc26c1cecf",
						"type": "text"
					}
				],
				"url": {
					"raw": "http://localhost:8080/books",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"books"
					]
				}
			},
			"response": []
		},
		{
			"name": "Add Book",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\r\n        \"name\": \"Game of Thrones\",\r\n        \"author\": \"George R. R. Martin\",\r\n        \"publishingHouse\": \"RAO Publishing House\",\r\n        \"edition\": 2,\r\n        \"daysForLoaning\": 14,\r\n        \"pricePerDay\": 0.7,\r\n        \"available\": \"true\"\r\n    }"
				},
				"url": {
					"raw": "http://localhost:8080/addBook",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"addBook"
					]
				}
			},
			"response": []
		},
		{
			"name": "Update Book",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\r\n\t\t\"id\" : \"5\",\r\n        \"name\": \"Game of Thrones\",\r\n        \"author\": \"George R. R. Martin\",\r\n        \"publishingHouse\": \"RAO Publishing House\",\r\n        \"edition\": 2,\r\n        \"daysForLoaning\": 10,\r\n        \"pricePerDay\": 0.7,\r\n        \"available\": \"false\"\r\n    }"
				},
				"url": {
					"raw": "http://localhost:8080/updateBook",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"updateBook"
					]
				}
			},
			"response": []
		},
		{
			"name": "Delete Book",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n\t\"id\" : \"5\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/deleteBook",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"deleteBook"
					]
				}
			},
			"response": []
		},
		{
			"name": "Get List Of Book Issues",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "http://localhost:8080/getIssueBooks",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"getIssueBooks"
					]
				}
			},
			"response": []
		},
		{
			"name": "issueBook",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n\t\"name\" : \"Gay Narnia\",\n\t\"author\" : \"Gay Preda\",\n\t\"idUser\" : \"2\"\n}"
				},
				"url": {
					"raw": "http://localhost:8080/issueBook",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"issueBook"
					]
				}
			},
			"response": []
		},
		{
			"name": "Delete Book Issue",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": ""
				}
			},
			"response": []
		}
	],
	"protocolProfileBehavior": {}
}
