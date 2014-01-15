/**
* 对象自动滑动到目标位置的效果处理
* 依赖jQuery
*/
$(function() {
$.fn.extend({
	swimming: function(target, options) {
		options = $.extend({}, $.Swimmer.defaults, {
			source: {
				top: $(this).offset().top,
				left: $(this).offset().left
			}
		}, options);
		
		options.display = options.display || $(this).html();
		
		new $.Swimmer($(this), target, options);
	}
});
$.Swimmer = function(source, target, options) {
	var top, left, height;
	if (typeof(target) != "undefined") {
		if (typeof(target) == 'object' && target instanceof jQuery) {
			top = $(target).offset().top;
			left = $(target).offset().left;
			height = $(target).height();
		} else {
			top = target.top;
			left = target.left;
		}
	}
	
	return swim(options);
	
	function generate(options) {
		var inittop = options.source.top;
		var initleft = options.source.left;
		
		var value = '';
		if (typeof(options.display) != 'undefined') {
			value = options.display;
		}
	
		var fo = $("<div></div>");

		var style = {"position":"absolute","top":inittop,"left":initleft,"z-index":"999"};
		style = $.extend({}, style, options.displayStyle);
		$(fo).css(style);
		$(fo).html(value);
		
		if (options.deleteSource) {
			$(source).unbind();
			$(source).empty();
			$(source).removeClass();
			$(source).children().remove();
			$(source).remove();
		}
		if (options.deleteParent && options.deleteParent != "") {
			$(source).closest(options.deleteParent).removeClass();
			$(source).closest(options.deleteParent).empty();
			$(source).closest(options.deleteParent).remove();
		}
		
		return fo;
	}
	
	function swim(options) {
		var swimmer = generate(options);
		jump($(swimmer), options);

		if (typeof(target) == 'object' && target instanceof jQuery) {
			top = scroll(target);
		} 

		$(swimmer).animate({top:top,left:left}, options.time, function() {
			land($(swimmer), options);
		});
		
		return true;
	}
	
	function jump(obj, options) {
		if (options.fadeIn) {
			$(obj).fadeIn(options.fadeSpeed);
		}
		$(obj).appendTo(document.body);
	}
	
	function land(obj, options) {
		if (options.fadeOut) {
			$(obj).fadeOut(options.fadeSpeed);
		}
		$(obj).removeClass();
		$(obj).remove();
		
		if (options.over && options.over != '' && typeof(options.over) != 'undefined') {
			var c = $.Callbacks();
			c.add(options.over);
			if (!c.fired()) {
				c.fire();	
			}
		}
	}
	
	function scroll(tg) {
		// get container(div) closest
		var container = $(tg).closest(options.targetParent);
		
		// get height of container displayed in page 
		var _height = $(container).height();
		// get offset of container displayed in page 
		var _top = $(container).offset().top;
		// get really height of container (include all elements)
		var scrollHeight = $(container)[0].scrollHeight;
		var _scrollTop = $(container).scrollTop();
		var scrolltop = 0;

		//alert("top:"+top+",_height:"+_height+",_top:"+_top+",_scrollTop:"+_scrollTop+",scrollHeight:"+scrollHeight);
		
		// the position of element is not in container, need to scroll down
		if (top >= (_height + _top)) {
			scrolltop = top - (_height + _top) + _scrollTop + $(tg).outerHeight(true);
		} else if (top < _top) {		// need to scroll up
			//if (_scrollTop )
			scrolltop = _scrollTop - (_top - top);
		} else {						// don't move
			scrolltop = _scrollTop;
		}
		
		if (scrolltop < 0) {
			scrolltop = 0;
		} else if (scrolltop > scrollHeight) {
			scrolltop = scrollHeight;
		}
		
		$(container).scrollTop(scrolltop);
		return $(tg).offset().top;
	}
	
	function wrap(html) {
		if ($.trim(html).toLowerCase().indexOf("<td") == 0) {
			var r = $(html).wrap($("<tr></tr>"));
			r = $(r).wrap($("<table></table>"));
			return r;
		} else if ($.trim(html).toLowerCase().indexOf("<tr") == 0) {
			return $(html).wrap($("<table></table>"));
		} 
	}
	
};

$.Swimmer.defaults = {
	time: 1000,
	targetParent: 'DIV',
	fadeIn: false,
	fadeOut: true,
	fadeSpeed: 'fast',
	deleteSource: false,
	deleteParent: '',
	display: '',
	displayStyle: {},
	over: ''		
};

});
