import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;

public class HelloAbility extends AbilityBot {

    public HelloAbility(String token, String username) {
        super(token, username);
    }

    public Ability sayHelloWorld() {
        return Ability
                .builder()
                .name("hello")
                .info("says hello world!")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.send("Hello world!", ctx.chatId()))
                .build();
    }

    @Override
    public long creatorId() {
        return 2145256237;
    }
}
