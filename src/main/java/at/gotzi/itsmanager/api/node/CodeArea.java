package at.gotzi.itsmanager.api.node;

import eu.mihosoft.monacofx.MonacoFX;

public class CodeArea extends MonacoFX {

    public CodeArea(MonacoFX monacoFX) {
        this.init();
    }

    private void init() {
        getEditor().setCurrentLanguage("c");
        getEditor().setCurrentTheme("vs-light");
    }

    public void setTheme(Theme theme) {
        switch (theme) {
            case Dark -> getEditor().setCurrentTheme("vs-dark");
            case Light -> getEditor().setCurrentTheme("vs-light");
        }
    }

    public static enum Theme {
        Light,
        Dark
    }

}
