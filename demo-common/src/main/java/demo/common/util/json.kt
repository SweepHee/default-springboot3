package demo.common.util

import com.jayway.jsonpath.JsonPath

fun String.jsonValue(key: String) = JsonPath.parse(this).read("$.${key}", String::class.java)