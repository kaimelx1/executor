package top.desq.executor;

import top.desq.executor.model.Script;
import top.desq.executor.repository.ScriptRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class TestData {

    static final int INITIAL_SCRIPT_ID = 1;
    static final Integer[] EXPECTED_ORDER_ARRAY = {4, 5, 2, 6, 3, 1};
    static final Map<Integer, String> EXPECTED_ORDER_MAP = new HashMap<Integer, String>() {{
        put(1, "1.1");
        put(2, "2.4");
        put(3, "1.1");
        put(4, "2.4");
        put(5, "1.1");
        put(6, "3.1");
    }};

    static void fillDependencies(ScriptRepository repository) {
        repository.add(1, new Script(1, Arrays.asList(2, 3), "1.1"));
        repository.add(2, new Script(2, Arrays.asList(4, 5), "1.2"));
        repository.add(3, new Script(3, Arrays.asList(5, 6), "1.1"));
        repository.add(4, new Script(4, Collections.emptyList(), "2.3"));
        repository.add(5, new Script(5, Collections.emptyList(), "1.1"));
        repository.add(6, new Script(6, Collections.emptyList(), "1.4"));
    }

    static void fillDependenciesWithNewVersions(ScriptRepository repository) {
        repository.add(1, new Script(1, Arrays.asList(2, 3), "1.1"));
        repository.add(2, new Script(2, Arrays.asList(4, 5), "2.4"));
        repository.add(3, new Script(3, Arrays.asList(5, 6), "1.1"));
        repository.add(4, new Script(4, Collections.emptyList(), "2.4"));
        repository.add(5, new Script(5, Collections.emptyList(), "1.1"));
        repository.add(6, new Script(6, Collections.emptyList(), "3.1"));
    }

    static void fillCircularDependencies(ScriptRepository repository) {
        repository.add(1, new Script(1, Arrays.asList(2, 3), "1.1"));
        repository.add(2, new Script(2, Arrays.asList(4, 5), "2.3"));
        repository.add(3, new Script(3, Arrays.asList(5, 6), "3.2"));
        repository.add(4, new Script(4, Collections.emptyList(), "4.1"));
        repository.add(5, new Script(5, Collections.emptyList(), "5.0"));
        repository.add(6, new Script(6, Arrays.asList(1, 3), "6.2"));
    }

    static void fillDependenciesWithIncorrectId(ScriptRepository repository) {
        repository.add(1, new Script(1, Arrays.asList(2, 3), "1.3"));
        repository.add(2, new Script(2, Arrays.asList(4, 5), "2.1"));
        repository.add(3, new Script(3, Arrays.asList(5, 6), "1.2"));
        repository.add(4, new Script(4, Collections.emptyList(), "3.2"));
        repository.add(5, new Script(5, Collections.emptyList(), "1.0"));
    }

}
