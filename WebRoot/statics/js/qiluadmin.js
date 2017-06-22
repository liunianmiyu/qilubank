//存放主要交互逻辑的js代码
// javascript 模块化(package.类.方法)
var qiluAdmin = {
    rootPath : $("#rootPath").val(),
    //封装相关ajax的url
    URL: {
        modules : function () {
            return qiluAdmin.rootPath + '/admin/modules';
        },
        programs : function(moduleId) {
        	return qiluAdmin.rootPath + '/admin/programs?moduleId=' + moduleId;
        },
        items: function (programId) {
            return qiluAdmin.rootPath + '/admin/items?programId=' + programId;
        },
        docsAndPics : function(itemId) {
        	return qiluAdmin.rootPath + '/admin/docsAndPics?itemId=' + itemId;
        },
        delDoc : function(docId) {
        	return qiluAdmin.rootPath + '/admin/delDoc?docId=' + docId;
        },
        delPic : function(picId) {
        	return qiluAdmin.rootPath + '/admin/delPic?picId=' + picId;
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
                    	if(data == "0"){
                    		window.location.href = qiluAdmin.rootPath + "/";
                    	}else{
                    		window.location.href = qiluAdmin.rootPath + "/admin";
                    	}
                    }
                });
            });
    	}
    },
    
    //首页逻辑处理
    indexInit : function(moduleId, programId, itemId) {
    	//初始化一级菜单
    	$.getJSON(qiluAdmin.URL.modules()).then(function(data){
    		templateModuleSelect(data, moduleId);
    	}).then(function(){
    		if(programId == "" && itemId == ""){
    			$.getJSON(qiluAdmin.URL.programs($("#modules").val())).then(function(data){
    				templateProgramSelect(data);
    			}).then(function(){
    				$("#items").html("");
    				$.getJSON(qiluAdmin.URL.items($("#programs").val())).then(function(data){
    					templateItemSelect(data);
    				}).then(function(){
    					$("#documentlist tbody").html("");
    					$.getJSON(qiluAdmin.URL.docsAndPics($("#items").val())).then(function(data){
    						$("#documentlist").show();
    						if(data.doc.length > 0){
    							templateDocuments(data.doc);
    						}
    						if(data.pic.length > 0) {
    							templatePictures(data.pic);
    						}
    					});
    				});
    		});
    		}
    	});
    	
    	if(moduleId != null && moduleId != ""){
    		$.getJSON(qiluAdmin.URL.programs(moduleId)).then(function(data){
    			templateProgramSelect(data, programId);
    		});
    	};
    	
    	if(programId != null && programId != ""){
    		$.getJSON(qiluAdmin.URL.items(programId)).then(function(data){
    			templateItemSelect(data, itemId);
    		})
    	};
    	if(itemId != null && itemId != ""){
    		$("#documentlist tbody").html("");
			$.getJSON(qiluAdmin.URL.docsAndPics(itemId)).then(function(data){
				$("#documentlist").show();
				if(data.doc.length > 0){
					templateDocuments(data.doc);
				}
				if(data.pic.length > 0) {
					templatePictures(data.pic);
				}
			});
    	};
    	
    	
    	$(document).on("change", "#modules", function(){
    		var moduleId = $(this).val();
    		$("#documentlist tbody").html("");
    		$.getJSON(qiluAdmin.URL.programs(moduleId)).then(function(data){
    			templateProgramSelect(data);
    		}).then(function(){
    			$.getJSON(qiluAdmin.URL.items($("#programs").val())).then(function(data){
        			templateItemSelect(data);
        		}).then(function(){
        			var dealItemId = parseInt($("#items").val());
        			if(dealItemId >= 201 && dealItemId <= 208){
        				dealItemId = parseInt($("#programs").val());
        			}
        			$.getJSON(qiluAdmin.URL.docsAndPics(dealItemId+"")).then(function(data){
        				$("#documentlist").show();
	    				if(data.doc.length > 0){
	    					templateDocuments(data.doc);
	    				}
	    				if(data.pic.length > 0) {
	    					templatePictures(data.pic);
	    				}
        			});
        		});
    		});
    	});
    	
    	$(document).on("change", "#programs", function(){
    		var programId = $(this).val();
    		$("#items").html("");
    		$.getJSON(qiluAdmin.URL.items(programId)).then(function(data){
    			templateItemSelect(data);
    		}).then(function(){
    			var dealItemId = parseInt($("#items").val());
    			if(dealItemId >= 201 && dealItemId <= 208){
    				dealItemId = parseInt(programId);
    			}
    			$("#documentlist tbody").html("");
    			$.getJSON(qiluAdmin.URL.docsAndPics(dealItemId)).then(function(data){
    				$("#documentlist").show();
        			if(data.doc.length > 0){
        				templateDocuments(data.doc);
    				}
        			if(data.pic.length > 0) {
    					templatePictures(data.pic);
    				}
    			});
    		});
    	});
    	
    	$(document).on("change", "#items", function(){
    		var itemId = $(this).val();
    		$("#documentlist tbody").html("");
    		var dealItemId = parseInt(itemId);
			if(dealItemId >= 201 && dealItemId <= 208){
				dealItemId = parseInt($("#programs").val());
			}
    		$.getJSON(qiluAdmin.URL.docsAndPics(dealItemId+"")).then(function(data){
    			$("#documentlist").show();
    			if(data.doc.length > 0){
    				templateDocuments(data.doc);
				}
    			if(data.pic.length > 0) {
					templatePictures(data.pic);
				}
			});
    	});
    	//拼装module select
    	templateModuleSelect = function(data, moduleId) {
    		var modulesStr = "";
    		$(data).each(function(index, item){
    			if(moduleId != null && moduleId != "" && moduleId == item.id){
    				modulesStr += "<option class='module' value='"+item.id+"' selected='selected'>"+item.name+"</option>";
    			}else{
    				modulesStr += "<option class='module' value='"+item.id+"'>"+item.name+"</option>";
    			}
    		});
    		$("#modules").html(modulesStr);
    	};
    	//拼装program select
    	templateProgramSelect = function(data, programId) {
    		$("#programs").html("");
    		var programsStr = "";
			$(data).each(function(index, item){
				if(programId != "" && programId != null && programId == item.id){
					programsStr += "<option value='"+item.id+"' selected='selected'>"+item.name+"</option>" 
				}else{
					programsStr += "<option value='"+item.id+"'>"+item.name+"</option>" 
				}
			});
			$("#programs").html(programsStr);
    	};
    	//拼装itmes select
    	templateItemSelect = function(data, itemId) {
    		$("#items").html("");
    		var itemssStr = "";
			$(data).each(function(index, item){
				if(itemId != null && itemId != "" && itemId == item.id){
					itemssStr += "<option value='"+item.id+"' title='"+item.name+"' selected='selected'>"+item.name.substring(0,50)+"</option>" 
				}else {
					itemssStr += "<option value='"+item.id+"' title='"+item.name+"'>"+item.name.substring(0,50)+"</option>" 
				}
			});
			$("#items").html(itemssStr);
    	};
    	//拼装文档内容
    	templateDocuments = function(data) {
    		$("#documentlist>tbody").html("");
    		var docsStr = "";
			$(data).each(function(index, item) {
				docsStr += "<tr>"+
				           "<td>文档</td>" +
				           "<td>" + item.name + "</td>" +
				           "<td>"+item.create_time+"</td>" +
				           "<td>"+item.update_time+"</td>" +
				           "<td>"+item.operate_user+"</td>" +
				           "<td><a href='"+qiluAdmin.rootPath+"/operate/showDoc?id="+item.id+"' class='btn btn-success' target='_blank'>查看详情</a></td>" +
				           "<td><a href='"+qiluAdmin.rootPath+"/admin/toEditDoc?itemId="+item.item_id+"&docId="+item.id+"' class='btn btn-warning'>编辑</a></td>" +
				           "<td><button class='btn btn-danger deldoc' doc-id='"+item.id+"'>删除</button></td>" +
				           "</tr>";
			});
			$("#documentlist>tbody").html(docsStr);
    	};
    	//拼装图片内容
    	templatePictures = function(data) {
    		var picsStr = "";
    		//http://127.0.0.1:8080/qilubank/operate/main?id=module_101
			$(data).each(function(index, item) {
				picsStr += "<tr>"+
				           "<td style='color:red;'>图片</td>" +
				           "<td>"+item.name+"</td>" +
				           "<td>"+item.create_time+"</td>" +
				           "<td>"+item.update_time+"</td>" +
				           "<td>"+item.operate_user+"</td>" +
				           "<td><a href='"+qiluAdmin.rootPath+""+item.url+"' class='btn btn-success' targer='_blank'>查看大图</a></td>" +
				           "<td><a href='"+qiluAdmin.rootPath+"/admin/toEditPic?itemId="+item.item_id+"&picId="+item.id+"' class='btn btn-warning'>编辑</a></td>" +
				           "<td><button class='btn btn-danger delpic' pic-id='"+item.id+"' >删除</button></td>" +
				           "</tr>";
			});
			$("#documentlist tbody").append(picsStr);
    	}
    	//文档删除
    	$(document).on("click", ".deldoc", function(){
    		var _this = this;
    		var docId = $(this).attr("doc-id");
    		if(confirm("确认删除此文档?")){
    			$.getJSON(qiluAdmin.URL.delDoc(docId)).then(function(data){
    				if(data.status == 200){
    					$(_this).parents("tr").remove();
    				}else{
    					alert("服务器出现错误，请联系技术人员!");
    				}
    			});
    		}
    	});
    	//图片删除
    	$(document).on("click", ".delpic", function(){
    		var _this = this;
    		var picId = $(this).attr("pic-id");
    		if(confirm("确认删除此图片?")){
    			$.getJSON(qiluAdmin.URL.delPic(picId)).then(function(data){
    				if(data.status == 200){
    					$(_this).parents("tr").remove();
    				}else{
    					alert("服务器出现错误，请联系技术人员!");
    				}
    			});
    		}
    	});
    	
    	$(document).on("click", "#searchBtn", function(){
			var key = $(".searchkey").val();
			if(key == "" || key == null) {
				alert("请输入搜索词！");
				return false;
			}
			window.location.href = qiluAdmin.rootPath + "/admin/search?key=" + key;
		});
    }
}