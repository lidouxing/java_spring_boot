#!/usr/bin/env python3
# -*- coding: utf-8 -*-
__author__ = "HymanHu";

'''
results
'''

class Result(object):
    def __init__(self, status, message):
        self.status = status;
        self.message = message;

    def result(self):
        return {"status": self.status, "message": self.message};