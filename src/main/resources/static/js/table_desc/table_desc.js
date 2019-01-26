$(function() {
    $('#add_project').click(function() {
        $('body').append($('.project').eq(0).clone(true))
    })

    $('#del_project').click(function() {
        var projects = $('.project')
        if(projects.length > 1) {
            $(projects[projects.length - 1]).remove()
        }
    })

    // 全选和全不选
    // $("#boxSelected").prop("checked",true)，该方法可以重复勾选
    // 若是用$("#boxSelected").attr("checked",true)方法，则只会勾选一次
    $("input[name=select]").click(function() {
        // 得到他相邻的
        var checkbox_v = $(this).parent().find('input[name=desc]')
        if($(this).parent().find('input[name=select]')[0] == this) { // 点击的是全选
            for(var i = 0; i < checkbox_v.length; i++) {
                $(checkbox_v[i]).prop('checked', true)
            }
            // 并将全部取消设置为不选状态, 删除这个checked属性
            $($(this).parent().find('input[name=select]')[1]).removeAttr('checked')
        } else {
            for(var i = 0; i < checkbox_v.length; i++) {
                $(checkbox_v[i]).prop('checked', false)
            }
            $($(this).parent().find('input[name=select]')[0]).removeAttr('checked')
        }
    })

    // 生成sql
    $('.makesql').click(function() {

        // 得到表
        table_name = $(this).parent().find('.search-query').val()
        values = []
        $(this).parent().parent().find('input[name=desc]:checked').each(function(index, e) {
            values[index] = $(e).val()
        })
        //写到输出框中取
        table_name_fix = table_name.split('.')
        table_name_fix = table_name_fix[table_name_fix.length - 1]
        $(this).parent().parent().find('textarea').val('(\nselect \n' + String(values).replace(/,/g, ',\n') + ' \nfrom ' + table_name + ' \n) ' + table_name_fix + '_t')
    })

    // 提价事件, 从服务器返回来的表的字段数据
    //				[{
    //					"Field": "id",
    //					"Type": "bigint(20) unsigned",
    //					"Null": "NO",
    //					"Extra": "auto_increment",
    //					"Key": "PRI"
    //				}]

    // Impala数据表的字段返回来的值不一样,[{"name":"mark"}]
    $('.submit').click(function() {
        table_name = $(this).parent().find('.search-query').val()
        table_files = $(this).parent().parent().parent().find('.table_fileds')
        checkbox_values = []
        index = 0
        $.ajax({
            type: "post",
            data: {
                "sql": "describe " + table_name
            },
            url: "/query",
            // async: false,
            dataType: "json",
            timeout:0, // 毫秒, 请求超时这个怎么测试
            success: function(data) {
                // 这里需要对数据进行输出, 否则输出的是[Object,Object]字符串
                // JSON和STR的互相转换JSON.parse(str), json.toJSONString()
                // 以及数组到字符串,字符串到数组转化JSON.stringify(jsonObj)
                // 字符串替换replace(/},{/g,'},<br/>{')
                // impala返回的列名为d['name'], mysql为Field
                data.forEach(function(d) {
                    checkbox_values[index++] = JSON.stringify(d['Field']).replace(/"/g, '')
                })

                // 制作checkbox
                // console.log($(this).parent())
                //								console.log(table_files)
                table_files.children().remove()
                checkbox_values.forEach(function(d) {
                    html_text = $('<div class="checkbox"> <label class="label"> <input type="checkbox" class="form-control" name="desc" value="{0}" /> {0} </label> </div>'.format(d))
                    table_files.append(html_text)
                })
            },
            error: function() {
                console.log('fail')
                alert('表不存在, 或者请求超时 ... ')
            }

        });

    })
})