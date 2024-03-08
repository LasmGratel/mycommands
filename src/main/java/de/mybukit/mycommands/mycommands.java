package de.mybukkit.mycommands;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import de.mybukkit.mycommands.commands.CommandBack;
import de.mybukkit.mycommands.commands.CommandDay;
import de.mybukkit.mycommands.commands.CommandDelHome;
import de.mybukkit.mycommands.commands.CommandDelWarp;
import de.mybukkit.mycommands.commands.CommandFly;
import de.mybukkit.mycommands.commands.CommandGamemode;
import de.mybukkit.mycommands.commands.CommandGod;
import de.mybukkit.mycommands.commands.CommandHeal;
import de.mybukkit.mycommands.commands.CommandHome;
import de.mybukkit.mycommands.commands.CommandNight;
import de.mybukkit.mycommands.commands.CommandRain;
import de.mybukkit.mycommands.commands.CommandRepair;
import de.mybukkit.mycommands.commands.CommandSetBlock;
import de.mybukkit.mycommands.commands.CommandSetHome;
import de.mybukkit.mycommands.commands.CommandSetSpawn;
import de.mybukkit.mycommands.commands.CommandSetWarp;
import de.mybukkit.mycommands.commands.CommandSetpos1;
import de.mybukkit.mycommands.commands.CommandSetpos2;
import de.mybukkit.mycommands.commands.CommandSpawn;
import de.mybukkit.mycommands.commands.CommandSun;
import de.mybukkit.mycommands.commands.CommandTpAccept;
import de.mybukkit.mycommands.commands.CommandTpDeny;
import de.mybukkit.mycommands.commands.CommandTpa;
import de.mybukkit.mycommands.commands.CommandWarp;
import de.mybukkit.mycommands.commands.CommandWorkbench;
import de.mybukkit.mycommands.helper.HomePoint;
import de.mybukkit.mycommands.helper.SimpleScheduler;
import de.mybukkit.mycommands.helper.WarpPoint;
import de.mybukkit.mycommands.helper.WorldDataHandler;
import java.io.File;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(modid = "mycommands", name = "MyCommands", version = "1710-03", acceptableRemoteVersions = "*")
public class mycommands {

    public static final Logger log = LogManager.getLogger("mycommands");
    public static MinecraftServer server;
    @Instance("mycommands")
    public static mycommands instance;
    public static String configPath;
    private int maxHomes;
    private File mainConfigFile;
    private Configuration config;
    private boolean back;
    private boolean delwarp;
    private boolean delhome;
    private boolean god;
    private boolean fly;
    private boolean home;
    private boolean sethome;
    private boolean setwarp;
    private boolean warp;
    private boolean setspawn;
    private boolean spawn;
    private boolean night;
    private boolean rain;
    private boolean sun;
    private boolean heal;
    private boolean repair;
    private boolean day;
    private boolean tpaccept;
    private boolean tpdeny;
    private boolean tpa;
    private boolean gm;
    private boolean wb;
    private boolean sb;
    private boolean pos;

    public static File getSaveDir() {
        File f = WorldDataHandler.dir();

        if (f.exists()) {
            f.mkdirs();
        }
        return f;
    }

    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        server = event.getServer();
        ICommandManager command = server.getCommandManager();
        ServerCommandManager serverCommand = (ServerCommandManager) command;
        serverCommand.registerCommand(new CommandBack(this.back));
        serverCommand.registerCommand(new CommandDay(this.day));
        serverCommand.registerCommand(new CommandDelHome(this.delhome));
        serverCommand.registerCommand(new CommandGod(this.god));
        serverCommand.registerCommand(new CommandFly(this.fly));
        serverCommand.registerCommand(new CommandHome(this.home));
        serverCommand.registerCommand(new CommandSetHome(this.sethome, maxHomes));
        serverCommand.registerCommand(new CommandSetWarp(this.setwarp));
        serverCommand.registerCommand(new CommandWarp(this.warp));
        serverCommand.registerCommand(new CommandDelWarp(this.delwarp));

        serverCommand.registerCommand(new CommandSetSpawn(this.setspawn));
        serverCommand.registerCommand(new CommandSpawn(this.spawn));

        serverCommand.registerCommand(new CommandNight(this.night));
        serverCommand.registerCommand(new CommandRain(this.rain));
        serverCommand.registerCommand(new CommandSun(this.sun));
        serverCommand.registerCommand(new CommandHeal(this.heal));
        serverCommand.registerCommand(new CommandRepair(this.repair));
        serverCommand.registerCommand(new CommandSetBlock(this.sb));
        serverCommand.registerCommand(new CommandSetpos1(this.pos));
        serverCommand.registerCommand(new CommandSetpos2(this.pos));
        serverCommand.registerCommand(new CommandWorkbench(this.wb));
        serverCommand.registerCommand(new CommandGamemode(this.gm));
        serverCommand.registerCommand(new CommandTpAccept(this.tpaccept));
        serverCommand.registerCommand(new CommandTpDeny(this.tpdeny));
        serverCommand.registerCommand(new CommandTpa(this.tpa));

        CommandFly.loadConfig();

        CommandGod.loadConfig();

        HomePoint.loadAll();
        WarpPoint.loadAll();
    }

    @EventHandler
    public void serverStop(FMLServerStoppingEvent event) {
        CommandFly.saveConfig();
        CommandGod.saveConfig();
        WarpPoint.saveAll();
        HomePoint.saveAll();
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
    }

    @EventHandler
    public void init(FMLPreInitializationEvent event) {
        configPath = event.getModConfigurationDirectory() + "/mycommands/";
        this.mainConfigFile = new File(configPath + "mycommands.cfg");
        cinit(this.mainConfigFile);
        MinecraftForge.EVENT_BUS.register(new WorldDataHandler());
        MinecraftForge.EVENT_BUS.register(new SimpleScheduler());
        FMLCommonHandler.instance().bus().register(new SimpleScheduler());
    }

    private void cinit(File configFile) {
        this.config = new Configuration(configFile);
        String catcom = "Commands";
        String plhome = "Homes";
        this.config.addCustomCategoryComment(catcom, "true = Op Only | false for All");

        try {
            this.config.load();

            this.back = this.config.get(catcom, "Back", false).getBoolean();
            this.day = this.config.get(catcom, "Day", true).getBoolean();
            this.delhome = this.config.get(catcom, "DelHome", false).getBoolean();
            this.delwarp = this.config.get(catcom, "DelWarp", true).getBoolean();
            this.fly = this.config.get(catcom, "Fly", true).getBoolean();
            this.gm = this.config.get(catcom, "Gamemode", true).getBoolean();
            this.god = this.config.get(catcom, "God", true).getBoolean();
            this.heal = this.config.get(catcom, "Heal", true).getBoolean();
            this.home = this.config.get(catcom, "Home", false).getBoolean();
            this.night = this.config.get(catcom, "Night", true).getBoolean();
            this.rain = this.config.get(catcom, "Rain", true).getBoolean();
            this.repair = this.config.get(catcom, "Repair", true).getBoolean();
            this.sethome = this.config.get(catcom, "SetHome", false).getBoolean();
            this.setspawn = this.config.get(catcom, "SetSpawn", true).getBoolean();
            this.setwarp = this.config.get(catcom, "SetWarp", true).getBoolean();
            this.spawn = this.config.get(catcom, "Spawn", false).getBoolean();
            this.sb = this.config.get(catcom, "SetBlock", true).getBoolean();
            this.pos = this.config.get(catcom, "SetPos", true).getBoolean();

            this.sun = this.config.get(catcom, "Sun", true).getBoolean();
            this.tpa = this.config.get(catcom, "Tpa", false).getBoolean();
            this.tpaccept = this.config.get(catcom, "TpAccept", false).getBoolean();
            this.tpdeny = this.config.get(catcom, "TpDeny", false).getBoolean();
            this.wb = this.config.get(catcom, "Workbench", true).getBoolean();
            this.warp = this.config.get(catcom, "Warp", false).getBoolean();

            this.config.addCustomCategoryComment(plhome, "max Player Homes for Members");
            this.maxHomes = this.config.get(plhome, "max", 5).getInt();


        } catch (Exception e) {

            FMLLog.log(Level.ERROR, "config fehler", e);
        } finally {

            if (this.config.hasChanged()) {
                this.config.save();
            }
        }
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}
