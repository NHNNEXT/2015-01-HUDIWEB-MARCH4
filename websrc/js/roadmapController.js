(function(){
    var Sortable = march4.util.Sortable;

    march4.app.registerController('roadmapController', function($http, $scope, $routeParams) {
        $scope.lastOrder = 0;
        $scope.quests = [];
        $scope.path = '/api' + window.location.pathname;
        $scope.initQuests = function() {
            console.log('init', $scope.lastOrder);
            $scope.newQuest = {
                order: ++($scope.lastOrder)
            };
        };
        $scope.addQuest = function() {
            console.log($scope.newQuest);
            $scope.quests.push($scope.newQuest);
            var data = $scope.newQuest;
            $http.post($scope.path, data).success(function(data, status, headers, config) {
                console.log("post good", status, "!");
                console.log(data);
                $scope.showQuests();
            }).error(function(data, status, headers, config) {
                console.log("post bad", status, "!");
                console.log(data);
            });
            $scope.initQuests();
        };
        $scope.showQuests = function() {
            console.log('getting quests');
            $http.get($scope.path).success(function(data, status, headers, config) {
                console.log("get good", status, "!");
                console.log(data);
                $scope.quests = data;
                $scope.lastOrder = parseInt(data[data.length - 1].order);
                if(typeof($scope.lastOrder) !== 'number') $scope.lastOrder = 0;
                console.log('show', $scope.lastOrder);
                $scope.initQuests();
            }).error(function(data, status, headers, config) {
                console.log("get bad", status, "!");
                console.log(data);
                $scope.initQuests();
            });
        };
        $scope.init = function() {
        	$scope.initQuests();
            $scope.showQuests();
        };
        $scope.makeItSortable = function(el) {
            new Sortable(el, function(nFrom, nTo) {
            	
            });
        };
        $scope.init();
    });
})();