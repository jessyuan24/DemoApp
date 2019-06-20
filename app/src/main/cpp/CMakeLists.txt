cmake_minimum_required(VERSION 3.4.1)

file(GLOB srcs *.cpp)
file(GLOB hdrs *.h)

add_library( # Sets the name of the library.
        native-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        ${srcs} ${hdrs})