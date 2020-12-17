package top.desq.executor.repository;

import top.desq.executor.model.Script;

public interface ScriptRepository {

    Script get(int id);

    void add(int id, Script script);

    void clear();
}
