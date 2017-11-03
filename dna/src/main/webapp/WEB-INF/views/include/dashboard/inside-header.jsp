<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!-- 头部 -->
<header>
    <!-- logo -->
    <div class="am-fl tpl-header-logo">
        <a href="${ctxroot}/index"><img src="${ctxStatic}/dashboard/img/logo.png" alt=""></a>
    </div>
    <!-- 右侧内容 -->
    <div class="tpl-header-fluid">
        <!-- 侧边切换 -->
        <div class="am-fl tpl-header-switch-button am-icon-list">
                    <span>

                </span>
        </div>
        <!-- 其它功能-->
        <div class="am-fr tpl-header-navbar">
            <ul>
                <!-- 欢迎语 -->
                <li class="am-text-sm tpl-header-navbar-welcome">
                    <a href="javascript:;">欢迎你, <span>Amaze UI</span> </a>
                </li>

                <!-- 退出 -->
                <li class="am-text-sm">
                    <a href="javascript:;" id="sign-out" data-am-modal="{target: '#my-logout',height: 100}">
                        <span class="am-icon-sign-out"></span> 退出
                    </a>
                </li>
            </ul>
        </div>
    </div>

</header>
<div class="am-modal am-modal-no-btn" tabindex="-1" id="my-logout">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">确认退出？
            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
        <div class="am-modal-bd">
            <a href="${ctxroot}/d/logout" type="button" class="am-btn am-btn-primary" >确定</a>
        </div>
    </div>
</div>