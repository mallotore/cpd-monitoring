//= require_tree lib/jquery
//= require_tree lib/bootstrap
//= require lib/jquery.jgrowl.min.js
//= require lib/lodash.js
//= require_self

(function(){
	
	if (typeof jQuery !== 'undefined') {
	    (function($) {
	        $('#spinner').ajaxStart(function() {
	            $(this).fadeIn();
	        }).ajaxStop(function() {
	            $(this).fadeOut();
	        });
	    })(jQuery);
	}

	// jQuery to collapse the navbar on scroll
	$(window).scroll(function() {
	    if ($(".navbar").offset().top > 50) {
	        $(".navbar-fixed-top").addClass("top-nav-collapse");
	    } else {
	        $(".navbar-fixed-top").removeClass("top-nav-collapse");
	    }
	});

	// jQuery for page scrolling feature - requires jQuery Easing plugin
	$(function() {
	    $('a.page-scroll').bind('click', function(event) {
	        var $anchor = $(this);
	        $('html, body').stop().animate({
	            scrollTop: $($anchor.attr('href')).offset().top
	        }, 1500, 'easeInOutExpo');
	        event.preventDefault();
	    });
	});

	$(function() {
	    $('body').on('click', '.page-scroll a', function(event) {
	        var $anchor = $(this);
	        $('html, body').stop().animate({
	            scrollTop: $($anchor.attr('href')).offset().top
	        }, 1500, 'easeInOutExpo');
	        event.preventDefault();
	    });
	});

	// Floating label headings for the contact form
	$(function() {
	    $("body").on("input propertychange", ".floating-label-form-group", function(e) {
	        $(this).toggleClass("floating-label-form-group-with-value", !! $(e.target).val());
	    }).on("focus", ".floating-label-form-group", function() {
	        $(this).addClass("floating-label-form-group-with-focus");
	    }).on("blur", ".floating-label-form-group", function() {
	        $(this).removeClass("floating-label-form-group-with-focus");
	    });
	});

	// Highlight the top nav as scrolling occurs
	$('body').scrollspy({
	    target: '.navbar-fixed-top'
	})

	// Closes the Responsive Menu on Menu Item Click
	$('.navbar-collapse ul li a').click(function() {
	    $('.navbar-toggle:visible').click();
	});

})();