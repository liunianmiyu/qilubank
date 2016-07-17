//存放主要交互逻辑的js代码
// javascript 模块化(package.类.方法)
var qiluBank = {
    //bootstrap colors
	colorArray : ["success","info", "warning", "danger", "default"],
    //封装相关ajax的url
    URL: {
        menu: function () {
            return '/html/menu.json';
        },
        items: function (programId) {
            return '/operate/item?id=' + programId;
        },
        itemContent : function(itemId) {
        	return '/operate/getDocAndPic?id=' + itemId;
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
    			$.post("loginValidate", $("#login-form").serialize(), function(data) {
    				if(data == "invalide"){
    					$("#loginDialog .modal-body").text("权限不足，请以游客身份登录!");
    					$("#loginDialog").modal('toggle');
    				}else if (data == "fail") {
    					$("#loginDialog .modal-body").text("用户名或密码不正确，请重新登录!");
    					$("#loginDialog").modal('toggle');
                    }else {
                        window.location.href = "/index";
                    }
                });
            });
    	}
    },
    
    //逻辑处理
    operate: {
        //init
        init: function (initParams) {
        	//load menu
        	$.getJSON(qiluBank.URL.menu()).then(function(data){
        		var htmlStr = "";
    			$(data).each(function(index,item){
    				var isActive = "";
    				if(index == 0){
    					isActive = "active";
    				}
    	            var menuStr = "";
    	            menuStr = "<li class='"+isActive+"'>"
    					     +"<a  href='javascript:;' aria-expanded='true'><i class='fa fa-building-o fa-fw'></i>"+item.moduleName+"<span class='fa arrow'></span></a>"
    					     +"<ul class='nav nav-second-level' aria-expanded='true'>";
    			             var twoLevel = "";
    					     $(item.programs).each(function(inx, ite){
    					    	 twoLevel = twoLevel + "<li class='program' program-id='"+ite.id+"'><a href='javascript:;'>"+ite.name+"</a></li>";
    			            });
    			    menuStr = menuStr + twoLevel + "</ul></li>";
    			    htmlStr = htmlStr + menuStr;
    	        }); 
    			$("#menu").html(htmlStr);
        	}).then(function(){$("#menu").metisMenu();});
        	
        	//init items list.
        	$(".breadcrumb").append("<li>"+initParams.module+"</li><li class='active'>"+initParams.program+"</li>");
        	$.get(qiluBank.URL.items(initParams.itemId)).then(function(data){
        		toHtml(data);
        	});
        	//get items by program id.
        	$(document).on("click", ".program", function(){
    			var programId = $(this).attr("program-id");
    			var moduleText = $(this).parent("ul").prev().text();
    			var programText = $(this).text();
    			$(".breadcrumb").html("<li><i class='fa fa-home fa-fw'></i><a href='/index'>Home</a></li>").append("<li class='active'>"+moduleText+"</li><li class='active'>"+programText+"</li>");
    			$.get(qiluBank.URL.items(programId)).then(function(data){
            		toHtml(data);
            	});
    		});
        	//click item event
        	$(document).on("click", ".item", function(){
        		var itemId = $(this).attr("item-id");
        		var itemText = $(this).text();
        		if(itemText.length > 30) {
        			itemText = itemText.substring(3,30) + "...";
        		}else {
        			itemText = itemText.substring(3, itemText.length);
        		}
        		$(".breadcrumb").append("<li class='active'>"+itemText+"</li>")
        		$("#itemList").hide();
        		$.getJSON(qiluBank.URL.itemContent(itemId)).then(function(data){
        			console.info(data);
        			var docLength = data.doc.length;
        			var picLength = data.pic.length;
        			if(docLength != 0 && picLength != 0){
        				alert("doc and pic both had.")
        			}else if(docLength != 0 && picLength == 0) {
        				$("#itemContent").show();
        			}else if(docLength == 0 && picLength != 0) {
        				alert("pic page");
        			}else {
        				alert("doc and pic both null.");
        			}
        		});
        	});
        	//将返回的数据拼装成html
        	var toHtml = function(data) {
        		$("#itemContent").hide();
        		$("#itemList").show();
        		var itemStr = "";
        		var dataLength = data.length;
				$(data).each(function(index, item){
					itemStr +="<div class='panel panel-success'>"
		                	+"<div class='panel-heading'>"
		                	+"<div class='panel-heading item' item-id='"+item.id+"' style='cursor:pointer;'>"+(index+1)+"、"+item.name+"</div>"
		                    +"</div>"
		                    +"</div>";
				});
				$("#itemList").html("");
				$("#itemList").html(itemStr);
        	}
        }
    }
}