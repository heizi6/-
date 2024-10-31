
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>学生列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/layui-v2.5.4/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public.css" media="all">
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <!-- 搜索条件开始 -->
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>查询条件</legend>
        </fieldset>
        <form class="layui-form" method="post" id="searchForm">

            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">用户名:</label>
                    <div class="layui-input-inline" style="padding: 5px">
                        <input type="text" name="username" id="username" autocomplete="off"
                               class="layui-input layui-input-inline"
                               placeholder="请输入用户名称" style="height: 30px;border-radius: 10px">
                    </div>
                </div>
                <div class="layui-inline" id="baseCourseDiv">
                    <label class="layui-form-label">班级</label>
                    <div class="layui-input-inline">
                        <select id="baseCourseSelect" lay-search>
                            <option name="classes" value=""></option>
                        </select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">是否工作:</label>
                    <div class="layui-input-inline">
                        <input type="radio" name="working" value="1" title="已工作">
                        <input type="radio" name="working" value="0" title="未工作">
                    </div>
                </div>
                <button type="button"
                        class="layui-btn layui-btn-normal layui-icon layui-icon-search layui-btn-radius layui-btn-sm"
                        id="doSearch" style="margin-top: 4px">查询
                </button>
                <button type="reset"
                        class="layui-btn layui-btn-warm layui-icon layui-icon-refresh layui-btn-radius layui-btn-sm"
                        style="margin-top: 4px">重置
                </button>
            </div>

        </form>


        <div id="ad-btn" style="display: none;">
            <%--<button type="button" class="layui-btn layui-btn-sm data-add-btn layui-btn-radius" lay-event="add">新增
            </button>--%>
            <button type="button" class="layui-btn layui-btn-danger data-delete-btn layui-btn-sm layui-btn-radius"
                    lay-event="deleteBatch">批量删除
            </button>

        </div>
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>

    </div>
    <div id="UserBar" style="display: none;">
        <a class="layui-btn layui-btn-xs layui-btn-radius" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs layui-btn-radius" lay-event="delete">删除</a>
    </div>

    <!-- 添加的弹出层-->
    <div style="display: none;padding: 20px" id="saveDiv">
        <form class="layui-form" lay-filter="dataFrm" id="dataFrm" >
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">学生学号:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="id" lay-verify="required" placeholder="请输入学号"
                               autocomplete="off" class="layui-input ">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">学生姓名:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="username" lay-verify="required" placeholder="请输入用户姓名"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">学生密码:</label>
                    <div class="layui-input-inline">
                        <input type="password" name="password" placeholder="请输入密码"
                               autocomplete="off" lay-verify="required" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">入学时间:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="jionDate" id="joinDate2" value="" readonly="readonly" placeholder="入学时间"
                               autocomplete="off"  class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">主修专业:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="major" placeholder="请输入专业"
                               autocomplete="off" lay-verify="required" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">年级班级:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="grade" placeholder="请输入班级"
                               autocomplete="off"  class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block" style="text-align: center;padding-right: 120px">
                    <button type="button"
                            class="layui-btn layui-btn-normal layui-btn-md layui-icon layui-icon-release layui-btn-radius"
                            lay-filter="doSubmitAdd" lay-submit="">提交
                    </button>
                    <button type="reset"
                            class="layui-btn layui-btn-warm layui-btn-md layui-icon layui-icon-refresh layui-btn-radius">
                        重置
                    </button>
                </div>
            </div>
        </form>
    </div>

    <!-- 修改的弹出层-->
    <div style="display: none;padding: 20px" id="UpdateDiv">
        <form class="layui-form" lay-filter="dataFrm1" id="dataFrm1" >
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">学生学号:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="id" lay-verify="required" readonly = "readonly" placeholder="请输入学号"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">学生姓名:</label>
                    <div class="layui-input-inline">
                        <input  type="text" name="username" lay-verify="required" placeholder="请输入学生姓名"
                                autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">班级:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="classes" placeholder="请输入班级"
                               readonly="readonly" autocomplete="off" lay-verify="required" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">是否工作:</label>
                    <div class="layui-input-inline">
                        <input type="radio" name="working" value="0" lay-verify="otherReq" title="无工作">
                        <input type="radio" name="working" value="1" lay-verify="otherReq" title="已工作">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">工作:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="work" placeholder="工作"
                               autocomplete="off" lay-verify="required" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">工作地点:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="address" placeholder="工作地点"
                               autocomplete="off"  class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block" style="text-align: center;padding-right: 120px">
                    <button type="button"
                            class="layui-btn layui-btn-normal layui-btn-md layui-icon layui-icon-release layui-btn-radius"
                            lay-filter="doSubmit" lay-submit="">提交
                    </button>
                    <button type="reset"
                            class="layui-btn layui-btn-warm layui-btn-md layui-icon layui-icon-refresh layui-btn-radius">
                        重置
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/lib/layui-v2.5.4/layui.js" charset="utf-8"></script>
<script>

    var tableIns;
    layui.use(['laydate','jquery', 'layer', 'form', 'table'], function () {
        var laydate = layui.laydate;
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        laydate.render({
            elem: '#birthdate' //指定元素
        })
        laydate.render({
            elem: '#joinDate1' //指定元素
        })
        laydate.render({
            elem: '#joinDate2' //指定元素
        })
        tableIns = table.render({
            elem: '#currentTableId',
            url: "${pageContext.request.contextPath}/student/getStudent.action",
            method: "POST",
            where: {
                username : null,
                working : null,
            },
            toolbar: "#ad-btn",
            height: 'full-150',
            request: {
                //pageName: 'pageNum' //页码的参数名称，默认：page
                //,limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            limits: [10, 15, 20, 25, 50, 100,1000],
            limit: 15,
            page: true,
            cols: [[
                {type: "checkbox", width: 80, fixed: "left", align: "center"},
                {field: 'id', width: 80, title: 'ID', sort: true, align: "center"},
                {field: 'number', width: 250, title: '学号', align: "center"},
                {field: 'username', width: 190, title: '学生姓名', align: "center"},
                /*{field: 'password', width: 300, title: '密码', align: "center"},*/
                /* {field: 'major', width: 100, title: '主修专业', align: "center"},*/
                {field: 'classes', width: 190, title: '班级', sort: true, align: "center"},
                {
                    field: 'working', width: 180, title: '是否工作', sort: true, align: "center", templet: function (a) {
                        return a.working == '1' ? '<font color=green>已工作</font>' : '<font color=red>未工作</font>';
                    }
                },
                {field: 'work', width: 200, title: '工作', align: "center"},
                {field: 'address', width: 280, title: '工作地点', align: "center"},
                {title: '操作', minWidth: 220, templet: '#UserBar', fixed: "right", align: "center"}
            ]]
        });

        // 监听搜索操作
        $("#doSearch").click(function () {
            search();
        });

        //监听头部工具栏事件
        table.on("toolbar(currentTableFilter)", function (obj) {
            switch (obj.event) {
                case 'add':
                    openAddStudent();
                    break;
                case 'deleteBatch':
                    deleteBatch();
                    break;
            }
        });

        //监听表格复选框选择
        table.on('checkbox(currentTableFilter)', function (obj) {
            console.log(obj)
        });
        //监听每一行的编辑和删除
        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                //layer.alert('编辑行：<br>' + JSON.stringify(data))
                openUpdateCustomer(data);//把layui框架拿到的数据(data)放到编辑框里，传入data数据
            } else if (obj.event === 'delete') {
                layer.confirm('真的删除【' + data.username + '】这个学生吗？', function (index) {
                    //向服务端发送删除指令
                    $.post("${pageContext.request.contextPath}/student/deleteStudent.action", {id: data.id}, function (res) {
                        layer.msg(res.message);
                        //刷新数据表格
                        tableIns.reload();
                    })
                });
            }
        });


        var url;
        var mainIndex;


        //打开添加页面
        function openAddStudent() {
            mainIndex = layer.open({
                type: 1,
                title: '添加学生',
                content: $("#saveDiv"),
                area: ['720px', '320px'],
                success: function (index) {
                    //清空表单数据
                    $("#dataFrm")[0].reset();
                    url = "${pageContext.request.contextPath}/student/addStudent.action";
                }
            });
        }

        //打开修改页面
        function openUpdateCustomer(data) {
            mainIndex = layer.open({
                type: 1,
                title: '修改学生信息',
                content: $("#UpdateDiv"),
                area: ['720px', '320px'],
                success: function (index) {
                    form.val("dataFrm1", data);//把数据放到编辑框
                    url = "${pageContext.request.contextPath}/student/editStudent.action";
                }
            });
        }

        loadCourse();
        function loadCourse() {
            inputObj = $("#baseCourseDiv").find("select");
            var searchKey = inputObj.val();
            $.ajax({
                type: 'get',
                url: '${pageContext.request.contextPath}/course/getCourseList.action',
                data: {name: searchKey},
                timeout: 1500,
                success: function (msg) {
                    var select = $('#baseCourseSelect');
                    var options = "";
                    var data = msg.data;
                    for (var i in data) {
                        options += "<option value='" + data[i].name + "'>" + data[i].name + "</option>";
                    }
                    select.html(options);
                    form.render('select');
                    //渲染后要重新绑定
                    inputObj = $("#baseCourseDiv").find("select");
                    inputObj.bind("keyup", function (event) {
                        if (event.keyCode == "13") {
                            loadCourse();
                        }
                    });

                }
            });
        }

        //模糊查询
        function search() {

            var username = $("#username").val();
            var working = $('#searchForm input[name="working"]:checked').val();
            var classes = $('#baseCourseSelect').val()
            //var userVo = $("#searchForm").serialize();
            tableIns.reload({
                where: {
                    username: username,
                    working: working,
                    classes: classes
                }
            });
        }

        //保存（添加）
        form.on("submit(doSubmitAdd)", function (obj) {
            //序列化表单数据
            var params = $("#dataFrm").serialize();
            //alert(params);
            $.post(url, params, function (obj) {
                layer.msg(obj.message);
                //关闭弹出层
                layer.close(mainIndex)
                //刷新数据 表格
                tableIns.reload();
            })
        });
        //保存（修改）
        form.on("submit(doSubmit)", function (obj) {
            //序列化表单数据
            var params = $("#dataFrm1").serialize();
            // alert(params);
            $.post(url, params, function (obj) {
                layer.msg(obj.message);
                //关闭弹出层
                layer.close(mainIndex)
                //刷新数据 表格
                tableIns.reload();
            })
        });





        //批量删除
        function deleteBatch() {
            //得到选中的数据行
            var checkStatus = table.checkStatus('currentTableId')
                , data = checkStatus.data;

            // layer.alert(data.length);
            var params = "";
            $.each(data, function (i, item) {
                if (i == 0) {
                    params += "ids=" + item.id;
                } else {
                    params += "&ids=" + item.id;
                }
            });

            layer.confirm('真的要删除这些学生吗？', function (index) {
                layer.msg('处理中', {icon: 16});
                //向服务端发送删除指令
                $.post("${pageContext.request.contextPath}/student/deleteBatchStudent.action", params, function (res) {

                    layer.msg(res.message);
                    //刷新数据表格
                    // tableIns.reload();
                    search();
                })
            });


        }
    });
</script>

</body>
</html>



