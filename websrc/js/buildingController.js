(function () {
    "use strict";

    march4.app.registerController('buildingController', function ($scope, $window, $http, $timeout, $routeParams, $location, $rootScope, QuestService) {
        $scope.host_uid = $rootScope.user.uId;
        $scope.panelOpened = ($routeParams.panel == "panel");
        $scope.pid = {};
        $scope.newData = {};

        $scope.floatingForm = {
            show: false
        };
        $scope.pageSet = {
            buildingBox: {
                x: 200,
                y: 200,
                margin: 20
            }
        };

        $scope.openPanel = function (pId) {
            if (pId === undefined) return;

            $routeParams.buildingId = pId;

            if (!$scope.panelOpened) {
                QuestService.getQuests(pId);
                //march4.util.setPathNoReloading($location.path().match(/(.*?)\/?$/)[1] + "/" + pId);
                $scope.panelOpened = true;
                $scope.panelID = pId;
            }
        };

        $scope.closePanel = function () {
            console.log(1);
            if ($scope.panelOpened) {
                //march4.util.setPathNoReloading($location.path().replace(/\/panel\/.*/g, ""));
                $scope.panelOpened = false;
            }
        };

        $scope.setPosition = function () {
            for (var i = 0; i < $(".buildingArea li").size(); i++) {
                $(".buildingArea li").eq(i).css("left", $scope.Buildings[i].posx + "px");
                $(".buildingArea li").eq(i).css("top", $scope.Buildings[i].posy + "px");
            }

            //            var col = parseInt($(".buildingArea").outerWidth(true) / ($scope.pageSet.buildingBox.x + $scope.pageSet.buildingBox.margin));
            //
            //            for (var i = 0; i < $(".buildingArea li").size(); i++) {
            //                var posX = parseInt(i % col) * $scope.pageSet.buildingBox.x + $scope.pageSet.buildingBox.margin * parseInt(i % col);
            //                var posY = parseInt(i / col) * $scope.pageSet.buildingBox.y + $scope.pageSet.buildingBox.margin * parseInt(i / col);
            //                $(".buildingArea li").eq(i).css("left", posX);
            //                $(".buildingArea li").eq(i).css("top", posY);
            //            };
        };

        $scope.recalc = function (no) {
            for (var i = no; i < $scope.Buildings.length; i++) {
                $scope.Buildings[i].no = i;
            }
        };

        $scope.default = function () {
            $http({
                method: 'GET',
                url: '/building/default',
                params: {
                    "host_uid": $scope.host_uid
                }
            }).
            success(function (data, status, headers, config) {
                $scope.Buildings = data;
                console.log($scope.Buildings);
                for (var i = 0; i < $scope.Buildings.length; i++) {
                    (function (i) {
                        $scope.Buildings[i].hide = true;
                        $timeout(function () {
                            $scope.Buildings[i].hide = false;
                        }, i * 150);
                    })(i);
                }

                $timeout($scope.setPosition, 0);
                $timeout($scope.arrange, 0);
            }).
            error(function (data, status, headers, config) {
                if (status == 400) {
                    $scope.messages = data;
                } else {
                    alert('Unexpected server error.');
                }
            });
        };

        $scope.add = function (addData) {
            console.log($scope.newData);
            if (addData === undefined) {
                var addData = {};
                addData.name = "";
                addData.shared = "";
            }

            $scope.addData = {};
            $scope.addData.name = addData.name;
            if (addData.shared === "" || addData.shared === undefined)
                addData.shared = false;
            $scope.addData.shared = addData.shared;
            $scope.addData.shared = addData.shared;
            $scope.addData.host_uid = $scope.host_uid;
            $scope.addData.posx = Math.round(($("main>.building-wrap").outerWidth() / 2) - ($scope.pageSet.buildingBox.x / 2));
            $scope.addData.posy = Math.round(($("main>.building-wrap").outerHeight() / 2) - ($scope.pageSet.buildingBox.y / 2));

            console.log($scope.addData.posx);
            console.log($scope.addData.posy);

            var addSetPosition = function (data) {
                $(".buildingArea li").eq($(".buildingArea li").length - 1).css("left", data.posx + "px");
                $(".buildingArea li").eq($(".buildingArea li").length - 1).css("top", data.posy + "px");
            };

            $http({
                method: 'POST',
                url: '/building/add',
                data: $scope.addData
            }).
            success(function (data, status, headers, config) {

                if (data.pid !== undefined) {
                    $scope.Buildings.push(data);
                    $timeout(function () {
                        addSetPosition(data);
                    }, 0);

                    $timeout(function () {
                        $scope.Buildings[$scope.Buildings.length - 1].hide = true;
                        $scope.Buildings[$scope.Buildings.length - 1].hide = false;
                    }, 0);
                    
                    $(".inputname").attr("value", "");
                    addData.shared = false;
                    $scope.closeFloatingForm();
                    
                } else {
                    $scope.addFailMessage = data;
                     
                }
                $timeout($scope.arrange, 0);
                
            }).
            error(function (data, status, headers, config) {
                if (status == 400) {
                    $scope.messages = data;
                } else {
                    alert('Unexpected server error.');
                }
            });
        };

        $scope.del = function (pid, e, i) {
            $scope.delData = {};
            $scope.delData.pid = pid;
            $http({
                method: 'POST',
                url: '/building/del',
                data: $scope.delData
            }).
            success(function (data, status, headers, config) {
                $(e.target.parentElement).css("margin-top", 100);
                $(e.target.parentElement).css("opacity", 0);
                $timeout(function () {
                    $scope.Buildings.splice(i, 1);
                    //$timeout($scope.setPosition, 0);
                }, 150);
                $timeout($scope.arrange, 160);

            }).
            error(function (data, status, headers, config) {
                if (status == 400) {
                    $scope.messages = data;
                } else {
                    alert('Unexpected server error.');
                }
            });

            e.stopPropagation();
        };
        
        $scope.modify = function (pid, e, i){
            alert("한 번 등록한 계획은 수정할 수 없습니다. 신중하세요");
            $scope.st = 1;
        };

        //        $scope.resizeId;
        //        $(window).resize(function () {
        //            if ($scope.resizeId) $timeout.cancel($scope.resizeId);
        //
        //            $scope.resizeId = $timeout(function () {
        //                $timeout($scope.setPosition, 0);
        //            }, 500)
        //        });

        $scope.floatingFormInit = function () {
            console.log(0);

            $(".bd-overlay").css("visibility", "hidden");
            $(".bd-effect .bd-content").css("visibility", "hidden");
        };

        $scope.openFloatingForm = function () {
            $scope.floatingForm.show = true;
            $(".bd-overlay").css("visibility", "visible");
            $(".bd-effect .bd-content").css("visibility", "visible");
            $(".bd-overlay").css("opacity", 1);
            $("header").addClass("blur");
            $(".bd-overlay ~ div").addClass("blur");
        };

        $scope.closeFloatingForm = function () {
            $scope.floatingForm.show = false;

            $(".bd-overlay").css("visibility", "hidden");
            $(".bd-effect .bd-content").css("visibility", "hidden");

            $(".bd-overlay").css("opacity", 0);
            $("header").removeClass("blur");
            $(".bd-overlay ~ div").removeClass("blur");
        };
        $scope.closeFloatingForm();

        //패널을 위한 상태 저장 변수
        $scope.st = 0;
        
        $scope.positionable = function (el) {
           
            console.dir($(el).find('button.building-button'));
            
            $(el).find('button.building-button').click(function(){
                
            });

            $scope.dragpos = {};
            $scope.boxDiff = {};
            var collision = {};
            
            //클릭의 시작
            var openPanelStart = function(){
                console.log($scope.st);
                if($scope.st === 0){
                    $scope.openPanel(angular.element(el).scope().Building.pid);
                }else{
                    $scope.st = 0;
                }
            };
            $(el).on('click',openPanelStart);
            
            
            
            march4.util.Draggable(el,
                function (e, el) {
                    console.log("press");

                    $scope.dragpos.startx = e.pageX;
                    $scope.dragpos.starty = e.pageY;
                    $scope.boxDiff.x = e.pageX - $(el).offset().left;
                    $scope.boxDiff.y = e.pageY - $(el).offset().top;
                    e.preventDefault();
                },
                function (e, el, position) {
                    $scope.collisionDetect(e, el, collision, $scope.boxDiff);
                    //                    console.log(collision);
                    //                    console.log($scope.boxDiff);
                    if (collision.top !== false)
                        position.y = collision.top + $scope.boxDiff.y;
                    if (collision.left !== false)
                        position.x = collision.left + $scope.boxDiff.x;
                    if (collision.bottom !== false)
                        position.y = collision.bottom + ($(el).outerHeight - $scope.boxDiff.y);
                    if (collision.right !== false)
                        position.x = collision.right + ($(el).outerWidth - $scope.boxDiff.x);

                    console.log("move");
                    $scope.arrange();
                    e.preventDefault();
                    //$(el).off('click',openPanelStart);
                    $scope.st = $scope.st + 1;
                    return position;
                },
                function (e, el) {
                    console.log("realese");
                    var mouseX = e.pageX;
                    var mouseY = e.pageY;
                    
                    if (collision.top !== false)
                        mouseY = collision.top + $scope.boxDiff.y;
                    if (collision.left !== false)
                        mouseX = collision.left + $scope.boxDiff.x;
                    if (collision.bottom !== false)
                        mouseY = collision.bottom + ($(el).outerHeight - $scope.boxDiff.y);
                    if (collision.right !== false)
                        mouseX = collision.right + ($(el).outerWidth - $scope.boxDiff.x);

                    var diffx = mouseX - $scope.dragpos.startx;
                    var diffy = mouseY - $scope.dragpos.starty;
                    $(el).css("left", parseInt($(el).css("left")) + diffx + "px");
                    $(el).css("top", parseInt($(el).css("top")) + diffy + "px");
                    
                    $scope.updatePosition(el);
                    $scope.arrange();
                    e.preventDefault();
                    //$(el).on('click',openPanelStart);
                
                },500,"button.building-button");
        };

        $scope.updatePosition = function (el) {
            $scope.updateData = {};
            $scope.updateData.pid = $scope.pid;
            $scope.updateData.posx = parseInt($(el).css("left"));
            $scope.updateData.posy = parseInt($(el).css("top"));

            $http({
                method: 'POST',
                url: '/building/updatePos',
                data: $scope.updateData
            }).
            success(function (data, status, headers, config) {

            }).
            error(function (data, status, headers, config) {
                if (status == 400) {
                    $scope.messages = data;
                } else {
                    alert('Unexpected server error.');
                }
            });
        };

        $scope.arrange = function () {
            var container = $(".buildingArea");
            var elements = container.children();
            var sortMe = [];
            
            for (var i = 0; i < elements.length; i++) {
                if (!elements.eq(i).css("top")) {
                    continue;
                }

                var sortPart = parseInt(elements.eq(i).css("top"));

                sortMe.push([1 * sortPart, elements[i]]);
            }

            sortMe.sort(function (x, y) {
                return x[0] - y[0];
            });
            
            for (i = 0; i < sortMe.length; i++) {
                sortMe[i][1].style.zIndex = i;
            }
        };

        $scope.collisionDetect = function (e, el, collision, boxDiff) {
            var currentX = e.pageX;
            var currentY = e.pageY;
            var areaX = $(".building-wrap.ng-scope").offset().left;
            var areaY = $(".building-wrap.ng-scope").offset().top;
            var areaWidth = $(".building-wrap.ng-scope").outerWidth();
            var areaHeight = $(".building-wrap.ng-scope").outerHeight();
            var boxX = $(el).offset().left;
            var boxY = $(el).offset().top;
            var boxWidth = $(el).outerWidth();
            var boxHeight = $(el).outerHeight();

            //박스 마우스 거리
            var boxintX = boxDiff.x;
            var boxintY = boxDiff.y;

            //만약 area 바깥으로 나가면 멈춤

            //왼쪽 막아
            if (areaX > (currentX - boxintX))
                collision.left = areaX;
            else
                collision.left = false;

            //위쪽 막아
            if (areaY > (currentY - boxintY))
                collision.top = areaY;
            else
                collision.top = false;

            //오른쪽 막아
            if ((areaX + areaWidth) < currentX + (boxWidth - boxintX))
                collision.right = (areaX + areaWidth);
            else
                collision.right = false;

            //아래 막아
            if ((areaY + areaHeight) < currentY + (boxHeight - boxintY))
                collision.bottom = (areaY + areaHeight);
            else
                collision.bottom = false;

            //마우스 거리 - 차거리 //위 왼쪽
            //마우스 거리 + (박스크기 - 차거리) // 아래 오른쪽            
        };

        $scope.setPid = function (pid) {
            $scope.pid = pid;
        };

        $scope.default();
    });


    march4.app.registerController('roadmapController', function($http, $scope, $routeParams, $q, QuestService) {
        $scope.lastOrder = 0;
        $scope.quests = [];
        $scope.pid = 2;
        $scope.path = '/api/projects/'+$scope.pid+'/quests';

        $scope.$watch(function(){
            return QuestService.quests;
        }, function (quests) {
            $scope.updateQuests(quests);
        });

        $scope.$watch(function(){
            return QuestService.pid;
        }, function (pid) {
            $scope.pid = pid;
            $scope.path = '/api/projects/'+$scope.pid+'/quests';
        });

        $scope.initQuest = function() {
            console.log(QuestService);
            console.log('init', $scope.lastOrder);
            $scope.newQuest = {
                order: ++($scope.lastOrder)
            };
        };
        
        $scope.updateQuests = function(data) {
            $scope.quests = data;
            $scope.lastOrder = parseInt(data[data.length - 1]);
            $scope.lastOrder = (typeof($scope.lastOrder) !== 'number')? 0 : $scope.lastOrder.order;
            console.log('update last order', $scope.lastOrder);
            $scope.updatePosition();
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
            new march4.util.Sortable(el, function(movingEl, nextEl){
                $scope.insertBefore($scope.getqId(movingEl), $scope.getqId(nextEl));
            });
        };
        
        $scope.getqId = function(element) {
            var scope = angular.element(element).scope();
            return (scope)? scope.quest.qId : 0;
        };

        $scope.toMillisec = function(sqlDatetime) {
            var t = sqlDatetime.split(/[- :]/);
            var d = new Date(t[0], t[1]-1, t[2], t[3] || 0, t[4] || 0, t[5] || 0);
            var s = d.getTime();
            return s;
        };

        $scope.calculatePosition = function(millisec) {
        };

        $scope.updatePosition = function() {
            var baseDuesec = $scope.toMillisec('2010-01-01 00:00:00');
            // var baseDuesec = new Date(); // 혹은 제일 가까운 날짜.
            var viewMaxHeight = 500;

            var accumulatedDuesec = 0;
            for (var i = $scope.quests.length - 1; i >= 0; i--) {
                var q = $scope.quests[i];
                q.duesec = ($scope.toMillisec(q.due) - baseDuesec) / 1000;
                accumulatedDuesec += q.duesec;
            };

            if($scope.quests.length > 0) $scope.quests[0].position = 0;
            for (var i = 1; i < $scope.quests.length; i++) {
                var q = $scope.quests[i];
                q.position = $scope.quests[i-1].position
                    + parseInt((q.duesec * viewMaxHeight) / accumulatedDuesec);
            };
            // debugger;
            $scope.calculatePosition();
        };
        
        $scope.init();
    });
})();