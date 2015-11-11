package com.ullink
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.junit.Assert.assertTrue

class PdbIndexPluginTest {
    @Test
    public void msbuildPluginAddsMsbuildTaskToProject() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'pdbindex'
        assertTrue(project.tasks.sourceLinkIndexing instanceof SourceLinkIndexing)
    }
}
