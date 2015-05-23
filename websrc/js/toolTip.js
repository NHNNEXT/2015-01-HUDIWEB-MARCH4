(function() {
	'use strict';
	march4.app.factory('ToolTip', function($rootScope) {
        var service = {};

        service.Success = Success;
        service.Error = Error;

        initService();

        return service;

        function initService() {
            $rootScope.$on('$locationChangeStart', function () {
                clearFlashMessage();
            });

            function clearFlashMessage() {
                var toolTip = $rootScope.toolTip;
                if (toolTip) {
                    if (!toolTip.keepAfterLocationChange) {
                        delete $rootScope.toolTip;
                    } else {
                        // only keep for a single location change
                    	toolTip.keepAfterLocationChange = false;
                    }
                }
            }
        }

        function Success(message, keepAfterLocationChange) {
            $rootScope.toolTip = {
                message: message,
                type: 'success', 
                keepAfterLocationChange: keepAfterLocationChange
            };
        }

        function Error(message, keepAfterLocationChange) {
            $rootScope.toolTip = {
                message: message,
                type: 'error',
                keepAfterLocationChange: keepAfterLocationChange
            };
        }
        
      
	});
}());
