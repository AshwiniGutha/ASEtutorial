/**
 * Created by Random on 10/31/2016.
 */
var loginapp = angular.module("login",[]);
loginapp.controller('LoginController',['$scope','$location','$http', function ($scope, $location,$http) {
    $scope.login = function (username, password) {
        console.log(username);
        var url = "http://localhost:8080/LabAssignment7/assignment/getuser";
        //Read the database to check for the user.
        var res = $http.get(url+"?name="+username+"&password="+password);

        res.success(function(result, status, headers, config) {
            if (result.length = 1) {
                userData = result[0];//contains the details of the logged in user.
                $window.location = 'BmiCalculator.html';
            } else {
                alert('Sorry, check your credentials.')
            }
        });
        res.error(function(result, status, headers, config) {
            console.log(result);
        });

    }
}])