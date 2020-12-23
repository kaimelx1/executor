package top.desq.executor.repository;

import top.desq.executor.model.Script;

import java.util.HashMap;
import java.util.Map;

public class InMemoryScriptRepository implements ScriptRepository {

    private final Map<Integer, Script> repository = new HashMap<>();

    public Script get(int id) {
        return repository.get(id);
    }

    public void add(int id, Script script) {
        repository.put(id, script);
    }

    public void clear() {
        repository.clear();
    }
}
