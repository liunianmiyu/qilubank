//存放主要交互逻辑的js代码
// javascript 模块化(package.类.方法)
var qiluBank = {
    rootPath : $("#rootPath").val(),
    //封装相关ajax的url
    URL: {
        menu: function () {
            return 'html/menu.json';
        },
        items: function (programId) {
            return qiluBank.rootPath + '/operate/item?id=' + programId;
        },
        itemContent : function(itemId) {
        	return qiluBank.rootPath + '/operate/getDocAndPic?id=' + itemId;
        }
    },

    //验证用户角色
    isAdmin: function () {
    	var role = $.cookie('role');
    	if(role == "1"){
    		return true;
    	}else {
    		return false;
    	}
    },
    //登录逻辑处理
    login : {
    	init : function() {
    		$("#login_btn").click(function() {
    			$.post("login/loginValidate", $("#login-form").serialize(), function(data) {
    				if(data == "invalide"){
    					$("#loginDialog .modal-body").text("权限不足，请以游客身份登录!");
    					$("#loginDialog").modal('toggle');
    				}else if (data == "fail") {
    					$("#loginDialog .modal-body").text("用户名或密码不正确，请重新登录!");
    					$("#loginDialog").modal('toggle');
                    }else {
                    	if(data == "1"){
                    		window.location.href = qiluBank.rootPath + "/admin";
                    	}else{
                    		window.location.href = qiluBank.rootPath + "/index/index_visit";
                    	}
                    }
                });
            });
    	}
    },
    //初始化菜单
    initMenu : function() {
    	$.getJSON(qiluBank.URL.menu()).then(function(data){
    		var htmlStr = ""
    		$(data).each(function(index, item){
    			var moduleName = item.moduleName
    			htmlStr += "<li class='dropdown'><a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'>"+moduleName+"<span class='caret'></span></a>"+
    					   "<ul class='dropdown-menu'>";
    			var programStr = "";
    			$(item.programs).each(function(i, d){
    				programStr += "<li><a href='items?programId="+d.id+"'>"+d.name+"</a></li>";
    			});
    			htmlStr += programStr + "</ul></li>";
    		});
    		$(".menulist").html(htmlStr);
    	});
    }
}