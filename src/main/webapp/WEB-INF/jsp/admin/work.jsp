
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>毕业生职业统计</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/layui-v2.5.4/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public.css" media="all">
</head>
<body style="height: 100%; margin: 0">
<div class="layuimini-container">
    <form class="layui-form" method="post" id="searchForm">

        <div class="layui-form-item">
            <div class="layui-inline" id="baseCourseDiv">
                <label class="layui-form-label">班级</label>
                <div class="layui-input-inline">
                    <select id="baseCourseSelect" lay-search>
                        <option name="classes" value=""></option>
                    </select>
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
</div>
<div id="container" style="height: 100%"></div>
<script src="${pageContext.request.contextPath}/lib/layui-v2.5.4/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/echarts/js/echarts.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/echarts/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
    layui.use(['jquery', 'form', 'table'], function () {
        var $ = layui.jquery;
        var form = layui.form;
        var table = layui.table;
        search();
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
        function search(){
            $.get("${pageContext.request.contextPath}/student/studentWork.action?classes="+$("#baseCourseSelect").val(),function (data) {
                var dom = document.getElementById("container");
                var myChart = echarts.init(dom);
                var app = {};
                option = null;
                option = {
                    title: {
                        text: '毕业生职业统计',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: data
                    },
                    series: [
                        {
                            name: '毕业生数量(占比)',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: data,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                if (option && typeof option === "object") {
                    myChart.setOption(option, true);
                }
            })
        }
        $("#doSearch").click(function () {
            search();
        });
    });
</script>
</body>
</html>
