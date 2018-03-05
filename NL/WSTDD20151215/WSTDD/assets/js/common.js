/**
 * Created by xp on 6/6/15.
 */
$("button").bind("touchstart",function(e){
    $(this).addClass("active");
}).bind("touchend",function(e){
    $(this).removeClass("active");
}).bind("touchmove",function(e){
    $(this).removeClass("active");
});
/**
 * ajax统一调用接口
 * url http地址
 * data jsonobject 数据
 * successfn 成功处理回调
 * errorfn 失败处理回调
 */
function myAjax(url, data, successfn, errorfn)
{
    $.ajax(
        {
            url: url,
            type:"post",
            async:true,
            data:{'data':JSON.stringify(data)},
            dataType:"html",
            timeout:"20000",
            error: function(XMLHttpRequest, textStatus, errorThrown){
                errorfn(XMLHttpRequest, textStatus, errorThrown);
            },
            success: function(data){
                successfn(data);
            }
        });
}
function ajaxJson(url, data, successfn, errorfn)
{
    $.ajax(
        {
            url: url,
            type:"post",
            async:true,
            data:data,
            dataType:"json",
            timeout:"20000",
            error: function(XMLHttpRequest, textStatus, errorThrown){
                errorfn(XMLHttpRequest, textStatus, errorThrown);
            },
            success: function(data){
                successfn(data);
            }
        });
}

/**
 * 删除数组指定值
 * @param val
 * @returns {Number}
 */
	Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
	if (this[i] == val) return i;
	}
	return -1;
	};


	Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
	this.splice(index, 1);
	}
	};
	
	function showObj(obj){
		var objStr="";
		for(items in obj){
			objStr += items + "=" +obj[items] + '\n';
		}
		alert(objStr);
	}
	
	/**
	 * 根据时间来判断周几
	 * @param time
	 * @returns
	 */
	function convertTimeToWeek(time)
	{
		 var weekDay = ["周天", "周一", "周二", "周三", "周四", "周五", "周六"];
			var dateStr = time; //输入框时间
			var myDate = new Date(Date.parse(dateStr.replace(/-/g, "/"))); 
			return weekDay[myDate.getDay()];   
	}
	
	
	/**
	 * 把10以下的 1变01  2变02
	 * @param num
	 */
	function convertDateNum2Double(num)
	{
		var newNum ="";
		if(num<10)
		{
			newNum = "0"+num;
		}
		else
		{
			newNum = num;
		}
		return newNum;
	}
	
	/**
	 *    去空格
	 * @param str
	 */
	function trim(str)
	{
		return str.replace(/\s+/g,"");       
	}
	//网站统计
	var _hmt = _hmt || [];
	(function() {
	  var hm = document.createElement("script");
	  hm.src = "//hm.baidu.com/hm.js?28dfa1556c974344054c49db767c0b85";
	  var s = document.getElementsByTagName("script")[0]; 
	  s.parentNode.insertBefore(hm, s);
	})();
	
	/**
	 * 校验输入框只能输入浮点型数字 
	 *  小数点后保留两位 
	 * @param dom
	 * @returns
	 */
	function valDoubleNum(dom)
	{
		  var reg = /^\d+\.?\d{0,2}$/;
	        var isMoneyFormatRight = reg.test(dom.value);

	        if(!isMoneyFormatRight)
	        	{
	        		dom.value = "";
	        	}
		
	}
	/**
	 * @param args  json
	 * {
	 * 		type:"activity",
	 * 		id: "abcdefg",
	 * 		success: function(){}
	 * }
	 */
	function shareStatic(args){
		if(!args.path){
			args.path = "";
		}
		$.ajax(
		        {
		            url: args.path+args.type+"/static/share/"+args.id,
		            type:"get",
		            async:true,
		            timeout:"20000",
		            error: function(XMLHttpRequest, textStatus, errorThrown){
		            	if(args&&args.error){
		            		args.error(data);
		            	}
		            },
		            success: function(data){
		            	if(args&&args.successfn){
		            		args.successfn(data);
		            	}
		            }
		        });
	}
	