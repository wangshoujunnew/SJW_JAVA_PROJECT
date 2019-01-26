// 类似python的字符串格式化
String.prototype.format = function(args) {
    var result = this;
    if(arguments.length > 0) {
        if(arguments.length == 1 && typeof(args) == "object") {
            for(var key in args) {
                if(args[key] != undefined) {
                    var reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            }
        } else {
            for(var i = 0; i < arguments.length; i++) {
                if(arguments[i] != undefined) {
                    var reg = new RegExp("({)" + i + "(})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    return result;
}

function _log(info) { // info是一个数组
	if(Object.prototype.toString.call(info) != '[object Array]') {
		console.log(info)
	} else {
		info.map(function(ele) {
			console.log(ele)
		})
	}

}

function _forin(element_a, collect_b) {
	var a_type = Object.prototype.toString.call(element_a)
	var b_type = Object.prototype.toString.call(collect_b)
	_log(a_type)
	_log(b_type)
	return collect_b.indexOf(element_a) >= 0
}

function _dict(collect_a, collect_b) {
	var obj = {}
	for(var i = 0; i < collect_a.length; i++) {
		obj[collect_a[i]] = collect_b[i]
	}
	return obj
}

function _enumarator(collect_a) {
	var obj = {}
	collect_a.forEach(function(value, index) {
		obj[index] = value
	})
	return obj
}