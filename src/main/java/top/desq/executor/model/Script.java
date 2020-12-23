package top.desq.executor.model;

import java.util.List;
import java.util.Objects;

public class Script {

    private final int scriptId;
    private final List<Integer> dependencies;
    private final String version;

    public Script(int scriptId, List<Integer> dependencies, String version) {
        this.scriptId = scriptId;
        this.dependencies = dependencies;
        this.version = version;
    }

    public int getScriptId() {
        return scriptId;
    }

    public List<Integer> getDependencies() {
        return dependencies;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Script script = (Script) o;
        return scriptId == script.scriptId &&
                Objects.equals(dependencies, script.dependencies) &&
                Objects.equals(version, script.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scriptId, dependencies, version);
    }
}
