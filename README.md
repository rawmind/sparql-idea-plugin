SPARQL language plugin for IntelliJ IDEA


Before run in idea need to copy project dependencies into lib folder:
 
gradle copy


Note:

gradle used only for copy dependency for project


WTF FAQ:

Firstly need to set project compile output dir. It is very important for following actions.


Q: Icon doesn't loaded and plugin throws exception!

A: Check "icons" folder in output dir. If doesn't exist mark folder resources as "resource dir" Project structure->modules

Q: No libraries!

A: Add lib directory Project structure->Libraries

Q: No source!

A: Well. Create module "sparql-idea-plugin" in "Project structure->Modules" and append source "src" directory.

