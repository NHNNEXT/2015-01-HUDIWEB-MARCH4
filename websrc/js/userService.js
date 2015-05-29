(function() {
	'use strict';
	march4.app.factory('UserService', function($http) {
		var service = {};
		service.GetByUser = GetByUser;
		service.Create = Create;
		// service.Delete = Delete;

		return service;

		function GetByUser(user) {
			return $http.post('/users/signin', user).then(handleSuccess,
					handleError('Error getting user by email'));
		}

		function Create(user) {
			return $http.post('/users/', user).then(handleSuccess,
					handleError('Error creating user'));
		}

		/*
		 * function Delete(id) { return $http.delete('' +
		 * user.id).then(handleSuccess, handleError('Error deleting user')); }
		 *  // private functions
		 */
		function handleSuccess(data) {
			return data;
		}

		function handleError(error) {
			return function() {
				return {
					success : false,
					message : error
				};
			};
		}
	});
}());
