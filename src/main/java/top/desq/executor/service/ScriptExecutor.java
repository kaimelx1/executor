package top.desq.executor.service;

import top.desq.executor.model.Script;
import top.desq.executor.repository.ScriptRepository;

import java.util.*;

public class ScriptExecutor implements Executor<Integer> {

    private final Map<Integer, String> cache = new LinkedHashMap<>();
    private final Stack<Integer> chain = new Stack<>();
    private final ScriptRepository repository;

    public ScriptExecutor(ScriptRepository repository) {
        this.repository = repository;
    }

    /**
     * Execute script and all its dependencies
     *
     * @param scriptId
     */
    public void execute(Integer scriptId) {
        Script script = getScript(scriptId);
        executeDependencies(script);
        lazyExecution(script);
        chain.clear();
    }

    /**
     * Execute dependencies
     *
     * @param script
     */
    private void executeDependencies(Script script) {
        List<Integer> dependencies = script.getDependencies();
        if (!dependencies.isEmpty()) {
            chain.push(script.getScriptId());
            dependencies.forEach(id -> {
                        circularDependenciesCheck(id);
                        Script dependencyScript = getScript(id);
                        executeDependencies(dependencyScript);
                        lazyExecution(dependencyScript);
                    }
            );
        }
    }

    /**
     * Check if script has circular dependency
     *
     * @param scriptId
     */
    private void circularDependenciesCheck(int scriptId) {
        if (chain.contains(scriptId)) {
            throw new IllegalArgumentException("Script with id = " + chain.peek() +
                    " has circular dependency on script with id = " + scriptId);
        }
    }

    /**
     * Lazy execution
     *
     * @param script
     */
    private void lazyExecution(Script script) {
        int scriptId = script.getScriptId();
        if (!cache.containsKey(scriptId) || !cache.get(scriptId).equals(script.getVersion())) {
            doWork(script);
            cache.put(scriptId, script.getVersion());
        }
    }

    /**
     * Get script with check on null element
     *
     * @param scriptId
     * @return
     */
    private Script getScript(int scriptId) {
        Script script = repository.get(scriptId);
        if (script == null)
            throw new IllegalArgumentException("Trying execute non-existent script with id = " + scriptId);
        return script;
    }

    /**
     * Some script execution logic
     *
     * @param script
     */
    private void doWork(Script script) {
        System.out.println("Executing script #" + script.getScriptId() + ", version = " + script.getVersion());
    }

    /**
     * Get already executed scripts
     */
    public Map<Integer, String> getCache() {
        return cache;
    }

    /**
     * Clear cache
     */
    public void clearCache() {
        cache.clear();
    }
}
