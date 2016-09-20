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
                    console.log("About to add this item to the todo list" + JSON.stringify($scope.newTodoItem));

                    $http.post("/todos.json", $scope.newTodoItem)
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

        $scope.newTodoItem = {};

    });
