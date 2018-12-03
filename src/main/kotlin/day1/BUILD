load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library", "kt_jvm_binary")

kt_jvm_library(
    name = "day1",
    srcs = glob(["*.kt"]),
    resources = [
        "//src/main/resources/day1",
    ],
)

kt_jvm_binary(
    name = "day1-binary",
    main_class = "day1.Day1Kt",
#    runtime_deps = [
#        ":day1",
#    ],
    srcs = glob(["*.kt"]),
    resources = [
        "//src/main/resources/day1",
    ],
)