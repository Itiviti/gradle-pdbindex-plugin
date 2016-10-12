package com.ullink

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.testng.Assert.assertEquals

class SourceLinkIndexingTest {
    @Test
    void givenTheProjectRootIsTheRootOfTheRepo_whenGettingRepo_thenRootOfTheRepoIsReturned() {
        def repoDir = File.createTempDir('repo', '')
        new File(repoDir, '.git').mkdir()

        Project project = ProjectBuilder.builder().withProjectDir(repoDir).build()
        project.apply plugin: 'pdbindex'

        assertEquals(project.tasks.sourceLinkIndexing.repo, repoDir)

        repoDir.deleteDir()
    }

    @Test
    void givenTheProjectRootIsASubFolderOfTheRootOfTheRepo_whenGettingRepo_thenRootOfTheRepoIsReturned() {
        def repoDir = File.createTempDir('repo', '')
        new File(repoDir, '.git').mkdir()
        def projectRoot = new File(repoDir, 'subFolder')
        projectRoot.mkdir()

        Project project = ProjectBuilder.builder().withProjectDir(projectRoot).build()
        project.apply plugin: 'pdbindex'

        assertEquals(project.tasks.sourceLinkIndexing.repo, repoDir)

        repoDir.deleteDir()
    }

    @Test
    void givenThereIsNoAGitRepo_whenGettingRepo_thenProjectRootIsReturned() {
        def projectRoot = File.createTempDir('projectRoot', '')

        Project project = ProjectBuilder.builder().withProjectDir(projectRoot).build()
        project.apply plugin: 'pdbindex'

        assertEquals(project.tasks.sourceLinkIndexing.repo, projectRoot)

        projectRoot.deleteDir()
    }
}
