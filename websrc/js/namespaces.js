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

march4.util.Draggable = function(el, downFunc, moveFunc, upFunc) {
    var that = this;

    downFunc = downFunc || function() {};
    moveFunc = moveFunc || function() {};
    upFunc = upFunc || function() {};
    
    this.$el = $(el);
    this.$el.on('mousedown', function(e) {
        downFunc(e, that.$el);
        var cursorX = e.clientX;
        var cursorY = e.clientY;
        var elY = that.$el.offset().top - $(window).scrollTop();
        var elX = that.$el.offset().left - $(window).scrollLeft();
        var diffX = elX - cursorX;
        var diffY = elY - cursorY;
        var originalStyle = that.$el.attr('style') || "";
        setPos(e);
        $(document).on('mousemove.drag', function(e) {
            moveFunc(e, that.$el);
            setPos(e);
        });

        function setPos(e) {
            cursorX = e.clientX;
            cursorY = e.clientY;
            that.$el.css({
                "position": "fixed",
                "top": cursorY + diffY,
                "left": cursorX + diffX,
            });
        }
        $(document).on('mouseup.drag mouseleave.drag', function(e) {
            that.$el.attr('style', originalStyle);
            $(document).off('.drag');
            upFunc(e, that.$el);
        });
        e.preventDefault();
    });
};


march4.util.Sortable = function(el, upFunc) {
    var that = this;
    this.$el = $(el);
    this.$dummy = null;
    
    upFunc = upFunc || function() {};

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
                        that.constructor.prototype.lastExchangeEl = that.constructor.prototype.exchangeEl;
                    }
                }
            }
        });

    }, function(e, $el) {
        march4.util.swap(that.exchangeEl, that.$dummy);
        console.log('tt',that.$el,that.constructor.prototype.lastExchangeEl);
        
        that.constructor.prototype.exchangeEl = null;
    }, function(e) {
        march4.util.swap(that.$el, that.$dummy);
        that.$dummy.remove();
        $(document).off('.sort');
        console.log(that.$el,that.constructor.prototype.lastExchangeEl,'tt');
        upFunc(that.$el,that.constructor.prototype.lastExchangeEl);
    });
};

march4.util.Sortable.prototype.exchangeEl = null;
march4.util.Sortable.prototype.lastExchangeEl = null;
march4.util.Sortable.prototype.sortableList = [];