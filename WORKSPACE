load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# Kotlin

kotlin_release_version = "1.2.70"
rules_kotlin_version = "cab5eaffc2012dfe46260c03d6419c0d2fa10be0"
kotlin_1_2_70_compiler_release = {
    "urls": [
        "https://github.com/JetBrains/kotlin/releases/download/v1.2.70/kotlin-compiler-1.2.70.zip",
    ],
    "sha256": "a23a40a3505e78563100b9e6cfd7f535fbf6593b69a5c470800fbafbeccf8434",
}
kotlin_1_3_10_compiler_release = {
    "urls": [
        "https://github.com/JetBrains/kotlin/releases/download/v1.3.10/kotlin-compiler-1.3.10.zip",
    ],
    "sha256": "ca79c93151e14e34ff49cfb56ec4c0fe83e1383143b1469af8cdc4f62fb8c67d",
}
kotlin_compiler_release_version = kotlin_1_2_70_compiler_release

http_archive(
    name = "io_bazel_rules_kotlin",
    urls = ["https://github.com/bazelbuild/rules_kotlin/archive/%s.zip" % rules_kotlin_version],
    type = "zip",
    strip_prefix = "rules_kotlin-%s" % rules_kotlin_version
)

load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kotlin_repositories", "kt_register_toolchains")
kotlin_repositories(compiler_release = kotlin_compiler_release_version)
kt_register_toolchains()