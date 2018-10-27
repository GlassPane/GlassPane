package com.github.upcraftlp.glasspane.command.subcommand;

import com.github.upcraftlp.glasspane.util.debug.RegistryDumper;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import java.util.*;

public class CommandDumpRegistries extends CommandBase {
    @Override
    public String getName() {
        return "dump_registry";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command.glasspane.debug.dump_registry.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        switch(args.length) {
            case 0:
                RegistryDumper.dumpAllRegistries();
                sender.sendMessage(new TextComponentTranslation("command.glasspane.debug.dump_registry.all.success"));
                break;
            case 1:
                if(RegistryDumper.REGISTRY_NAMES.contains(args[0])) {
                    RegistryDumper.dumpRegistry(new ResourceLocation(args[0]));
                    sender.sendMessage(new TextComponentTranslation("command.glasspane.debug.dump_registry.success", args[0]));
                }
                else throw new SyntaxErrorException("command.glasspane.debug.dump_registry.notfound", args[0]);
                break;
            default:
                throw new WrongUsageException(getUsage(sender));
        }
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("dump");
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, RegistryDumper.REGISTRY_NAMES) : Collections.emptyList();
    }
}
