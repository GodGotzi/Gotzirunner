package at.gotzi.itsmanager.code.compile;

import java.util.ArrayList;
import java.util.List;

public class CCodeStructureHelper {

    private List<String> code;

    public CCodeStructureHelper(List<String> code) {
        this.code = code;
    }

    public String build() {
        for (int i = 0; i < code.size(); i++) {
            String line = code.get(i);
            if (line.contains("scanf(\"%c\",")) {
                List<String> newCode = new ArrayList<>(code.subList(0, i));
                String variable = line.split("scanf\\(\"%c\",")[1].split("\\)")[0].replace("&", "");
                newCode.add("do {" + line + "} while (" + variable + " == ' ');");
                newCode.addAll(code.subList(i+1, code.size()));
                code = newCode;
                i++;
            }
        }

        System.out.println(String.join("\n", code));
        return String.join("\n", code);
    }
}