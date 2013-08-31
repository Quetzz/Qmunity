package net.quetzi.qmunity.sleeppeeps;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;

public class EventHookContainerClass {
    private int sleepingPlayers;

    @SuppressWarnings("unchecked")
    @ForgeSubscribe
    public void onSleepyTime(PlayerSleepInBedEvent event) {
        long worldTime = MinecraftServer.getServer().worldServers[0]
                .getWorldTime();
        if (worldTime > 12540 && worldTime < 23999) // Is it night?
        {
            sleepingPlayers = 1;
            String alert = EnumChatFormatting.GOLD + "Player "
                    + EnumChatFormatting.WHITE + event.entityPlayer.username
                    + EnumChatFormatting.GOLD + " is now sleeping.";
            // DedicatedServer.getServer().getConfigurationManager().sendChatMsg(alert);//method
            // 1
            // event.entityPlayer.getBedLocation().set(event.entityPlayer.chunkCoordX,
            // event.entityPlayer.chunkCoordY, event.entityPlayer.chunkCoordZ);
            // event.entityPlayer.sendChatToPlayer("Spawn location set.");
            // ArrayList playerList = (ArrayList)
            // DedicatedServer.getServer().getConfigurationManager().playerEntityList;
            ArrayList<EntityPlayerMP> playerList = (ArrayList<EntityPlayerMP>) MinecraftServer.getServer().worldServerForDimension(event.entityPlayer.dimension).playerEntities;
            SleepPeeps.splog.info(alert + " " + sleepingPlayers + "/" + playerList.size());
            Iterator<?> iterator = playerList.iterator();
            // System.out.println("[Sleep Peeps] Total Players: " +
            // playerList.size());
            while (iterator.hasNext()) {
                EntityPlayerMP player = (EntityPlayerMP) iterator.next();
                player.sendChatToPlayer(alert);
                // player.addChatMessage("3 " + alert); //method 3
                if (player.isPlayerSleeping()) {
                    sleepingPlayers++;
                }
            }

            int playerCount = playerList.size();
            int percAsleep = sleepingPlayers * 100 / playerCount;
            SleepPeeps.splog.info("Sleeping players: " + sleepingPlayers + "/"
                    + playerCount + " " + percAsleep + "%");
            if (percAsleep >= SleepPeeps.perc) {
                setTime(0);
                sleepingPlayers = 0;
            }
        } else {
            // System.out.println("[Sleep Peeps] " + event.entityPlayer.username
            // + " tried to sleep during the day");
        }
    }

    protected void setTime(int ticks) {
        MinecraftServer.getServer().worldServers[0].setWorldTime(ticks);
    }
}