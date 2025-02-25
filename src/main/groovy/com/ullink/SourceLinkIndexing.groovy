package com.ullink

import org.gradle.api.internal.ConventionTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

import java.lang.reflect.Array

class SourceLinkIndexing extends ConventionTask {
    @Internal
    def projectFile
    @Internal
    def sourcelinkDir
    @Internal
    def properties = [:]
    @Internal
    def url
    @Internal
    def commit
    @Internal
    def pdbFile
    @Internal
    def sourceFiles
    @Internal
    def repo
    @Internal
    def verifyGit
    @Internal
    def verifyPdb

    SourceLinkIndexing() {

        conventionMapping.map "projectFile", { pdbFile ? null : project.tasks.findByPath('msbuild')?.mainProject?.projectFile }
        conventionMapping.map "properties", {
            def msbuild = getMsbuildTaskMatchingProject()
            if (msbuild) {
                def proj = msbuild.projects.values().find { it.projectFile == getProjectFile() }
                // Configuration/Platform props are special and get remapped from sln->csproj
                msbuild.initProperties + [ Configuration: proj.properties.Configuration, Platform: proj.properties.Platform ]
            }
        }
        conventionMapping.map "repo", { findRepoRootDir(project.rootDir) ?: project.rootDir }
        conventionMapping.map "verifyGit", { false }
        conventionMapping.map "verifyPdb", { true }

        project.afterEvaluate {
            if (!url) return;
            def msbuild = getMsbuildTaskMatchingProject()
            if (msbuild) {
                dependsOn msbuild
                msbuild.finalizedBy this
            }
        }
    }

    File findRepoRootDir(File dir) {
        if(dir) {
            new File(dir, '.git').exists() ? dir : findRepoRootDir(dir.getParentFile())
        }
    }

    @Internal
    def getMsbuildTaskMatchingProject() {
        def projFile = getProjectFile()
        if (projFile)
            return project.tasks.withType(Msbuild.class).find {
                it.projects.values().any {
                    it.projectFile == projFile
                }
            }
    }

    @TaskAction
    void run() {
        def args = [ "$sourcelinkDir/SourceLink.exe", 'index' ]
        if (!getVerifyGit()) args += '-nvg'
        if (!getVerifyPdb()) args += '-nvp'
        if (getRepo()) args += [ '-r', getRepo() ]
        if (getProjectFile()) args += [ '-pr', getProjectFile() ]
        if (commit) args += [ '-c', commit]
        addArgs(args, '-p', pdbFile, { project.file(it) })
        if (url) args += [ '-u', url]
        addArgs(args, '-f', sourceFiles, { it })
        getProperties().each {
            args += [ '-pp', it.key, it.value ]
        }
        project.exec {
            commandLine args
        }
    }

    void addArgs(List args, def prefix, def value, def mapper) {
        if (!value) return;
        if (value instanceof Iterable || value instanceof Array) {
            value.each {
                args.addAll([prefix, mapper(it)])
            }
        } else {
            args.addAll([prefix, mapper(value)])
        }
    }
}
