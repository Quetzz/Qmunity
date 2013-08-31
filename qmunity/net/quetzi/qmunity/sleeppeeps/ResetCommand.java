package net.quetzi.qmunity.sleeppeeps;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;

public class ResetCommand implements ICommand {
    private List<String> aliases;

    public ResetCommand() {
        aliases = new ArrayList<String>();
        aliases.add("plreset");
        aliases.add("plr");
    }

    @Override
    public String getCommandName() {
        return "playerreset";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return "playerreset <player>";
    }
    
    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring) {
        if (astring.length == 0) {
            icommandsender.sendChatToPlayer("Usage: "
                    + this.getCommandUsage(icommandsender));
            return;
        }
        MinecraftServer server = DedicatedServer.getServer();
        if (this.isPlayerAvailable(astring[0])) {
            icommandsender.sendChatToPlayer("Player data is available!");
            EntityPlayerMP playerEntity;
            boolean online;
            if(server.getConfigurationManager().getPlayerListAsString().contains(astring[0])) {
                playerEntity = server
                        .getConfigurationManager()
                        .getPlayerForUsername(astring[0]);
                online = true;                
            }
            else {
                playerEntity = server
                        .getConfigurationManager()
                        .createPlayerForUser(astring[0]);
                online = false;               
            }
            /*
             try {
                playerEntity = server
                        .getConfigurationManager()
                        .getPlayerForUsername(astring[0]);
                online = true;
            } catch (Exception e) {
                playerEntity = server
                        .getConfigurationManager()
                        .createPlayerForUser(astring[0]);
                online = false;
            }
*/
            playerEntity.setWorld(server
                    .worldServerForDimension(0));
            if (playerEntity.getBedLocation() != null) {
                if (online) {
                    playerEntity.setWorld(server
                            .worldServerForDimension(0));
                    playerEntity.setPositionAndUpdate(
                            playerEntity.getBedLocation().posX,
                            playerEntity.getBedLocation().posY,
                            playerEntity.getBedLocation().posZ);
                    playerEntity
                            .sendChatToPlayer("Teleporting you to your bed...");
                } else {
                    playerEntity.setWorld(server
                            .worldServerForDimension(0));
                    playerEntity.setLocationAndAngles(
                            playerEntity.getBedLocation().posX,
                            playerEntity.getBedLocation().posY,
                            playerEntity.getBedLocation().posZ,0,0);
                    server.getConfigurationManager()
                            .playerLoggedOut(playerEntity);
                }
                SleepPeeps.splog.info("Bed location found, moving player to : "
                        + (double) playerEntity.getBedLocation().posX + ","
                        + (double) playerEntity.getBedLocation().posY + ","
                        + (double) playerEntity.getBedLocation().posZ);
            } else {
                if (online) {
                    playerEntity.setWorld(server
                            .worldServerForDimension(0));
                    playerEntity.setPositionAndUpdate(server.worldServers[0].getSpawnPoint().posX,
                            server.worldServers[0]
                                    .getSpawnPoint().posY, server.worldServers[0]
                                    .getSpawnPoint().posZ);
                    playerEntity
                            .sendChatToPlayer("Can't find your bed, teleporting you to overworld spawn...");
                } else {
                    playerEntity.setWorld(server
                            .worldServerForDimension(0));
                    playerEntity.setLocationAndAngles(DedicatedServer
                            .getServer().worldServers[0].getSpawnPoint().posX,
                            server.worldServers[0]
                                    .getSpawnPoint().posY, DedicatedServer
                                    .getServer().worldServers[0]
                                    .getSpawnPoint().posZ,0,0);
                    server.getConfigurationManager()
                            .playerLoggedOut(playerEntity);
                }
                SleepPeeps.splog
                        .info("No bed found, moving player to overworld spawn: "
                                + (double) server.worldServers[0]
                                        .getSpawnPoint().posX
                                + ","
                                + (double) server.worldServers[0]
                                        .getSpawnPoint().posY
                                + ","
                                + (double) server.worldServers[0]
                                        .getSpawnPoint().posZ);
            }
        } else {
            icommandsender.sendChatToPlayer("Can't find player");
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
        return true;
    }

    @Override
    public List<?> addTabCompletionOptions(ICommandSender icommandsender,
            String[] astring) {
        return null;
    }

    private boolean isPlayerAvailable(String astring) {
        String availablePlayers[] = DedicatedServer.getServer()
                .getConfigurationManager().getAvailablePlayerDat();
        for (int i = 0; i < availablePlayers.length; i++) {
            if (astring.toLowerCase().equals(availablePlayers[i].toLowerCase())) {
                SleepPeeps.splog.info("Player data found for " + astring);
                return true;
            }
        }
        SleepPeeps.splog.info("No player data found for " + astring);
        return false;
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i) {
        return false;
    }

    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}