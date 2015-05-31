(function(){
	function jquerythings() {

		jQuery('main').ready(function($) {
			console.log('jquery ready');
			$('.sortableContainer').on('click', '[name=delete]', function(e) {
				e.stopPropagation();
				event.preventDefault();
				debugger;
				angular.element(e.target).scope().deleteQuest();
			});
		});
	}
	
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
        
        $scope.deleteQuest = function(deleteQuestElement) {
        	console.log('hohoho'+deleteQuestElement);
        	debugger;
        	var path = $scope.path+"/"+$scope.getqId(deleteQuestElement);
        	$http.delete(path).success(function(data, status, headers, config) {
        		console.log("delete good", status, "!");
        		console.log(data);
        		$scope.updateQuests(data);
        		debugger;
        		$scope.initQuest();
        		$scope.quests.pop($scope.newQuest);
        	}).error(function(data, status, headers, config) {
        		console.log("delete bad", status, "!");
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
            	$scope.insertBefore($scope.getqId(movingEl), $scope.getqId(nextEl));
            });
        };
        
        $scope.getqId = function(element) {
        	var scope = angular.element(element).scope();
    		return (scope)? scope.quest.qId : 0;
        };
        
        $scope.init();
        $scope.$on('$viewContentLoaded', jquerythings);
    });
})();