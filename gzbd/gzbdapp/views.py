#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
from django.http import HttpResponse, JsonResponse
from django.shortcuts import render
from django.core import serializers
##这是导入类似于pojo，然后写入数据，save保存。
from gzbdapp.models import *;
import datetime,random,json;
from common.result import *;

def success_result():
    return {"status":200,"message":"Success"}

def failed_result():
    return {"status":500,"message":"Failed"}

def hello_world(request):
    user_model = User(user_name="ldx" + str(random.randint(1, 12)), password="123456",
                      create_date=datetime.datetime.now())
    user_model.save();
    ##获得所有的数据
    users=User.objects.all().filter()
    # return HttpResponse("xxx");
    #序列化
    users = serializers.serialize("json", User.objects.order_by("user_name")[0:2])
    print("==== 排序&limit ====" + users)
    #需要设置safe，否则报错，转换非字典型数据
    return JsonResponse(users,safe=False);
    # return

def hello_world1(request):
    return HttpResponse("Hello World1!");

def hello_world2(request):
    return ("Hello World2!");

#这里是
def hello_world_page(request):
    context = {}
    context["name"] = "ldx"
    return render(request, "hello_world.html", context)

'''
http://127.0.0.1:8080/user/1 ---- get
http://127.0.0.1:8080/user/1 ---- delete
'''
def user(request, id):
    if request.method == "GET":
        user = User.objects.get(id=id);
        print(user.user_name);
        user_result = {};
        user_result["id"] = user.id;
        user_result["user_name"] = user.user_name;
        user_result["user_email"] = user.user_email;
        user_result["password"] = user.password;
        user_result["create_date"] = user.create_date;
        return JsonResponse(user_result, safe=False);
    elif request.method == "DELETE":
        User.objects.filter(id=id).delete();
        return JsonResponse(Result(status=200, message="delete success").result(), safe=False);
    else:
        return JsonResponse(Result(status=200, message="No opration for user").result(), safe=False);

'''
http://127.0.0.1:8080/user?userNmae=hujiang&password=111111 ---- get
http://127.0.0.1:8080/user ---- post
{"user_name":"hujiang","password":"111111"}
http://127.0.0.1:8080/user ---- put
{"id":"1","user_name":"hujiang","password":"111111"}
'''
def user_(request):
    if request.method == "GET":
        user_name = request.GET.get("userNmae");
        password = request.GET.get("password");
        user = User.objects.filter(user_name=user_name, password=password).first();
        if user:
            user_result = {};
            user_result["id"] = user.id;
            user_result["user_name"] = user.user_name;
            user_result["user_email"] = user.user_email;
            user_result["password"] = user.password;
            user_result["create_date"] = user.create_date;
            return JsonResponse(user_result, safe=False);
        else:
            return JsonResponse(Result(status=500, message="No user find").result(), safe=False);
        pass;
    elif request.method == "POST":
        # 获取查询参数、form表单参数
        # user_name = request.POST.get("userName")
        # password = request.POST.get("password")
        # 获取 json 数据
        query = json.loads(request.body);
        user_name = query.get("userName");
        password = query.get("password");
        user = User(user_name=user_name, password=password, create_date=datetime.datetime.now())
        user.save()
        return JsonResponse(Result(status=200, message="Insert success").result(), safe=False);
    elif request.method == "PUT":
        query = json.loads(request.body);
        id = query.get("userId");
        user_name = query.get("userName");
        user_email = query.get("userEmail");
        user = User.objects.get(id=id);
        user.user_name = user_name;
        user.user_email = user_email;
        user.save()
        return JsonResponse(Result(status=200, message="Update success").result(), safe=False);
    else:
        return JsonResponse({"status": 200, "message": "No opration for user"}, safe=False);

'''
http://127.0.0.1:8080/gzbd
'''
def gzbd(request):
    gzbds = Epidemic.objects.all()[0:7]
    gzbd_list = [];
    for item in gzbds:
        epidemic = {};
        epidemic["id"] = item.id;
        epidemic["region"] = item.region;
        epidemic["date"] = item.date;
        epidemic["diagnosis"] = item.diagnosis;
        epidemic["overseas_import"] = item.overseas_import;
        epidemic["cure"] = item.cure;
        epidemic["death"] = item.death;
        gzbd_list.append(epidemic)
    return JsonResponse(gzbd_list, safe=False);

'''
http://127.0.0.1:8080/register
'''
def register(request):
    context = {}
    return render(request, "account/register.html", context);

'''
http://127.0.0.1:8080/login
'''
def login(request):
    context = {}
    return render(request, "account/login.html", context);

'''
http://127.0.0.1:8080/dashbord ---- get
'''
def dashbord(request):
    context = {}
    return render(request, "common/dashboard.html", context);