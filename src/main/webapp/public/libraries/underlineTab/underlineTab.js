(function ($) {

    $.fn.underlineTab = function (options) {
        var opts = $.extend({}, $.fn.underlineTab.defaults, options);
        var lineWidth = opts.lineWidth;
        var height = opts.height;
        var lineHeight = opts.lineHeight;
        var onShowPanel = opts.onShowPanel || function () {};
        var onHidePanel = opts.onHidePanel || function () {};

        var $obj = $(this).data("target", opts.target);
        var target = $(this).data("target");
        $(this).init.prototype.showPanel = function(index){
            this.find('.underline-tab a').eq(index).click();
            return this;
        };

        $(this).init.prototype.refresh = function(){
            $(this).bjuiajax("doLoad", {
                target: this.data("target"),
                url: this.find('.underline-tab li.active a').data("url")
            });
            return this;
        };

        var switchPanel = function () {
            if (target.is(":visible")) {
                onHidePanel.apply($obj);
            } else {
                onShowPanel.apply($obj);
            }
        }

        $(this).find('.underline-nav-cursor').click(function () {
            if ($(target).children().size() > 0) {
                switchPanel();
            } else {
                $obj.find('.underline-tab a').eq(0).click();
            }
        });

        return this.each(function () {
            var target = $(this).data("target");
            $obj.children('ul')
                .css('height', height)
                .css('line-height', lineHeight)
                .children('li')
                .css('width', lineWidth + "px");

            $obj.children('.under-line').css("width", (lineWidth - 10) + "px");

            $obj.find('.underline-tab a').click(function (e) {
                var $closesttab = $(e.target).closest('.underline-tab');
                var index = $closesttab.find('a').index($(e.target));
                var $li = $closesttab.find('li').eq(index);
                if ($li.hasClass('active')) {
                    switchPanel();
                } else {
                    $li.addClass('active').siblings().removeClass('active');
                    $closesttab.next('.under-line')
                        .css("display", "block")
                        .show()
                        .animate({
                            left: (index * 70 + 5) + "px",
                        }, "fast", function () {
                            $(this).bjuiajax("doLoad", {
                                target: target,
                                url: $(e.target).data("url"),
                                callback: function () {
                                    onShowPanel.apply($obj);
                                }
                            });
                        });
                }
            });
        });
    };

    //定义默认
    $.fn.underlineTab.defaults = {
        height: "32px",
        lineHeight: "32px",
        lineWidth: 70,
        show: false,
    };

})(jQuery);
