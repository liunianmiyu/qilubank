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
                    	if(data == "1"){
                    		window.location.href = qiluAdmin.rootPath + "/admin";
                    	}else{
                    		window.location.href = qiluAdmin.rootPath + "/";
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
    			templateProgramSelect(data);
    		});
    	};
    	
    	if(programId != null && programId != ""){
    		$.getJSON(qiluAdmin.URL.items(programId)).then(function(data){
    			templateItemSelect(data);
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
    	});
    	
    	$(document).on("change", "#programs", function(){
    		var programId = $(this).val();
    		$("#items").html("");
    		$.getJSON(qiluAdmin.URL.items(programId)).then(function(data){
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
    	
    	$(document).on("change", "#items", function(){
    		var itemId = $(this).val();
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
    	templateProgramSelect = function(data, moduleId) {
    		$("#programs").html("");
    		var programsStr = "";
			$(data).each(function(index, item){
				if(programId != "" && programId == item.id){
					programsStr += "<option value='"+item.id+"' selected='selected'>"+item.name+"</option>" 
				}else{
					programsStr += "<option value='"+item.id+"'>"+item.name+"</option>" 
				}
			});
			$("#programs").html(programsStr);
    	};
    	//拼装itmes select
    	templateItemSelect = function(data, programId) {
    		$("#items").html("");
    		var itemssStr = "";
			$(data).each(function(index, item){
				itemssStr += "<option value='"+item.id+"' title='"+item.name+"'>"+item.name.substring(0,20)+"</option>" 
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
				           "<td>-</td>" +
				           "<td>"+item.create_time+"</td>" +
				           "<td>"+item.operate_user+"</td>" +
				           "<td><a href='"+qiluAdmin.rootPath+"/operate/showDoc?id="+item.id+"' class='btn btn-success' target='_blank'>详情</a></td>" +
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
				           "<td>图片</td>" +
				           "<td>"+item.name+"</td>" +
				           "<td>"+item.url+"</td>"+
				           "<td>"+item.create_time+"</td>" +
				           "<td>"+item.operate_user+"</td>" +
				           "<td><a href='javascript:;' disabled='true' class='btn btn-success'>详情</a></td>" +
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
    	})
    }
}