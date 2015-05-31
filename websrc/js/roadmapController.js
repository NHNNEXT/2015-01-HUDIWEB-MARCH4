(function(){
    var Sortable = march4.util.Sortable;

    march4.app.registerController('roadmapController', function($http, $scope, $routeParams, $q) {
        $scope.lastOrder = 0;
        $scope.quests = [];
        $scope.path = '/api' + window.location.pathname;
        
        $scope.initQuest = function() {
            console.log('init', $scope.lastOrder);
            $scope.newQuest = {
                order: ++($scope.lastOrder)
            };
        };
        
        $scope.updateQuests = function(data) {
        	$scope.quests = data;
        	$scope.lastOrder = parseInt(data[data.length - 1].order);
        	if(typeof($scope.lastOrder) !== 'number') $scope.lastOrder = 0;
        	console.log('update last order', $scope.lastOrder);
        };
        
        $scope.addQuest = function() {
            console.log($scope.newQuest);
            $scope.quests.push($scope.newQuest);
            var data = $scope.newQuest;
            $http.post($scope.path, data).success(function(data, status, headers, config) {
                console.log("post good", status, "!");
                console.log(data);
                $scope.updateQuests(data);
                $scope.initQuest();
            }).error(function(data, status, headers, config) {
                console.log("post bad", status, "!");
                console.log(data);
                debugger;
            });
        };
        
        $scope.getQuests = function() {
            console.log('getting quests');
            $http.get($scope.path).success(function(data, status, headers, config) {
                console.log("get good", status, "!");
                console.log(data);
                $scope.updateQuests(data);
                $scope.initQuest();
            }).error(function(data, status, headers, config) {
                console.log("get bad", status, "!");
                console.log(data);
                debugger;
            });
        };
        
        $scope.init = function() {
            $scope.getQuests();
        };
        
        $scope.insertBefore = function(movingIdx, nextIdx) {
        	console.log('insert before path');
        	var path = $scope.path+"/"+movingIdx+"/movetobefore?qId="+nextIdx;
        	console.log(path);
        	$http.put(path).success(function(data, status, headers, config) {
                console.log("insert success");
                console.log(data);
                $scope.updateQuests(data);
            }).error(function(data, status, headers, config) {
            	console.log("insert fail");
            });
        };
        
        $scope.makeItSortable = function(el) {
            new Sortable(el, function(movingEl, nextEl){
            	function getqId(element) {
            		var scope = angular.element(element).scope();
            		return (scope)? scope.quest.qId : 0;
            	}
            	console.log('hi');
            	$scope.insertBefore(getqId(movingEl), getqId(nextEl));
            });
        };
        $scope.init();
    });
})();