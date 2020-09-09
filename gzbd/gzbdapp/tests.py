#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
from django.test import TestCase
from django.http import HttpResponse,JsonResponse
from django.core import serializers
from gzbdapp.models import User
import datetime
import random
import json
from django.shortcuts import render
# Create your tests here.
def test_db(request):
    # 插入数据
    user_model = User(user_name="ldx" + str(random.randint(1, 10)), password="123456",
                      create_date=datetime.datetime.now())
    user_model.save()
    users = User.objects.all()
    print("==== 查询所有 ====", users)
    users = User.objects.filter(id=1)
    print("==== 条件查询 ====", users)
    user = User.objects.get(id=1)
    print("==== 获取单个对象 ====", user)
    # 更新数据
    user.user_name = "hymanhu" + str(random.randint(1, 10))
    user.save()
    users = serializers.serialize("json", User.objects.order_by("user_name")[0:2])
    print("==== 排序&limit ====" + users)
    # 转换非自典型数据，需要设置 safe 为 false，否则抛出错误
    return JsonResponse(users, safe=False)