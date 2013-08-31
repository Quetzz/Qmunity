package net.quetzi.qmunity.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.quetzi.qmunity.references.References;

public class VersionCommand implements ICommand {
    private List<String> aliases;

    public VersionCommand() {
        aliases = new ArrayList<String>();
        aliases.add("qvers");
    }
    @Override
    public int compareTo(Object arg0) {
        return 0;
    }

    @Override
    public String getCommandName() {
        return "qversion";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return "Usage: /qversion";
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }
    
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring) {
        icommandsender.sendChatToPlayer("Qmunity utilities version: " + References.VERSION);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender icommandsender,
            String[] astring) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i) {
        return false;
    }

}
