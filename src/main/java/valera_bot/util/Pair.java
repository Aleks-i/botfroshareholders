package valera_bot.util;

import valera_bot.command.Command;

public class Pair {

    private Command key;
    private String text;

    public Pair(Command key, String text) {
        this.key = key;
        this.text = text;
    }

    public Command getKey() {
        return key;
    }

    public void setKey(Command key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
