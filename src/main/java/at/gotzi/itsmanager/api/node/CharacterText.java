package at.gotzi.itsmanager.api.node;

import javafx.scene.text.Text;

public class CharacterText extends Text {

    public CharacterText(char character) {
        super.setText(String.valueOf(character));
    }
}