package com.ullink

import org.gradle.api.Project

class SourceLinkExtension {
    private final Project project

    SourceLinkExtension(Project project) {
        this.project = project
    }

    def setupNugetDownload() {
        project.task(type: com.ullink.NuGetInstall, "sourceLinkDownload")
        project.tasks.sourceLinkDownload {
            packageId = 'sourcelink'
            outputDirectory = temporaryDir
            includeVersionInPath = false
            version = '1.0.0'
        }
        project.tasks.withType(SourceLinkIndexing) {
            it.dependsOn project.tasks.sourceLinkDownload
            it.sourcelinkDir = "${project.tasks.sourceLinkDownload.outputDirectory}/SourceLink/tools"
        }
    }
}
