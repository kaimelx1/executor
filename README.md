Let's say I have a database of scripts. Each script has an arbitrary number of dependencies. The dependencies are expressed as a list of scriptIds that need to be executed before a given script. There are no circular dependencies. I want to come up with an execution plan so that I can run all of the scripts in a sane order. Below is the script representation.

public class VulnerabilityScript {

private final int scriptId; private final List dependencies;

public VulnerabilityScript(int scriptId, List dependencies) { this.scriptId = scriptId; this.dependencies = dependencies; }

public int getScriptId() { return scriptId; }

public List getDependencies() { return dependencies; } }

=================================

Executor version 1.0.1

Script class:
- Add version to scripts
- Remove execute() method

ScriptExecutor class:
- Implement Executor<T> interface
- Check script version before caching
- Detect circular dependecies
- Add getScript() method that throw IllegalArgumentException if script was not found in repository
- Add doWork() method that execute scripts

MainTest class:
- Move test data to TestData class
- Add nonExistentScriptTest, circularDependenciesTest, newVersionsTest