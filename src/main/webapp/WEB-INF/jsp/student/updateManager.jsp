
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>学生信息修改</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/layui-v2.5.4/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public.css" media="all">
</head>
<body>
<div class="layuimini-container">
    <div class="layuimini-main">
        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>

    </div>
    <div id="UserBar" style="display: none;">
        <%--<a class="layui-btn layui-btn-xs layui-btn layui-btn-normal layui-btn-radius" lay-event="add">生成信息</a>--%>
        <a class="layui-btn layui-btn-xs layui-btn-radius" lay-event="edit">编辑</a>
        <%--<a class="layui-btn layui-btn-danger layui-btn-xs layui-btn-radius" lay-event="delete">删除</a>--%>
    </div>

    <!-- 添加和修改的弹出层-->
    <div style="display: none;padding: 20px" id="saveOrUpdateDiv">
        <form class="layui-form" lay-filter="dataFrm" id="dataFrm">
            <div class="layui-form-item">
                <input type="hidden" name="id" placeholder="用户id：用来方便通过id修改，在页面中并不现实这个" autocomplete="off" class="layui-input">
                <div class="layui-inline">
                    <label class="layui-form-label">学号:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="number" lay-verify="required" placeholder="请输入用户学号"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">用户姓名:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="username" lay-verify="required" placeholder="请输入用户姓名"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">邮箱:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="email" lay-verify="required" placeholder="请输入邮箱"
                               autocomplete="off" class="layui-input">
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
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">性别:</label>
                    <div class="layui-input-inline">
                        <input type="radio" name="sex" value="0" checked="checked" lay-verify="otherReq" title="男">
                        <input type="radio" name="sex" value="1" lay-verify="otherReq" title="女">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">是否工作:</label>
                    <div class="layui-input-inline">
                        <input type="radio" name="working" value="1" title="已工作">
                        <input type="radio" name="working" value="0" title="未工作">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">

                <div class="layui-inline">
                    <label class="layui-form-label">工作</label>
                    <div class="layui-input-block">
                        <input type="text" name="work" lay-verify="required" placeholder="请输入工作"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">工作地点</label>
                    <div class="layui-input-block">
                        <input type="text" name="address" lay-verify="required" placeholder="请输入工作地点"
                               autocomplete="off" class="layui-input">
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
        var dtree = layui.dtree;
        laydate.render({
            elem: '#birthdate' //指定元素
        })
        tableIns = table.render({
            elem: '#currentTableId',
            url: "${pageContext.request.contextPath}/user/getMe.action",
            method: "POST",
            where: {
            },
            toolbar: "#ad-btn",
            height: 'full-150',
            request: {
                //pageName: 'pageNum' //页码的参数名称，默认：page
                //,limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },
            cols: [[
                {field: 'id', width: 60, title: 'ID', sort: true, align: "center"},
                {field: 'number', width: 220, title: '教工号/学号', sort: true, align: "center"},
                {field: 'username', width: 180, title: '用户名', align: "center"},
                {
                    field: 'sex', width: 95, title: '性别', sort: true, align: "center", templet: function (a) {
                        return a.sex == '0' ? '<font color=blue>男</font>' : '<font color=red>女</font>';
                    }
                },
                {field: 'email', width: 280, title: '邮箱', align: "center"},
                {
                    field: 'working', width: 165, title: '是否工作', sort: true, align: "center", templet: function (d) {
                        return d.working == '1' ? '<font color=green>已工作</font>' : '<font color=red>未工作</font>';
                    }
                },
                {field: 'work', width: 180, title: '工作', align: "center"},
                {field: 'address', width: 290, title: '工作地点', align: "center"},
                {field: 'classes', width: 180, title: '班级', align: "center"},
                {title: '操作', minWidth: 220, templet: '#UserBar', fixed: "right", align: "center"}
            ]]
        });

        // 监听搜索操作
        $("#doSearch").click(function () {
            search();
        });

        // 监听添加操作，后面的可以用
        /*   $(".data-add-btn").on("click", function () {

              openAddUser();
              return true;
          });*/

        //监听头部工具栏事件
        table.on("toolbar(currentTableFilter)", function (obj) {
            switch (obj.event) {
                case 'add':
                    openAddUser();
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
        //监听每一行的编辑和删除和生成老师/学生的表
        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                //layer.alert('编辑行：<br>' + JSON.stringify(data))
                openUpdateCustomer(data);//把layui框架拿到的数据(data)放到编辑框里，传入data数据
            }
        });

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


        var url;
        var mainIndex;



        //打开修改页面
        function openUpdateCustomer(data) {
            mainIndex = layer.open({
                type: 1,
                title: '修改客户',
                content: $("#saveOrUpdateDiv"),
                area: ['750px', '380px'],
                success: function (index) {
                    form.val("dataFrm", data);//把数据放到编辑框
                    url = "${pageContext.request.contextPath}/user/editStudent.action";
                }
            });
        }


        //保存（添加和修改公用）
        form.on("submit(doSubmit)", function (obj) {
            //序列化表单数据
            var params = $("#dataFrm").serialize();
            console.log(params);
            var abc = params + "&classes="+ $("#baseCourseSelect").val();
            console.log(abc);
            // alert(params);
            $.post(url, abc, function (obj) {
                layer.msg(obj.msg);
                //关闭弹出层
                layer.close(mainIndex)
                //刷新数据 表格
                tableIns.reload();
            })
        });

    });
</script>

</body>
</html>