#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";
from django.db import models
# Create your models here.
class User(models.Model):
    id = models.AutoField(primary_key=True)
    user_name = models.CharField(max_length=20,blank = True,null = True)
    password = models.CharField(max_length=20,blank = True,null = True)
    create_date = models.DateTimeField(auto_now=True,blank = True,null = True)
class Epidemic(models.Model):
    id = models.AutoField(primary_key=True)
    region = models.CharField(max_length=20,blank = True,null = True)
    date = models.CharField(max_length=20,blank = True,null = True)
    diagnosis = models.IntegerField(blank = True,null = True)
    overseas_import = models.IntegerField(blank = True,null = True)
    cure = models.IntegerField(blank = True,null = True)
    death = models.IntegerField(blank = True,null = True)
    therapy = models.IntegerField(blank = True,null = True)
    observation = models.IntegerField(blank = True,null = True)
