all_sql = {} // 存放本次查询用到的所有的sql
$(function() {

    // 查询按钮evt
    $('.select').click(function(ev) {
        var evobj = (ev || window.event).target
        console.log(evobj)
        var obj = {}
        // 查询当前table有多少个tr
        obj['id'] = $('tr').length + 1

        obj['input'] = '' // 得到多少输入
        obj['output'] = ''
        obj['sql'] = $(this).prev().val() // 输入的sql
        all_sql[obj['sql']] = obj['sql']

        // 输入参数,并对sql进行替换
        // 进行$替换操作
        // 通过输入框得到有几个$
        var input_index = 1
        $(this).parent().parent().find('input').each(function() {
            obj['input'] += $(this).val() + ','
            // 因为这里$是关键字不好使, 改成#
            obj['sql'] = obj['sql'].replace(new RegExp('#' + input_index, 'g'), $(this).val())
            input_index += 1
        })

        // ajax请求,得到sql执行后的输出是一个json数组
        $.ajax({
            type: "post",
            data: {
                "sql": obj['sql']
            },
            url: "/query",
            dataType: "json",
            async: false,
            timeout:40, // 毫秒, 请求超时这个怎么测试
            success: function(data) {
                // 这里需要对数据进行输出, 否则输出的是[Object,Object]字符串
                // JSON和STR的互相转换JSON.parse(str), json.toJSONString()
                // 以及数组到字符串,字符串到数组转化JSON.stringify(jsonObj)
                // 字符串替换replace(/},{/g,'},<br/>{')
                data.forEach(d => {
                    obj['output'] += JSON.stringify(d) + '<br/>'
                })

                // 如果没有拿到数据,就不追加数据到table中了
                if(obj['output'] != '') {
                    // 将输出赋值到当前的output文本框中, 需要将所有的<br>标签替换成换行符号
                    $(evobj).next().next().next().val(obj['output'].replace(/<br\/>/g, '\n'))
                    //						 生成tr
                    html = '<tr>'
                    for(var i in obj) {
                        html += '<td>' + obj[i] + '</td>'
                    }
                    html += '</tr>'
                    $('tbody').append(html)

                    // ====== 后续额外的操作
                    // 有输出表示是有效的,将此次运行的sql放入到总sql集合中
                    // all_sql[obj['sql']] = obj['sql']
                    console.log(all_sql) // 查看一下目前有了多少sql
                    // 将所有的sql放入到当前有的sql面板中
                    // 先移除所有的tag,然后添加
                    var select_tag = $('select')
                    select_tag.children().remove()
                    for(var k in all_sql){
                        var tmp = $('<option value="1">1</option>')
                        tmp.attr('value',k)
                        tmp.html(k)
                        select_tag.append(tmp)
                    }

                }
            },
            error:function(){
                console.log('没有拿到数据 . . . ')
                alert('没有拿到数据 ... ')
            }
        });
    })

    $('#add_select_frame').click(function() {
        console.log('add')
        $('#select_frame_parent').append($('.select_frame').eq(0).clone(true))
    })
    $('#del_select_frame').click(function() {
        e_length = $('.select_frame').length
        console.log(e_length)
        if(e_length <= 1) {} else {
            $('.select_frame').eq(e_length - 1).remove()
        }
    })
    $('.add_input').click(function() {
        $(this).parent().parent().parent().append($('.input_text_select').eq(0).clone())
        console.log('add input text select')
    })
    $('.del_input').click(function() {
        e_length = $('.input_text_select').length
        if(e_length <= 1) {} else {
            $('.input_text_select').eq(e_length - 1).remove()
        }
        console.log('add input text select')
    })
    $('.clear').click(function() {
        $(this).parent().find('textarea').val('')
        $(this).parent().parent().find('input').val('')
    })

    // 上选按钮事件
    $('#up_select').click(function(){
        // 得到所有input:textarea
        var selectedOp = $('option:selected')
        var input_sql = $('.input_sql')
        for (var i = 0; i<input_sql.length && i<selectedOp.length;i++) {
            $(input_sql[i]).val($(selectedOp[i]).val())
        }
    })
})