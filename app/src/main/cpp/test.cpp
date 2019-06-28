//
// Created by jess on 19-6-11.
//
#include <jni.h>
#include <stdio.h>
#include "test.h"

JNIEXPORT jstring JNICALL Java_com_coldwizards_demoapp_ndk_NdkJniUtils_getString
  (JNIEnv *env, jobject)
{
    return env->NewStringUTF("Hi");
}