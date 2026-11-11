package com.kant1.kant1smod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kant1.kant1smod.commands.GoForward;
import com.kant1.kant1smod.commands.OpenAndReadChest;
import com.kant1.kant1smod.commands.TellPos;
import com.kant1.kant1smod.tickevent.GlobalTickEvent;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = kant1smod.MODID, version = Tags.VERSION, name = "Kant1's Mod", acceptedMinecraftVersions = "[1.7.10]")
public class kant1smod {

    public static final String MODID = "Kant1's Mod";
    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "com.kant1.kant1smod.ClientProxy", serverSide = "com.kant1.kant1smod.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        FMLCommonHandler.instance()
            .bus()
            .register(new GlobalTickEvent());
    }

    @Mod.EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
        event.registerServerCommand(new TellPos());
        event.registerServerCommand(new GoForward());
        event.registerServerCommand((new OpenAndReadChest()));
        // event.registerServerCommand(new Gallium());

    }
}
