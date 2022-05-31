package com.viettel.command;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

public class StartCommand extends BotCommand {
    public static final String LOGTAG = "START_COMMAND";

    public StartCommand() {
        super("start", "Start our telegram bot");
    }

}
