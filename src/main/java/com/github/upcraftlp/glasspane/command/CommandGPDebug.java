package com.github.upcraftlp.glasspane.command;

import com.github.upcraftlp.glasspane.command.subcommand.*;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.*;

import java.util.*;

public class CommandGPDebug extends CommandTreeBase {

    public CommandGPDebug() {
        this.addSubcommand(new CommandDumpRegistries());
        this.addSubcommand(new CommandLoadStructure());
        this.addSubcommand(new CommandTreeHelp(this)); //needs to be last!
    }

    @Override
    public String getName() {
        return "glasspane_debug";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("gp_debug");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command.glasspane.debug.usage";
    }
}
