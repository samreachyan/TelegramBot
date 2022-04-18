import org.telegram.abilitybots.api.bot.AbilityBot;

public class HelloAbility extends AbilityBot {

    public HelloAbility(String token, String username) {
        super(token, username);
    }

    public Ability sayHelloWorld() {
        return Ability
                .builder()
                .name("hello")
                .info("says hello world!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Hello world!", ctx.chatId()))
                .build();
    }
}
