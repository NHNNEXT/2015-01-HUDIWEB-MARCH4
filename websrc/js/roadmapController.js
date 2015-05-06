/* todo
* 무조건 insertBefore여서 맨 밑으로 안 들어가짐
* 삭제, 수정 구현
*/

jQuery(document).ready(function($) {
    $('.sortableContainer').on('mousedown', '.sortable', function(e) {
        e.stopPropagation();
        e.preventDefault();

        var initEl = $(this);
        var initElPos = getPosition(this);
        var initClickPos = {pageX:e.pageX, pageY:e.pageY};
        var overEl;

        $(document).on('mousemove.drag', function(e) {
            var deltaClickPos = {pageX:e.pageX-initClickPos.pageX,
                pageY:e.pageY-initClickPos.pageY};


            var result = getMouseoveredIndex(e, $('.sortable'), initEl[0]);
            if(result >= 0) {
                var currentOverEl = $('.sortable').eq(result);
                if(!currentOverEl.is(overEl)) {
                    overEl = currentOverEl;
                    // init
                    $(".sortable")
                        .filter(":lt("+result+")").not(initEl).css({"position":"relative", "top":"0"});
                    // push down
                    $(".sortable").filter(":eq("+result+"), :gt("+result+")")
                        .css({"position":"relative", "top":initEl.outerHeight()});
                }
            }


            initEl.css({"position": "relative",
            "top" : initElPos.top + deltaClickPos.pageY,
            "left": initElPos.left + deltaClickPos.pageX});
        });

        $(this).on('mouseup', function(e) {
            $(document).off('mousemove.drag');
            console.log(overEl);
            
            $(".sortable").css({"position":"relative", "top":"0"});
            $(this).insertBefore(overEl);
            $(this).css({"position": "relative",
            "top" : 0,
            "left": 0});
        });
    });

    // 일단 세로만.
    var getMouseoveredIndex = function(e, jList, self) {
        var eY = e.pageY;
        
        function getBound(el) {
            var top = el.offset().top;
            var down = top + el.outerHeight();
            return {top:top, down:down};
        }
        
        for(var i=0; i<jList.length; i++) {
            if(jList[i] === self) continue;
            var bound = getBound(jList.eq(i));
            if(bound.top <= eY && eY <= bound.down) {
                return i;
            }
        }
        return -1;
    };

    var getPosition = function(el) {
        var position = {
            top : parseInt($(el).css('top')) || 0,
            left : parseInt($(el).css('left')) || 0
        };
        return position;
    };
});

march4.app.registerController('roadmapController', function($http, $scope, $routeParams) {
	$scope.testdata = 50;
    $scope.quests = [];
    $scope.newQuest = {};
    $scope.path = window.location.pathname.split('/').slice(0,-1).join('/');

    $scope.addQuest = function() {
        console.log($scope.newQuest);
        $scope.quests.push($scope.newQuest);
        
        var data = $scope.newQuest;
        
        $http.post($scope.path, data)
        .success(function (data, status, headers, config) {
        	console.log("post good", status,"!");
            console.log(data);
            console.log(headers);
            console.log(config);
            $scope.showQuests();
        })
        .error(function (data, status, headers, config) {
        	console.log("post bad", status,"!");
            console.log(data);
            console.log(headers);
            console.log(config);
        });
        
        $scope.newQuest = {};
    };
    
    $scope.showQuests = function() {
    	console.log('getting quests');
    	$http.get($scope.path)
    	.success(function (data, status, headers, config) {
            console.log("get good", status,"!");
            console.log(data);
            console.log(headers);
            console.log(config);
        })
        .error(function (data, status, headers, config) {
        	console.log("get bad", status,"!");
            console.log(data);
            console.log(headers);
            console.log(config);
        });
    };
});

