#Task Manager Application

The project aims to build a task manager application using Apache maven and Java with MongoDB 

The task manager can perform the following tasks

ADD new tasks
UPDATE tasks
SEARCH For tasks using Task Name
DELETE Taks by ID
EXECUTE a task

The task execution results in printing the Time when the task was last executed and also executes a windows shell command which is put when creating the task

a sample task looks like the below

 {
    "id": "67c20c7bba276e08b84a414f",
    "name": "Samuel John",
    "owner": "risa john ",
    "command": "cmd /c Hey There!",
    "taskExecutions": [
      {
        "startTime": "2025-03-01T01:01:29.542",
        "endTime": "2025-03-01T01:01:30.542",
        "output": "Hey There!\""
      }
    ]
  }
]

POSTMAN API with all the endpoints ie DELETE, PUT, POST, DELETE Is used to check the execution of the tasks
The screen shots file will give all the images required for the project

Tools used
Java 22
Apache maven
Mongo DB
POSTMAN API 
