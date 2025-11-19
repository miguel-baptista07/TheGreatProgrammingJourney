package pt.ulusofona.lp2.greatprogrammingjourney;

import java.util.HashSet;
import java.util.Set;

public class ToolInventory {

    private final Set<Integer> tools = new HashSet<>();

    public void addTool(int id) {
        tools.add(id);
    }

    public boolean hasTool(int id) {
        return tools.contains(id);
    }

    public boolean hasAnyOf(int... ids) {
        for (int id : ids) {
            if (tools.contains(id)) return true;
        }
        return false;
    }

    public void removeTool(int id) {
        tools.remove(id);
    }

    public String toStringList() {
        if (tools.isEmpty()) return "No tools";
        StringBuilder sb = new StringBuilder();
        for (int id : tools) {
            sb.append(id).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public Set<Integer> asSet() {
        return tools;
    }
}
