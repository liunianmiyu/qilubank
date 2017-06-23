//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 120);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

// 路由
var router = new Router();
var url = window.location.hash;
if(url !== '') {
	routerList(url)
}

function routerList(url) {
	//var url = window.location.hash;
	router.add(url, function() {
		// 替换iframe的url
		$('#iframe').attr("src", url.replace('#', ''))
		// 导航菜单展开
		$(".treeview-menu li").removeClass("active");
		$("a[href='" + url + "']").parents("li").addClass("active");
	});
	router.start();
}
