"""gzbd URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path

# -*- coding: utf-8 -*-
__author__ = "HymanHu";
from django.contrib import admin
from django.urls import path, re_path
from django.conf.urls import url
from gzbdapp import views
'''
函数作为参数传递，不加括号，相当于绑定事件，事件触发再执行
path路径末尾带/，Django中设置了APPEND_SLASH=True, 当URL后面缺少斜杠时，会自动拼上斜杠，并重定向
三种路径写法，第一种是path，尾部假/。第二种re_path，第三种url,这三种均为映射地址，真正地址在后面映射到app的views中
'''
urlpatterns = [
    path('admin/', admin.site.urls),
    path(r'helloWorld/', views.hello_world),
    re_path(r'^helloWorld1$', views.hello_world1),
    url(r'^userPage/', views.hello_world_page),
    # -------user-------
    re_path(r'^$user/(\d+)', views.user),
    re_path(r'^user/(\d+)$', views.user),
    re_path(r'^user$', views.user_),
    re_path(r'^register$', views.register),
    re_path(r'^login$', views.login),
    # ------ gzbd -----
    re_path(r'^gzbd$', views.gzbd),
    # ------ common -----
    re_path(r'^dashbord$', views.dashbord),

]
