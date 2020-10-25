package botforshareholders.util;

import java.util.ArrayList;
import java.util.List;

public class TextMessageForAnswer {
    public static final List<String> greetings = new ArrayList<>();
    public static final List<String> answerForValera = new ArrayList<>();

    static {
        greetings.add("Хай нига");
        greetings.add("Привет привет");
        greetings.add("Дарова кожанный");
        greetings.add("И тебе не хворать");
        greetings.add("Как сам? Как сала килограмм? Или как молодой Ван Дам?");
        greetings.add("Хуясе какие люди... хай");
        greetings.add("Привет друган");
        greetings.add("Здравствуй хозяин");
        greetings.add("Арасьте, забор покрасте");
        greetings.add("Хело раша, хаваю?");
        greetings.add("Привет пацаны");
        greetings.add("Привет друзья");
        greetings.add("Всем... Всем... Всем... Доброго времени суток");
    }

    static {
        answerForValera.add("Чо тебе");
        answerForValera.add("А аааа");
        answerForValera.add("Да мой господин");
        answerForValera.add("Ну");
        answerForValera.add("Говори");
        answerForValera.add("Чо заспамить");
        answerForValera.add("Ты взывал ко мне мой господин");
        answerForValera.add("Ну заебали блять, поспать не дадут, чо тебе");
        answerForValera.add("Да хозяин");
        answerForValera.add("Ох опять эти спамеры хуямеры проснулись");
        answerForValera.add("Патрон, внимаю каждому твоему слову");
        answerForValera.add("Чо те нада");
        answerForValera.add("Ну что блять опять");
        answerForValera.add("Ну что блядь опять");
        answerForValera.add("Что");
    }

    public static String getTextAnswer(List<String> list) {
        return list.get((int) (Math.random() * list.size()));
    }
}
