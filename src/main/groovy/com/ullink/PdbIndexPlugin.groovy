package com.ullink
import org.gradle.api.Plugin
import org.gradle.api.Project

class PdbIndexPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.apply plugin: 'base'
        project.extensions.create('sourceLink', SourceLinkExtension, project)

        project.task('sourceLinkIndexing', type: SourceLinkIndexing)
    }
}

