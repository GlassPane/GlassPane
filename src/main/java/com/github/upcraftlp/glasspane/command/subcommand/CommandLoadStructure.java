package com.github.upcraftlp.glasspane.command.subcommand;

import com.github.upcraftlp.glasspane.api.structure.StructureLoaders;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.template.PlacementSettings;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.*;

public class CommandLoadStructure extends CommandBase {

    @Override
    public String getName() {
        return "load_structure";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        /*
        1: ResourceLocation - structure name
        2 - 4: BlockPos - position
        5: boolean - centered
        6: int - rotation
        7: int - mirror
        8: double (float) - integrity
        9: boolean - ignore entities
         */
        return "command.glasspane.debug.load_structure.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        PlacementSettings settings = new PlacementSettings();
        if(args.length < 1 || args.length > 9) throw new WrongUsageException(getUsage(sender));
        BlockPos pos = args.length > 2 ? parseBlockPos(sender, args, 1, false) : sender.getPosition();
        boolean centered = args.length < 5 || parseBoolean(args[4]);
        Rotation rotation = args.length < 6 ? Rotation.NONE : Rotation.values()[parseInt(args[5])]; //TODO use enum names in command
        Mirror mirror = args.length < 7 ? Mirror.NONE : Mirror.values()[parseInt(args[6])]; //TODO use enum names in command
        settings.setRotation(rotation);
        settings.setMirror(mirror);
        settings.setIntegrity(args.length < 8 ? 1.0F : (float) parseDouble(args[7]));
        settings.setIgnoreEntities(args.length > 8 && parseBoolean(args[8]));
        StructureLoaders.VANILLA_NBT.placeInWorld(new ResourceLocation(args[0]), sender.getEntityWorld(), pos, settings, centered);
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        switch(args.length) {
            case 2:
            case 3:
            case 4:
                return getTabCompletionCoordinate(args, 1, sender.getPosition());
            case 5:
            case 9:
                return getListOfStringsMatchingLastWord(args, "true", "false");
            case 6:
                return getListOfStringsMatchingLastWord(args, IntStream.range(0, Rotation.values().length).mapToObj(String::valueOf).collect(Collectors.toList()));
            case 7:
                return getListOfStringsMatchingLastWord(args, IntStream.range(0, Mirror.values().length).mapToObj(String::valueOf).collect(Collectors.toList()));
            default:
                return Collections.emptyList();
        }
    }
}
