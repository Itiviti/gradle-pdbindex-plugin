Plugin changelog
====================

1.6
-------
* Fixed an issue when sourcelink would have been run in a project which was not in the root of the git repository

1.5
-------
* Upgrade gradle-nuget dependency to 2.12 and only apply nuget-base plugin

1.4
-------
* Msbuild and sourcelink are now using the same properties when evaluating solution/projects

1.3
-------
* When calling `setupNugetDownload`, nuget plugin is now applied (as it's required for download)

