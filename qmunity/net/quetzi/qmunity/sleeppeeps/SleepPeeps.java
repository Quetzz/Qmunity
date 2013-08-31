package net.quetzi.qmunity.sleeppeeps;

import java.util.logging.Logger;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "SleepPeeps", name = "Sleep Peeps", version = "152.0.0.7")
@NetworkMod(clientSideRequired = false, serverSideRequired = true)
public class SleepPeeps {
    public static int perc;
    public static Logger splog = Logger.getLogger("SleepPeeps");
    
    @Mod.Init
    //@SideOnly(Side.SERVER)
    public void Init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHookContainerClass());
    }

    @Mod.PreInit
    //@SideOnly(Side.SERVER)
    public void PreInit(FMLPreInitializationEvent event) {
        splog.setParent(FMLLog.getLogger());
        splog.info("Loading configuration");
        Configuration config = new Configuration(
                event.getSuggestedConfigurationFile());
        config.load();
        perc = config.get("settings", "SleeperPerc", 50).getInt();
        config.save();
    }

    @Mod.PostInit
    //@SideOnly(Side.SERVER)
    public void PostInit(FMLPostInitializationEvent event) {
    }

    @Mod.ServerStarting
    //@SideOnly(Side.SERVER)
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new ResetCommand());
    }
}