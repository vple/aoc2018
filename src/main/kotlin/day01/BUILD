load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_jvm_binary")

kt_jvm_binary(
    name = "day01",
    main_class = "day01.Day01Kt",
    srcs = glob(["*.kt"]),
    deps = [
        "//src/main/kotlin/util",
    ],
    resources = [
        "//src/main/resources/day01",
    ],
)