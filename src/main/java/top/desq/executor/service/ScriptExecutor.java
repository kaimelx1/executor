package top.desq.executor.service;

import top.desq.executor.model.Script;
import top.desq.executor.repository.ScriptRepository;

import java.util.ArrayList;
import java.util.List;

public class ScriptExecutor {

    private final List<Integer> cache = new ArrayList<>();
    private final ScriptRepository repository;

    public ScriptExecutor(ScriptRepository repository) {
        this.repository = repository;
    }

    /**
     * Execute script and all its dependencies
     * @param scriptId
     */
    public void execute(int scriptId) {
        Script script = repository.get(scriptId);
        executeDependencies(script);
        lazyExecution(script);
    }

    /**
     * Execute dependencies
     * @param script
     */
    private void executeDependencies(Script script) {
        List<Integer> dependencies = script.getDependencies();
        if (!dependencies.isEmpty()) {
            dependencies.forEach(id -> {
                        Script dependencyScript = repository.get(id);
                        executeDependencies(dependencyScript);
                        lazyExecution(dependencyScript);
                    }
            );
        }
    }

    /**
     * Lazy execution
     * @param script
     */
    private void lazyExecution(Script script) {
        int scriptId = script.getScriptId();
        if (!cache.contains(scriptId)) {
            script.execute();
            cache.add(scriptId);
        }
    }

    public List getCache() {
        return cache;
    }
}
