package antlr;

import com.company.LabelCounterUtil;

import java.util.HashMap;
import java.util.Map;

public class JavaCodeGeneratingVisitor extends JavaBaseVisitor<String> {
    private final Map<String, String> usedIdentifiers = new HashMap<>();
    private final StringBuilder stringBuilder;

    public JavaCodeGeneratingVisitor(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    private String formatAssignment(String identifier, String type, String value) {
        if (type.startsWith("[") && type.endsWith("]")) {
            var allocation = String.format("%%%s = alloca %s", identifier, type);
            var storing = String.format("store %s c%s, %s* %%0", type, value, type);

            if (checkIdentifierAssignation(identifier, type))
                return String.format("%s\n", storing);

            return String.format("" +
                    "%s\n" +
                    "%s\n", allocation, storing);
        }

        if ("i1".equals(type) && ("True".equals(value) || "False".equals(value))) {
            var actualValue = "True".equals(value) ? 1 : 0;
            if (checkIdentifierAssignation(identifier, type))
                return String.format("store %s %s, %s* %%%s\n", type, actualValue, type, identifier);

            return String.format("%%%s = alloca i1\n", identifier) +
                    String.format("store %s %s, %s* %%%s\n", type, actualValue, type, identifier);
        }

        if (checkIdentifierAssignation(identifier, type))
            return String.format("store %s %s, %s* %%%s\n", type, value, type, identifier);

        return String.format("%%%s = alloca %s\n", identifier, type) +
                String.format("store %s %s, %s* %%%s\n", type, value, type, identifier);
    }

    private boolean checkIdentifierAssignation(String identifier, String type) {
        if (!usedIdentifiers.containsKey(identifier)) {
            usedIdentifiers.put(identifier, type);
            return false;
        } else {
            return true;
        }
    }

    private String formatIncrementAssignment(String identifier, String type, String value) {
        var tmpValue = String.format("tmp%d", LabelCounterUtil.getIncrementTmpLabelCounter());
        var tmpAdd = String.format("tmp%d", LabelCounterUtil.getIncrementTmpLabelCounter());

        return String.format("%%%s = load %s, %s* %%%s\n", tmpValue, type, type, identifier) +
                String.format("%%%s = add nsw %s %%%s,%s\n", tmpAdd, type, tmpValue, value) +
                String.format("store %s %%%s, %s* %%%s\n", type, tmpAdd, type, identifier);
    }

    private String javaToLlvmOperator(String operator) {
        switch (operator) {
            case "==":
                return "eq";
            case "<>":
                return "ne";
            case "!=":
                return "ne";
            case "<":
                return "ult";
            case ">":
                return "ugt";
            case "<=":
                return "ule";
            case ">=":
                return "uge";
            default:
                return "";
        }
    }
}
