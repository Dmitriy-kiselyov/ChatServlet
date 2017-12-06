package ru.tanya_dima.chat;

import java.util.*;

public class Database {

    private static Map<String, User> users;
    private static ArrayList<Message> messages;

    public static void init() {
        users = new HashMap<>();
        messages = new ArrayList<>();
    }

    public static void preset() {
        User dima = new User("Dima", "123123");
        User tanya = new User("Tanya", "qwerty");
        User kama = new User("KamaPulya", "kAmA_228");
        addUser(dima);
        addUser(tanya);
        addUser(kama);

        long cur = System.currentTimeMillis();
        long sec = 1_000;
        long min = sec * 60;
        long hour = min * 60;
        long day = hour * 24;
        long month = hour * 12;
        long year = day * 365;

        addMessage(new Message(dima, "Привет, народ!!!", cur - year));
        addMessage(new Message(tanya, ":)", cur - month * 9));
        addMessage(new Message(kama, "Здарова дИМОН, как жытуха, гыгыгыгы", cur - month * 6 - hour * 2));
        addMessage(new Message(dima, "Таня, как удалить этого придурка из беседы?", cur - month));
        addMessage(new Message(tanya, "Никак. Нет такой кнопки", cur - day * 2));
        addMessage(new Message(kama, "Дима эжжжжжииии ты чо такое гвришь брахтуха? 100 лет тебя знаю", cur - day));
        addMessage(new Message(dima, "Завали<br>своё<br>хлебало!!!", cur - hour * 2));
        addMessage(new Message(dima, "Чо за фигня, почему <br> не пашет?", cur - hour - min * 30));
        addMessage(new Message(tanya, "Тут textContent изпользуется, а не innerHTML, поэтому тэги не работают", cur - min * 10 - 50 * sec));
        addMessage(new Message(kama, "Не отступай и не сдавайся, не останавливайся иди вперед, эта остановка может быть последней, холодной, будь дерзким", cur - 10 * sec));
        addMessage(new Message(dima, "АААААААААААААААААААААААААААААААААААААААААААААААААААА!!!!!!!!!!!!!!!!!!!!!!!!!!", cur - 15));
        addMessage(new Message(tanya, "Спокойствие, только спокойствие XD"));
    }

    public static User getUser(String login) {
        return users.get(login);
    }

    public static boolean addUser(User user) {
        return users.putIfAbsent(user.getLogin(), user) == null;
    }

    public static ArrayList<Message> getMessages() {
        return messages;
    }

    public static void addMessage(Message message) {
        messages.add(message);
    }

}
