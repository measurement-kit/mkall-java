// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
#ifndef SRC_MKALL_HEADERS_MKALL_UTIL_H
#define SRC_MKALL_HEADERS_MKALL_UTIL_H

#include <iostream>

/// MKALL_THROW_RUNTIME_EXCEPTION throws a runtime exception.
#define MKALL_THROW_RUNTIME_EXCEPTION(text)                    \
  do {                                                         \
    jclass exc = env->FindClass("java/lang/RuntimeException"); \
    if (exc == nullptr) {                                      \
      abort();                                                 \
    }                                                          \
    env->ThrowNew(exc, text);                                  \
  } while (0)

/// MKALL_THROW_EINTERNAL throws an invalid argument error.
#define MKALL_THROW_EINVAL MKALL_THROW_RUNTIME_EXCEPTION("Invalid argument")

/// MKALL_THROW_EINTERNAL throws an internal error.
#define MKALL_THROW_EINTERNAL MKALL_THROW_RUNTIME_EXCEPTION("Internal error")

/// MKALL_CALL calls a void method of a type.
#define MKALL_CALL(java_func, cxx_func, cxx_type)                   \
  JNIEXPORT void JNICALL                                            \
      Java_io_ooni_mk_##java_func(JNIEnv *, jclass, jlong handle) { \
    cxx_func((cxx_type *)handle);                                   \
  }

/// MKALL_DELETE defines a JNI deleter for the specified type.
#define MKALL_DELETE MKALL_CALL

/// MKALL_GET_BOOLEAN returns a boolean value.
#define MKALL_GET_BOOLEAN(java_func, cxx_func, cxx_type)               \
  JNIEXPORT jboolean JNICALL                                           \
      Java_io_ooni_mk_##java_func(JNIEnv *env, jclass, jlong handle) { \
    if (handle == 0) {                                                 \
      MKALL_THROW_EINVAL;                                              \
      return JNI_FALSE;                                                \
    }                                                                  \
    return cxx_func((cxx_type *)handle) ? JNI_TRUE : JNI_FALSE;        \
  }

/// MKALL_GET_BYTE_ARRAY returns a byte array.
#define MKALL_GET_BYTE_ARRAY(java_func, cxx_func, cxx_type)               \
  JNIEXPORT jbyteArray JNICALL                                            \
      Java_io_ooni_mk_##java_func(JNIEnv *env, jclass, jlong handle) {    \
    if (handle == 0) {                                                    \
      MKALL_THROW_EINVAL;                                                 \
      return nullptr;                                                     \
    }                                                                     \
    const uint8_t *base = nullptr;                                        \
    size_t count = 0;                                                     \
    /* Implementation note: both in Java and Android jsize is jint */     \
    cxx_func((cxx_type *)handle, &base, &count);                          \
    if (base == nullptr || count <= 0 || count > INT_MAX) {               \
      MKALL_THROW_EINTERNAL;                                              \
      return nullptr;                                                     \
    }                                                                     \
    jbyteArray array = env->NewByteArray((jsize)count);                   \
    if (array == nullptr) {                                               \
      /* Exception should already be pending. */                          \
      return nullptr;                                                     \
    }                                                                     \
    env->SetByteArrayRegion(array, 0, (jsize)count, (const jbyte *)base); \
    return array;                                                         \
  }

/// MKALL_GET_DOUBLE returns a double value.
#define MKALL_GET_DOUBLE(java_func, cxx_func, cxx_type)                \
  JNIEXPORT jdouble JNICALL                                            \
      Java_io_ooni_mk_##java_func(JNIEnv *env, jclass, jlong handle) { \
    if (handle == 0) {                                                 \
      MKALL_THROW_EINVAL;                                              \
      return 0.0;                                                      \
    }                                                                  \
    return cxx_func((cxx_type *)handle);                               \
  }

/// MKALL_GET_LONG returns a long value.
#define MKALL_GET_LONG(java_func, cxx_func, cxx_type)                  \
  JNIEXPORT jlong JNICALL                                              \
      Java_io_ooni_mk_##java_func(JNIEnv *env, jclass, jlong handle) { \
    if (handle == 0) {                                                 \
      MKALL_THROW_EINVAL;                                              \
      return 0L;                                                       \
    }                                                                  \
    return cxx_func((cxx_type *)handle);                               \
  }

/// MKALL_GET_POINTER returns a pointer value.
#define MKALL_GET_POINTER(java_func, cxx_func, cxx_type)               \
  JNIEXPORT jlong JNICALL                                              \
      Java_io_ooni_mk_##java_func(JNIEnv *env, jclass, jlong handle) { \
    if (handle == 0) {                                                 \
      MKALL_THROW_EINVAL;                                              \
      return 0L;                                                       \
    }                                                                  \
    return (jlong)cxx_func((cxx_type *)handle);                        \
  }

/// MKALL_GET_STRING returns a string value.
#define MKALL_GET_STRING(java_func, cxx_func, cxx_type)                \
  JNIEXPORT jstring JNICALL                                            \
      Java_io_ooni_mk_##java_func(JNIEnv *env, jclass, jlong handle) { \
    if (handle == 0) {                                                 \
      MKALL_THROW_EINVAL;                                              \
      return nullptr;                                                  \
    }                                                                  \
    const char *s = cxx_func((cxx_type *)handle);                      \
    if (s == nullptr) {                                                \
      MKALL_THROW_EINTERNAL;                                           \
      return nullptr;                                                  \
    }                                                                  \
    return env->NewStringUTF(s);                                       \
  }

/// MKALL_NEW returns a new instance of a type.
#define MKALL_NEW(java_func, cxx_func)                \
  JNIEXPORT jlong JNICALL                             \
      Java_io_ooni_mk_##java_func(JNIEnv *, jclass) { \
    return (jlong)cxx_func();                         \
  }

/// MKALL_NEW_WITH_STRING_ARGUMENT returns a new instance of a type.
#define MKALL_NEW_WITH_STRING_ARGUMENT(java_func, cxx_func) \
  JNIEXPORT jlong JNICALL Java_io_ooni_mk_##java_func(      \
      JNIEnv *env, jclass, jstring value) {                 \
    if (value == nullptr) {                                 \
      MKALL_THROW_EINVAL;                                   \
      return 0L;                                            \
    }                                                       \
    const char *s = env->GetStringUTFChars(value, nullptr); \
    if (s == nullptr) {                                     \
      /* Exception should already be pending. */            \
      return 0L;                                            \
    }                                                       \
    jlong rv = (jlong)cxx_func(s);                          \
    env->ReleaseStringUTFChars(value, s);                   \
    return rv;                                              \
  }

/// MKALL_SET_LONG sets a long value.
#define MKALL_SET_LONG(java_func, cxx_func, cxx_type)   \
  JNIEXPORT void JNICALL Java_io_ooni_mk_##java_func(   \
      JNIEnv *env, jclass, jlong handle, jlong value) { \
    if (handle == 0) {                                  \
      MKALL_THROW_EINVAL;                               \
      return;                                           \
    }                                                   \
    cxx_func((cxx_type *)handle, value);                \
  }

/// MKALL_SET_STRING sets a string value.
#define MKALL_SET_STRING(java_func, cxx_func, cxx_type)     \
  JNIEXPORT void JNICALL Java_io_ooni_mk_##java_func(       \
      JNIEnv *env, jclass, jlong handle, jstring value) {   \
    if (handle == 0 || value == nullptr) {                  \
      MKALL_THROW_EINVAL;                                   \
      return;                                               \
    }                                                       \
    const char *s = env->GetStringUTFChars(value, nullptr); \
    if (s == nullptr) {                                     \
      /* Exception should already be pending. */            \
      return;                                               \
    }                                                       \
    cxx_func((cxx_type *)handle, s);                        \
    env->ReleaseStringUTFChars(value, s);                   \
  }

#endif  // SRC_MKALL_HEADERS_MKALL_UTIL_H
