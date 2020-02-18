package com.wjltechservices.context;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used to store context for a scenario
 *
 * Allows for sharing values between steps in a scenario, for example a value generated in one step which is ten
 * validated in another step
 */
@Component
public class ScenarioContext {
    private Map<Context, Object> scenarioContext;

    public ScenarioContext() {
        scenarioContext = new HashMap<>();
    }

    /**
     * Store a context for later retrieval
     *
     * @param context The key to store the context at
     * @param value The value to store
     */
    public void putContext(Context context, Object value) {
        scenarioContext.put(context, value);
    }

    /**
     * Get a previously stored context
     * @param context The key the context is stored at
     * @return The value stored at the given key, or null if not present
     */
    public Object getContext(Context context) {
        return scenarioContext.get(context);
    }

    /**
     * Clear out any stored contexts
     */
    public void clearContext() {
        scenarioContext = new HashMap<>();
    }
}
