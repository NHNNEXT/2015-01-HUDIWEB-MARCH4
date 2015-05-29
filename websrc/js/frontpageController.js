(function() {
	'use strict';
	march4.app.registerController('frontpageController', function($scope, $http, $location) {
		$scope.user ={};
        $scope.signout = signout;
        
        $http.get('/goggg/').success(function(response){
        	$scope.user.email = response;
        })
       
        function signout(){
        	//
        } 
	});
}());