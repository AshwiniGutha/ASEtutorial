// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
angular.module('app', ['ionic','ngCordova','app.controllers', 'app.routes', 'app.directives','app.services','ngCordovaOauth'])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.plugins.Keyboard.disableScroll(true);
    }
    if (window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleDefault();
    }
  });
})



.config(function($stateProvider,$urlRouterProvider){
	$stateProvider

	.state('login', {
		url: "/login",
		templateUrl: "templates/searchApp.html",
		controller: 'searchAppCtrl'
	})
    
    .state('register', {
		url: "/register",
		templateUrl: "templates/register.html",
		controller: 'registerCtrl'
	})
    
    
     .state('home', {
    url: "/home",
    templateUrl: "templates/home.html",
    controller:"homeCtrl"    
  });
    
    	
$urlRouterProvider
    
    .otherwise('/login');
  .service("ContactsService", ['$q', function($q) {

        var formatContact = function(contact) {

            return {
                "displayName"   : contact.name.formatted || contact.name.givenName + " " + contact.name.familyName || "Mystery Person",
                "emails"        : contact.emails || [],
                "phones"        : contact.phoneNumbers || [],
                "photos"        : contact.photos || []
            };

        };

        var pickContact = function() {

            var deferred = $q.defer();

            if(navigator && navigator.contacts) {

                navigator.contacts.pickContact(function(contact){

                    deferred.resolve( formatContact(contact) );
                });

            } else {
                deferred.reject("Bummer.  No contacts in desktop browser");
            }

            return deferred.promise;
        };

        return {
            pickContact : pickContact
        };
    }])

    .controller("AppCtrl", ['$scope', 'ContactsService', function($scope, ContactsService) {

        $scope.data = {
            selectedContacts : []
        };

        $scope.pickContact = function() {

            ContactsService.pickContact().then(
                function(contact) {
                    $scope.data.selectedContacts.push(contact);
                    console.log("Selected contacts=");
                    console.log($scope.data.selectedContacts);

                },
                function(failure) {
                    console.log("Bummer.  Failed to pick a contact");
                }
            );

        }

});