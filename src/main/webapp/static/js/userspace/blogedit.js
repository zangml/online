/*!
 * blogedit.html 页面脚本.
 *
 */
"use strict";
//# sourceURL=blogedit.js

// DOM 加载完再执行
$(function() {
 
	// 初始化 md 编辑器
    $("#md").markdown({
        language: 'zh',
        fullscreen: {
            enable: true
        },
        resize:'vertical',
        localStorage:'md',
        imgurl: 'http://localhost:8081',
        base64url: 'http://localhost:8081'
    });


    (function ($) {
        $.fn.extend({
            insertAtCaret: function (myValue) {
                var $t = $(this)[0];
                if (document.selection) {
                    this.focus();
                    var sel = document.selection.createRange();
                    sel.text = myValue;
                    this.focus();
                } else
                if ($t.selectionStart || $t.selectionStart == '0') {
                    var startPos = $t.selectionStart;
                    var endPos = $t.selectionEnd;
                    var scrollTop = $t.scrollTop;
                    $t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
                    this.focus();
                    $t.selectionStart = startPos + myValue.length;
                    $t.selectionEnd = startPos + myValue.length;
                    $t.scrollTop = scrollTop;
                } else {
                    this.value += myValue;
                    this.focus();
                }
            }
        })
    })(jQuery);

    // 初始化下拉
    $('.form-control-chosen').chosen();
    
    
    // 初始化标签
    $('.form-control-tag').tagsInput({
    	'defaultText':'输入标签'
 
    });
 
 	$("#uploadImage").click(function() {
		$.ajax({
		    url: '/u/'+ $(this).attr("userName") +'/blogs/image/upload',
		    type: 'POST',
		    cache: false,
		    data: new FormData($('#uploadformid')[0]),
		    processData: false,
		    contentType: false,
		    success: function(data){
		    	// var mdcontent=$("#md").val();
		    	//  $("#md").val(mdcontent + "\n![]("+data.data +") \n");
                $("#md").insertAtCaret("\n![]("+data.data +") \n");
	         }
		}).done(function(res) {
			$('#file').val('');
		}).fail(function(res) {});
 	})


 	// 发布博客
 	$("#submitBlog").click(function() {

		$.ajax({
		    url: '/u/'+ $(this).attr("userName") + '/blogs/edit',
		    type: 'POST',
			contentType: "application/json; charset=utf-8",
		    data:JSON.stringify({
				"id":$('#blogId').val(),
		    	"title": $('#title').val(), 
		    	"summary": $('#summary').val() , 
		    	"content": $('#md').val(), 
		    	"catalogId":$('#catalogSelect').val(),
		    	"tags":$('.form-control-tag').val()
		    	}),
			 success: function(data){
				 if (data.success) {
					// 成功后，重定向
					 window.location = data.body;
				 } else {
					 toastr.error("error!"+data.message);
				 }
				 
		     },
		     error : function(jqXHR) {
		    	 toastr.error("error!");
                 // alert(jqXHR.responseText);
		     }
		})
 	})
 	
 	
});