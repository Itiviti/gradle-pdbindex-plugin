# Gradle PdbIndex Plugin [![Build status](https://ci.appveyor.com/api/projects/status/mn98qsdh8c8lq9gn/branch/master?svg=true)](https://ci.appveyor.com/project/gluck/gradle-pdbindex-plugin/branch/master) [![Build Status](https://travis-ci.org/Ullink/gradle-pdbindex-plugin.svg?branch=master)](https://travis-ci.org/Ullink/gradle-pdbindex-plugin)

This plugin aims at working things around PDBs, mainly source indexing.

Below tasks are provided by the plugin:

## sourceLink

Sample usage:

    buildscript {
        repositories {
          mavenCentral()
        }
    
        dependencies {
            classpath "com.ullink.gradle:gradle-pdbindex-plugin:0.9"
        }
    }
    
    apply plugin:'pdbindex'

    // this task will execute SourceLink on the generated asembly, for PDB indexing
    sourceLinkIndexing {
      // directory where SourceLink.exe can be found
      // Note that you may use gradle nuget plugin together with a nuget install task to download it
      // You may also add it to your project nuget dependencies, and reference it from there
      sourcelinkDir = "${sourcelinkNugetInstall.outputDirectory}/SourceLink/tools"

      // project (e.g. csproj) to pass to SourceLink, defaults to pdbindex.mainProject if not set
      projectFile = msbuild.mainProject?.projectFile

      // git viewer raw URL, use '{0}' placeholder for GIT commit (SHA1) and '%var2%' for file path
      // Note that because of SRCSRV limitations, the url may not contain non-valid-windows-path characters (e.g. '?' won't work)
      url = "http://my-git-server/${project.name}/%var2%/raw/{0}"

      // some other defaults, overwrite if needed, look for the param meaning in SourceLink
      verifyGit = false
      verifyPdb = true
      repo = project.rootDir
    }

# See also

[Gradle Msbuild plugin](https://github.com/Ullink/gradle-msbuild-plugin) - Allows to build VS projects & solutions.

[Gradle NuGet plugin](https://github.com/Ullink/gradle-nuget-plugin) - Allows to restore NuGet packages prior to building the projects with this plugin, and to pack&push nuget packages.

[Gradle NUnit plugin](https://github.com/Ullink/gradle-nunit-plugin) - Allows to execute NUnit tests from CI (used with this plugin to build the projects prior to UT execution)

[Gradle OpenCover plugin](https://github.com/Ullink/gradle-opencover-plugin) - Allows to execute the UTs through OpenCover for coverage reports.

You can see all these plugins in use on [ILRepack](https://github.com/gluck/il-repack) project ([build.gradle](https://github.com/gluck/il-repack/blob/master/build.gradle)).

# License

All these plugins are licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html) with no warranty (expressed or implied) for any purpose.
