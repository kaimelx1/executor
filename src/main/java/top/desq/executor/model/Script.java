package top.desq.executor.model;

import top.desq.executor.Executable;

import java.util.List;

public class Script implements Executable {

    private final int scriptId;
    private final List<Integer> dependencies;

    public Script(int scriptId, List<Integer> dependencies) {
        this.scriptId = scriptId;
        this.dependencies = dependencies;
    }

    public int getScriptId() {
        return scriptId;
    }

    public List<Integer> getDependencies() {
        return dependencies;
    }

    public void execute() {
        System.out.println("Executing script #" + scriptId);
    }
}
