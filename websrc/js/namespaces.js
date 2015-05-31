var march4 = {
	util:{
		addScript : function(url,callback,baseEl,timeout){
	        var timeoutID,
	            script = document.createElement('script');

	        baseEl = baseEl || document.getElementsByTagName('head')[0];
	        script.src = url;
	        baseEl.appendChild(script); 
	        
	        script.onreadystatechange = script.onload = function(){
	            if (!this.readyState || this.readyState == "complete") {
	                script.onload = script.onreadystatechange = null;
	                clearTimeout(timeoutID);

	                return callback ? callback(true) : true;
	            }
	        };

	        if(timeout){
	            timeoutID = setTimeout(function(){
	                script.onload = script.onreadystatechange = null;
	                return callback ? callback(false) : false;
	            },timeout);
	        }
	    },

	    swap : function($el1, $el2) {
	        if (!$el1 || !$el2) return;
	        $temp = $('<div>');
	        $el1.before($temp);
	        $el2.after($el1);
	        $temp.after($el2);
	        $temp.remove();
	    },

	    isMouseOver : function(event, $self) {
	        var top = $self.offset().top;
	        var down = top + $self.outerHeight();
	        return (top <= event.pageY && event.pageY <= down);
	    }
	},
	building:{},
	roadmap:{}
};

march4.util.Draggable = function(el, downFunc, moveFunc, upFunc, wait, exclude) {
    if(!(this instanceof march4.util.Draggable)){
        return new march4.util.Draggable(el, downFunc, moveFunc, upFunc, wait, exclude);
    }
    
    var that = this;
    downFunc = downFunc || function() {};
    moveFunc = moveFunc || function() {};
    upFunc = upFunc || function() {};
    wait = wait || 0;
    this.$el = $(el);

    function waitAnimationEnd(callback){
        if(that.$el.css('transition').indexOf('0s') < 0){
            that.$el.one('transitionend',function(){
                callback();
            });
        }else{
            callback();
        }
    }

    function dragStart(e) {
        var position = {
            x:e.clientX,
            y:e.clientY
        };
        
        position = downFunc(e, that.$el, position) || position;
        var cursorX = e.clientX;
        var cursorY = e.clientY;
        var marginX = parseInt(that.$el.css('marginLeft'));
        var marginY = parseInt(that.$el.css('marginTop'));
        console.log(that.$el.offset().top, that.$el.offset().left);
        var elY = that.$el.offset().top - $(window).scrollTop() - marginY;
        var elX = that.$el.offset().left - $(window).scrollLeft() - marginX;
        var diffX = elX - cursorX;
        var diffY = elY - cursorY;
        var originalStyle = that.$el.attr('style') || "";
        
        that.$el.addClass('dragging');
        setPos(position);
        
        $(document).on('mousemove.drag', function(e) {
            position = {
                x:e.clientX,
                y:e.clientY
            };   
            
            position = moveFunc(e, that.$el, position) || position;
            setPos(position);
            e.preventDefault();
        });

        function setPos(position) {
            that.$el.css({
                "position": "fixed",
                "top": position.y + diffY,
                "left": position.x + diffX,
            });
        }
        
        $(document).on('mouseup.drag mouseleave.drag', function(e) {
            
            that.$el.removeClass('dragging');
            that.$el.attr('style', originalStyle);
            $(document).off('.drag');
            upFunc(e, that.$el, position);
        });

        e.preventDefault();
    }

    this.$el.on('mousedown', function(e){
        if(!wait){
            dragStart(e)
        }else{
            that.$el.addClass('waiting');
            var timeoutIdx = setTimeout(function(){
                that.$el.removeClass('waiting');
                dragStart(e)
            },wait);

            that.$el.one('mouseup mouseleave',function(){
                that.$el.removeClass('waiting');
                clearTimeout(timeoutIdx);
            });
        }

        e.preventDefault();
    });
    if(exclude){
        $(exclude).on('mousedown',function(event) {
            event.stopPropagation();
        });
    }
};


march4.util.Sortable = function(el, upFunc) {
    var that = this;
    this.$el = $(el);
    this.$dummy = null;
    
    upFunc = upFunc || function(movingEl, nextEl) {};

    this.sortableList.push(this.$el);
    new march4.util.Draggable(el, function() {
        that.$dummy = that.$el.clone().css({
            'visibility': 'hidden'
        });
        that.$el.after(that.$dummy);
        $(document).on('mousemove.sort', function(e) {
            for (var i = 0; i < that.sortableList.length; i++) {
                if (!that.$el.is(that.sortableList[i])) {
                    if (march4.util.isMouseOver(e, that.sortableList[i])) {
                        that.constructor.prototype.exchangeEl = that.sortableList[i];
                    }
                }
            }
        });
    }, function(e, $el) {
        march4.util.swap(that.exchangeEl, that.$dummy);
        that.constructor.prototype.exchangeEl = null;
    }, function(e) {
        march4.util.swap(that.$el, that.$dummy);
        that.$dummy.remove();
        $(document).off('.sort');
        upFunc(that.$el, that.$el.next());
    });
};

march4.util.Sortable.prototype.exchangeEl = null;
march4.util.Sortable.prototype.sortableList = [];