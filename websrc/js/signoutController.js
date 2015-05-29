(function() {
	'use strict';
	march4.app.registerController('signoutController', function($location, $rootScope, UserService) {
		UserService.signout()
        .then(function(){
            $rootScope.getUser();
            $location.path("/")    
        });
	});
}());
