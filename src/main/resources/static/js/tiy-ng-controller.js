angular.module('TIYAngularToDoApp', [])
   .controller('SampleController', function($scope, $http) {

    $scope.testValue = "testing here";


        $scope.getAllToDos = function() {
               console.log("About to go get me some data!");


             $http.get("http://localhost:8080/todos.json") //asynch call
                   .then( //provides the callback
                        function successCallback(response) { //inside of the promise object then holds the 2 functions
                           console.log(response.data); //data is the json object as a javascript object
                           console.log("Adding data to scope");
                           $scope.todos = response.data;
                        },
                        function errorCallback(response) {
                           console.log("Unable to get data");
                        });
                        console.log("Done with the promise - waiting for the callback");
        };

        $scope.addToDo = function() {
               console.log("About to add this item to the todo list" + JSON.stringify($scope.newToDoItem));

               $http.post("/addToDo.json", $scope.newToDoItem)// this is the one and only connection between the front & back end
                   .then(
                       function successCallback(response) {
                           console.log(response.data);
                           console.log("Adding data to scope");
                           $scope.todos = response.data;
                      },
                      function errorCallback(response) {
                          console.log("Unable to get data");
                      });
        };

        $scope.toggleToDo = function(todoID) {
                    console.log("About to toggle todo with ID " + todoID);

                    $http.get("/toggleToDo.json?todoID=" + todoID)
                        .then(
                            function success(response) {
                                console.log(response.data);
                                console.log("Todo toggled");

                                $scope.todos = {};
                                alert("About to refresh the todos on the scope"); //pauses the execution of the js code

                                $scope.todos = response.data;
                            },
                            function error(response) {
                                console.log("Unable to toggle todo");
                            });
        };

        $scope.deleteToDo = function(todoID) {

                    $http.get("/deleteToDoItem?todoID=" + todoID)// this is the one and only connection between the front & back end
                        .then(
                            function successCallback(response) {
                                console.log(response.data);
                                console.log("Deleting data on scope");

                                $scope.todos = {};
                                alert("About to refresh the todos on the scope");

                                $scope.todos = response.data;
                            },
                            function errorCallback(response) {
                                console.log("Unable to delete data");
                            });
        };


        $scope.newToDoItem = {};

    });
