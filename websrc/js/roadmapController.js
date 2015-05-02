/* todo
* 무조건 insertBefore여서 맨 밑으로 안 들어가짐
* 삭제, 수정 구현
*/

// YG: 전반적으로 라인 수를 줄이려는 듯한 느낌의 코딩 스타일이 보입니다.
//     하지만 jQuery 스타일 가이드는
//     https://contribute.jquery.org/style-guide/js/#spacing
//     (과장하자면) 가능한 최대한 띄어쓰기를 권장하고 있습니다.
//     한 번 참고해보세요.

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

angular.module('roadmap', [])
.controller('roadmapCtrl', function($http) {
    // {content(string),order(number),due(datetime),pos(x,y)}
    this.quests = [];
    this.newQuest = {};

    this.addQuest = function() {
        console.log(this.newQuest);
        this.quests.push(this.newQuest);
        
        var data = {content:this.newQuest.content};
        $http.post(window.location.pathname, data)
        .success(function (data, status, headers, config) {
        })
        .error(function (data, status, headers, config) {
        	alert("AJAX failed!");
        });
        this.newQuest = {};
    };

    // this.getLastOrder = function() {
    //     // for()
    // };
});
