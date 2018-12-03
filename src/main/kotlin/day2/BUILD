load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_library", "kt_jvm_binary")

kt_jvm_binary(
    name = "day2",
    main_class = "day2.Day2Kt",
    srcs = glob(["*.kt"]),
    deps = [
        "//src/main/kotlin/util",
    ],
    resources = [
        "//src/main/resources/day2",
    ],
)